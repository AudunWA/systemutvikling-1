DROP TABLE IF EXISTS _order_food_package;
DROP TABLE IF EXISTS _order;
DROP TABLE IF EXISTS recurring_order;
DROP TABLE IF EXISTS subscription;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS dish_package;
DROP TABLE IF EXISTS dish_food_package;
DROP TABLE IF EXISTS food_package;
DROP TABLE IF EXISTS ingredient_dish;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS dish_type;
DROP TABLE IF EXISTS ingredient;

DROP TABLE IF EXISTS timesheet_employee;
DROP TABLE IF EXISTS timesheet;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS employee_type;

CREATE TABLE employee_type (
  e_type_id   INTEGER,
  e_type_name VARCHAR(20) NOT NULL,
  salary      DOUBLE      NOT NULL,
  commission  INTEGER,
  PRIMARY KEY (e_type_id)
);

CREATE TABLE employee (
  employee_id INTEGER AUTO_INCREMENT,
  username    VARCHAR(30) NOT NULL,
  forename    VARCHAR(50) NOT NULL,
  surname     VARCHAR(30) NOT NULL,
  address     VARCHAR(30) NOT NULL,
  phone       VARCHAR(10) NOT NULL,
  email       VARCHAR(50) NOT NULL,
  e_type_id   INTEGER     NOT NULL,
  active      BOOLEAN     NOT NULL,
  password    VARCHAR(64) NOT NULL,
  salt        VARCHAR(32) NOT NULL,
  salary      DOUBLE      NOT NULL,
  commission  INTEGER,
  FOREIGN KEY (e_type_id) REFERENCES employee_type (e_type_id)
    ON UPDATE CASCADE,
  PRIMARY KEY (employee_id),
  UNIQUE KEY (username)
);

CREATE TABLE timesheet (
  timesheet_id INTEGER AUTO_INCREMENT NOT NULL,
  employee_id  INTEGER                NOT NULL,
  from_time    DATETIME               NOT NULL,
  to_time      DATETIME,
  active       BOOLEAN                NOT NULL,
  FOREIGN KEY (employee_id) REFERENCES employee (employee_id)
    ON UPDATE CASCADE,
  PRIMARY KEY (timesheet_id, employee_id)
);


CREATE TABLE ingredient (
  ingredient_id INTEGER AUTO_INCREMENT,
  arrival_date  DATETIME    NOT NULL,
  name          VARCHAR(32) NOT NULL,
  description   TEXT,
  vegetarian    BOOLEAN     NOT NULL,
  expire_date   DATE        NOT NULL,
  amount        DOUBLE,
  unit          VARCHAR(10) NOT NULL,
  PRIMARY KEY (ingredient_id)
);

CREATE TABLE dish_type (
  dish_type_id INTEGER AUTO_INCREMENT,
  name         VARCHAR(30) NOT NULL,
  PRIMARY KEY (dish_type_id)
);

CREATE TABLE dish (
  dish_id      INTEGER AUTO_INCREMENT,
  name         VARCHAR(30) NOT NULL,
  description  VARCHAR(200),
  dish_type_id INTEGER     NOT NULL,
  active       BOOLEAN     NOT NULL,
  FOREIGN KEY (dish_type_id) REFERENCES dish_type (dish_type_id)
    ON UPDATE CASCADE,
  PRIMARY KEY (dish_id)
);

CREATE TABLE ingredient_dish (
  ingredient_id INTEGER,
  dish_id       INTEGER,
  quantity      DOUBLE      NOT NULL,
  unit          VARCHAR(30) NOT NULL,
  FOREIGN KEY (ingredient_id) REFERENCES ingredient (ingredient_id)
    ON UPDATE CASCADE,
  FOREIGN KEY (dish_id) REFERENCES dish (dish_id)
    ON UPDATE CASCADE,
  PRIMARY KEY (ingredient_id, dish_id)
);

CREATE TABLE food_package (
  food_package_id INTEGER AUTO_INCREMENT,
  name            VARCHAR(64) NOT NULL,
  cost            DOUBLE      NOT NULL,
  active          BOOLEAN     NOT NULL,
  PRIMARY KEY (food_package_id)
);

CREATE TABLE dish_food_package (
  dish_id         INTEGER,
  food_package_id INTEGER,
  FOREIGN KEY (dish_id) REFERENCES dish (dish_id)
    ON UPDATE CASCADE,
  FOREIGN KEY (food_package_id) REFERENCES food_package (food_package_id)
    ON UPDATE CASCADE,
  PRIMARY KEY (dish_id, food_package_id)
);

CREATE TABLE customer (
  customer_id INTEGER AUTO_INCREMENT,
  surname     VARCHAR(100) NOT NULL,
  forename    VARCHAR(50)  NOT NULL,
  address     VARCHAR(255) NOT NULL,
  active      BOOLEAN      NOT NULL,
  phone       VARCHAR(10)  NOT NULL,
  email       VARCHAR(50)  NOT NULL,
  PRIMARY KEY (customer_id)
);

CREATE TABLE subscription (
  subscription_id INTEGER AUTO_INCREMENT,
  start_date      DATE    NOT NULL,
  end_date        DATE    NOT NULL,
  cost            DOUBLE  NOT NULL,
  customer_id     INTEGER NOT NULL,
  active          BOOLEAN NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
  PRIMARY KEY (subscription_id)
);

CREATE TABLE recurring_order (
  rec_order_id    INTEGER AUTO_INCREMENT,
  week_day        INTEGER NOT NULL,
  relative_time   INTEGER NOT NULL,
  subscription_id INTEGER NOT NULL,
  food_package_id INTEGER NOT NULL,
  amount          INTEGER NOT NULL,
  FOREIGN KEY (subscription_id) REFERENCES subscription (subscription_id),
  FOREIGN KEY (food_package_id) REFERENCES food_package (food_package_id),
  PRIMARY KEY (rec_order_id)
);


CREATE TABLE _order (
  _order_id           INTEGER AUTO_INCREMENT,
  description         VARCHAR(200),
  delivery_time       DATETIME NOT NULL,
  _order_time         DATETIME NOT NULL,
  portions            INTEGER  NOT NULL,
  priority            BOOLEAN  NOT NULL,
  salesperson_id      INTEGER,
  customer_id         INTEGER  NOT NULL,
  rec_order_id        INTEGER,
  status              TINYINT  NOT NULL,
  chauffeur_id        INTEGER,
  delivery_start_time DATETIME,
  FOREIGN KEY (rec_order_id) REFERENCES recurring_order (rec_order_id)
    ON UPDATE CASCADE,
  FOREIGN KEY (salesperson_id) REFERENCES employee (employee_id)
    ON UPDATE CASCADE,
  FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
    ON UPDATE CASCADE,
  FOREIGN KEY (chauffeur_id) REFERENCES employee (employee_id)
    ON UPDATE CASCADE,
  PRIMARY KEY (_order_id)
);

CREATE TABLE _order_food_package (
  _order_id       INTEGER,
  food_package_id INTEGER,
  FOREIGN KEY (_order_id) REFERENCES _order (_order_id)
    ON UPDATE CASCADE,
  FOREIGN KEY (food_package_id) REFERENCES food_package (food_package_id)
    ON UPDATE CASCADE,
  PRIMARY KEY _order_pk (_order_id, food_package_id)
);


