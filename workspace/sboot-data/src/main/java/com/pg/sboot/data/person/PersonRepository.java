package com.pg.sboot.data.person;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public interface PersonRepository extends CrudRepository<Person, Long>{

	List<Person> findByLName(String ln);
	
	/*List<Person> findByFName(String fn);
	
	@Query("select c from Person p where p.sex = :sex")
	List<Person> findBySex(@Param("sex") String sex );
	*/
}
