insert into City (id, name, country, description) values
	('100000','novi sad','serbia','panonian one horse town'),
	('100001','el calafate','argentina','patagonian one horse town'),
	('1739','Belgrade','Serbia','Serbian capitol'),
	('2643', 'Santiago','Chile','Chilean capitol'),
	('2647','Punta Arenas','Chile','Cold capitol'),
	('1489','Budapest','Hungary','Hungary capitol'),
	('1382','Paris','France','French capitol'),
	('1242','San Sebastian','Spain','City in Spain');
	
insert into Airport (airport_id,name,city,country,iata,icao,latitude,longitude,altitude,timezone,dst,tz,type,source) values
	(1739,'Belgrade Nikola Tesla Airport','Belgrade','Serbia','BEG','LYBE',44.8184013367,20.3090991974,335,1,'E',null,'airport','OurAirports'),
	(2643,'El Bosque Airport','Santiago','Chile',null,'SCBQ',-33.561798095703125,-70.68840026855469,1844,-4,'S',null,'airport','OurAirports'),
	(2647,'Pdte. Carlos Ibañez del Campo Airport','Punta Arenas','Chile','PUQ','SCCI',-53.002602,-70.854599,139,-4,'S',null,'airport','OurAirports'),
	(1489,'Budapest Liszt Ferenc International Airport','Budapest','Hungary','BUD','LHBP',47.42976,19.261093,495,1,'E',null,'airport','OurAirports'),
	(1382,'Charles de Gaulle International Airport','Paris','France','CDG','LFPG',49.012798,2.55,392,1,'E',null,'airport','OurAirports');
	
insert into Route (id,airline,airline_id,source_airport,source_airport_id,destination_airport,destination_airport_id,codeshare,stops,equipment,price) values
	(1 ,'4U',2548,'BEG',1739,'BUD',1489,'Y',0,319,120.00),
	(2 ,'4U',2548,'BEG',1739,'CDG',1382,'Y',0,319,200.20),
	(3 ,'4U',2548,'BUD',1489,'CDG',1382,'Y',0,319,140.00),
	(4 ,'4U',2548,'BUD',1489,'SCBQ',2643,'Y',0,319,900.00),
	(5 ,'4U',2548,'CDG',1382,'SCBQ',2643,'Y',0,319,700.00),
	(6 ,'4U',2548,'SCBQ',2643,'PUQ',2647,'Y',0,319,120.00),
	(7 ,'4U',2548,'BEG',1739,'PUQ',2647,'Y',0,319,1220.00);