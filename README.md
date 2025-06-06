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
