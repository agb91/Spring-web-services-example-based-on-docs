package hello;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "countries", path = "countries")
public interface CountryRepository extends PagingAndSortingRepository<CountryEntity, Long> {

	CountryEntity findByName(@Param("name") String name);
	CountryEntity findByCapital(@Param("name") String capital);
	CountryEntity findById(@Param("name") long id);
	
}
