package com.pg.sboot.batch.csvora.data;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TradeDevidentTxn {

	private String clientCode;	
	private String clientFundCode;	
	private String tradeId;	
	private String extRef;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date startDt;

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date endDt;	

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date tradeDt;	

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date maturityDt;	
	
	private String pricingCurrency;	
	
	private String tradeStatus;
	
	
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getClientFundCode() {
		return clientFundCode;
	}
	public void setClientFundCode(String clientFundCode) {
		this.clientFundCode = clientFundCode;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getExtRef() {
		return extRef;
	}
	public void setExtRef(String extRef) {
		this.extRef = extRef;
	}
	public Date getStartDt() {
		return startDt;
	}
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	public Date getTradeDt() {
		return tradeDt;
	}
	public void setTradeDt(Date tradeDt) {
		this.tradeDt = tradeDt;
	}
	public Date getMaturityDt() {
		return maturityDt;
	}
	public void setMaturityDt(Date maturityDt) {
		this.maturityDt = maturityDt;
	}
	
	public String getPricingCurrency() {
		return pricingCurrency;
	}
	public void setPricingCurrency(String pricingCurrency) {
		this.pricingCurrency = pricingCurrency;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	

	
	
}
