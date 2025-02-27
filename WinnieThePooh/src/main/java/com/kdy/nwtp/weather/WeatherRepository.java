package com.kdy.nwtp.weather;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Integer>{

}
