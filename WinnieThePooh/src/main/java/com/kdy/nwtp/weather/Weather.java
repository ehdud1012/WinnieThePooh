package com.kdy.nwtp.weather;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "pooh_weather_color")
public class Weather {
	
	@Id
	@SequenceGenerator(sequenceName = "pooh_weather_color_seq", name = "pwcs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pwcs")
	@Column(name = "pwc_no")
	private Integer no;
	
	@Column(name = "pwc_temp")
	private Double temp;
	@Column(name = "pwc_feel_temp")
	private Double feeltemp;
	@Column(name = "pwc_humidity")
	private Integer humidity;
	@Column(name = "pwc_description")
	private String description;
	@Column(name = "pwc_color")
	private String color;
}
