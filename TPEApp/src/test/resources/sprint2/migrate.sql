CREATE TABLE GENRE(
	ID SERIAL PRIMARY KEY     NOT NULL,
	GENRE           VARCHAR(30)    NOT NULL,
	UNIQUE(GENRE)
);
insert into genre (genre)  (select distinct(genre) from film);
alter table film add column genre_id int references genre(id) on delete no action;
update film set genre_id = genre.id from genre where genre.genre=film.genre;
alter table film drop column if exists genre;