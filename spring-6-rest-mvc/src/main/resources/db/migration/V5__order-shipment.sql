create table beer_order_shipment (
                                     id varchar(36) not null,
                                     created_date timestamp(6),
                                     last_modified_date timestamp(6),
                                     tracking_number varchar(255),
                                     version bigint,
                                     beer_order_id varchar(36),
                                     primary key (id)
);
alter table if exists beer_order_shipment
    add constraint bos_pk
        foreign key (beer_order_id)
            references beer_order;

alter table beer_order
    add column beer_order_shipment_id varchar(36);

alter table if exists beer_order
    add constraint bos_shipment_fk
        foreign key (beer_order_shipment_id)
            references beer_order_shipment;
