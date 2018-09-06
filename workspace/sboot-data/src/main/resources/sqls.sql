/
SELECT * FROM tmp_person;
/
select add_months(TRUNC(sysdate),-12*36) from dual
/
insert into tmp_person (f_name, m_name, l_name, dob, sex) values('g','','s',add_months(TRUNC(sysdate),-12*65),'M')
/
CREATE TABLE tmp_person (
    p_id NUMBER,
    f_name VARCHAR2(50) NOT NULL,
    m_name VARCHAR2(50),
    l_name VARCHAR2(50),
    dob TIMESTAMP(0),
    sex CHAR
)
/
CREATE SEQUENCE tmp_person_seq 
START WITH 1
INCREMENT BY 1
/
CREATE OR REPLACE TRIGGER tmp_person_trig
BEFORE INSERT ON tmp_person FOR EACH ROW
BEGIN
    SELECT tmp_person_seq.NEXTVAL
    INTO :new.p_id
    FROM dual;
END;
/