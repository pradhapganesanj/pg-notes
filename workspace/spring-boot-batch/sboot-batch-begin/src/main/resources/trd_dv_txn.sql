/
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE trd_dv_txn';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

/
CREATE TABLE trd_dv_txn (
	client_code VARCHAR2(50), 
	client_fund_code VARCHAR2(50), 
	trade_id VARCHAR2(50),
	ext_ref VARCHAR2(20),
	start_dt TIMESTAMP(0),
	end_dt TIMESTAMP(0),
	trade_dt TIMESTAMP(0),
	maturity_dt TIMESTAMP(0),
	pricing_currency VARCHAR2(10), 
	trade_status VARCHAR2(50)
	)
/
select  *from TRD_DV_TXN