use `computer-database-db`;

drop table if exists user_role;
drop table if exists user;
drop table if exists role;

create table role (
    id bigint not null,
    name varchar(255),
    constraint pk_role primary key (id))
;

create table user (
    username varchar(255) not null,
    password varchar(255) not null,
    enabled tinyint not null default 1,
    constraint pk_user primary key (username))
;

create table user_role (
    idRole bigint not null,
    username varchar(255) not null,
    constraint pk_authority primary key (idRole, username))
;

alter table user_role add constraint fk_authority_1 foreign key (idRole) references role (id) on delete restrict on update restrict;
alter table user_role add constraint fk_authority_2 foreign key (username) references user (username) on delete restrict on update restrict;

INSERT INTO user(username, password) VALUES ('user','$2a$10$K6IPLRdgnB753J045WluiOeaWHncbEHJahutQmUz73koiq6E3JgzG');
INSERT INTO user(username, password) VALUES ('admin','$2a$10$K1GgiIREMk0H1LCQBF6cc.JtTR2x32i2VoCFDriYE0PEEvSduizw.');

INSERT INTO role(id, name) VALUES (1, 'USER');
INSERT INTO role(id, name) VALUES (2, 'ADMIN');

INSERT INTO user_role(idRole, username) VALUES (1, 'user');
INSERT INTO user_role(idRole, username) VALUES (2, 'admin');