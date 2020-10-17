insert into User (username, first_name, last_name, hashpwd, salt, is_admin) values
	('admin','admin','admin','adminpwd', 'adminsalt',1),
	('user','user','user','userpwd','usersalt',0);
	
insert into City (id, name, country, description) values
	('100000','novi sad','serbia','panonian one horse town'),
	('100001','el calafate','argentina','patagonian one horse town'),
	('1739','Belgrade','Serbia','Serbian capitol'),
	('2643', 'Santiago','Chile','Chilean capitol'),
	('2647','Punta Arenas','Chile','Cold capitol');
	
/*
create or replace view routes_join_airports_view as
	select
		r.id id, r.airline airline, src.city source, dest.city destination, r.stops stops, r.price price
	from route r
	  inner join airport src on src.airport_id = r.source_airport_id
	  inner join airport dest on dest.airport_id = r.destination_airport_id;
*/