INSERT INTO pass_category (category, pass_duration, pass_expire_in_day, price)
VALUES ('Adult','Daily',1,300),
       ('Adult','Weekly',7,2300),
       ('Adult','Monthly',30,8200),
       ('Student','Daily',1,100),
       ('Student','Weekly',1,700),
       ('Student','Monthly',1,2500);

INSERT INTO client (email, password, provider, role)
VALUES ('customer@customer.com', password, 'LOCAL','CUSTOMER'),
       ('emp@bkr.com', 12345678, 'LOCAL','EMPLOYEE');

INSERT INTO stop (latitude, longitude, name)
VALUES (47.548802,19.029743,'Bécsi út / Vörösvári út'),
       (47.546903,19.032387,'Óbudai rendelőintézet'),
       (47.541360,19.040294,'Flórián tér'),
       (47.539448,19.045808,'Szentlélek tér'),
       (47.536155,19.059350,'Népfürdő utca / Árpád híd'),
       (47.533572,19.065620,'Göncz Árpád városközpont'),

       (47.539448,19.045808,'Erdőkerülő utca'),
       (47.539448,19.045808,'Fő tér'),
       (47.539448,19.045808,'Vásárcsarnok'),
       (47.539448,19.045808,'Sárfű utca'),
       (47.539448,19.045808,'Szerencs utca/ Bánkút utca'),
       (47.539448,19.045808,'Rákospalota, MÁV-telep'),
       (47.539448,19.045808,'Tóth István utca'),
       (47.539448,19.045808,'Öv utca'),
       (47.539448,19.045808,'Miskolci utca'),
       (47.539448,19.045808,'Rákospalota utca'),
       (47.539448,19.045808,'Fűrész utca'),
       (47.539448,19.045808,'Nagy Lajos király útja/ Czobor utca'),
       (47.539448,19.045808,'Laky Adolf utca'),
       (47.539448,19.045808,'Erzsébet királyné útja aluljáró'),
       (47.539448,19.045808,'Mexikói út');

INSERT INTO route (name, category_type)
VALUES ('69','TRAM'),
       ('1','METRO'),
       ('296','BUS'),
       ('70','BUS');