-- (employee_type) --
INSERT INTO employee_type VALUES (1, 'chef', 235.99, 0);
INSERT INTO employee_type VALUES (2, 'chauffeur', 235.99, 0);
INSERT INTO employee_type VALUES (3, 'nutrition expert', 235.99, 0);
INSERT INTO employee_type VALUES (4, 'administrator', 277.29, 0);
INSERT INTO employee_type VALUES (5, 'sales person', 106.19, 11);
INSERT INTO employee_type VALUES (6, 'secretary', 206.49, 0);

-- (employee) --
INSERT INTO employee VALUES
  (default, 'nootnoot', 'Knut', 'Kirkhorn', 'Pinguveien 23', '12345678', 'pingu@pingumail.com', 6, TRUE,
            'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e', 'YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh',
            206.49, 0);
INSERT INTO employee VALUES
  (default, 'eliasbs', 'Elias', 'Sørensen', 'Valgrindvegen', '88888888', 'dragonslayer69@hotmail.com', 1, TRUE,
            'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e', 'YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh',
            235.99, 0);
INSERT INTO employee VALUES
  (default, 'chechter', 'Christian', 'Echtermeyer', 'Stibakken 2', '99258217', 'chrech.birr@gmail.com', 4, TRUE,
            'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e', 'YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh',
            277.29, 0);
INSERT INTO employee VALUES
  (default, 'pisken', 'Kenan', 'Mahic', 'Kolstad', '123232323', 'kenanpølser@pølsefest.no', 3, TRUE,
            'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e', 'YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh',
            235.99, 0);
INSERT INTO employee VALUES
  (default, 'vebjørn', 'Audun', 'Arbo', 'Trondheim', '69696969', 'glassmesteren@knusemail.com', 2, TRUE,
            'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e', 'YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh',
            235.99, 0);
INSERT INTO employee VALUES (default, 'drammen', 'Håvard', 'Høydalsnes', 'Drammen',
                                      '87654321', 'rave@ravemail.com', 5, FALSE,
                                      'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e',
                                      'YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh',
                                      106.19, 11);

-- (timesheet) --
INSERT INTO timesheet VALUES (DEFAULT, 1, '2015-12-31 11:30:45', '2015-12-31 17:30:45', TRUE);
INSERT INTO timesheet VALUES (DEFAULT, 3, '2015-12-31 10:30:45', '2015-12-31 17:40:45', TRUE);
INSERT INTO timesheet VALUES (DEFAULT, 3, '2016-01-01 11:30:45', '2015-12-31 17:30:45', TRUE);
INSERT INTO timesheet VALUES (DEFAULT, 3, '2016-01-02 11:00:45', '2015-12-31 17:40:45', FALSE);
INSERT INTO timesheet VALUES (DEFAULT, 1, '2016-01-31 11:30:45', '2015-12-31 19:30:45', TRUE);

-- (ingredient) --
INSERT INTO ingredient
VALUES (default, '2012-12-31 11:30:45', 'Pølse', 'AKA Kenans pølse', FALSE, DATE('2016-10-20'), 5, "units");
INSERT INTO ingredient
VALUES (default, '2016-10-05 10:10:10', 'Ost', 'Det e jo nais da', TRUE, DATE('2016-10-16'), 1, "kg");
INSERT INTO ingredient VALUES
  (default, '2016-10-10 10:10:10', 'Pizzabolle', 'The real mvp, premium, OG laken', TRUE, DATE('2016-10-19'), 5,
   "units");
INSERT INTO ingredient
VALUES (default, '2016-10-20 10:10:10', 'Laks', 'fishy fishy', TRUE, DATE('2016-10-24'), 12, "units");
INSERT INTO ingredient
VALUES (default, '2016-09-06 10:10:10', 'Bønner', 'Protein = Gøls', TRUE, DATE('2016-10-26'), 5, "liters");

-- (dish_type) --
INSERT INTO dish_type VALUES (default, 'Forrett');
INSERT INTO dish_type VALUES (default, 'Hovedrett');
INSERT INTO dish_type VALUES (default, 'Dessert');

-- (dish) --
INSERT INTO dish VALUES (default, 'Laks med bønner', 'Masse protein og fisk', 2, TRUE);
INSERT INTO dish VALUES (default, 'Pizzabolle med ekstra ost', 'enda bedre OG laken', 2, TRUE);
INSERT INTO dish VALUES (default, 'Pølse forrett', 'Kenan spesial F', 1, TRUE);
INSERT INTO dish VALUES (default, 'Dessert bønner', 'sick dessert', 3, TRUE);
INSERT INTO dish VALUES (default, 'Pølse hovedrett', 'Kenan spesial H', 2, TRUE);
INSERT INTO dish VALUES (default, 'Pølse dessert', 'Kenan spesial D', 3, FALSE);

