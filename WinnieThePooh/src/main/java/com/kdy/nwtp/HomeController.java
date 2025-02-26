package com.kdy.nwtp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kdy.nwtp.member.MemberDAO;
import com.kdy.nwtp.weather.WeatherDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private WeatherDAO wDAO;
	
	@GetMapping("/")
	public String home(HttpServletRequest req) {
		req.setAttribute("contentPage", "home");
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		return "index";
	}
	@GetMapping("/index.do")
	public String indexDo(HttpServletRequest req) {
		req.setAttribute("contentPage", "home");
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		return "index";
	}
}
