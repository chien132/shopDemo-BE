create table if not exists customers
(
    id           int auto_increment
        primary key,
    type         bit                                   not null,
    password     varchar(255)                          not null,
    username     varchar(255)                          not null,
    created_date timestamp   default CURRENT_TIMESTAMP not null,
    created_by   varchar(20) default 'init'            null,
    updated_date timestamp                             null,
    updated_by   varchar(20)                           null,
    constraint UKbepynu3b6l8k2ppuq6b33xfxc
        unique (username)
);

create table if not exists carts
(
    customerId   int                                   not null,
    id           int auto_increment
        primary key,
    created_date timestamp   default CURRENT_TIMESTAMP not null,
    created_by   varchar(20) default 'init'            null,
    updated_date timestamp                             null,
    updated_by   varchar(20)                           null,
    constraint FKkmmj5xhd9wwovmii271x88uj5
        foreign key (customerId) references customers (id)
);

create table if not exists items
(
    id           int auto_increment
        primary key,
    price        double                                not null,
    name         varchar(255)                          not null,
    created_date timestamp   default CURRENT_TIMESTAMP not null,
    created_by   varchar(20) default 'init'            null,
    updated_date timestamp                             null,
    updated_by   varchar(20)                           null,
    check (`price` >= 0)
);

create table if not exists cartdetails
(
    cartId       int                                   not null,
    dateAdded    date                                  not null,
    id           int auto_increment
        primary key,
    itemId       int                                   not null,
    quantity     int                                   not null,
    created_date timestamp   default CURRENT_TIMESTAMP not null,
    created_by   varchar(20) default 'init'            null,
    updated_date timestamp                             null,
    updated_by   varchar(20)                           null,
    constraint FK2iyyk7rvs6fqwo7wullqi6gpx
        foreign key (cartId) references carts (id),
    constraint FK9evd6rd6pkwxbpplrjeml7s2k
        foreign key (itemId) references items (id)
);

create table if not exists orders
(
    completed    bit                                   not null,
    customerId   int                                   not null,
    id           int auto_increment
        primary key,
    orderDate    date                                  not null,
    created_date timestamp   default CURRENT_TIMESTAMP not null,
    created_by   varchar(20) default 'init'            null,
    updated_date timestamp                             null,
    updated_by   varchar(20)                           null,
    constraint FK1bpj2iini89gbon333nm7tvht
        foreign key (customerId) references customers (id)
);

create table if not exists orderdetails
(
    id           int auto_increment
        primary key,
    itemId       int                                   not null,
    orderId      int                                   not null,
    quantity     int                                   not null,
    created_date timestamp   default CURRENT_TIMESTAMP not null,
    created_by   varchar(20) default 'init'            null,
    updated_date timestamp                             null,
    updated_by   varchar(20)                           null,
    constraint FK3ohml2o6a85wh1nn65snnaind
        foreign key (orderId) references orders (id),
    constraint FKbey6xuy2aier3hyvp6dgkttnb
        foreign key (itemId) references items (id)
);

