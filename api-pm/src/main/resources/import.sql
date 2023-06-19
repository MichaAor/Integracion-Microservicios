INSERT INTO Category(name) VALUES ('Manga');
INSERT INTO Category(name) VALUES ('Comic');
INSERT INTO Category(name) VALUES ('Novel');
INSERT INTO Category(name) VALUES ('ArtBook');

INSERT INTO Product(name,brand,stock,unit_price,release_date,register_date,last_mod_date) VALUES ('Saint Seiya Episode G 12','Ivrea',15,2600,'2008-08-20',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO Product(name,brand,stock,unit_price,release_date,register_date,last_mod_date) VALUES ('Spider-Man - Back in black 1','Marvel',10,5700,'2007-04-20',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO Product(name,brand,stock,unit_price,release_date,register_date,last_mod_date) VALUES ('The Hunger Games','Penguin',8,8000,'2008-09-14',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
INSERT INTO Product(name,brand,stock,unit_price,release_date,register_date,last_mod_date) VALUES ('Rurouni Kenshin Profiles','VIZ',2,15900,'2005-11-1',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

INSERT INTO product_category VALUES (1,1);
INSERT INTO product_category VALUES (2,2);
INSERT INTO product_category VALUES (3,3);
INSERT INTO product_category VALUES (4,1);
INSERT INTO product_category VALUES (4,4);