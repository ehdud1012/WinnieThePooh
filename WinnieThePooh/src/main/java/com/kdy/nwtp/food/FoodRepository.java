package com.kdy.nwtp.food;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Integer>{
	public abstract List<Food> findAll();
}
