package com.kdy.nwtp.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kdy.nwtp.KdyTokenGenerator;
import com.kdy.nwtp.gallery.GalleryDAO;
import com.kdy.nwtp.sns.SnsDAO;
import com.kdy.nwtp.weather.WeatherDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberPageController {
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private GalleryDAO gDAO;
	@Autowired
	private WeatherDAO wDAO;
	@Autowired
	private SnsDAO sDAO;
	@Autowired
	private KdyTokenGenerator tg;
	
	@GetMapping("/memberPage.go")
	public String memberPageGo(HttpServletRequest req) {
		if(mDAO.isLogined(req)) {
			tg.generate(req);
			sDAO.memberSnsGet(req);
			gDAO.memberGalleryGet(req);
			wDAO.curWeather(req);
			req.setAttribute("contentPage", "member/memberPage");
		} else {
			req.setAttribute("contentPage", "home");
		}
		return "index";
	}
}
