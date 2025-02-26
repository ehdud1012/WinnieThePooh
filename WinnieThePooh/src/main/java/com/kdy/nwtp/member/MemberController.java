package com.kdy.nwtp.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kdy.nwtp.DateManager;
import com.kdy.nwtp.weather.WeatherDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberController {
	
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private WeatherDAO wDAO;
	@GetMapping("/photo.folder/{fileName}")
	public @ResponseBody Resource imgFolder(@PathVariable("fileName") String f, HttpServletRequest req) {
		return mDAO.getPhoto(f);
	}
	
	@GetMapping(value = "/member.get", produces = "application/json;charset=utf-8")
	public @ResponseBody Members memberGet(Member m) {
		return mDAO.getMembersToJson(m);
	}
	
	@GetMapping("/join.go")
	public String joinGo(HttpServletRequest req) {
		DateManager.getCurYear(req);
		req.setAttribute("contentPage", "member/join");
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		return "index";
	}
	@PostMapping("/join.do")
	public String joinDo(@RequestParam("photo2") MultipartFile mf, Member m, HttpServletRequest req) {
		mDAO.join(mf, m, req);
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "home");
		return "index";
	}
	
	@PostMapping("/login.do")
	public String loginDo(Member m, HttpServletRequest req) {
		mDAO.login(m, req);
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "home");
		return "index";
	}
	@GetMapping("/info.go")
	public String infoGo(Member m, HttpServletRequest req) {
		mDAO.address(req);
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "member/info");
		return "index";
	}
	
	@PostMapping("/update.go")
	public String updateGo(@RequestParam("photo2") MultipartFile mf, Member m, HttpServletRequest req) {
		mDAO.update(mf, m, req);
		mDAO.address(req);
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "member/info");
		return "index";
	}
	
	@GetMapping("/logout.go")
	public String logoutGo(HttpServletRequest req) {
		mDAO.logout(req);
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "home");
		return "index";
	}
	
	@GetMapping("/member.delete.do")
	public String deleteDo(Member m, HttpServletRequest req) {
		mDAO.delete(req);
		mDAO.logout(req);
		mDAO.isLogined(req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "home");
		return "index";
	}
}
