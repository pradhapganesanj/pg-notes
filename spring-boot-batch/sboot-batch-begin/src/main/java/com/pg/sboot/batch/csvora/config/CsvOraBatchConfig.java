package com.pg.sboot.batch.csvora.config;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.citi.divtax.feed.batch.excel.RowMapper;
import com.citi.divtax.feed.batch.excel.mapping.BeanWrapperRowMapper;
import com.citi.divtax.feed.batch.excel.poi.PoiItemReader;
import com.pg.sboot.batch.csvora.data.TradeDevidentTxn;
import com.pg.sboot.batch.csvora.service.ExcelItemReader;
import com.pg.sboot.batch.csvora.service.TrdDvTxnJobCompletionListener;

@Configuration
@EnableBatchProcessing
public class CsvOraBatchConfig {


	@Bean
    public BatchConfigurer configurer(DataSource dataSource){
      return new DefaultBatchConfigurer(dataSource);
    }
	
	@Bean(name="jobRepository")
	public JobRepository createJobRepository(DataSource dataSource,PlatformTransactionManager transactionManager) throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(dataSource);
		factory.setTransactionManager(transactionManager);
		factory.setDatabaseType("oracle");
		factory.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
		return factory.getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher(DataSource dataSource,PlatformTransactionManager transactionManager) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(createJobRepository(dataSource,transactionManager));
		return jobLauncher;
	}

	@Autowired
	public JobBuilderFactory jobBuildFactory;

	@Autowired
	public StepBuilderFactory stepBuildFactory;
	
	@Bean
	public Job tradeDevideTxnJob(TrdDvTxnJobCompletionListener listener, Step step1) {
		return jobBuildFactory.get("tradeDevideTxnJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				//.start(step1(null,null))
				.start(step1)
				.build();
	}

	@Bean
	@JobScope
	//public Step step1(JdbcBatchItemWriter<TradeDevidentTxn> writer,@Value("#{jobParameters['param1']}") String param){
	public Step step1(JdbcBatchItemWriter<TradeDevidentTxn> writer){		
//		System.err.println("step1 params... "+param);
		return stepBuildFactory.get("step1")
				.<TradeDevidentTxn, TradeDevidentTxn> chunk(100)
				//.reader(flatFileItemReader(""))
				.reader(readerXlsx())
				//.reader(readerXls())
				.processor(itemProcessor())
				.writer(writer)
				.build();
	}
	
	@Bean
	@StepScope
	public FlatFileItemReader<TradeDevidentTxn> flatFileItemReader(@Value("#{jobParameters['param1']}") String param){
		return new FlatFileItemReaderBuilder<TradeDevidentTxn>()
						.name("tradeDevidentTxnReader")
						.resource(new ClassPathResource("TradeDevidentTxn.csv"))
						.delimited()
						.names(new String[]{"clientFundCode","clientCode","tradeId","extRef","startDt","endDt","tradeDt","maturityDt","tradeStatus","pricingCurrency"})
						.fieldSetMapper(new BeanWrapperFieldSetMapper<TradeDevidentTxn>(){{
							System.err.println("flatFileItemReader params... "+param);
							setTargetType(TradeDevidentTxn.class);
						}})
						.build();
		
	}
	@Bean
	public ItemReader<TradeDevidentTxn> readerXls(){
		
		ExcelItemReader reader = new ExcelItemReader();
				reader.setResource(new ClassPathResource("TradeDevidentTxn.xls"));
				reader.setLinesToSkip(1);
				try {
					Map<String, String> row = (HashMap<String, String>)reader.read();
					if(null == row) return null;
					row.keySet().forEach( e-> System.err.println(e));
				} catch (Exception e) {
					e.printStackTrace();
				}	
		return reader;
	}
	
	@Bean
	public ItemReader<TradeDevidentTxn> readerXlsx(){
		
		PoiItemReader<TradeDevidentTxn> reader = new PoiItemReader<>();
				reader.setResource(new ClassPathResource("TradeDevidentTxn50.xlsx"));
				reader.setLinesToSkip(1);
				reader.setRowMapper(excelRowMapper());
				reader.setRowMapper((rs)->{
					String[] strarr = rs.getCurrentRow();
					Arrays.asList(strarr).forEach( e -> System.err.println(e)); 
					TradeDevidentTxn t = new TradeDevidentTxn();
					t.setClientCode(strarr[0]);
					t.setClientFundCode(strarr[1]);
					t.setTradeId(strarr[2]);
					t.setExtRef(strarr[3]);
					//t.setStartDt(new SimpleDateFormat("dd-MMM-yyyy").parse(strarr[4]));
					//t.setEndDt(new SimpleDateFormat("dd-MMM-yyyy").parse(strarr[5]));
					//t.setTradeDt(new SimpleDateFormat("dd-MMM-yyyy").parse(strarr[6]));
					//t.setMaturityDt(new SimpleDateFormat("dd-MMM-yyyy").parse(strarr[7]));
					t.setTradeStatus(strarr[8]);
					t.setPricingCurrency(strarr[9]);
					return t;
				});
	
		return reader;
	}
	
    private RowMapper<TradeDevidentTxn> excelRowMapper() {
        BeanWrapperRowMapper<TradeDevidentTxn> rowMapper = new BeanWrapperRowMapper<>();
        rowMapper.setTargetType(TradeDevidentTxn.class);
        return rowMapper;
    }
	
	private DefaultLineMapper<TradeDevidentTxn> lineMapper(){
		DefaultLineMapper<TradeDevidentTxn> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(lineTokenizer());
		lineMapper.setFieldSetMapper(fieldSetMapper());	
		return lineMapper;
	}
	
	private LineTokenizer lineTokenizer() {
		DelimitedLineTokenizer feedLineTokenizer = new DelimitedLineTokenizer();
		feedLineTokenizer.setNames(new String[]{"clientFundCode","clientCode","tradeId","extRef","startDt","endDt","tradeDt","maturityDt","tradeStatus","pricingCurrency"});
		return feedLineTokenizer;
	}
	private FieldSetMapper<TradeDevidentTxn> fieldSetMapper() {
		return new BeanWrapperFieldSetMapper<TradeDevidentTxn>(){{
			setTargetType(TradeDevidentTxn.class);
		}};
	}	
	public ItemReader itemReader(){
		ItemReader iReader = () -> {
			Scanner scan = new Scanner(new File("TradeDevidentTxn.csv"));
			scan.useDelimiter(",");
			while(scan.hasNext()){
				System.out.println(scan.next());
			}
			return "";
		};
		return iReader;
	}
	

	@Bean
	public ItemProcessor itemProcessor() {
		ItemProcessor<TradeDevidentTxn, TradeDevidentTxn> iProcess = (txnP1) -> {
			TradeDevidentTxn newTxn = new TradeDevidentTxn();
			newTxn.setClientCode(txnP1.getClientCode());
			newTxn.setClientFundCode(txnP1.getClientFundCode());
			newTxn.setEndDt(txnP1.getEndDt());
			newTxn.setExtRef(txnP1.getExtRef());
			newTxn.setMaturityDt(txnP1.getMaturityDt());
			newTxn.setPricingCurrency(txnP1.getPricingCurrency());
			newTxn.setStartDt(txnP1.getStartDt());
			newTxn.setTradeDt(txnP1.getTradeDt());
			newTxn.setTradeId(txnP1.getTradeId());
			newTxn.setTradeStatus(txnP1.getTradeStatus());
			return newTxn;
		};
		return iProcess;
	}

	@Bean
	public JdbcBatchItemWriter<TradeDevidentTxn> writer(DataSource ds){
		return new JdbcBatchItemWriterBuilder<TradeDevidentTxn>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO trd_dv_txn (client_code, client_fund_code, trade_id, trade_status) VALUES (:clientCode, :clientFundCode, :tradeId, :tradeStatus)")
				.dataSource(ds)
				.build();
	}
}
