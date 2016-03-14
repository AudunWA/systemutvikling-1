-- (employee_type) --
INSERT INTO employee_type VALUES (1, 'chef', 100);
INSERT INTO employee_type VALUES (2, 'chauffeur', 80);
INSERT INTO employee_type VALUES (3, 'nutrition expert', 110);
INSERT INTO employee_type VALUES (4, 'administrator', 120);
INSERT INTO employee_type VALUES (5, 'sales person', 50);
INSERT INTO employee_type VALUES (6, 'laundry staff', 60);

-- (employee) --
INSERT INTO employee VALUES (default, 'nootnoot', 'Knut', 'Kirkhorn', 'Pinguveien 23', '12345678', 'pingu@pingumail.com', 6,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');
INSERT INTO employee VALUES (default, 'eliasbs', 'Elias', 'Sørensen', 'Valgrindvegen', '88888888', 'dragonslayer69@hotmail.com', 1,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');
INSERT INTO employee VALUES (default, 'chechter', 'Christian', 'Echtermeyer', 'Stibakken 2', '99258217', 'chrech.birr@gmail.com', 4,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');
INSERT INTO employee VALUES (default, 'pisken', 'Kenan', 'Mahic', 'Kolstad', '123232323', 'kenanpølser@pølsefest.no', 3,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');
INSERT INTO employee VALUES (default, 'vebjørn', 'Audun', 'Arbo', 'Trondheim', '69696969', 'glassmesteren@knusemail.com', 2,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');
INSERT INTO employee VALUES (default, 'drammen', 'Håvard', 'Høydalsnes', 'Drammen',
'87654321', 'rave@ravemail.com', 5,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');

-- (employee types - includes all employee specialty tables) --
INSERT INTO employee VALUES (1);
INSERT INTO chef VALUES (2);
INSERT INTO administrator VALUES (3);
INSERT INTO nutrition_exp VALUES (4);
INSERT INTO chauffeur VALUES (5);
INSERT INTO salesperson VALUES (6);

-- (hours) --
INSERT INTO hours VALUES (default, DATE('2016-03-01'), 8);
INSERT INTO hours VALUES (default, DATE('2016-03-01'), 0);
INSERT INTO hours VALUES (default, DATE('2016-03-02'), 8);
INSERT INTO hours VALUES (default, DATE('2016-03-02'), 0);
INSERT INTO hours VALUES (default, DATE('2016-03-03'), 8);
INSERT INTO hours VALUES (default, DATE('2016-03-03'), 5);


-- (employee_hours) --
INSERT INTO hours_employee VALUES (1,1);
INSERT INTO hours_employee VALUES (2,1);
INSERT INTO hours_employee VALUES (3,1);
INSERT INTO hours_employee VALUES (4,1);
INSERT INTO hours_employee VALUES (5,1);
INSERT INTO hours_employee VALUES (6,2);
INSERT INTO hours_employee VALUES (1,3);
INSERT INTO hours_employee VALUES (2,3);
INSERT INTO hours_employee VALUES (3,3);
INSERT INTO hours_employee VALUES (4,4);
INSERT INTO hours_employee VALUES (5,3);
INSERT INTO hours_employee VALUES (6,3);

INSERT INTO hours_employee VALUES (1,6);
INSERT INTO hours_employee VALUES (2,6);
INSERT INTO hours_employee VALUES (3,5);
INSERT INTO hours_employee VALUES (4,5);
INSERT INTO hours_employee VALUES (5,5);
INSERT INTO hours_employee VALUES (6,5);

-- (ingredient) --
INSERT INTO ingredient VALUES (default, DATE('2016-09-20'), 'Pølse', 'AKA Kenans pølse', false, DATE('2016-10-20'));
INSERT INTO ingredient VALUES (default, DATE('2016-10-05'), 'Ost', 'Det e jo nais da', true, DATE('2016-10-16'));
INSERT INTO ingredient VALUES (default, DATE('2016-10-10'), 'Pizzabolle', 'The real mvp, premium, OG laken', true, DATE('2016-10-19'));
INSERT INTO ingredient VALUES (default, DATE('2016-10-20'), 'Laks', 'fishy fishy', true, DATE('2016-10-24'));
INSERT INTO ingredient VALUES (default, DATE('2016-09-06'), 'Bønner', 'Protein = Gøls', true, DATE('2016-10-26'));

-- (dish_type) --
INSERT INTO dish_type VALUES (default, 'Forrett');
INSERT INTO dish_type VALUES (default, 'Hovedrett');
INSERT INTO dish_type VALUES (default, 'Dessert');

-- (dish) --
INSERT INTO dish VALUES (default, 'Laks med bønner', 'Masse protein og fisk', 2);
INSERT INTO dish VALUES (default, 'Pizzabolle med ekstra ost', 'enda bedre OG laken', 2);
INSERT INTO dish VALUES (default, 'Pølse forrett', 'Kenan spesial F', 1);
INSERT INTO dish VALUES (default, 'Dessert bønner', 'sick dessert', 3);
INSERT INTO dish VALUES (default, 'Pølse hovedrett', 'Kenan spesial H', 2);
INSERT INTO dish VALUES (default, 'Pølse dessert', 'Kenan spesial D', 3);

-- (ingredient_dish) --
INSERT INTO ingredient_dish VALUES (4,1,200,'g');
INSERT INTO ingredient_dish VALUES (5,1,100,'g');

INSERT INTO ingredient_dish VALUES (3,2,200,'g');
INSERT INTO ingredient_dish VALUES (2,2,100,'g');

INSERT INTO ingredient_dish VALUES (1,3,1000,'g');

INSERT INTO ingredient_dish VALUES (5,4,100,'g');

INSERT INTO ingredient_dish VALUES (1,5,100,'g');
INSERT INTO ingredient_dish VALUES (1,6,50,'g');

-- (package) --

INSERT INTO _package VALUES (default, 'Kenan special', 100);
INSERT INTO _package VALUES (default, 'Balansert', 150);
INSERT INTO _package VALUES (default, 'Variert', 200);

-- (dish_package) --
INSERT INTO dish_package VALUES (3,1);
INSERT INTO dish_package VALUES (5,1);
INSERT INTO dish_package VALUES (6,1);

INSERT INTO dish_package VALUES (1,2);
INSERT INTO dish_package VALUES (2,2);
INSERT INTO dish_package VALUES (4,2);

INSERT INTO dish_package VALUES (1,3);
INSERT INTO dish_package VALUES (3,3);
INSERT INTO dish_package VALUES (6,3);

-- (subscription) --


-- (area) --
INSERT INTO area VALUES (default, 'Kolstad');
INSERT INTO area VALUES (default, 'Byåsen');
INSERT INTO area VALUES (default, 'Lerkendal');
INSERT INTO area VALUES (default, 'Malvik');
INSERT INTO area VALUES (default, 'Klæbu');

-- (customer) --
INSERT INTO customer VALUES (default, 'Gates', 'Bill', 'Rikmannsvegen 2', 2);
INSERT INTO customer VALUES (default, 'Zuckerberg', 'Mark', 'Rikmannsvegen 3', 2);
INSERT INTO customer VALUES (default, 'Smith', 'Will', 'lerkendalvegen 5', 3);
INSERT INTO customer VALUES (default, 'Olsen', 'Karl', 'Klæbuvegen 3', 5);
INSERT INTO customer VALUES (default, 'Karlsen', 'Ole', 'Stibakken 2', 4);
INSERT INTO customer VALUES (default, 'Berntsen', 'Bernt', 'Byåsvegen 4', 2);
INSERT INTO customer VALUES (default, 'Arnsen', 'Gustaff', 'Kolstadvegen 3', 1);
INSERT INTO customer VALUES (default, 'Olafdottir', 'Fredrikke', 'Hormonvegen 2', 1);

INSERT INTO subcsription VALUES (default, DATE('2016-03-10'), DATE('2016-03-30'),
-- (order) --

INSERT INTO _order VALUES (default, 'sunn ordre', DATE('2016-03-12'), DATE('2016-03-10'), 3, false,  6, 1,null);
INSERT INTO _order VALUES (default, 'kenans spesial', DATE('2016-03-17'), DATE('2016-03-09'), 3, false,  6,2,null);
INSERT INTO _order VALUES (default, 'balansert ordre', DATE('2016-03-12'), DATE('2016-03-09'), 3, false,  6, 3, null);
INSERT INTO _order VALUES (default, 'ekstra sunn ordre', DATE('2016-03-17'), DATE('2016-03-08'), 3, false,  6, 4, null);
INSERT INTO _order VALUES (default, 'pølsefest', DATE('2016-03-11'), DATE('2016-03-10'), 3, false,  6, 5, null);
INSERT INTO _order VALUES (default, 'helt super ordre', DATE('2016-03-22'), DATE('2016-02-10'), 3, false,  6, 6, null);

-- (_order_package) --

INSERT INTO _order_package VALUES(1,2);
INSERT INTO _order_package VALUES(2,1);
INSERT INTO _order_package VALUES(3,3);
INSERT INTO _order_package VALUES(3,2);
INSERT INTO _order_package VALUES(4,2);
INSERT INTO _order_package VALUES(5,1);
INSERT INTO _order_package VALUES(6,1);
INSERT INTO _order_package VALUES(6,2);
INSERT INTO _order_package VALUES(6,3);






