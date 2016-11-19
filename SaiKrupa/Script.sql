DROP DATABASE SAIKRUPA_DB;
CREATE DATABASE SAIKRUPA_DB;
USE SAIKRUPA_DB;

CREATE TABLE EXPENSE_CATEGORY (
	EXP_CAT_CODE INT(5)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
	EXP_CAT_NAME VARCHAR(50) NOT NULL,
	EXP_CAT_DESC VARCHAR(100)

)AUTO_INCREMENT=1000;

CREATE TABLE PAYMENT_MODE (
	PAYMEMT_CODE INT(5)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
	PAYMEMT_CODE_NAME VARCHAR(50) NOT NULL
)AUTO_INCREMENT=1000;

CREATE TABLE CONTACT_PERSON_TYPE (
	TYPE_CODE INT(5)   NOT NULL PRIMARY KEY,
	TYPE_NAME VARCHAR(50) NOT NULL	

)AUTO_INCREMENT=1000;

CREATE TABLE VENDOR (
	CODE INT(5)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(50) NOT NULL,
	CONTACT_PRIMARY VARCHAR(15) NOT NULL,
	CONTACT_SECONDARY VARCHAR(15),
	INDEX ID_VENDOR(CODE)
	
)AUTO_INCREMENT=1000;

CREATE TABLE EMPLOYEE (
	CODE INT(5)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(50) NOT NULL,
	CONTACT_PRIMARY VARCHAR(15) NOT NULL,
	CONTACT_SECONDARY VARCHAR(15),
	ACTIVE INT(1) NOT NULL,
	JOINING_DATE TIMESTAMP NOT NULL,
	LAST_MODIFIED_BY VARCHAR(10),
	LAST_MODIFIED_DATE TIMESTAMP NOT NULL
)AUTO_INCREMENT=1000;

CREATE TABLE EMPLOYEE_SALARY (
	CODE INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	EMPLOYEE_CODE INT NOT NULL,
	EFFECTIVE_FROM TIMESTAMP NOT NULL,
	EFFECTIVE_TILL TIMESTAMP,
	SALARY DOUBLE(10,2)  NOT NULL,	
	ACTIVE_REVISION INT(1),
	LAST_MODIFIED_BY VARCHAR(10),
	LAST_MODIFIED_DATE TIMESTAMP NOT NULL,
	FOREIGN KEY (EMPLOYEE_CODE) REFERENCES EMPLOYEE(CODE) ON DELETE CASCADE
)AUTO_INCREMENT=1000;

CREATE TABLE CONTACT_PERSON (
	CODE INT(5)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(50) NOT NULL,
	CONTACT_PRIMARY VARCHAR(15) NOT NULL,
	CONTACT_SECONDARY VARCHAR(15),
	TYPE_CODE INT(5)  NOT NULL,
	VENDOR_CODE INT(5)  NOT NULL,
	LAST_MODIFIED_BY VARCHAR(10),
	FOREIGN KEY (VENDOR_CODE) REFERENCES VENDOR(CODE) ON DELETE CASCADE
)AUTO_INCREMENT=1000;


CREATE TABLE ADDRESS (
	ADDRESS_CODE INT(5)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ADDRESS_LINE1 VARCHAR(100) NOT NULL,
	ADDRESS_LINE2 VARCHAR(100),
	ADDRESS_LINE3 VARCHAR(100),
	ADDRESS_LANDMARK VARCHAR(30),
	CONTACT_PERSON_CODE INT(5)  NOT NULL,
	CREATED_BY VARCHAR(10),
	INDEX ID_CONTACT_PERSON(CONTACT_PERSON_CODE),
	FOREIGN KEY (CONTACT_PERSON_CODE) REFERENCES CONTACT_PERSON(CODE) ON DELETE CASCADE	

)AUTO_INCREMENT=1000;


CREATE TABLE INVESTOR (
	CODE INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(50) NOT NULL,
	CONTACT_PRIMARY VARCHAR(15) NOT NULL,
	CONTACT_SECONDARY VARCHAR(15)	
)AUTO_INCREMENT=1000;

CREATE TABLE INVESTMENT (
	CODE INT(5)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(50) NOT NULL,
	INVESTED_ON TIMESTAMP NOT NULL,
	AMOUNT DOUBLE(10,2)  NOT NULL,
	INVESTOR_CODE INT(5) NOT NULL,
	FOREIGN KEY (INVESTOR_CODE) REFERENCES INVESTOR(CODE) ON DELETE CASCADE
	
)AUTO_INCREMENT=1000;


