ALTER USER paw WITH PASSWORD 'paw';
CREATE TABLE GENRE(
	ID SERIAL PRIMARY KEY     NOT NULL,
	GENRE           VARCHAR(30)    NOT NULL,
	UNIQUE(GENRE)
);
insert into genre (genre)  (select distinct(genre) from film);
alter table film add column genre_id int references genre(id) on delete no action;
update film set genre_id = genre.id from genre where genre.genre=film.genre;
alter table film drop column if exists genre;

update gajamdbuser set admin=false;
update gajamdbuser set admin=true where id = 4;

CREATE TABLE IMAGE(
	ID SERIAL PRIMARY KEY     NOT NULL,
	CONTENTTYPE           VARCHAR(50)    NOT NULL,
	LENGTH	INTEGER NOT NULL,
	
);