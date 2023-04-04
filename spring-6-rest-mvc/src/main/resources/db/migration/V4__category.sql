create table category (
                          id varchar(36) not null,
                          created_date timestamp(6),
                          description varchar(255),
                          last_modified_date timestamp(6),
                          version bigint,
                          primary key (id)
);
create table beer_category (
                               category_id varchar(36) not null,
                               beer_id varchar(36) not null,
                               primary key (category_id, beer_id)
);
alter table if exists beer_category
    add constraint FK7o42knkmhb44bhnsb804o16ch
        foreign key (beer_id)
            references beer;
alter table if exists beer_category
    add constraint FKrmk65j5tao1q8mp3v4mkpesie
        foreign key (category_id)
            references category;
