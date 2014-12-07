alter table film add column image_fk int;
alter table film add foreign key (image_fk) references image(id);
insert into film (image_fk) (select image.id from film, image where film.id = image.film_id);
alter table image drop column film_id;

alter table film add column visits int;
update film set visits = 0 where visits is null;