-- (ingredient_dish) --
INSERT INTO ingredient_dish VALUES (4, 1, 200.0, 'g');
INSERT INTO ingredient_dish VALUES (5, 1, 100.0, 'g');

INSERT INTO ingredient_dish VALUES (3, 2, 200.0, 'g');
INSERT INTO ingredient_dish VALUES (2, 2, 100.0, 'g');

INSERT INTO ingredient_dish VALUES (1, 3, 1000.0, 'g');

INSERT INTO ingredient_dish VALUES (5, 4, 100.0, 'g');

INSERT INTO ingredient_dish VALUES (1, 5, 100.0, 'g');
INSERT INTO ingredient_dish VALUES (1, 6, 50.0, 'g');

-- (food_package) --

INSERT INTO food_package VALUES (default, 'Kenan special', 100, TRUE);
INSERT INTO food_package VALUES (default, 'Balansert', 150, TRUE);
INSERT INTO food_package VALUES (default, 'Variert', 200, TRUE);

-- (dish_food_package) --
INSERT INTO dish_food_package VALUES (3, 1);
INSERT INTO dish_food_package VALUES (5, 1);
INSERT INTO dish_food_package VALUES (6, 1);

INSERT INTO dish_food_package VALUES (1, 2);
INSERT INTO dish_food_package VALUES (2, 2);
INSERT INTO dish_food_package VALUES (4, 2);

INSERT INTO dish_food_package VALUES (1, 3);
INSERT INTO dish_food_package VALUES (3, 3);
INSERT INTO dish_food_package VALUES (6, 3);

-- (customer) --
INSERT INTO customer VALUES (default, 'Gates', 'Bill', 'Jomfrugata 5', TRUE, '12345678', 'mail@m.com');
INSERT INTO customer
VALUES (default, 'Zuckerberg', 'Mark', 'Vestre Moholt-tun 21', TRUE, '87654321', 'testmail@testmail.com');
INSERT INTO customer VALUES (default, 'Smith', 'Will', 'Klosterenget 6', TRUE, '13245768', 'ayy@lmao.mail');
INSERT INTO customer VALUES (default, 'Olsen', 'Karl', 'Klæbuvegen 3', TRUE, '86754231', 'trondheim@mail.com');
INSERT INTO customer VALUES (default, 'Karlsen', 'Ole', 'Stibakken 2', TRUE, '45362718', 'mail@mail.mail');
INSERT INTO customer VALUES (default, 'Berntsen', 'Bernt', 'Byåsvegen 4', TRUE, '81726354', 'noot@noot.noot');
INSERT INTO customer VALUES (default, 'Arnsen', 'Gustaff', 'Kolstadvegen 3', TRUE, '76543218', 'eg@ekkje.sur');
INSERT INTO customer VALUES (default, 'Olafdottir', 'Fredrikke', 'Sem Sælands vei 5', TRUE, '647382816', 'a@b.c');

-- (subscription) --
INSERT INTO subscription VALUES (default, DATE('2016-03-10'), DATE('2016-03-30'), 400, 1, TRUE);

-- (order) --

INSERT INTO _order VALUES (default, 'sunn ordre', '2016-05-12', '2016-03-10', 3, FALSE, 6, 1, NULL, 1, NULL, NULL);
INSERT INTO _order VALUES (default, 'kenans spesial', '2016-05-17', '2016-03-09', 3, FALSE, 6, 2, NULL, 1, NULL, NULL);
INSERT INTO _order VALUES (default, 'balansert ordre', '2016-05-12', '2016-03-09', 3, FALSE, 6, 3, NULL, 1, NULL, NULL);
INSERT INTO _order
VALUES (default, 'ekstra sunn ordre', '2016-05-17', '2016-03-08', 3, FALSE, 6, 4, NULL, 1, NULL, NULL);
INSERT INTO _order VALUES (default, 'pølsefest', '2016-05-11', '2016-03-10', 3, FALSE, 6, 5, NULL, 1, NULL, NULL);
INSERT INTO _order
VALUES (default, 'helt super ordre', '2016-05-22', '2016-02-10', 3, FALSE, 6, 6, NULL, 1, NULL, NULL);

-- (_order_food_package) --

INSERT INTO _order_food_package VALUES (1, 2);
INSERT INTO _order_food_package VALUES (2, 1);
INSERT INTO _order_food_package VALUES (3, 3);
INSERT INTO _order_food_package VALUES (3, 2);
INSERT INTO _order_food_package VALUES (4, 2);
INSERT INTO _order_food_package VALUES (5, 1);
INSERT INTO _order_food_package VALUES (6, 1);
INSERT INTO _order_food_package VALUES (6, 2);
INSERT INTO _order_food_package VALUES (6, 3);








