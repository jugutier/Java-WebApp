CREATE TABLE GENRE(
	GENRE_ID SERIAL PRIMARY KEY     NOT NULL,
	GENRE           VARCHAR(30)    NOT NULL,
	UNIQUE(GENRE)
);
insert into genre (genre)  (select distinct(genre) from film);
alter table film add column genre_id int references genre(genre_id) on delete no action;
update film set genre_id = genre.genre_id from genre where genre.genre=film.genre;
alter table film drop column if exists genre;