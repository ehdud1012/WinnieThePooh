package com.kdy.nwtp.gallery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kdy.nwtp.KdyTokenGenerator;
import com.kdy.nwtp.member.MemberDAO;
import com.kdy.nwtp.weather.WeatherDAO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GalleryController {
	@Autowired
	private MemberDAO mDAO;
	@Autowired
	private GalleryDAO gDAO;
	@Autowired
	private WeatherDAO wDAO;
	@Autowired
	private KdyTokenGenerator tg;
	
	@GetMapping("/gallery.photo.folder/{fileName}")
	public @ResponseBody Resource imgFolder(@PathVariable("fileName") String f, HttpServletRequest req) {
		return gDAO.getPhoto(f);
	}
	
	@GetMapping("/gallery.photo.download/{fileName}")
	public ResponseEntity<Resource> downloadGo(@PathVariable("fileName") String f, HttpServletRequest req) {
		return gDAO.download(f);
	}
	
	@GetMapping("/gallery.go")
	public String galleryGo(Gallery g, HttpServletRequest req) {
		mDAO.isLogined(req);
		tg.generate(req);
		gDAO.clearSearch(req);
		gDAO.galleryGet(g, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "gallery/gallery");
		return "index";
	}
	
	@PostMapping("/gallery.upload.do")
	public String uploadDo(@RequestParam("file2") MultipartFile mf, Gallery g, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			gDAO.upload(mf, g, req);
			tg.generate(req);
		}
		gDAO.clearSearch(req);
		gDAO.galleryGet(g, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "gallery/gallery");
		return "index";
	}
	@GetMapping("/gallery.post.delete")
	public String deleteGo(Gallery g, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			gDAO.galleryDelete(g, req);
			tg.generate(req);
		}
		gDAO.galleryGet(g, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "gallery/gallery");
		return "index";
	}
	@GetMapping("/gallery.search.go")
	public String searchGo(Gallery g, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			gDAO.setSearch(req);
			tg.generate(req);
		}
		gDAO.galleryGet(g, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "gallery/gallery");
		return "index";
	}
	@GetMapping("/gallery.postPage.go")
	public String galleryPageGo(Gallery g, HttpServletRequest req) {
		if (mDAO.isLogined(req)) {
			tg.generate(req);
		}
		gDAO.galleryGet(g, req);
		wDAO.curWeather(req);
		req.setAttribute("contentPage", "gallery/gallery");
		return "index";
	}
}
