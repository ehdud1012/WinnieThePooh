package com.kdy.nwtp.sns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kdy.nwtp.KdyTokenGenerator;
import com.kdy.nwtp.member.MemberDAO;
import com.kdy.nwtp.weather.Weather;
import com.kdy.nwtp.weather.WeatherDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SnsController {
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private SnsDAO sDAO;
	@Autowired
	private KdyTokenGenerator tg;
	@Autowired
	private WeatherDAO wDAO;
	
	@GetMapping("/sns.go")
	public String snsGo(Sns s, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			tg.generate(req);
		}
		sDAO.clearSearch(req);
		sDAO.getSns(1, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "sns/sns");
		return "index";
	}
	
	
	
	@GetMapping("/sns.reply.write.do")
	public String snsReplyWriteDo(Sns s, Reply r, @RequestParam("s_page") int page, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			tg.generate(req);
			sDAO.writeSnsReply(r,s, req);
		}
		sDAO.clearSearch(req);
		sDAO.getSns(page, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "sns/sns");
		return "index";
	}
	@GetMapping("/sns.write.do")
	public String snsWriteDo(Weather w, Sns s, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			tg.generate(req);
			sDAO.writeSns(s, req);
			wDAO.jsonPasing(w, req);
		}
		sDAO.clearSearch(req);
		sDAO.getSns(1, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "sns/sns");
		return "index";
	}
	
	@GetMapping("/sns.search.go")
	public String snsSearchGo(Sns s, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			tg.generate(req);
		}
		sDAO.setSearch(req);
		sDAO.getSns(1, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "sns/sns");
		return "index";
	}
	@GetMapping("/sns.post.delete")
	public String snsDeleteGo(Sns s, @RequestParam("s_page") int page, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			sDAO.snsDelete(s, req);
			tg.generate(req);
		}
		sDAO.clearSearch(req);
		sDAO.getSns(page, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "sns/sns");
		return "index";
	}
	@GetMapping("/sns.post.update")
	public String snsUpdateGo(Sns s, @RequestParam("s_page") int page, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			sDAO.snsUpdate(s, req);
			tg.generate(req);
		}
		sDAO.clearSearch(req);
		sDAO.getSns(page, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "sns/sns");
		return "index";
	}
	@GetMapping("/sns.reply.delete")
	public String snsReplyDeleteGo(@RequestParam("s_page") int page, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			sDAO.deleteSnsReply(req);
			tg.generate(req);
		}
		sDAO.clearSearch(req);
		sDAO.getSns(page, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "sns/sns");
		return "index";
	}
	@GetMapping("/sns.reply.update")
	public String snsReplyUpdateGo(Reply r, @RequestParam("s_page") int page, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			sDAO.updateSnsReply(r, req);
			tg.generate(req);
		}
		sDAO.clearSearch(req);
		sDAO.getSns(page, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "sns/sns");
		return "index";
	}
	@GetMapping("/sns.postPage.go")
	public String snsPageGo(Sns s, @RequestParam("s_page") int page, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			tg.generate(req);
		}
		sDAO.getSns(page, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "sns/sns");
		return "index";
	}
}
