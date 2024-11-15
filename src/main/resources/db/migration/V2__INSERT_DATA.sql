insert into customers(id, username, password, type)
values (1, 'admin', '$2a$10$2u0cGZb2Wx0bdOOggcZTaepe907zbjNip8OmpA/NrHcbLmmdKdbzC', 0),
       (2, 'customer1', '$2a$10$ctdvpbWa/OfSBv8.63Blr.qoLrRc1aaKbnVCNPTB2LVQBe7GIFWye', 1),
       (3, 'customer2', '$2a$10$ctdvpbWa/OfSBv8.63Blr.qoLrRc1aaKbnVCNPTB2LVQBe7GIFWye', 1),
       (4, 'customer3', '$2a$10$ctdvpbWa/OfSBv8.63Blr.qoLrRc1aaKbnVCNPTB2LVQBe7GIFWye', 1),
       (5, 'chien', '$2a$10$ctdvpbWa/OfSBv8.63Blr.qoLrRc1aaKbnVCNPTB2LVQBe7GIFWye', 1),
       (6, 'chien132', '$2a$10$ctdvpbWa/OfSBv8.63Blr.qoLrRc1aaKbnVCNPTB2LVQBe7GIFWye', 1);

insert into items(id, name, price)
values (1, 'Logitech LM115G', 145000),
       (2, 'Haylo GT1 Pro', 320000),
       (3, 'RTX 4090', 52000000);

insert into orders(id, customerId, orderDate, completed)
values (1, 2, '2023-03-18', 0),
       (2, 2, '2023-03-15', 0),
       (3, 2, '2023-03-12', 0);

insert into orderDetails(orderId, itemId, quantity)
values (1, 1, 2),
       (1, 2, 1),
       (2, 3, 1),
       (3, 3, 1),
       (3, 2, 1),
       (3, 1, 1);

insert into carts(id, customerId)
values (1, 2),
       (2, 3);

insert into cartDetails(cartId, itemId, quantity, dateAdded)
values (1, 1, 1, '2023-03-15'),
       (1, 2, 2, '2023-03-17'),
       (1, 3, 1, '2023-03-23');


insert into items(name,price) values('Ống kính Fujifilm XF 56mm f/1.2 R',20900000),
                                    ('Fujifilm X-T3 WW (Chính hãng)',25990000),
                                    ('Fujifilm X-T20',16990000),
                                    ('Fujifilm X-T30 II (Chính hãng)',21990000),
                                    ('Fujifilm X-S10 (Chính hãng)',23990000),
                                    ('Sony A6000',13990000),
                                    ('Sony Alpha a6400',22990000),
                                    ('Sony Alpha a7 III',44990000),
                                    ('Sony Alpha a7 IV',59990000),
                                    ('Canon EOS M200 + Kit 15-45mm',15990000),
                                    ('Canon EOS M50 Mark II + Kit 15-45mm',20990000),
                                    ('Canon EOS R',42490000),
                                    ('Canon EOS RP',38000000),
                                    ('Olympus PEN-F',27500000),
                                    ('Nikon Z5',35500000),
                                    ('Nikon Z6',39990000),
                                    ('Nikon Z7',61490000),
                                    ('Canon EOS-1D X Mark III',169000000),
                                    ('Canon EOS 90D',32490000),
                                    ('Canon EOS 850D',24990000),
                                    ('Nikon D850',79000000),
                                    ('Nikon D750',48530000),
                                    ('Nikon D7500',27990000),
                                    ('Nikon D610',34200000),
                                    ('Nikon D5600 + Kit 18-55mm',15900000),
                                    ('Fujifilm XF 50mm f/1 R WR',37490000),
                                    ('Fujifilm XF 18-55mm f/2.8-4 R LM OIS',17120000),
                                    ('Sony E 16-55mm f/2.8 G',29990000),
                                    ('Sony E 50mm f/1.8 OSS',6990000),
                                    ('Sony E 18-200mm f/3.5-6.3 OSS',15990000),
                                    ('Sony E 24mm F/1.8 ZA E-Mount Carl Zeiss Sonnar',20990000),
                                    ('Nikon AF-S 85mm f/1.8G',14550000),
                                    ('Nikon NIKKOR Z 50mm f/1.8 S',12990000),
                                    ('Nikon AF-S 70-300mm f/4.5-5.6G IF ED',15140000),
                                    ('Nikon AF-S DX 18-140mm f/3.5-5.6G ED VR',8840000),
                                    ('Nikon AF-S 50mm f/1.8G',5990000),
                                    ('Nikon AF-S NIKKOR 200-500mm f/5.6E ED',31990000),
                                    ('Canon EF-S 18-55mm f/3.5-5.6 IS STM',2990000),
                                    ('Canon EF 50mm F/1.8 STM',3790000),
                                    ('Canon EF 24-70mm f/4L IS USM',26710000),
                                    ('Canon EF 85mm f/1.8 USM',12230000),
                                    ('Canon EF 24‐70mm f/2.8L II USM',56970000),
                                    ('Canon EF 16-35mm f/4L IS USM',26400000),
                                    ('SanDisk Extreme Pro SDHC 32GB',1050000),
                                    ('Sandisk Extreme Pro UHS-II U3 32GB',1550000),
                                    ('SanDisk Extreme Pro UHS-II U3 64GB',2750000),
                                    ('Pentax K-1 Mark II',45900000),
                                    ('Sạc Canon LC-E6',1330000),
                                    ('Bộ pin sạc Sony NP-BX1',610000),
                                    ('Sạc Fujifilm BC-W126S',1400000),
                                    ('Sạc Nikon MH-25',1920000),
                                    ('Đèn Flash Nikon Speedlight SB-700',9500000),
                                    ('Máy Film Canon AE1',1200000),
                                    ('Máy Film Pentax Asahi Spotmatic',1400000),
                                    ('Máy Film Full cơ Canon FTb',1100000),
                                    ('Máy ảnh Canon 1',1500000),
                                    ('Fujifilm X-T3 like new 99%',27000000);
