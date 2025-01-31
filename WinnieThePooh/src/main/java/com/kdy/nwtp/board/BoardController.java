package com.kdy.nwtp.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kdy.nwtp.member.MemberDAO;
import com.kdy.nwtp.weather.WeatherDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BoardController {
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private WeatherDAO wDAO;
	@GetMapping("/board.go")
	public String home(HttpServletRequest req) {
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "board");
		return "index";
	}
}
