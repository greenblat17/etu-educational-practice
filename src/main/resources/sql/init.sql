create table usr
(
    id       bigserial primary key,
    password varchar(255) not null,
    username varchar(255) not null
);

create table public.document
(
    id            bigserial primary key,
    document_name varchar(255),
    document_text bytea,
    download_date date,
    update_date   date,
    author_id     bigint not null
);