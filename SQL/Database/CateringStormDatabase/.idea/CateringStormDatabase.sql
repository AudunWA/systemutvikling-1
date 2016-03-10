


DROP TABLE IF EXISTS _order_package;
DROP TABLE IF EXISTS _order;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS area;
DROP TABLE IF EXISTS subscription;
DROP TABLE IF EXISTS dish_package;
DROP TABLE IF EXISTS _package;
DROP TABLE IF EXISTS ingredient_dish;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS dish_type;
DROP TABLE IF EXISTS ingredient;

DROP TABLE IF EXISTS hours_employee;
DROP TABLE IF EXISTS hours;
DROP TABLE IF EXISTS chef;
DROP TABLE IF EXISTS nutrition_exp;
DROP TABLE IF EXISTS chauffeur;
DROP TABLE IF EXISTS administrator;
DROP TABLE IF EXISTS salesperson;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS employee_type;

CREATE TABLE employee_type(
 e_type_id INTEGER,
 e_type_name VARCHAR(20) NOT NULL,
 salary INTEGER NOT NULL,
 PRIMARY KEY (e_type_id)
);

CREATE TABLE employee(
 employee_id INTEGER AUTO_INCREMENT,
 username VARCHAR(30) NOT NULL,
 forename VARCHAR(50) NOT NULL,
 surname VARCHAR(30) NOT NULL,
 address VARCHAR(30) NOT NULL,
 phone VARCHAR(10) NOT NULL,
 email VARCHAR(50) NOT NULL,
 e_type_id INTEGER NOT NULL,
 FOREIGN KEY (e_type_id) REFERENCES employee_type(e_type_id) ON UPDATE CASCADE,
 PRIMARY KEY (employee_id)
);

CREATE TABLE salesperson(
 employee_id INTEGER,
 FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON UPDATE CASCADE,
 PRIMARY KEY (employee_id)
);

CREATE TABLE administrator(
 employee_id INTEGER,
 FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON UPDATE CASCADE,
 PRIMARY KEY (employee_id)
);

CREATE TABLE chauffeur(
 employee_id INTEGER,
 FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON UPDATE CASCADE,
 PRIMARY KEY (employee_id)
);

CREATE TABLE nutrition_exp(
 employee_id INTEGER,
 FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON UPDATE CASCADE,
 PRIMARY KEY (employee_id)
);

CREATE TABLE chef(
 employee_id INTEGER,
 FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON UPDATE CASCADE,
 PRIMARY KEY (employee_id)
);
CREATE TABLE hours(
 hours_id INTEGER AUTO_INCREMENT,
 _date DATE NOT NULL,
 work_time INTEGER NOT NULL,
 PRIMARY KEY (hours_id)
);

CREATE TABLE hours_employee(
 hours_id INTEGER NOT NULL,
 employee_id INTEGER NOT NULL,
 FOREIGN KEY (hours_id) REFERENCES hours(hours_id) ON UPDATE CASCADE,
 FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON UPDATE CASCADE,
 PRIMARY KEY (hours_id, employee_id)
);


CREATE TABLE ingredient(
 ingredient_id INTEGER AUTO_INCREMENT,
 arrival_date DATE NOT NULL,
  name VARCHAR(20) NOT NULL,
  description VARCHAR(50),
  vegetarian BOOLEAN NOT NULL,
  expire_date DATE NOT NULL,
  PRIMARY KEY (ingredient_id)
);
CREATE TABLE dish_type(
 dish_type_id INTEGER AUTO_INCREMENT,
 name VARCHAR(30) NOT NULL,
 PRIMARY KEY (dish_type_id)
);

CREATE TABLE dish(
  dish_id INTEGER AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  description VARCHAR(200),
  dish_type_id INTEGER NOT NULL,
  FOREIGN KEY (dish_type_id) REFERENCES dish_type (dish_type_id) ON UPDATE CASCADE,
  PRIMARY KEY(dish_id)
);

CREATE TABLE ingredient_dish(
 ingredient_id INTEGER,
 dish_id INTEGER,
 FOREIGN KEY (ingredient_id) REFERENCES ingredient(ingredient_id) ON UPDATE CASCADE,
 FOREIGN KEY (dish_id) REFERENCES dish(dish_id) ON UPDATE CASCADE,
 PRIMARY KEY (ingredient_id, dish_id)
);

CREATE TABLE package(
 package_id INTEGER AUTO_INCREMENT,
 name VARCHAR(20) NOT NULL,
 PRIMARY KEY (package_id)
);

CREATE TABLE dish_package(
 dish_id INTEGER,
 package_id INTEGER,
 FOREIGN KEY (dish_id) REFERENCES dish(dish_id) ON UPDATE CASCADE,
 FOREIGN KEY (package_id) REFERENCES package(package_id) ON UPDATE CASCADE,
 PRIMARY KEY (dish_id, package_id)
);

CREATE TABLE subscription(
 subscribe_id INTEGER,
 start_date DATE NOT NULL,
 end_date DATE NOT NULL,
 cost INTEGER NOT NULL,
 PRIMARY KEY (subscribe_id)
);

CREATE TABLE area(
 area_id INTEGER AUTO_INCREMENT,
 area_name VARCHAR(50),
 PRIMARY KEY (area_id)
);

CREATE TABLE customer(
 customer_id INTEGER AUTO_INCREMENT,
 surname VARCHAR(20) NOT NULL,
 forename VARCHAR(20) NOT NULL,
 location_id INTEGER NOT NULL,
 address VARCHAR(20) NOT NULL,
 area_id INTEGER NOT NULL,
 FOREIGN KEY (area_id) REFERENCES area(area_id) ON UPDATE CASCADE,
 PRIMARY KEY (customer_id)
);

CREATE TABLE _order(
 _order_id INTEGER AUTO_INCREMENT,
 description VARCHAR(20),
 delivery_date DATE NOT NULL,
 _order_date DATE NOT NULL,
 portions INTEGER NOT NULL,
 priority BOOLEAN NOT NULL,
 cost INTEGER NOT NULL,
 employee_id INTEGER,
 subscribe_id INTEGER,
 customer_id INTEGER NOT NULL,
 FOREIGN KEY (subscribe_id) REFERENCES subscription(subscribe_id) ON UPDATE CASCADE,
 FOREIGN KEY (employee_id) REFERENCES salesperson(employee_id) ON UPDATE CASCADE,
 FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON UPDATE CASCADE,
 PRIMARY KEY (_order_id)
);

CREATE TABLE _order_package(
 _order_id INTEGER,
 package_id INTEGER,
 FOREIGN KEY (_order_id) REFERENCES _order(_order_id) ON UPDATE CASCADE,
 FOREIGN KEY (package_id) REFERENCES package(package_id) ON UPDATE CASCADE,
 PRIMARY KEY _order_pk (_order_id, package_id)
);


















