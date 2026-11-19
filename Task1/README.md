# Task 1 - Fee Payment Window

## Files

- FeeService.java
- FeeWindow.java

## Setup

Create this table in your test database:

```sql
CREATE TABLE fees (
    reg_no VARCHAR(20),
    amount DOUBLE
);
```

The JDBC programs need the MySQL connector jar. Run them with:

```
java -cp .;mysql-connector-j.jar ClassName
```

Use `:` instead of `;` on Linux or Mac. Change the user name and password in the URL lines to your own.

## How to compile and run

```
javac FeeService.java FeeWindow.java
java FeeWindow
```

## Result

The window only collects input. FeeService checks the input and saves the payment. The Receipt button shows the last payment.
