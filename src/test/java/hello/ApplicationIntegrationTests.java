/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hello;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import mynamespace.GetCountryRequest;
import mynamespace.GetCountryResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTests {

    private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

    @LocalServerPort
    private int port = 0;

    @Before
    public void init() throws Exception {
        marshaller.setPackagesToScan(ClassUtils.getPackageName(GetCountryRequest.class));
        marshaller.afterPropertiesSet();
    }
    
    @Test
    public void testURL() {
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        GetCountryRequest request = new GetCountryRequest();
        request.setName("Spain");
        
        String url = "http://localhost:" + port + "/ws";
        
        assertThat( url ).isNotNull();
        assertEquals( url , "http://localhost:" + port + "/ws" );
    }

    @Test
    public void testSendAndReceive() {
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        GetCountryRequest request = new GetCountryRequest();
        request.setName("Spain");

        assertThat(ws.marshalSendAndReceive("http://localhost:"
                + port + "/ws", request)).isNotNull();
    }
    

    @Test
    public void testResultName() {
    	WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        GetCountryRequest request = new GetCountryRequest();
        request.setName("Spain");
     
    	GetCountryResponse response = (GetCountryResponse) ws
    			.marshalSendAndReceive("http://localhost:"
                + port + "/ws",
    					request,
    					new SoapActionCallback("http://localhost:"
    			                + port + "/ws"));
        assertEquals( response.getCountry().getCapital() , "Madrid" );
    
    }
    
    @Test
    public void testResultCapital() {
    	WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        GetCountryRequest request = new GetCountryRequest();
        request.setCapital("Madrid");
     
    	GetCountryResponse response = (GetCountryResponse) ws
    			.marshalSendAndReceive("http://localhost:"
                + port + "/ws",
    					request,
    					new SoapActionCallback("http://localhost:"
    			                + port + "/ws"));
    	
        assertEquals( response.getCountry().getCapital() , "Madrid" );
        assertEquals( response.getCountry().getName() , "Spain" );
    
    }
    
    @Test
    public void testResultBoth() {
    	WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        GetCountryRequest request = new GetCountryRequest();
        request.setCapital("Madrid");
        request.setName("Spain");
        
    	GetCountryResponse response = (GetCountryResponse) ws
    			.marshalSendAndReceive("http://localhost:"
                + port + "/ws",
    					request,
    					new SoapActionCallback("http://localhost:"
    			                + port + "/ws"));
    	
        assertEquals( response.getCountry().getCapital() , "Madrid" );
    
    }
    
    @Test
    public void testResultKO() {
    	WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        GetCountryRequest request = new GetCountryRequest();
        request.setCapital("NS");
        request.setName("nonsense");
        
    	GetCountryResponse response = (GetCountryResponse) ws
    			.marshalSendAndReceive("http://localhost:"
                + port + "/ws",
    					request,
    					new SoapActionCallback("http://localhost:"
    			                + port + "/ws"));
    	
        assertEquals( response.getCountry().getCapital() , null );
    
    }
    
    @Test
    public void testResultWrongNameButRightCapital() {
    	WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        GetCountryRequest request = new GetCountryRequest();
        request.setCapital("Rome");
        request.setName("nonsense");
        
    	GetCountryResponse response = (GetCountryResponse) ws
    			.marshalSendAndReceive("http://localhost:"
                + port + "/ws",
    					request,
    					new SoapActionCallback("http://localhost:"
    			                + port + "/ws"));
    	
        assertEquals( response.getCountry().getName() , "Italy" );
    
    }
    
    
    
    
}