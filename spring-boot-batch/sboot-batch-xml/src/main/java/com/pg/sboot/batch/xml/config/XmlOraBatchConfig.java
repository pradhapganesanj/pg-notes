package com.pg.sboot.batch.xml.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.pg.sboot.batch.xml.bo.AccountCmmBo;

@Configuration
@EnableBatchProcessing
public class XmlOraBatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public Job xmlBatchJob() {
		return jobBuilderFactory
				.get("xmlBatchJob")
				.incrementer(new RunIdIncrementer())
				.flow(xmlBatchStep())
				.end()
				.build();
	}

	@Bean
	public Step xmlBatchStep() {
		return stepBuilderFactory
				.get("xmlBatchStep")
				.<AccountCmmBo, AccountCmmBo>chunk(10)
				.reader(reader())
				//.processor(processor())
				.writer(writer()).build();
	}

	@Bean
	public ItemReader<AccountCmmBo> reader() {
		StaxEventItemReader<AccountCmmBo> xmlFileReader = new StaxEventItemReader<>();
		xmlFileReader.setResource(new ClassPathResource("account-cmm.xml"));
		xmlFileReader.setFragmentRootElementName("Account");

		Jaxb2Marshaller actMarshaller = new Jaxb2Marshaller();
		actMarshaller.setClassesToBeBound(AccountCmmBo.class);
		
		xmlFileReader.setUnmarshaller(actMarshaller);

		return xmlFileReader;
	}

	/*
	@Bean
	public ItemProcessor<AccountCmmBo, AccountCmmBo> processor() {
		return new ItemProcessor() {
			@Override
			public Object process(Object item) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}*/

	@Bean
	public JdbcBatchItemWriter<AccountCmmBo> writer(){
		return new JdbcBatchItemWriterBuilder<AccountCmmBo>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO account_cmm (act_id, account_type, act_owner_cd, us_person_flg, firm_cd, prime_loc, mnemonic, ims_num, last_trade_dt) "
						+ "VALUES (:actId, :actType, :actOwnerCd, :usPersonFlg, :firmCd, :primeLoc, :mnemonic, :imsNum, :lastTradeDt)")
				.dataSource(dataSource)
				.build();
	}
}
