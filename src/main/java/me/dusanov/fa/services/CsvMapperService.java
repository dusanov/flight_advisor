package me.dusanov.fa.services;

import java.util.Collections;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class CsvMapperService {

	//Generic method trying to map uploaded csv file to class type passed
	public <T> List<T> loadObjectList(Class<T> type, Resource fileResource) {
	    try {
	        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();//.withoutHeader();
	        CsvMapper mapper = new CsvMapper();	        
	        mapper.registerModule(new JavaTimeModule());
	        MappingIterator<T> readValues = 
	        		mapper.readerFor(type).with(bootstrapSchema).readValues(fileResource.getFile());
	        return readValues.readAll();
	    } catch (Exception e) {
	    	//TODO: this needs better handling
	    	System.out.println(e);
	        //logger.error("Error occurred while loading object list from file " + fileName, e);
	        return Collections.emptyList();
	    }
	}
	
}
