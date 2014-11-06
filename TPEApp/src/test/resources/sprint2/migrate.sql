ALTER USER paw WITH PASSWORD 'paw';
create table CommentRate (id  serial not null, rating int4 not null, comment_id int4, user_id int4, primary key (id));
create table Film_Genre (Film_id int4 not null, genres_id int4 not null);

insert into  Film_Genre select film.id, genre.id from film,genre where film.genre=genre.genre;

alter table GAJAmdbUser add column admin bool;

update gajamdbuser set admin=false;
update gajamdbuser set admin=true where id = 4;

create table GAJAmdbUser_GAJAmdbUser (GAJAmdbUser_id int4 not null, follows_id int4 not null);
create table Genre (id  serial not null, genre varchar(30) not null, primary key (id));
insert into genre (genre)  (select distinct(genre) from film);
create table Report (id  serial not null, comment_id int4, user_id int4, primary key (id));
create table image (id  serial not null, content oid not null, contentType varchar(50) not null, length int4 not null, name varchar(50) not null, film_id int4, primary key (id));
alter table Comment add constraint FK9BDE863FD8DE7209 foreign key (user_id) references GAJAmdbUser;
alter table Comment add constraint FK9BDE863F599F0D50 foreign key (film_id) references Film;
alter table CommentRate add constraint FKE0CA0FBFD8DE7209 foreign key (user_id) references GAJAmdbUser;
alter table CommentRate add constraint FKE0CA0FBF2768CF77 foreign key (comment_id) references Comment;
alter table Film_Genre add constraint FK9B611B08FBCC566E foreign key (genres_id) references Genre;
alter table Film_Genre add constraint FK9B611B08599F0D50 foreign key (Film_id) references Film;
alter table GAJAmdbUser_GAJAmdbUser add constraint FKA0996D8B68DF3792 foreign key (follows_id) references GAJAmdbUser;
alter table GAJAmdbUser_GAJAmdbUser add constraint FKA0996D8B6529F04F foreign key (GAJAmdbUser_id) references GAJAmdbUser;
alter table Report add constraint FK91B14154D8DE7209 foreign key (user_id) references GAJAmdbUser;
alter table Report add constraint FK91B141542768CF77 foreign key (comment_id) references Comment;
alter table image add constraint FK5FAA95B599F0D50 foreign key (film_id) references Film;


alter table film drop column if exists genre;