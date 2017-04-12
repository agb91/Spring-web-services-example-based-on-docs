package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
	
	private CountryRepository countryRepository;

	@Autowired
	public CountryService(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}
	
	public void saveCountry(CountryEntity c)
	{
		countryRepository.save(c);
	}
	
	public void saveCountries(List<CountryEntity> cs)
	{
		for( int i = 0 ; i < cs.size() ; i++ )
		{
			countryRepository.save( cs.get( i ) );
		}
	}

	public CountryEntity findByCapital(String capital) {
		return countryRepository.findByCapital( capital );
	}
	
	public CountryEntity findByName(String name) {
		return countryRepository.findByName( name );
	}
	
}
