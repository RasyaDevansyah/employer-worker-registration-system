# employer-worker-registration-system
An accounting program that contains employee and employer information and records of relationships between them.

---

# v1 branch
## Screenshot
<p align="center"><strong>Login</strong></p>
<p align="center"><img src="https://user-images.githubusercontent.com/71611710/157845415-c8f293df-5e1a-4ac5-a066-1971ee3ab6ae.png"></p>

| **Homepage**            | **Employer registration**|  **Worker registration**
:------------------------:|:------------------------:|:-------------------------:
![2-home_page](https://user-images.githubusercontent.com/71611710/157845986-0b99502d-ec6a-411c-999c-d37859dcf47e.png) | ![3-new_employer](https://user-images.githubusercontent.com/71611710/157849241-2a4ea23f-f195-4152-ab57-b2da20a1ea87.png)  |  ![3-new_worker](https://user-images.githubusercontent.com/71611710/157849850-5c6cfda1-05cd-4164-8287-474496cd189e.png)

| **Search Box**  | **Registration document**
:----------------:|:-------------------------:
![5-view_worker](https://user-images.githubusercontent.com/71611710/157850829-c03944a1-bd1b-41d6-875b-61f8d8ce4d62.png) | ![7-new_record_optionpane](https://user-images.githubusercontent.com/71611710/158039292-30c103d1-bdaa-4f3f-bd36-342815fd6efd.png)

---

## Requirements
* Java JDK 24 Tutorial : [https://www.youtube.com/watch?v=O9PWH9SeTTE]
* Eclipse EE (Enterprise Edition) Tutorial : [https://www.youtube.com/watch?v=8aDsEV7txXE]
* PostgreSQL and PgAdmin Tutorial [https://youtu.be/4qH-7w5LZsA?si=XBH_dXeIIZCKlqbz]


Postgresql is used in this program. You can find the necessary jar file for postgresql java connection here:

> https://jdbc.postgresql.org/download/

Or you can use a different database but for this to work, change:
```
DriverManager.getConnection("jdbc:database://host:port/database-name", "user-name", "password");
```
for postgresql:
```
DriverManager.getConnection("jdbc:postgresql://localhost:5432/db", "postgres", "password");
```
---

**And finally, in order not to get a database error, you should add the following tables to the database:**
### UPDATED DATABASE TABLES by rasya
```
-- Table for user authentication (derived from LoginDAO.java)
CREATE TABLE auth (
    id SERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    pass VARCHAR(255) NOT NULL
);

-- Table for employers (derived from EmployerDAO.java)
CREATE TABLE employer (
    id SERIAL PRIMARY KEY NOT NULL,
    fname VARCHAR(255) NOT NULL,
    lname VARCHAR(255) NOT NULL,
    tel VARCHAR(255)[], -- Array of phone numbers
    description TEXT,
    date TIMESTAMP DEFAULT NOW()
);

-- Table for workers (derived from WorkerDAO.java)
CREATE TABLE worker (
    id SERIAL PRIMARY KEY NOT NULL,
    fname VARCHAR(255) NOT NULL,
    lname VARCHAR(255) NOT NULL,
    tel VARCHAR(255)[], -- Array of phone numbers
    iban VARCHAR(255),
    description TEXT,
    date TIMESTAMP DEFAULT NOW()
);

-- Table for payment types (derived from PaytypeDAO.java)
CREATE TABLE paytype (
    id SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL UNIQUE,
    date TIMESTAMP DEFAULT NOW()
);

-- Table for pricing information (derived from PriceDAO.java)
CREATE TABLE price (
    id SERIAL PRIMARY KEY NOT NULL,
    fulltime NUMERIC(10, 2) NOT NULL,
    halftime NUMERIC(10, 2) NOT NULL,
    overtime NUMERIC(10, 2) NOT NULL,
    date TIMESTAMP DEFAULT NOW()
);

-- Table for jobs (derived from JobDAO.java)
CREATE TABLE job (
    id SERIAL PRIMARY KEY NOT NULL,
    employer_id INTEGER NOT NULL REFERENCES employer(id) ON DELETE CASCADE,
    price_id INTEGER NOT NULL REFERENCES price(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    date TIMESTAMP DEFAULT NOW()
);

-- Table for work types (derived from WorktypeDAO.java)
CREATE TABLE worktype (
    id SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL UNIQUE,
    no INTEGER, -- Added 'no' column as per WorktypeDAO.java
    date TIMESTAMP DEFAULT NOW()
);

-- Table for work groups (derived from WorkgroupDAO.java)
CREATE TABLE workgroup (
    id SERIAL PRIMARY KEY NOT NULL,
    job_id INTEGER NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    worktype_id INTEGER NOT NULL REFERENCES worktype(id) ON DELETE CASCADE,
    workcount INTEGER, -- Added 'workcount' column as per WorkgroupDAO.java
    description TEXT,
    date TIMESTAMP DEFAULT NOW()
);

-- Table for individual work records (derived from WorkDAO.java)
CREATE TABLE work (
    id SERIAL PRIMARY KEY NOT NULL,
    job_id INTEGER NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    worker_id INTEGER NOT NULL REFERENCES worker(id) ON DELETE CASCADE,
    worktype_id INTEGER NOT NULL REFERENCES worktype(id) ON DELETE CASCADE,
    workgroup_id INTEGER NOT NULL REFERENCES workgroup(id) ON DELETE CASCADE,
    description TEXT,
    date TIMESTAMP DEFAULT NOW()
);

-- Table for payments (derived from PaymentDAO.java)
CREATE TABLE payment (
    id SERIAL PRIMARY KEY NOT NULL,
    worker_id INTEGER NOT NULL REFERENCES worker(id) ON DELETE CASCADE,
    job_id INTEGER NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    paytype_id INTEGER NOT NULL REFERENCES paytype(id) ON DELETE CASCADE,
    amount NUMERIC(10, 2) NOT NULL,
    date TIMESTAMP DEFAULT NOW()
);

-- Table for invoices (derived from InvoiceDAO.java)
CREATE TABLE invoice (
    id SERIAL PRIMARY KEY NOT NULL,
    job_id INTEGER NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    amount NUMERIC(10, 2) NOT NULL,
    date TIMESTAMP DEFAULT NOW()
);
```

**For testing, you dan insert this sample data :**
### UPDATED DATABASE TABLES by rasya
```
-- Sample Data for auth table
INSERT INTO auth (username, pass) VALUES ('admin', 'admin');

-- Sample Data for employer table
INSERT INTO employer (fname, lname, tel, description) VALUES ('JOHN', 'DOE', ARRAY['123-456-7890'], 'TECH SOLUTIONS INC.');
INSERT INTO employer (fname, lname, tel, description) VALUES ('JANE', 'SMITH', ARRAY['098-765-4321', '555-123-4567'], 'CREATIVE DESIGNS CO.');
INSERT INTO employer (fname, lname, tel, description) VALUES ('PETER', 'JONES', NULL, 'GLOBAL LOGISTICS LTD.');
INSERT INTO employer (fname, lname, tel, description) VALUES ('EMILY', 'WHITE', ARRAY['222-333-4444'], 'FUTURE INNOVATIONS GROUP');
INSERT INTO employer (fname, lname, tel, description) VALUES ('MICHAEL', 'BLACK', ARRAY['777-888-9999'], 'DYNAMIC MARKETING AGENCY');
INSERT INTO employer (fname, lname, tel, description) VALUES ('SARAH', 'GREEN', ARRAY['101-202-3030'], 'ECO-FRIENDLY BUILDERS');
INSERT INTO employer (fname, lname, tel, description) VALUES ('DAVID', 'BLUE', NULL, 'HEALTH SOLUTIONS CORP.');

-- Sample Data for worker table
INSERT INTO worker (fname, lname, tel, iban, description) VALUES ('ALICE', 'WILLIAMS', ARRAY['111-222-3333'], 'TR000000000000000000000001', 'EXPERIENCED SOFTWARE DEVELOPER.');
INSERT INTO worker (fname, lname, tel, iban, description) VALUES ('BOB', 'BROWN', ARRAY['444-555-6666'], 'TR000000000000000000000002', 'SKILLED GRAPHIC DESIGNER.');
INSERT INTO worker (fname, lname, tel, iban, description) VALUES ('CHARLIE', 'DAVIS', NULL, 'TR000000000000000000000003', 'LOGISTICS AND DELIVERY EXPERT.');
INSERT INTO worker (fname, lname, tel, iban, description) VALUES ('DIANA', 'MILLER', ARRAY['666-777-8888'], 'TR000000000000000000000004', 'FRONTEND SPECIALIST.');
INSERT INTO worker (fname, lname, tel, iban, description) VALUES ('EVE', 'WILSON', ARRAY['999-000-1111'], 'TR000000000000000000000005', 'ILLUSTRATOR AND UI/UX DESIGNER.');
INSERT INTO worker (fname, lname, tel, iban, description) VALUES ('FRANK', 'MOORE', ARRAY['333-222-1111'], 'TR000000000000000000000006', 'BACKEND ENGINEER.');
INSERT INTO worker (fname, lname, tel, iban, description) VALUES ('GRACE', 'TAYLOR', NULL, 'TR000000000000000000000007', 'CONTENT WRITER AND EDITOR.');
INSERT INTO worker (fname, lname, tel, iban, description) VALUES ('HENRY', 'ANDERSON', ARRAY['777-111-2222'], 'TR000000000000000000000008', 'DATA ANALYST.');
INSERT INTO worker (fname, lname, tel, iban, description) VALUES ('IVY', 'THOMAS', ARRAY['888-333-4444'], 'TR000000000000000000000009', 'PROJECT MANAGER.');
INSERT INTO worker (fname, lname, tel, iban, description) VALUES ('JACK', 'JACKSON', NULL, 'TR000000000000000000000010', 'QA TESTER.');

-- Sample Data for paytype table
INSERT INTO paytype (title) VALUES ('HOURLY');
INSERT INTO paytype (title) VALUES ('DAILY');
INSERT INTO paytype (title) VALUES ('PROJECT-BASED');
INSERT INTO paytype (title) VALUES ('WEEKLY');
INSERT INTO paytype (title) VALUES ('MONTHLY');

-- Sample Data for price table
INSERT INTO price (fulltime, halftime, overtime) VALUES (150.00, 75.00, 200.00);
INSERT INTO price (fulltime, halftime, overtime) VALUES (120.00, 60.00, 180.00);
INSERT INTO price (fulltime, halftime, overtime) VALUES (100.00, 50.00, 150.00);
INSERT INTO price (fulltime, halftime, overtime) VALUES (180.00, 90.00, 250.00);
INSERT INTO price (fulltime, halftime, overtime) VALUES (90.00, 45.00, 130.00);
INSERT INTO price (fulltime, halftime, overtime) VALUES (135.00, 67.50, 190.00);

-- Sample Data for job table (requires existing employer and price IDs)
INSERT INTO job (employer_id, price_id, title, description) VALUES (1, 1, 'WEBSITE REDESIGN', 'COMPLETE OVERHAUL OF THE COMPANY WEBSITE.');
INSERT INTO job (employer_id, price_id, title, description) VALUES (2, 2, 'MARKETING CAMPAIGN', 'DESIGN AND EXECUTE A NEW MARKETING STRATEGY.');
INSERT INTO job (employer_id, price_id, title, description) VALUES (1, 3, 'MOBILE APP DEVELOPMENT', 'DEVELOP AN IOS AND ANDROID APPLICATION.');
INSERT INTO job (employer_id, price_id, title, description) VALUES (3, 4, 'SUPPLY CHAIN OPTIMIZATION', 'IMPROVE LOGISTICS AND DELIVERY EFFICIENCY.');
INSERT INTO job (employer_id, price_id, title, description) VALUES (4, 1, 'AI RESEARCH PROJECT', 'DEVELOP A NEW AI MODEL FOR DATA ANALYSIS.');
INSERT INTO job (employer_id, price_id, title, description) VALUES (5, 2, 'BRAND IDENTITY CREATION', 'CREATE A NEW BRAND IMAGE FOR A STARTUP.');
INSERT INTO job (employer_id, price_id, title, description) VALUES (6, 3, 'GREEN BUILDING INITIATIVE', 'CONSTRUCT AN ENVIRONMENTALLY FRIENDLY OFFICE BUILDING.');
INSERT INTO job (employer_id, price_id, title, description) VALUES (7, 4, 'PATIENT MANAGEMENT SYSTEM', 'DEVELOP A SOFTWARE FOR HOSPITAL PATIENT RECORDS.');
INSERT INTO job (employer_id, price_id, title, description) VALUES (4, 5, 'CLOUD MIGRATION SERVICE', 'MIGRATE EXISTING INFRASTRUCTURE TO CLOUD.');
INSERT INTO job (employer_id, price_id, title, description) VALUES (5, 6, 'SOCIAL MEDIA STRATEGY', 'DEVELOP A COMPREHENSIVE SOCIAL MEDIA PLAN.');

-- Sample Data for worktype table
INSERT INTO worktype (title, no) VALUES ('DEVELOPMENT', 1);
INSERT INTO worktype (title, no) VALUES ('DESIGN', 2);
INSERT INTO worktype (title, no) VALUES ('TESTING', 3);
INSERT INTO worktype (title, no) VALUES ('CONSULTING', 4);
INSERT INTO worktype (title, no) VALUES ('RESEARCH', 5);
INSERT INTO worktype (title, no) VALUES ('MARKETING', 6);
INSERT INTO worktype (title, no) VALUES ('CONSTRUCTION', 7);
INSERT INTO worktype (title, no) VALUES ('DATA ENTRY', 8);

-- Sample Data for workgroup table (requires existing job and worktype IDs)
INSERT INTO workgroup (job_id, worktype_id, workcount, description) VALUES (1, 1, 10, 'FRONTEND DEVELOPMENT TASKS.');
INSERT INTO workgroup (job_id, worktype_id, workcount, description) VALUES (1, 2, 5, 'UI/UX DESIGN FOR THE WEBSITE.');
INSERT INTO workgroup (job_id, worktype_id, workcount, description) VALUES (2, 2, 8, 'GRAPHIC DESIGN FOR ADS.');
INSERT INTO workgroup (job_id, worktype_id, workcount, description) VALUES (3, 1, 15, 'BACKEND DEVELOPMENT FOR MOBILE APP.');
INSERT INTO workgroup (job_id, worktype_id, workcount, description) VALUES (4, 4, 7, 'CONSULTING ON LOGISTICS WORKFLOWS.');
INSERT INTO workgroup (job_id, worktype_id, workcount, description) VALUES (5, 5, 12, 'ALGORITHM DEVELOPMENT FOR AI.');
INSERT INTO workgroup (job_id, worktype_id, workcount, description) VALUES (6, 6, 9, 'CAMPAIGN PLANNING AND EXECUTION.');
INSERT INTO workgroup (job_id, worktype_id, workcount, description) VALUES (7, 7, 20, 'FOUNDATION AND STRUCTURAL WORK.');
INSERT INTO workgroup (job_id, worktype_id, workcount, description) VALUES (8, 1, 18, 'DATABASE INTEGRATION FOR PATIENT SYSTEM.');
INSERT INTO workgroup (job_id, worktype_id, workcount, description) VALUES (1, 3, 6, 'QUALITY ASSURANCE FOR WEB APP.');

-- Sample Data for work table (requires existing job, worker, worktype, and workgroup IDs)
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (1, 1, 1, 1, 'DEVELOPED LOGIN MODULE.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (1, 2, 2, 2, 'CREATED WIREFRAMES FOR HOMEPAGE.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (2, 2, 2, 3, 'DESIGNED BANNER ADS.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (3, 4, 1, 4, 'IMPLEMENTED USER AUTHENTICATION.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (4, 3, 4, 5, 'ANALYZED CURRENT SHIPPING ROUTES.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (5, 1, 5, 6, 'CODED NEURAL NETWORK LAYERS.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (6, 2, 6, 7, 'PREPARED SOCIAL MEDIA CONTENT.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (7, 5, 7, 8, 'SUPERVISED CONCRETE POURING.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (8, 6, 1, 9, 'DEVELOPED PATIENT REGISTRATION API.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (1, 10, 3, 10, 'EXECUTED REGRESSION TESTS.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (2, 5, 6, 7, 'CREATED EMAIL MARKETING TEMPLATES.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (3, 4, 3, 10, 'PERFORMED UNIT TESTS ON MOBILE APP.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (4, 7, 4, 5, 'WROTE LOGISTICS PROCESS DOCUMENTATION.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (5, 8, 5, 6, 'COLLECTED AND CLEANED TRAINING DATA.');
INSERT INTO work (job_id, worker_id, worktype_id, workgroup_id, description) VALUES (6, 9, 6, 7, 'MANAGED SOCIAL MEDIA AD CAMPAIGNS.');

-- Sample Data for payment table (requires existing worker, job, and paytype IDs)
INSERT INTO payment (worker_id, job_id, paytype_id, amount) VALUES (1, 1, 1, 1200.00);
INSERT INTO payment (worker_id, job_id, paytype_id, amount) VALUES (2, 2, 2, 800.00);
INSERT INTO payment (worker_id, job_id, paytype_id, amount) VALUES (1, 3, 3, 2500.00);
INSERT INTO payment (worker_id, job_id, paytype_id, amount) VALUES (3, 4, 1, 950.00);
INSERT INTO payment (worker_id, job_id, paytype_id, amount) VALUES (4, 1, 1, 1300.00);
INSERT INTO payment (worker_id, job_id, paytype_id, amount) VALUES (5, 2, 2, 850.00);
INSERT INTO payment (worker_id, job_id, paytype_id, amount) VALUES (6, 3, 3, 2700.00);
INSERT INTO payment (worker_id, job_id, paytype_id, amount) VALUES (7, 4, 1, 1050.00);
INSERT INTO payment (worker_id, job_id, paytype_id, amount) VALUES (8, 5, 2, 1100.00);
INSERT INTO payment (worker_id, job_id, paytype_id, amount) VALUES (9, 6, 3, 1500.00);
INSERT INTO payment (worker_id, job_id, paytype_id, amount) VALUES (10, 1, 1, 700.00);

-- Sample Data for invoice table (requires existing job IDs)
INSERT INTO invoice (job_id, amount) VALUES (1, 5000.00);
INSERT INTO invoice (job_id, amount) VALUES (2, 3000.00);
INSERT INTO invoice (job_id, amount) VALUES (3, 7000.00);
INSERT INTO invoice (job_id, amount) VALUES (4, 4500.00);
INSERT INTO invoice (job_id, amount) VALUES (5, 8000.00);
INSERT INTO invoice (job_id, amount) VALUES (6, 3200.00);
INSERT INTO invoice (job_id, amount) VALUES (7, 10000.00);
INSERT INTO invoice (job_id, amount) VALUES (8, 6000.00);
INSERT INTO invoice (job_id, amount) VALUES (9, 5500.00);
INSERT INTO invoice (job_id, amount) VALUES (10, 2800.00);


```
