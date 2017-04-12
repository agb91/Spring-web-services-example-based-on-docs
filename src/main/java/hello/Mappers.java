package hello;

import mynamespace.Country;
import mynamespace.Currency;

public class Mappers {

	public static Country country( CountryEntity ce )
	{
		if(ce==null)
		{
			return new Country();
		}
		assert(ce!=null);
		Country c = new Country();
		c.setCapital( ce.getCapital() );
		c.setName( ce.getName() );
		c.setPopulation( ce.getPopulation() );
		c.setCurrency( Currency.fromValue( ce.getCurrency() ) );
		return c;
	}
	
}
