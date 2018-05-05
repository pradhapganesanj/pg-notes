package com.pg.sboot.data.person;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

@EnableCaching
@SpringBootApplication
public class PersonApp{

	public static void main(String...args) {
		SpringApplication.run(PersonApp.class, args);
	}
	
	/*
	@Override
	public void run(String... args) throws Exception {
		
		for(Person p : personService.findAll() ){
			System.out.println("P " + p.toString());
		}
		
	} */
	
	@Autowired
	DataSource ds;
	
	@Autowired
	PersonService personService;
	
	/*
	HazelcastInstance hazelcastInstance() {
		HazelcastInstance instance = null;
		try {
			ClientConfig config = new ClientConfig();
			config.getGroupConfig().setName("dev").setPassword("dev");
			config.getNetworkConfig().addAddress("localhost:5701");
			config.setInstanceName("prad-ins1");
			instance = HazelcastClient.newHazelcastClient(config);
		} catch (Exception e) {
			System.out.println("hazelcastInstance exception " + e.getMessage());
		}
		return instance;
	}
	*/
	
	/*
    @Bean
    HazelcastInstance hazelcastInstance() {
        // for client HazelcastInstance LocalMapStatistics will not available
        return HazelcastClient.newHazelcastClient();
    }	*/

	/*
    @Bean
    CacheManager cacheManager() {
        return new HazelcastCacheManager(hazelcastInstance());
    }
    
    */
}
