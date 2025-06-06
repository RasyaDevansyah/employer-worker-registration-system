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

> https://jdbc.postgresql.org/download.html

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
-- Core Tables
CREATE TABLE auth (id SERIAL PRIMARY KEY, username VARCHAR(255) NOT NULL, pass VARCHAR(255) NOT NULL);
CREATE TABLE employer (id SERIAL PRIMARY KEY, fname VARCHAR(255) NOT NULL, lname VARCHAR(255) NOT NULL, tel VARCHAR(255)[], description TEXT, date TIMESTAMP);
CREATE TABLE worktype (id SERIAL PRIMARY KEY, title VARCHAR(255) NOT NULL, no INTEGER, date TIMESTAMP);
CREATE TABLE price (id SERIAL PRIMARY KEY, fulltime DECIMAL(10,2) NOT NULL, halftime DECIMAL(10,2) NOT NULL, overtime DECIMAL(10,2) NOT NULL, date TIMESTAMP);

-- Job Related Tables
CREATE TABLE job (id SERIAL PRIMARY KEY, employer_id INTEGER REFERENCES employer(id), price_id INTEGER REFERENCES price(id), title VARCHAR(255) NOT NULL, description TEXT, date TIMESTAMP);
CREATE TABLE workgroup (id SERIAL PRIMARY KEY, job_id INTEGER REFERENCES job(id), worktype_id INTEGER REFERENCES worktype(id), workcount INTEGER NOT NULL, description TEXT, date TIMESTAMP);

-- Payment Tables
CREATE TABLE paytype (id SERIAL PRIMARY KEY, title VARCHAR(255) NOT NULL, date TIMESTAMP);
CREATE TABLE payment (id SERIAL PRIMARY KEY, worker_id INTEGER NOT NULL, job_id INTEGER REFERENCES job(id), paytype_id INTEGER REFERENCES paytype(id), amount DECIMAL(10,2) NOT NULL, date TIMESTAMP);
CREATE TABLE invoice (id SERIAL PRIMARY KEY, job_id INTEGER REFERENCES job(id), amount DECIMAL(10,2) NOT NULL, date TIMESTAMP);
```
