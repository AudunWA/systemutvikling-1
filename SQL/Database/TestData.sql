

INSERT INTO `customer` (`customer_id`, `surname`, `forename`, `address`, `active`, `phone`, `email`) VALUES
  (1, 'Sørensen', 'Elias', 'Valgrindvegen 14', 1, '234546453', 'Dragonslayer69@hotmail.com'),
  (2, 'W. Arbo', 'Audun', 'Anders tvereggens veg 9', 1, '876567898', 'mail@mail.com'),
  (3, 'Mahic', 'Kenan', 'Saupstadringen 21a', 1, '98765456', 'kenan@polsemail.com'),
  (4, 'Høydalsnes', 'Håvard', 'Jakobstien 6', 1, '43456876', 'barelegginn@mail.com');

INSERT INTO `dish_type` (`dish_type_id`, `name`) VALUES
  (1, 'Forrett'),
  (2, 'Hovedrett'),
  (3, 'Dessert');

INSERT INTO `dish` (`dish_id`, `name`, `description`, `dish_type_id`, `active`) VALUES
  (1, 'Vegetarian burger', 'Tasty vegetarian burger made with beans', 2, 1),
  (2, 'Meat burger', 'Tasty hamburger', 2, 1),
  (3, 'Tomato soup', 'Tasty and fresh appetizer', 1, 1),
  (4, 'Chicken with rice', 'Tasty and classic', 2, 1),
  (5, 'Vanilla ice cream', 'Excellent dessert', 3, 1),
  (6, 'Scallops', 'Fresh seafood appetizer', 1, 1),
  (7, 'Beef rice wok', 'cultural and nice', 2, 1),
  (8, 'Testgetdish', 'TestGetDish desc', 2, 1);

  INSERT INTO `food_package` (`food_package_id`, `name`, `cost`, `active`) VALUES
    (1, 'Vegetarian special', 270.5, 1),
    (2, 'Burger and dessert', 210, 1),
    (3, 'Beef wok full meal', 320.5, 1),
    (4, 'Chicken and rice main only', 200, 1),
    (5, 'Fine dining full meal', 410, 1);

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

  INSERT INTO `employee_type` (`e_type_id`, `e_type_name`, `salary`, `commission`) VALUES
    (1, 'chef', 235.99, 0),
    (2, 'chauffeur', 235.99, 0),
    (3, 'nutrition expert', 235.99, 0),
    (4, 'administrator', 277.29, 0),
    (5, 'sales person', 106.19, 11),
    (6, 'secretary', 206.49, 0);

INSERT INTO `employee` (`employee_id`, `username`, `forename`, `surname`, `address`, `phone`, `email`, `e_type_id`, `active`, `password`, `salt`, `salary`, `commission`)
VALUES
  (1, 'chechter', 'Christian', 'Echtermeyer', 'Stibakken 2', '99258217', 'chrech.birr@gmail.com', 4, 1,
      'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e', 'YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh', 277.29,
   0);


INSERT INTO `ingredient` (`ingredient_id`, `arrival_date`, `name`, `description`, `vegetarian`, `expire_date`, `amount`, `unit`)
VALUES
  (1, '2016-04-27 21:46:32', 'Chocolate', 'Tasty', 0, '2016-05-29', 20000, 'g'),
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

  INSERT INTO `subscription` (`subscription_id`, `start_date`, `end_date`, `cost`, `customer_id`, `active`) VALUES
    (1, '2016-04-27', '2016-05-26', 210, 3, 1);

INSERT INTO `recurring_order` (`rec_order_id`, `week_day`, `relative_time`, `subscription_id`, `food_package_id`, `amount`)
VALUES
  (1, 0, 81010, 1, 2, 1);


INSERT INTO `_order` (`_order_id`, `description`, `delivery_time`, `_order_time`, `portions`, `priority`, `salesperson_id`, `customer_id`, `rec_order_id`, `status`, `chauffeur_id`, `delivery_start_time`)
VALUES
  (1, 'Vegetarian', '2016-04-29 17:25:00', '2016-04-26 22:26:27', 1, 1, 1, 3, NULL, 1, NULL, NULL),
  (2, 'Extra meat if possible', '2016-04-27 17:26:00', '2016-04-26 22:27:16', 1, 1, 1, 1, NULL, 1, NULL, NULL),
  (3, '', '2016-04-27 20:27:00', '2016-04-26 22:27:35', 1, 1, 1, 1, NULL, 1, NULL, NULL),
  (4, '', '2016-04-27 18:27:00', '2016-04-26 22:28:04', 2, 1, 1, 2, NULL, 1, NULL, NULL),
  (5, 'Extra fine', '2016-04-27 12:00:00', '2016-04-26 22:28:28', 3, 1, 1, 4, NULL, 1, NULL, NULL);

INSERT INTO `_order_food_package` (`_order_id`, `food_package_id`) VALUES
  (1, 1),
  (2, 2),
  (4, 3),
  (3, 4),
  (5, 5);
