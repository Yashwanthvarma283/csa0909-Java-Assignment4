# Task 6 - Event Registration

## Files

- Registration.java
- RegistrationDao.java
- RegistrationService.java
- RegistrationApp.java

## Setup

Run this SQL script first:

```sql
CREATE DATABASE IF NOT EXISTS eventdb;
USE eventdb;

CREATE TABLE IF NOT EXISTS registrations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reg_no VARCHAR(20) NOT NULL,
    name VARCHAR(50) NOT NULL,
    event_name VARCHAR(50) NOT NULL,
    UNIQUE (reg_no, event_name)
);
```

The JDBC programs need the MySQL connector jar. Run them with:

```
java -cp .;mysql-connector-j.jar ClassName
```

Use `:` instead of `;` on Linux or Mac. Change the user name and password in the URL lines to your own.

## How to compile and run

```
javac *.java
java RegistrationApp
```

## Result

Register a student, try an empty register number, and try the same student and event twice. The table can be filtered by event and exported from the File menu to registrations.txt.
