package com.kdy.nwtp.note;

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

@Controller
public class NoteController {
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private NoteDAO nDAO;
	@Autowired
	private WeatherDAO wDAO;
	
	@GetMapping("/note.photo.folder/{fileName}")
	public @ResponseBody Resource imgFolder(@PathVariable("fileName") String f, HttpServletRequest req) {
		return nDAO.getPhoto(f);
	}
	
	@GetMapping("/note.go")
	public String noteGo(HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			req.setAttribute("contentPage", "note/note");
		} else {
			req.setAttribute("contentPage", "home");
		}
		wDAO.curWeather(req);
		return "index";
	}
	@GetMapping("/note.folder.go")
	public String noteFolderGo(HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			nDAO.getNote(req);
			req.setAttribute("contentPage", "note/folder");
		} else {
			req.setAttribute("contentPage", "home");
		}
		wDAO.curWeather(req);
		return "index";
	}
	@GetMapping("/note.reg.go")
	public String noteRegGo(HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			req.setAttribute("contentPage", "note/reg");
		} else {
			req.setAttribute("contentPage", "home");
		}
		wDAO.curWeather(req);
		return "index";
	}
	@GetMapping("/note.update.page.go")
	public String noteUpdatePageGo(HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			nDAO.openNote(req);
			req.setAttribute("contentPage", "note/noteUpdate");
		} else {
			req.setAttribute("contentPage", "home");
		}
		wDAO.curWeather(req);
		return "index";
	}
	@PostMapping("/note.update.go")
	public String noteUpdateGo(@RequestParam("iconn") MultipartFile mf, Note n, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			nDAO.updateNote(mf, n, req);
			req.setAttribute("contentPage", "note/noteUpdate");
		} else {
			req.setAttribute("contentPage", "home");
		}
		wDAO.curWeather(req);
		return "index";
	}
	
	@PostMapping("/note.reg.do")
	public String noteRegDo(Note n, @RequestParam("icon2") MultipartFile mf, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			nDAO.writeNote(n, mf, req);
			nDAO.getNote(req);
			req.setAttribute("contentPage", "note/note");
		} else {
			req.setAttribute("contentPage", "home");
		}
		wDAO.curWeather(req);
		return "index";
	}
}
