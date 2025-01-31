package com.kdy.nwtp.bg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kdy.nwtp.member.MemberDAO;
import com.kdy.nwtp.weather.WeatherDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BallGameController {
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private WeatherDAO wDAO;
	@GetMapping("/ballgame.go")
	public String home(HttpServletRequest req) {
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "bg/ballGame");
		return "index";
	}
	@GetMapping("/player1.go")
	public String player1(HttpServletRequest req) {
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "bg/player01");
		return "index";
	}
	@GetMapping("/player2.go")
	public String player2(HttpServletRequest req) {
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "bg/player02");
		return "index";
	}
}
