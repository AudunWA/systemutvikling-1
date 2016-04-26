-- (employee_type) --
INSERT INTO employee_type VALUES (1, 'chef', 235.99, 0);
INSERT INTO employee_type VALUES (2, 'chauffeur', 235.99, 0);
INSERT INTO employee_type VALUES (3, 'nutrition expert', 235.99, 0);
INSERT INTO employee_type VALUES (4, 'administrator', 277.29, 0);
INSERT INTO employee_type VALUES (5, 'sales person', 106.19, 11);
INSERT INTO employee_type VALUES (6, 'secretary', 206.49, 0);

-- (employee) --
INSERT INTO employee VALUES
  (default, 'chechter', 'Christian', 'Echtermeyer', 'Stibakken 2', '99258217', 'chrech.birr@gmail.com', 4, TRUE,
            'dd1a1d47e15b4e2165f597ded84c05640c4506ba30d76a7d25e7726af37dc41e', 'YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh',
            277.29, 0);

-- (dish_type) --
INSERT INTO dish_type VALUES (default, 'Forrett');
INSERT INTO dish_type VALUES (default, 'Hovedrett');
INSERT INTO dish_type VALUES (default, 'Dessert');