CREATE TABLE EXPENSE (
	CODE INT(10)   NOT NULL AUTO_INCREMENT PRIMARY KEY,	
	EXPENSE_DATE TIMESTAMP NOT NULL,
	AMOUNT DOUBLE(10,2) NOT NULL,
	VENDOR_CODE INT(5) NOT NULL,
	INVESTOR_CODE INT(5) NOT NULL,
	CATEGORY_CODE INT(5) NOT NULL,
	NOTES VARCHAR(255),
	PAYMEMT_CODE INT(5) NOT NULL,
	CREATED_BY VARCHAR(10) NOT NULL,
	LAST_MODIFIED_BY VARCHAR(10) NOT NULL,
	CREATED_DATE TIMESTAMP NOT NULL,
	LAST_MODIFIED_DATE TIMESTAMP NOT NULL,
	FOREIGN KEY (INVESTOR_CODE) REFERENCES INVESTOR(CODE) ON DELETE CASCADE,
	FOREIGN KEY (VENDOR_CODE) REFERENCES VENDOR(CODE) ON DELETE CASCADE,
	FOREIGN KEY (CATEGORY_CODE) REFERENCES EXPENSE_CATEGORY(EXP_CAT_CODE) ON DELETE CASCADE,
	FOREIGN KEY (PAYMEMT_CODE) REFERENCES PAYMENT_MODE(PAYMEMT_CODE) ON DELETE CASCADE
	
)AUTO_INCREMENT=1000;



CREATE TABLE PRODUCT (
	CODE INT(5)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(50) NOT NULL,
	DESCRIPTION VARCHAR(30),
	SALEABLE INT(1),
	CREATED_BY VARCHAR(10)
)AUTO_INCREMENT=1000;


CREATE TABLE INVENTORY (
	CODE INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,	
	LAST_UPDATED_DATE TIMESTAMP NOT NULL,
	TOTAL_AVAILABLE_QUANTITY DOUBLE(10,2) NOT NULL,
	TOTAL_RESERVED_QUANTITY DOUBLE(10,2) NOT NULL,
	TOTAL_DAMAGED_QUANTITY DOUBLE(10,2) NOT NULL,
	PRODUCT_CODE INT(5)   NOT NULL,
	LAST_MODIFIED_BY VARCHAR(10),
	FOREIGN KEY (PRODUCT_CODE) REFERENCES PRODUCT(CODE) ON DELETE CASCADE
	
)AUTO_INCREMENT=1000;


CREATE TABLE INVENTORY_ENTRY (
	CODE INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	INVENTORY_CODE INT NOT NULL,	
	CREATED_DATE TIMESTAMP NOT NULL,	
	QUANTITY_ADDED DOUBLE(10,2) NOT NULL,
	QUANTITY_REDUCED DOUBLE(10,2) NOT NULL,	
	QUANTITY_DAMAGED DOUBLE(10,2) NOT NULL,
	QUANTITY_RESERVED DOUBLE(10,2) NOT NULL,
	LAST_MODIFIED_BY VARCHAR(10),
	FOREIGN KEY (INVENTORY_CODE) REFERENCES INVENTORY(CODE) ON DELETE CASCADE
)AUTO_INCREMENT=1000;

CREATE TABLE PRODUCT_PRICE_ROW (
	CODE INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,	
	LAST_UPDATED_DATE TIMESTAMP NOT NULL,
	BASEPRICE DOUBLE(10,2) NOT NULL,
	VALID_FROM DATE,
	VALID_TILL DATE,
	PRODUCT_CODE INT(5) NOT NULL,
	FOREIGN KEY (PRODUCT_CODE) REFERENCES PRODUCT(CODE) ON DELETE CASCADE
	
)AUTO_INCREMENT=1000;

CREATE TABLE CUSTOMER (
	CODE INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	NAME VARCHAR(50) NOT NULL,
	CONTACT_PRIMARY VARCHAR(15) NOT NULL,
	CONTACT_SECONDARY VARCHAR(15),
	LAST_MODIFIED_BY VARCHAR(10),
	INDEX ID_CUSTOMER(CODE)
) AUTO_INCREMENT=1000;


