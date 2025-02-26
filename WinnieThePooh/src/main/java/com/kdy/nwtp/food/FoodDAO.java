package com.kdy.nwtp.food;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kdy.nwtp.KdyFileNameGenerator;
import com.kdy.nwtp.member.Member;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FoodDAO {
	@Autowired
	private FoodRepository fr;
	@Value("${foodreview.photo.folder}")
	private String photoFolder;
	@Value("${foodreview.photo.size}")
	private int photoSize;
	
	public Resource getPhoto(String f) {
		try {
			return new UrlResource("file:" + photoFolder + "/" + f);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Foods foodGetToJson(Food f) {
		return new Foods(fr.findAll());
	}
	
	public String foodReg(MultipartFile mf, Food f, HttpServletRequest req) {
		String photoFile = null;
		try {
			if (mf.getSize() > photoSize) {
				throw new Exception();
			}
			photoFile = KdyFileNameGenerator.file(mf);
			mf.transferTo(new File(photoFolder + "/" + photoFile));
		} catch (Exception e) {
			return "{\"result\":\"등록실패(파일용량)\"}";
		}
		try {
//			String oldSuccessToken = (String) (req.getSession().getAttribute("successToken"));
//			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
//				new File(photoFolder + "/" + photoFile).delete();
//				return "{\"result\":\"등록실패(새로고침)\"}";
//			}
			
			f.setPhoto(photoFile);
			f.setMember((Member) req.getSession().getAttribute("loginMember"));
			fr.save(f);
//			req.getSession().setAttribute("successToken", token);
			return "{\"result\":\"등록성공\"}";
		} catch (Exception e) {
			e.printStackTrace();
//			new File(photoFolder + "/" + photoFile).delete();
			return "{\"result\":\"등록실패\"}";
		}
	}
}
