create table ROLE
(
    ID          VARCHAR(45) not null primary key,
    NAME        VARCHAR(255)
);
create table USER
(
    ID           VARCHAR(45) not null primary key,
    NAME         VARCHAR(255),
    OPENING_BANK VARCHAR(255),
    PHONE_NUMBER VARCHAR(255),
    ROLE_ID      VARCHAR(45)
);