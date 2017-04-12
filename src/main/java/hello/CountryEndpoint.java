package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import mynamespace.Country;
import mynamespace.Currency;
import mynamespace.GetCountryRequest;
import mynamespace.GetCountryResponse;

@Endpoint
public class CountryEndpoint {
	private static final String NAMESPACE_URI = "myNameSpace";

	private CountryService countryService;

	@Autowired
	public CountryEndpoint(CountryService countryService) {
		this.countryService = countryService;
		putSomeFixture();
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
		GetCountryResponse response = new GetCountryResponse();
		if(request.getCapital() != null) //for this example capital is the preferred way..
		{
			Country c = new Country();
			c = Mappers.country( countryService.findByCapital( request.getCapital() ) );
			response.setCountry(c);
		}
		else
		{
			if (request.getName()!=null)
			{
				Country c = new Country();
				c = Mappers.country(countryService.findByName( request.getName() ));
				response.setCountry( c );
			}
		}
		
		//empty answer
		if(response.getCountry() == null)
		{
			System.err.print("--------------> WE ARE HERE");
			response.setCountry( new Country() );
		}
		return response;
	}
	
	private void putSomeFixture()
	{
		CountryEntity c1 = new CountryEntity();
		c1.setCapital("Rome");
		c1.setName("Italy");
		c1.setPopulation(60500000);
		c1.setCurrency( "Euro" );
		
		countryService.saveCountry(c1);
		
		CountryEntity c2 = new CountryEntity();
		c2.setCapital("Madrid");
		c2.setName("Spain");
		c2.setPopulation(45500000);
		c2.setCurrency( "Euro" );
		
		countryService.saveCountry(c2);
	}
	
	private void putOneFixture(CountryEntity c)
	{
		countryService.saveCountry(c);
	}
}
