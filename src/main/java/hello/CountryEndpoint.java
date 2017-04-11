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

	private CountryRepository countryRepository;

	@Autowired
	public CountryEndpoint(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
	@ResponsePayload
	public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
		GetCountryResponse response = new GetCountryResponse();
		if(request.getId()!=null) //id is the preferred way..
		{
			response.setCountry(countryRepository.findCountryById(request.getId()));
		}
		
		if(response.getCountry() == null) //name is the alternative
		{
			if(request.getName()!=null)
			{
				response.setCountry(countryRepository.findCountryByName(request.getName()));
			}
		}
		
		if(response.getCountry() == null) // return a default instead (never a null)
		{
			Country defaultCoutry = new Country();
			defaultCoutry.setId("none");
			defaultCoutry.setName("null country");
			defaultCoutry.setCapital("none");
			defaultCoutry.setCurrency(Currency.NONE);
			defaultCoutry.setPopulation(0);
			response.setCountry(defaultCoutry);
		}

		return response;
	}
}
