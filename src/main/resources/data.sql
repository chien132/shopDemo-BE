insert into customers(username, password, type)
values ('Soumitra', 'soumitra@email.com', 1);
insert into customers(username, password, type)
values ('Liton', 'liton@email.com', 1);
insert into customers(username, password, type)
values ('Suman', 'suman@email.com', 1);
insert into customers(username, password, type)
values ('Debabrata', 'debabrata@email.com', 1);

insert into items(name, price)
values ('Logitech LM115G', 145000);
insert into items(name, price)
values ('Haylo GT1 Pro', 320000);
insert into items(name, price)
values ('RTX 4090', 52000000);

insert into orders(customerId, orderDate)
values (1, '2023-03-18');
insert into orders(customerId, orderDate)
values (2, '2023-03-15');
insert into orders(customerId, orderDate)
values (2, '2023-03-12');

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
values (1);
insert into cartDetails(cartId, itemId, quantity, dateAdded)
values (1, 3, 1, '2023-03-15');