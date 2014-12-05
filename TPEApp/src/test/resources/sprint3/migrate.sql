alter table film add column image_fk int;
alter table film add foreign key (image_fk) references image(id);
alter table image drop column film_id;