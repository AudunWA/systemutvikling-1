-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Vert: 127.0.0.1
-- Generert den: 26. Apr, 2016 22:32 PM
-- Tjenerversjon: 5.5.47-0ubuntu0.14.04.1
-- PHP-Versjon: 5.5.9-1ubuntu4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `g_tdat1006_t6`
--

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `customer_id` INT(11)      NOT NULL AUTO_INCREMENT,
  `surname`     VARCHAR(100) NOT NULL,
  `forename`    VARCHAR(50)  NOT NULL,
  `address`     VARCHAR(255) NOT NULL,
  `active`      TINYINT(1)   NOT NULL,
  `phone`       VARCHAR(10)  NOT NULL,
  `email`       VARCHAR(50)  NOT NULL,
  PRIMARY KEY (`customer_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  AUTO_INCREMENT = 9;

--
-- Dataark for tabell `customer`
--

INSERT INTO `customer` (`customer_id`, `surname`, `forename`, `address`, `active`, `phone`, `email`) VALUES
  (1, 'Sørensen', 'Elias', 'Valgrindvegen 14', 1, '234546453', 'Dragonslayer69@hotmail.com'),
  (2, 'W. Arbo', 'Audun', 'Anders tvereggens veg 9', 1, '876567898', 'mail@mail.com'),
  (3, 'Mahic', 'Kenan', 'Saupstadringen 21a', 1, '98765456', 'kenan@polsemail.com'),
  (4, 'Høydalsnes', 'Håvard', 'Jakobstien 6', 1, '43456876', 'barelegginn@mail.com');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `dish`
--

CREATE TABLE IF NOT EXISTS `dish` (
  `dish_id`      INT(11)     NOT NULL AUTO_INCREMENT,
  `name`         VARCHAR(30) NOT NULL,
  `description`  VARCHAR(200)         DEFAULT NULL,
  `dish_type_id` INT(11)     NOT NULL,
  `active`       TINYINT(1)  NOT NULL,
  PRIMARY KEY (`dish_id`),
  KEY `dish_type_id` (`dish_type_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  AUTO_INCREMENT = 9;

--
-- Dataark for tabell `dish`
--

INSERT INTO `dish` (`dish_id`, `name`, `description`, `dish_type_id`, `active`) VALUES
  (1, 'Vegetarian burger', 'Tasty vegetarian burger made with beans', 2, 1),
  (2, 'Meat burger', 'Tasty hamburger', 2, 1),
  (3, 'Tomato soup', 'Tasty and fresh appetizer', 1, 1),
  (4, 'Chicken with rice', 'Tasty and classic', 2, 1),
  (5, 'Vanilla ice cream', 'Excellent dessert', 3, 1),
  (6, 'Scallops', 'Fresh seafood appetizer', 1, 1),
  (7, 'Beef rice wok', 'cultural and nice', 2, 1),
  (8, 'Testgetdish', 'TestGetDish desc', 2, 1);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `dish_food_package`
--

CREATE TABLE IF NOT EXISTS `dish_food_package` (
  `dish_id`         INT(11) NOT NULL DEFAULT '0',
  `food_package_id` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`dish_id`, `food_package_id`),
  KEY `food_package_id` (`food_package_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dataark for tabell `dish_food_package`
--

INSERT INTO `dish_food_package` (`dish_id`, `food_package_id`) VALUES
  (1, 1),
  (3, 1),
  (5, 1),
  (2, 2),
  (5, 2),
  (3, 3),
  (5, 3),
  (7, 3),
  (4, 4),
  (4, 5),
  (5, 5),
  (6, 5);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `dish_type`
--

CREATE TABLE IF NOT EXISTS `dish_type` (
  `dish_type_id` INT(11)     NOT NULL AUTO_INCREMENT,
  `name`         VARCHAR(30) NOT NULL,
  PRIMARY KEY (`dish_type_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  AUTO_INCREMENT = 4;

--
-- Dataark for tabell `dish_type`
--

INSERT INTO `dish_type` (`dish_type_id`, `name`) VALUES
  (1, 'Forrett'),
  (2, 'Hovedrett'),
  (3, 'Dessert');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `employee`
--

CREATE TABLE IF NOT EXISTS `employee` (
  `employee_id` INT(11)     NOT NULL AUTO_INCREMENT,
  `username`    VARCHAR(30) NOT NULL,
  `forename`    VARCHAR(50) NOT NULL,
  `surname`     VARCHAR(30) NOT NULL,
  `address`     VARCHAR(30) NOT NULL,
  `phone`       VARCHAR(10) NOT NULL,
  `email`       VARCHAR(50) NOT NULL,
  `e_type_id`   INT(11)     NOT NULL,
  `active`      TINYINT(1)  NOT NULL,
  `password`    VARCHAR(64) NOT NULL,
  `salt`        VARCHAR(32) NOT NULL,
  `salary`      DOUBLE      NOT NULL,
  `commission`  INT(11)              DEFAULT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `username` (`username`),
  KEY `e_type_id` (`e_type_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  AUTO_INCREMENT = 2;

--
-- Dataark for tabell `employee`
--

INSERT INTO `employee` (`employee_id`, `username`, `forename`, `surname`, `address`, `phone`, `email`, `e_type_id`, `active`, `password`, `salt`, `salary`, `commission`)
VALUES
  (1, 'chechter', 'Christian', 'Echtermeyer', 'Stibakken 2', '99258217', 'chrech.birr@gmail.com', 4, 1,
      'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e', 'YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh', 277.29,
   0);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `employee_type`
--

CREATE TABLE IF NOT EXISTS `employee_type` (
  `e_type_id`   INT(11)     NOT NULL DEFAULT '0',
  `e_type_name` VARCHAR(20) NOT NULL,
  `salary`      DOUBLE      NOT NULL,
  `commission`  INT(11)              DEFAULT NULL,
  PRIMARY KEY (`e_type_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dataark for tabell `employee_type`
--

INSERT INTO `employee_type` (`e_type_id`, `e_type_name`, `salary`, `commission`) VALUES
  (1, 'chef', 235.99, 0),
  (2, 'chauffeur', 235.99, 0),
  (3, 'nutrition expert', 235.99, 0),
  (4, 'administrator', 277.29, 0),
  (5, 'sales person', 106.19, 11),
  (6, 'secretary', 206.49, 0);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `food_package`
--

CREATE TABLE IF NOT EXISTS `food_package` (
  `food_package_id` INT(11)     NOT NULL AUTO_INCREMENT,
  `name`            VARCHAR(64) NOT NULL,
  `cost`            DOUBLE      NOT NULL,
  `active`          TINYINT(1)  NOT NULL,
  PRIMARY KEY (`food_package_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  AUTO_INCREMENT = 6;

--
-- Dataark for tabell `food_package`
--

INSERT INTO `food_package` (`food_package_id`, `name`, `cost`, `active`) VALUES
  (1, 'Vegetarian special', 270.5, 1),
  (2, 'Burger and dessert', 210, 1),
  (3, 'Beef wok full meal', 320.5, 1),
  (4, 'Chicken and rice main only', 200, 1),
  (5, 'Fine dining full meal', 410, 1);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `ingredient`
--

CREATE TABLE IF NOT EXISTS `ingredient` (
  `ingredient_id` INT(11)     NOT NULL AUTO_INCREMENT,
  `arrival_date`  DATETIME    NOT NULL,
  `name`          VARCHAR(32) NOT NULL,
  `description`   TEXT,
  `vegetarian`    TINYINT(1)  NOT NULL,
  `expire_date`   DATE        NOT NULL,
  `amount`        DOUBLE               DEFAULT NULL,
  `unit`          VARCHAR(10) NOT NULL,
  PRIMARY KEY (`ingredient_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  AUTO_INCREMENT = 14;

--
-- Dataark for tabell `ingredient`
--

INSERT INTO `ingredient` (`ingredient_id`, `arrival_date`, `name`, `description`, `vegetarian`, `expire_date`, `amount`, `unit`)
VALUES
  (2, '2016-04-26 21:46:38', 'Chicken', 'Chicken meat, good with rice or vegetables', 0, '2016-05-27', 20000, 'g'),
  (3, '2016-04-26 21:47:24', 'Rice', 'White rice, good with chicken or beef.', 1, '2016-05-17', 40000, 'g'),
  (4, '2016-04-26 21:47:56', 'Pepper', 'Default spice for any dish', 1, '2018-04-24', 2000, 'g'),
  (5, '2016-04-26 21:48:18', 'Salt', 'Default spice for any dish.', 1, '2019-04-23', 1000, 'g'),
  (6, '2016-04-26 21:49:39', 'Beef', 'High quality Argentinian beef.', 0, '2016-05-19', 9000, 'g'),
  (7, '2016-04-26 21:50:17', 'Vanilla ice cream', 'Excellent for dessert.', 1, '2016-05-17', 4000, 'g'),
  (8, '2016-04-26 21:51:02', 'Tomato soup', 'Made with fresh tomato, good appetiser.', 1, '2016-05-10', 2000, 'g'),
  (9, '2016-04-26 21:52:59', 'Vegetarian burger patty', 'Made with beans, vegetarian.', 1, '2016-05-31', 200, 'units'),
  (10, '2016-04-26 21:53:51', 'Burger bun', 'Made fresh, used in burgers.', 1, '2016-05-24', 300, 'units'),
  (11, '2016-04-26 21:54:44', 'Minced meat', 'Easily shaped meat, good for burgers.', 0, '2016-05-12', 5000, 'g'),
  (12, '2016-04-26 21:55:11', 'Pudding', 'wobbly dessert item', 1, '2016-05-27', 3000, 'g'),
  (13, '2016-04-26 21:55:49', 'Scallops', 'High quality appetiser.', 0, '2016-05-24', 5000, 'g');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `ingredient_dish`
--

CREATE TABLE IF NOT EXISTS `ingredient_dish` (
  `ingredient_id` INT(11)     NOT NULL DEFAULT '0',
  `dish_id`       INT(11)     NOT NULL DEFAULT '0',
  `quantity`      DOUBLE      NOT NULL,
  `unit`          VARCHAR(30) NOT NULL,
  PRIMARY KEY (`ingredient_id`, `dish_id`),
  KEY `dish_id` (`dish_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dataark for tabell `ingredient_dish`
--

INSERT INTO `ingredient_dish` (`ingredient_id`, `dish_id`, `quantity`, `unit`) VALUES
  (2, 4, 200, 'g'),
  (3, 4, 100, 'g'),
  (3, 7, 150, 'g'),
  (4, 1, 5, 'g'),
  (4, 2, 5, 'g'),
  (4, 3, 10, 'g'),
  (4, 4, 6, 'g'),
  (4, 7, 10, 'g'),
  (5, 1, 5, 'g'),
  (5, 2, 5, 'g'),
  (5, 3, 5, 'g'),
  (5, 4, 7, 'g'),
  (5, 7, 10, 'g'),
  (6, 7, 150, 'g'),
  (7, 5, 120, 'g'),
  (8, 3, 150, 'g'),
  (9, 1, 1, 'units'),
  (10, 1, 1, 'units'),
  (10, 2, 1, 'units'),
  (11, 2, 200, 'g'),
  (12, 6, 130, 'g');

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `recurring_order`
--

CREATE TABLE IF NOT EXISTS `recurring_order` (
  `rec_order_id`    INT(11) NOT NULL AUTO_INCREMENT,
  `week_day`        INT(11) NOT NULL,
  `relative_time`   INT(11) NOT NULL,
  `subscription_id` INT(11) NOT NULL,
  `food_package_id` INT(11) NOT NULL,
  `amount`          INT(11) NOT NULL,
  PRIMARY KEY (`rec_order_id`),
  KEY `subscription_id` (`subscription_id`),
  KEY `food_package_id` (`food_package_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  AUTO_INCREMENT = 2;

--
-- Dataark for tabell `recurring_order`
--

INSERT INTO `recurring_order` (`rec_order_id`, `week_day`, `relative_time`, `subscription_id`, `food_package_id`, `amount`)
VALUES
  (1, 0, 81010, 1, 2, 1);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `subscription`
--

CREATE TABLE IF NOT EXISTS `subscription` (
  `subscription_id` INT(11)    NOT NULL AUTO_INCREMENT,
  `start_date`      DATE       NOT NULL,
  `end_date`        DATE       NOT NULL,
  `cost`            DOUBLE     NOT NULL,
  `customer_id`     INT(11)    NOT NULL,
  `active`          TINYINT(1) NOT NULL,
  PRIMARY KEY (`subscription_id`),
  KEY `customer_id` (`customer_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  AUTO_INCREMENT = 2;

--
-- Dataark for tabell `subscription`
--

INSERT INTO `subscription` (`subscription_id`, `start_date`, `end_date`, `cost`, `customer_id`, `active`) VALUES
  (1, '2016-04-27', '2016-05-26', 210, 3, 1);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `timesheet`
--

CREATE TABLE IF NOT EXISTS `timesheet` (
  `timesheet_id` INT(11)    NOT NULL AUTO_INCREMENT,
  `employee_id`  INT(11)    NOT NULL,
  `from_time`    DATETIME   NOT NULL,
  `to_time`      DATETIME            DEFAULT NULL,
  `active`       TINYINT(1) NOT NULL,
  PRIMARY KEY (`timesheet_id`, `employee_id`),
  KEY `employee_id` (`employee_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  AUTO_INCREMENT = 1;

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `_order`
--

CREATE TABLE IF NOT EXISTS `_order` (
  `_order_id`           INT(11)    NOT NULL AUTO_INCREMENT,
  `description`         VARCHAR(200)        DEFAULT NULL,
  `delivery_time`       DATETIME   NOT NULL,
  `_order_time`         DATETIME   NOT NULL,
  `portions`            INT(11)    NOT NULL,
  `priority`            TINYINT(1) NOT NULL,
  `salesperson_id`      INT(11)             DEFAULT NULL,
  `customer_id`         INT(11)    NOT NULL,
  `rec_order_id`        INT(11)             DEFAULT NULL,
  `status`              TINYINT(4) NOT NULL,
  `chauffeur_id`        INT(11)             DEFAULT NULL,
  `delivery_start_time` DATETIME            DEFAULT NULL,
  PRIMARY KEY (`_order_id`),
  KEY `rec_order_id` (`rec_order_id`),
  KEY `salesperson_id` (`salesperson_id`),
  KEY `customer_id` (`customer_id`),
  KEY `chauffeur_id` (`chauffeur_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1
  AUTO_INCREMENT = 6;

--
-- Dataark for tabell `_order`
--

INSERT INTO `_order` (`_order_id`, `description`, `delivery_time`, `_order_time`, `portions`, `priority`, `salesperson_id`, `customer_id`, `rec_order_id`, `status`, `chauffeur_id`, `delivery_start_time`)
VALUES
  (1, 'Vegetarian', '2016-04-29 17:25:00', '2016-04-26 22:26:27', 1, 1, 1, 3, NULL, 1, NULL, NULL),
  (2, 'Extra meat if possible', '2016-04-27 17:26:00', '2016-04-26 22:27:16', 1, 1, 1, 1, NULL, 1, NULL, NULL),
  (3, '', '2016-04-27 20:27:00', '2016-04-26 22:27:35', 1, 1, 1, 1, NULL, 1, NULL, NULL),
  (4, '', '2016-04-27 18:27:00', '2016-04-26 22:28:04', 2, 1, 1, 2, NULL, 1, NULL, NULL),
  (5, 'Extra fine', '2016-04-27 12:00:00', '2016-04-26 22:28:28', 3, 1, 1, 4, NULL, 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Tabellstruktur for tabell `_order_food_package`
--

CREATE TABLE IF NOT EXISTS `_order_food_package` (
  `_order_id`       INT(11) NOT NULL DEFAULT '0',
  `food_package_id` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`_order_id`, `food_package_id`),
  KEY `food_package_id` (`food_package_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dataark for tabell `_order_food_package`
--

INSERT INTO `_order_food_package` (`_order_id`, `food_package_id`) VALUES
  (1, 1),
  (2, 2),
  (4, 3),
  (3, 4),
  (5, 5);

--
-- Begrensninger for dumpede tabeller
--

--
-- Begrensninger for tabell `dish`
--
ALTER TABLE `dish`
ADD CONSTRAINT `dish_ibfk_1` FOREIGN KEY (`dish_type_id`) REFERENCES `dish_type` (`dish_type_id`)
  ON UPDATE CASCADE;

--
-- Begrensninger for tabell `dish_food_package`
--
ALTER TABLE `dish_food_package`
ADD CONSTRAINT `dish_food_package_ibfk_1` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`dish_id`)
  ON UPDATE CASCADE,
ADD CONSTRAINT `dish_food_package_ibfk_2` FOREIGN KEY (`food_package_id`) REFERENCES `food_package` (`food_package_id`)
  ON UPDATE CASCADE;

--
-- Begrensninger for tabell `employee`
--
ALTER TABLE `employee`
ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`e_type_id`) REFERENCES `employee_type` (`e_type_id`)
  ON UPDATE CASCADE;

--
-- Begrensninger for tabell `ingredient_dish`
--
ALTER TABLE `ingredient_dish`
ADD CONSTRAINT `ingredient_dish_ibfk_1` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredient` (`ingredient_id`)
  ON UPDATE CASCADE,
ADD CONSTRAINT `ingredient_dish_ibfk_2` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`dish_id`)
  ON UPDATE CASCADE;

--
-- Begrensninger for tabell `recurring_order`
--
ALTER TABLE `recurring_order`
ADD CONSTRAINT `recurring_order_ibfk_1` FOREIGN KEY (`subscription_id`) REFERENCES `subscription` (`subscription_id`),
ADD CONSTRAINT `recurring_order_ibfk_2` FOREIGN KEY (`food_package_id`) REFERENCES `food_package` (`food_package_id`);

--
-- Begrensninger for tabell `subscription`
--
ALTER TABLE `subscription`
ADD CONSTRAINT `subscription_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`);

--
-- Begrensninger for tabell `timesheet`
--
ALTER TABLE `timesheet`
ADD CONSTRAINT `timesheet_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
  ON UPDATE CASCADE;

--
-- Begrensninger for tabell `_order`
--
ALTER TABLE `_order`
ADD CONSTRAINT `_order_ibfk_1` FOREIGN KEY (`rec_order_id`) REFERENCES `recurring_order` (`rec_order_id`)
  ON UPDATE CASCADE,
ADD CONSTRAINT `_order_ibfk_2` FOREIGN KEY (`salesperson_id`) REFERENCES `employee` (`employee_id`)
  ON UPDATE CASCADE,
ADD CONSTRAINT `_order_ibfk_3` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
  ON UPDATE CASCADE,
ADD CONSTRAINT `_order_ibfk_4` FOREIGN KEY (`chauffeur_id`) REFERENCES `employee` (`employee_id`)
  ON UPDATE CASCADE;

--
-- Begrensninger for tabell `_order_food_package`
--
ALTER TABLE `_order_food_package`
ADD CONSTRAINT `_order_food_package_ibfk_1` FOREIGN KEY (`_order_id`) REFERENCES `_order` (`_order_id`)
  ON UPDATE CASCADE,
ADD CONSTRAINT `_order_food_package_ibfk_2` FOREIGN KEY (`food_package_id`) REFERENCES `food_package` (`food_package_id`)
  ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
