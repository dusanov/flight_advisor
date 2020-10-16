package me.dusanov.fa.dtos;

import java.util.List;

import lombok.Data;

@Data
public class ImportResult<T> {
	
	private final int successful;
	private final List<T> failed;

}