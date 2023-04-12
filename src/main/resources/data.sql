insert into customers(username, password, type)
values ('admin', '$2a$10$2u0cGZb2Wx0bdOOggcZTaepe907zbjNip8OmpA/NrHcbLmmdKdbzC', 0);
insert into customers(username, password, type)
values ('customer1', '$2a$10$ctdvpbWa/OfSBv8.63Blr.qoLrRc1aaKbnVCNPTB2LVQBe7GIFWye', 1);
insert into customers(username, password, type)
values ('customer2', '$2a$10$ctdvpbWa/OfSBv8.63Blr.qoLrRc1aaKbnVCNPTB2LVQBe7GIFWye', 1);
insert into customers(username, password, type)
values ('customer3', '$2a$10$ctdvpbWa/OfSBv8.63Blr.qoLrRc1aaKbnVCNPTB2LVQBe7GIFWye', 1);
insert into customers(username, password, type)
values ('chien', '$2a$10$ctdvpbWa/OfSBv8.63Blr.qoLrRc1aaKbnVCNPTB2LVQBe7GIFWye', 1);
insert into customers(username, password, type)
values ('chien132', '$2a$10$ctdvpbWa/OfSBv8.63Blr.qoLrRc1aaKbnVCNPTB2LVQBe7GIFWye', 1);

insert into items(name, price)
values ('Logitech LM115G', 145000);
insert into items(name, price)
values ('Haylo GT1 Pro', 320000);
insert into items(name, price)
values ('RTX 4090', 52000000);

insert into orders(customerId, orderDate, completed)
values (2, '18-Mar-23', 0);
insert into orders(customerId, orderDate, completed)
values (2, '15-Mar-23', 0);
insert into orders(customerId, orderDate, completed)
values (2, '12-Mar-23', 0);

insert into orderDetails(orderId, itemId, quantity)
values (1, 1, 2);
insert into orderDetails(orderId, itemId, quantity)
values (1, 2, 1);
insert into orderDetails(orderId, itemId, quantity)
values (2, 3, 1);
insert into orderDetails(orderId, itemId, quantity)
values (3, 3, 1);
insert into orderDetails(orderId, itemId, quantity)
values (3, 2, 1);
insert into orderDetails(orderId, itemId, quantity)
values (3, 1, 1);

insert into carts(customerId)
values (2);
insert into carts(customerId)
values (1);
insert into cartDetails(cartId, itemId, quantity, dateAdded)
values (1, 3, 1, '15-Mar-23');
insert into cartDetails(cartId, itemId, quantity, dateAdded)
values (1, 1, 2, '17-Mar-23');
insert into cartDetails(cartId, itemId, quantity, dateAdded)
values (1, 2, 1, '23-Mar-23');


insert into items(name,price) values('Ống kính Fujifilm XF 56mm f/1.2 R',20900000);
insert into items(name,price) values('Fujifilm X-T3 WW (Chính hãng)',25990000);
insert into items(name,price) values('Fujifilm X-T20',16990000);
insert into items(name,price) values('Fujifilm X-T30 II (Chính hãng)',21990000);
insert into items(name,price) values('Fujifilm X-S10 (Chính hãng)',23990000);
insert into items(name,price) values('Sony A6000',13990000);
insert into items(name,price) values('Sony Alpha a6400',22990000);
insert into items(name,price) values('Sony Alpha a7 III',44990000);
insert into items(name,price) values('Sony Alpha a7 IV',59990000);
insert into items(name,price) values('Canon EOS M200 + Kit 15-45mm',15990000);
insert into items(name,price) values('Canon EOS M50 Mark II + Kit 15-45mm',20990000);
insert into items(name,price) values('Canon EOS R',42490000);
insert into items(name,price) values('Canon EOS RP',38000000);
insert into items(name,price) values('Olympus PEN-F',27500000);
insert into items(name,price) values('Nikon Z5',35500000);
insert into items(name,price) values('Nikon Z6',39990000);
insert into items(name,price) values('Nikon Z7',61490000);
insert into items(name,price) values('Canon EOS-1D X Mark III',169000000);
insert into items(name,price) values('Canon EOS 90D',32490000);
insert into items(name,price) values('Canon EOS 850D',24990000);
insert into items(name,price) values('Nikon D850',79000000);
insert into items(name,price) values('Nikon D750',48530000);
insert into items(name,price) values('Nikon D7500',27990000);
insert into items(name,price) values('Nikon D610',34200000);
insert into items(name,price) values('Nikon D5600 + Kit 18-55mm',15900000);
insert into items(name,price) values('Fujifilm XF 50mm f/1 R WR',37490000);
insert into items(name,price) values('Fujifilm XF 18-55mm f/2.8-4 R LM OIS',17120000);
insert into items(name,price) values('Sony E 16-55mm f/2.8 G',29990000);
insert into items(name,price) values('Sony E 50mm f/1.8 OSS',6990000);
insert into items(name,price) values('Sony E 18-200mm f/3.5-6.3 OSS',15990000);
insert into items(name,price) values('Sony E 24mm F/1.8 ZA E-Mount Carl Zeiss Sonnar',20990000);
insert into items(name,price) values('Nikon AF-S 85mm f/1.8G',14550000);
insert into items(name,price) values('Nikon NIKKOR Z 50mm f/1.8 S',12990000);
insert into items(name,price) values('Nikon AF-S 70-300mm f/4.5-5.6G IF ED',15140000);
insert into items(name,price) values('Nikon AF-S DX 18-140mm f/3.5-5.6G ED VR',8840000);
insert into items(name,price) values('Nikon AF-S 50mm f/1.8G',5990000);
insert into items(name,price) values('Nikon AF-S NIKKOR 200-500mm f/5.6E ED',31990000);
insert into items(name,price) values('Canon EF-S 18-55mm f/3.5-5.6 IS STM',2990000);
insert into items(name,price) values('Canon EF 50mm F/1.8 STM',3790000);
insert into items(name,price) values('Canon EF 24-70mm f/4L IS USM',26710000);
insert into items(name,price) values('Canon EF 85mm f/1.8 USM',12230000);
insert into items(name,price) values('Canon EF 24‐70mm f/2.8L II USM',56970000);
insert into items(name,price) values('Canon EF 16-35mm f/4L IS USM',26400000);
insert into items(name,price) values('SanDisk Extreme Pro SDHC 32GB',1050000);
insert into items(name,price) values('Sandisk Extreme Pro UHS-II U3 32GB',1550000);
insert into items(name,price) values('SanDisk Extreme Pro UHS-II U3 64GB',2750000);
insert into items(name,price) values('Pentax K-1 Mark II',45900000);
insert into items(name,price) values('Sạc Canon LC-E6',1330000);
insert into items(name,price) values('Bộ pin sạc Sony NP-BX1',610000);
insert into items(name,price) values('Sạc Fujifilm BC-W126S',1400000);
insert into items(name,price) values('Sạc Nikon MH-25',1920000);
insert into items(name,price) values('Đèn Flash Nikon Speedlight SB-700',9500000);
insert into items(name,price) values('Máy Film Canon AE1',1200000);
insert into items(name,price) values('Máy Film Pentax Asahi Spotmatic',1400000);
insert into items(name,price) values('Máy Film Full cơ Canon FTb',1100000);
insert into items(name,price) values('Máy ảnh Canon 1',1500000);
insert into items(name,price) values('Fujifilm X-T3 like new 99%',27000000);
