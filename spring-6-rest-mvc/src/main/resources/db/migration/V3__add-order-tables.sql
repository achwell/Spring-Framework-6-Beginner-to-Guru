create table beer_order
(
    id                 varchar(36) not null,
    created_date       timestamp(6),
    customer_ref       varchar(255),
    last_modified_date timestamp(6),
    version            bigint,
    customer_id        varchar(36),
    primary key (id)
);
create table beer_order_line
(
    id                 varchar(36) not null,
    beer_id            varchar(36),
    created_date       timestamp(6),
    last_modified_date timestamp(6),
    order_quantity     integer,
    quantity_allocated integer,
    version            bigint,
    beer_order_id      varchar(36),
    primary key (id)
);
alter table if exists beer_order
    add constraint FK5siih2e7vpx70nx4wexpxpji
        foreign key (customer_id)
            references customer;

alter table if exists beer_order_line
    add constraint FKslskqsf79v6iekvb6d3gcc1l4
        foreign key (beer_id)
            references beer;

alter table if exists beer_order_line
    add constraint FKhkgofxhwx8yw9m3vat8mgtnxs
        foreign key (beer_order_id)
            references beer_order;
