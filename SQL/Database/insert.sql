-- (employee_type) --
INSERT INTO employee_type VALUES (1, 'chef', 100);
INSERT INTO employee_type VALUES (2, 'chauffeur', 80);
INSERT INTO employee_type VALUES (3, 'nutrition expert', 110);
INSERT INTO employee_type VALUES (4, 'administrator', 120);
INSERT INTO employee_type VALUES (5, 'sales person', 50);
INSERT INTO employee_type VALUES (6, 'laundry staff', 60);

-- (employee) --
INSERT INTO employee VALUES (default, 'nootnoot', 'Knut', 'Kirkhorn', 'Pinguveien 23', '12345678', 'pingu@pingumail.com', 6,true,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');
INSERT INTO employee VALUES (default, 'eliasbs', 'Elias', 'Sørensen', 'Valgrindvegen', '88888888', 'dragonslayer69@hotmail.com', 1,true,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');
INSERT INTO employee VALUES (default, 'chechter', 'Christian', 'Echtermeyer', 'Stibakken 2', '99258217', 'chrech.birr@gmail.com', 4,false,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');
INSERT INTO employee VALUES (default, 'pisken', 'Kenan', 'Mahic', 'Kolstad', '123232323', 'kenanpølser@pølsefest.no', 3,true,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');
INSERT INTO employee VALUES (default, 'vebjørn', 'Audun', 'Arbo', 'Trondheim', '69696969', 'glassmesteren@knusemail.com', 2,true,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');
INSERT INTO employee VALUES (default, 'drammen', 'Håvard', 'Høydalsnes', 'Drammen',
'87654321', 'rave@ravemail.com', 5,false,'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e','YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh');

-- (employee types - includes all employee specialty tables) --
INSERT INTO chef VALUES (2);
INSERT INTO administrator VALUES (3);
INSERT INTO nutrition_exp VALUES (4);
INSERT INTO chauffeur VALUES (5);
INSERT INTO salesperson VALUES (6);

-- (hours) --
-- INSERT INTO hours VALUES (1, DATETIME('2016-03-01'), 8);
-- INSERT INTO hours VALUES (2, DATETIME('2016-03-01'), 0);
-- INSERT INTO hours VALUES (1, DATETIME('2016-03-02'), 8);
-- INSERT INTO hours VALUES (2, DATETIME('2016-03-02'), 0);
-- INSERT INTO hours VALUES (1, DATETIME('2016-03-03'), 8);
-- INSERT INTO hours VALUES (3, DATETIME('2016-03-03'), 5);

-- (ingredient) --
INSERT INTO ingredient VALUES (default, '2012-12-31 11:30:45', 'Pølse', 'AKA Kenans pølse', false, DATE('2016-10-20'), 5, "units");
INSERT INTO ingredient VALUES (default, '2016-10-05 10:10:10', 'Ost', 'Det e jo nais da', true, DATE('2016-10-16'), 1, "kg");
INSERT INTO ingredient VALUES (default, '2016-10-10 10:10:10', 'Pizzabolle', 'The real mvp, premium, OG laken', true, DATE('2016-10-19'), 5, "units");
INSERT INTO ingredient VALUES (default, '2016-10-20 10:10:10', 'Laks', 'fishy fishy', true, DATE('2016-10-24'), 12, "units");
INSERT INTO ingredient VALUES (default, '2016-09-06 10:10:10', 'Bønner', 'Protein = Gøls', true, DATE('2016-10-26'), 5, "liters");

-- (dish_type) --
INSERT INTO dish_type VALUES (default, 'Forrett');
INSERT INTO dish_type VALUES (default, 'Hovedrett');
INSERT INTO dish_type VALUES (default, 'Dessert');

-- (dish) --
INSERT INTO dish VALUES (default, 'Laks med bønner', 'Masse protein og fisk', 2,true);
INSERT INTO dish VALUES (default, 'Pizzabolle med ekstra ost', 'enda bedre OG laken', 2,true);
INSERT INTO dish VALUES (default, 'Pølse forrett', 'Kenan spesial F', 1,true);
INSERT INTO dish VALUES (default, 'Dessert bønner', 'sick dessert', 3,true);
INSERT INTO dish VALUES (default, 'Pølse hovedrett', 'Kenan spesial H', 2,true);
INSERT INTO dish VALUES (default, 'Pølse dessert', 'Kenan spesial D', 3,false);

-- (ingredient_dish) --
INSERT INTO ingredient_dish VALUES (4,1,200,'g');
INSERT INTO ingredient_dish VALUES (5,1,100,'g');

INSERT INTO ingredient_dish VALUES (3,2,200,'g');
INSERT INTO ingredient_dish VALUES (2,2,100,'g');

INSERT INTO ingredient_dish VALUES (1,3,1000,'g');

INSERT INTO ingredient_dish VALUES (5,4,100,'g');

INSERT INTO ingredient_dish VALUES (1,5,100,'g');
INSERT INTO ingredient_dish VALUES (1,6,50,'g');

-- (food_package) --

INSERT INTO food_package VALUES (default, 'Kenan special', 100,true);
INSERT INTO food_package VALUES (default, 'Balansert', 150,true);
INSERT INTO food_package VALUES (default, 'Variert', 200,true);

-- (dish_food_package) --
INSERT INTO dish_food_package VALUES (3,1);
INSERT INTO dish_food_package VALUES (5,1);
INSERT INTO dish_food_package VALUES (6,1);

INSERT INTO dish_food_package VALUES (1,2);
INSERT INTO dish_food_package VALUES (2,2);
INSERT INTO dish_food_package VALUES (4,2);

INSERT INTO dish_food_package VALUES (1,3);
INSERT INTO dish_food_package VALUES (3,3);
INSERT INTO dish_food_package VALUES (6,3);

-- (area) --
-- INSERT INTO area VALUES (default, 'Kolstad');
-- INSERT INTO area VALUES (default, 'Byåsen');
-- INSERT INTO area VALUES (default, 'Lerkendal');
-- INSERT INTO area VALUES (default, 'Malvik');
-- INSERT INTO area VALUES (default, 'Klæbu');

-- (customer) --
INSERT INTO customer VALUES (default, 'Gates', 'Bill', 'Rikmannsvegen 2', true, '12345678', 'mail@m.com');
INSERT INTO customer VALUES (default, 'Zuckerberg', 'Mark', 'Rikmannsvegen 3',true, '87654321' ,'testmail@testmail.com');
INSERT INTO customer VALUES (default, 'Smith', 'Will', 'lerkendalvegen 5', true, '13245768' ,'ayy@lmao.mail');
INSERT INTO customer VALUES (default, 'Olsen', 'Karl', 'Klæbuvegen 3', true, '86754231' ,'trondheim@mail.com');
INSERT INTO customer VALUES (default, 'Karlsen', 'Ole', 'Stibakken 2', true, '45362718' ,'mail@mail.mail');
INSERT INTO customer VALUES (default, 'Berntsen', 'Bernt', 'Byåsvegen 4',true, '81726354' ,'noot@noot.noot');
INSERT INTO customer VALUES (default, 'Arnsen', 'Gustaff', 'Kolstadvegen 3', true, '76543218' ,'eg@ekkje.sur');
INSERT INTO customer VALUES (default, 'Olafdottir', 'Fredrikke', 'Hormonvegen 2',true, '647382816' ,'a@b.c');

-- (subscription) --
INSERT INTO subscription VALUES (default, DATE('2016-03-10'), DATE('2016-03-30'),400,1,true);


-- (order) --

INSERT INTO _order VALUES (default, 'sunn ordre', '2016-03-12', '2016-03-10', 3, false,  6, 1,null, 0, null);
INSERT INTO _order VALUES (default, 'kenans spesial', '2016-03-17', '2016-03-09', 3, false,  6,2,null,0, null);
INSERT INTO _order VALUES (default, 'balansert ordre', '2016-03-12', '2016-03-09', 3, false,  6, 3, null,0, null);
INSERT INTO _order VALUES (default, 'ekstra sunn ordre', '2016-03-17', '2016-03-08', 3, false,  6, 4, null,0, null);
INSERT INTO _order VALUES (default, 'pølsefest', '2016-03-11', '2016-03-10', 3, false,  6, 5, null,0, null);
INSERT INTO _order VALUES (default, 'helt super ordre', '2016-03-22', '2016-02-10', 3, false,  6, 6, null,0, null);

-- (_order_food_package) --

INSERT INTO _order_food_package VALUES(1,2);
INSERT INTO _order_food_package VALUES(2,1);
INSERT INTO _order_food_package VALUES(3,3);
INSERT INTO _order_food_package VALUES(3,2);
INSERT INTO _order_food_package VALUES(4,2);
INSERT INTO _order_food_package VALUES(5,1);
INSERT INTO _order_food_package VALUES(6,1);
INSERT INTO _order_food_package VALUES(6,2);
INSERT INTO _order_food_package VALUES(6,3);








