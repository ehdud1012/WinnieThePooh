package com.kdy.nwtp.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kdy.nwtp.member.MemberDAO;
import com.kdy.nwtp.weather.WeatherDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ChatController {
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private WeatherDAO wDAO;
	@GetMapping("/chat.go")
	public String home(HttpServletRequest req) {
		if(mDAO.isLogined(req)) {
			wDAO.curWeather(req);
			req.setAttribute("contentPage", "chat/chat");
		} else {
			req.setAttribute("contentPage", "home");
		}
		return "index";
	}

}
