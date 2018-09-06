package com.pg.sboot.batch.xml.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pg.sboot.batch.xml.bo.AccountCmmBo;

@SpringBootApplication (scanBasePackages={"com.pg.sboot.batch.xml"})
public class XmlOraApp {

	public static void main(String[] args) throws ParseException {
		SpringApplication.run(XmlOraApp.class, args);
		
		// pojoToXmlTest();
	}

	private static void pojoToXmlTest() throws ParseException {
		AccountCmmBo act = new AccountCmmBo();
		act.setActId("13706");
		act.setActType("13706");
		act.setActOwnerCd("13706");
		act.setFirmCd("01");
		act.setImsNum("03165891");
		act.setLastTradeDt(new SimpleDateFormat("mm-dd-yyyy").parse("01-01-2000"));
		act.setMnemonic("ST13400");
		act.setPrimeLoc("Tampa");
		act.setUsPersonFlg(false);
		
		marshallToXml(act);
	}

	public static void marshallToXml(Object object){
		
		
		try {
			
			JAXBContext jaxbContext =  JAXBContext.newInstance(object.getClass());
			
			Marshaller marshaller = jaxbContext.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			marshaller.marshal(object, System.out);
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		
	}
}
