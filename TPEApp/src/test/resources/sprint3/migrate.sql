alter table film add column image_fk int;
alter table film add foreign key (image_fk) references image(id);
insert into film (image_fk) (select image.id from film, image where film.id = image.film_id);
alter table image drop column film_id;

alter table film add column visits int;
update film set visits = 0 where visits is null;

alter table GAJAmdbUser add column muted bool;
update GAJAmdbUser set muted = false;
update GAJAmdbUser set muted = true where id = 2;

alter table report add column reason varchar(140);

create table ReportResolution (
	id  serial not null, 
	reason varchar(140), 
	resolution varchar(40) not null, 
	user_id int4, 
	primary key (id)
);
alter table Report add column reportresolution_id int4;
alter table Report add constraint FK91B1415463D19E40 foreign key (reportResolution_id) references ReportResolution;
insert into reportresolution (id)  (select comment_id from report);
alter table Report add constraint FK91B141543A9BA861 foreign key (comment_id) references ReportResolution;
alter table ReportResolution add constraint FKEC51FD20D8DE7209 foreign key (user_id) references GAJAmdbUser;