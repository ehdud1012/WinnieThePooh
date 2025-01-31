package com.kdy.nwtp.weather;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class WeatherDAO {
	
	@Autowired 
	private WeatherRepository wr;
	
	public void jsonPasing(Weather w, HttpServletRequest req) {
		HttpsURLConnection huc = null;
		try {
			URL u = URI.create("https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=baff8f3c6cbc28a4024e336599de28c4&units=metric&lang=kr").toURL();
			huc = (HttpsURLConnection) u.openConnection();
			InputStream is = huc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			
			JSONParser jp = new JSONParser();
			JSONObject jo = (JSONObject) jp.parse(isr);
			JSONObject m = (JSONObject) jo.get("main");
			JSONArray ww = (JSONArray) jo.get("weather");
			JSONObject w0 = (JSONObject) ww.get(0);
			
			Double temp = (double) m.get("temp");
			
			w.setTemp(temp);
			w.setFeeltemp((double) m.get("feels_like"));
			w.setHumidity(Integer.parseInt(m.get("humidity").toString()));
			w.setDescription((String) w0.get("description"));
			wr.save(w);
//			req.setAttribute("color", color);
			
		} catch (Exception e) {
			System.out.println("날씨등록 (DB문제)");
		}
		huc.disconnect();
	}
	
	public void curWeather(HttpServletRequest req) {
		HttpsURLConnection huc = null;
		try {
			URL u = URI.create("https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=baff8f3c6cbc28a4024e336599de28c4&units=metric&lang=kr").toURL();
			huc = (HttpsURLConnection) u.openConnection();
			InputStream is = huc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			
			JSONParser jp = new JSONParser();
			JSONObject jo = (JSONObject) jp.parse(isr);
			JSONObject m = (JSONObject) jo.get("main");
			JSONArray ww = (JSONArray) jo.get("weather");
			JSONObject w0 = (JSONObject) ww.get(0);
			
			Double temp = (double) m.get("temp");
			
			Weather w = new Weather();
			w.setTemp(temp);
			w.setFeeltemp((double) m.get("feels_like"));
			w.setHumidity(Integer.parseInt(m.get("humidity").toString()));
			w.setDescription((String) w0.get("description"));
			String color = "0e1185";
			if(temp >= 28) {color = "ff4646";}
			else if(temp >= 23) {color = "ffa346";}
			else if(temp >= 20) {color = "fff546";}
			else if(temp >= 17) {color = "46ff6c";}
			else if(temp >= 12) {color = "46e2ff";}
			else if(temp >= 9) {color = "46a6ff";}
			else if(temp >= 5) {color = "4673ff";}
			w.setColor(color);
			
			req.setAttribute("curWeather", w);
		} catch (Exception e) {
			e.printStackTrace();
		}
		huc.disconnect();
	}
}
