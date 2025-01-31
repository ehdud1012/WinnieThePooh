package com.kdy.nwtp.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kdy.nwtp.member.MemberDAO;
import com.kdy.nwtp.weather.WeatherDAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FoodController {
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private WeatherDAO wDAO;
	@Autowired
	private FoodDAO fDAO;
	@GetMapping("/food.photo.folder/{fileName}")
	public @ResponseBody Resource imgFolder(@PathVariable("fileName") String f, HttpServletRequest req) {
		return fDAO.getPhoto(f);
	}
	@GetMapping("/food.go")
	public String home(HttpServletRequest req) {
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "food/food");
		return "index";
	}
	
	@GetMapping(value = "/food.post.get", produces = "application/json;charset=utf-8")
	public @ResponseBody Foods foodPostGet(Food f, HttpServletRequest req, HttpServletResponse res) {
		res.setHeader("Access-Control-Allow-Origin", "*");
		return fDAO.foodGetToJson(f);
	}
	
	@PostMapping(value = "/food.post.reg", produces = "application/json;charset=utf-8")
	public @ResponseBody String foodPostReg(@RequestParam("photo2") MultipartFile mf, HttpServletRequest req, Food f) {
		return fDAO.foodReg(mf, f, req);
	}
}
