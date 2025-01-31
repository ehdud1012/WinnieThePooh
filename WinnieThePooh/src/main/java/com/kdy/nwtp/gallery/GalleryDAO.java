package com.kdy.nwtp.gallery;

import java.io.File;
import java.net.URLEncoder;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kdy.nwtp.KdyFileNameGenerator;
import com.kdy.nwtp.member.Member;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class GalleryDAO {
	
	@Autowired
	private GalleryRepository gr;
	
	@Value("${gallery.photo.folder}")
	private String photoFolder;
	@Value("${gallery.photo.size}")
	private int photoSize;
	
	public void clearSearch(HttpServletRequest req) {
		req.getSession().setAttribute("gallerySearch", null);
	}
	
	public ResponseEntity<Resource> download(String f) {
		try {
			UrlResource ur = new UrlResource("file:" + photoFolder + "/" + f);
			
			// header 값에 attachment; filename="" 이 붙어 있어야함
			String h = "attachment; filename=\"" + URLEncoder.encode(f, "utf-8") + "\"";
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, h).body(ur);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void galleryDelete(Gallery g, HttpServletRequest req) {
		int no = Integer.parseInt(req.getParameter("no"));
		gr.deleteById(no);
		req.setAttribute("result", "삭제성공");
	}
	
	public void galleryGet(Gallery g, HttpServletRequest req) {
		String searchWord = (String) req.getSession().getAttribute("gallerySearch");
		if (searchWord == null) {
			searchWord = "";
		}
//		req.setAttribute("galleryPost", gr.findAll(p));
		req.setAttribute("galleryPost", gr.findByTitleContaining(searchWord));
	}
	
	public Resource getPhoto(String f) {
		try {
			return new UrlResource("file:" + photoFolder + "/" + f);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public void memberGalleryGet(HttpServletRequest req) {
		try {
			Member m = (Member) req.getSession().getAttribute("loginMember");
			req.setAttribute("memberGalleryPost", gr.findByMember(m));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setSearch(HttpServletRequest req) {
		try {
			String search = req.getParameter("gallerySearch");
			req.getSession().setAttribute("gallerySearch", search);
		} catch (Exception e) {
			
		}
	}
	public void upload(MultipartFile mf, Gallery g, HttpServletRequest req) {
		String photoFile = null;
		try {
			if (mf.getSize() > photoSize) {
				throw new Exception(); // catch 로 던지기
			}
			photoFile = KdyFileNameGenerator.file(mf);
			mf.transferTo(new File(photoFolder + "/" + photoFile));
		} catch (Exception e) {
			req.setAttribute("result", "등록실패(파일용량)");
			return;
		}
		try {
			String token = req.getParameter("token");
			String oldSuccessToken = (String) (req.getSession().getAttribute("successToken"));
			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				new File(photoFolder + "/" + photoFile).delete();
				req.setAttribute("result", "등록실패(새로고침)");
				return;
			}
			g.setMember((Member) req.getSession().getAttribute("loginMember"));
			g.setDate(new Date());
			g.setPhoto(photoFile);
			gr.save(g);
			req.setAttribute("result", "등록성공");
			req.getSession().setAttribute("successToken", token);
			
		} catch (Exception e) {
			req.setAttribute("result", "등록실패(DB)");
			new File(photoFolder + "/" + photoFile).delete();
		}
	}
	
}