CREATE TABLE COM_ORDER (
	CODE INT(10)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ORDER_STATUS 		INT(1) 		NOT NULL,
	PAYMENT_STATUS 		INT(1) 		NOT NULL,
	DELIVERY_STATUS 	INT(1) 		NOT NULL,
	CUSTOMER_CODE 		INT(5)  	NOT NULL,
	CREATED_DATE		TIMESTAMP	NOT NULL,
	CREATED_BY		VARCHAR(10)	NOT NULL,
	MODIFIED_DATE		TIMESTAMP	NOT NULL,
	LAST_MODIFIED_BY	VARCHAR(10) 	NOT NULL,
	INDEX ID_COM_ORDER(CODE),
	FOREIGN KEY (CUSTOMER_CODE) REFERENCES CUSTOMER(CODE) ON DELETE CASCADE
)AUTO_INCREMENT=5000;

CREATE TABLE COM_ORDER_ENTRY (
	CODE INT(10)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ENTRY_NO INT(2) NOT NULL,
	QUANTITY DOUBLE(10,2) NOT NULL,
	PRICE FLOAT NOT NULL,
	DELIVERY_COST DOUBLE(10,2),
	DISCOUNT DOUBLE(10,2),
	NOTES VARCHAR(50),	
	ORDER_CODE INT(5) NOT NULL,	
	PRODUCT_CODE INT(5) NOT NULL,	
	LAST_MODIFIED_BY VARCHAR(10),
	FOREIGN KEY (ORDER_CODE) REFERENCES COM_ORDER(CODE) ON DELETE CASCADE,
	FOREIGN KEY (PRODUCT_CODE) REFERENCES PRODUCT(CODE) ON DELETE CASCADE
	
)AUTO_INCREMENT=1000;

CREATE TABLE COM_ORDER_DELIVERY (
	CODE INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ORDER_CODE INT(5) NOT NULL,
	DELIVERED_QUANTITY DOUBLE(10,2) NOT NULL,
	ENTRY_NO INT(2) NOT NULL,	
	DELIVERY_RECEIPT_NO VARCHAR(25) NOT NULL,	
	DELIVERY_DATE TIMESTAMP NOT NULL,
	VEHICLE_CODE VARCHAR(10) NOT NULL,
	INDEX ID_COM_ORDER_DELIVERY(CODE),
	LAST_MODIFIED_BY VARCHAR(10),
	FOREIGN KEY (ORDER_CODE) REFERENCES COM_ORDER(CODE) ON DELETE CASCADE	
) AUTO_INCREMENT=1000;

CREATE TABLE COM_ORDER_DELIVERY_ADDRESS (
	ADDRESS_CODE INT(10)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
	ADDRESS_LINE1 VARCHAR(100) NOT NULL,
	ADDRESS_LINE2 VARCHAR(100),
	ADDRESS_LINE3 VARCHAR(100),
	ADDRESS_LANDMARK VARCHAR(100),
	ADDRESS_ZIP VARCHAR(20),
	ORDER_ENTRY_CODE INT(5)  NOT NULL,
	ADDRESS_TYPE INT(1) NOT NULL,
	INDEX IDX_ORDER_ADDRESS_CODE(ADDRESS_CODE),
	FOREIGN KEY (ORDER_ENTRY_CODE) REFERENCES COM_ORDER_ENTRY(CODE) ON DELETE CASCADE	
)AUTO_INCREMENT=1000;

CREATE TABLE APPLICATION_ROLE (
	CODE INT NOT NULL PRIMARY KEY,	
	NAME VARCHAR(30) NOT NULL
) AUTO_INCREMENT=1000;

CREATE TABLE APPLICATION_USER (
	CODE 			VARCHAR(10)	NOT NULL PRIMARY KEY,
	PASSCODE 		VARCHAR(255)	NOT NULL,
	NAME	 		VARCHAR(30) 	NOT NULL,
	CONTACT		 	BIGINT(12)	NOT NULL,
	CREATED_DATE		TIMESTAMP	NOT NULL,
	LAST_MODIFIED_BY 	VARCHAR(10)	NOT NULL,
	USER_ROLE 		INT		NOT NULL,
	LAST_LOGGED_ON	 	TIMESTAMP,
	FOREIGN KEY (USER_ROLE) REFERENCES APPLICATION_ROLE(CODE)
) AUTO_INCREMENT=1000;









