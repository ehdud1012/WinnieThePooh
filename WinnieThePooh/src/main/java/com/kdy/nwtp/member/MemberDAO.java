package com.kdy.nwtp.member;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kdy.nwtp.KdyFileNameGenerator;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class MemberDAO {
	
	@Autowired
	private MemberRepository mr;
	
	@Value("${member.photo.folder}")
	private String photoFolder;
	@Value("${member.photo.size}")
	private int photoSize;
	
	private BCryptPasswordEncoder bcpe;
	private SimpleDateFormat sdf;
	
	public MemberDAO() {
		bcpe = new BCryptPasswordEncoder();
		sdf = new SimpleDateFormat("yyyyMMdd");
	}
	
	public Resource getPhoto(String f) {
		try {
			return new UrlResource("file:" + photoFolder + "/" + f);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public Members getMembersToJson(Member m) {
		Optional<Member> memberTemp = mr.findById(m.getId());
		List<Member> member = new ArrayList<>();
		if(memberTemp.isPresent()) {
			member.add(memberTemp.get());
		}
		return new Members(member);
	}
	
	public void delete(HttpServletRequest req) {
		try {
			Member loginMember = (Member) req.getSession().getAttribute("loginMember");
			new File(photoFolder + "/" + loginMember.getPhoto()).delete();
			mr.delete(loginMember);
			req.setAttribute("result", "탈퇴성공");
		} catch (Exception e) {
			req.setAttribute("result", "탈퇴실패");
		}
		
	}
	
	public void update(MultipartFile mf, Member m, HttpServletRequest req) {
		String newphoto = null;
		String oldphoto = null;
		try {
			if (mf.getSize() != 0) {
				if (mf.getSize() > photoSize) {
					throw new Exception();
				}
				newphoto = KdyFileNameGenerator.file(mf);
				mf.transferTo(new File(photoFolder + "/" + newphoto));
			}
		} catch (Exception e) {
			req.setAttribute("result", "수정실패(파일용량)");
			return;
		}
		
		try {
			Member loginMember = (Member) req.getSession().getAttribute("loginMember");
			if (!m.getPw().equals(loginMember.getPw())) {
				m.setPw(bcpe.encode(m.getPw()));
			}
			String addr = String.format("%s!%s!%s", req.getParameter("add2"),
					req.getParameter("add3"),
					req.getParameter("add1"));
			m.setAddress(addr);
			
			oldphoto = loginMember.getPhoto();
			if (newphoto == null) {
				newphoto = oldphoto;
			}
			m.setPhoto(newphoto);
			m.setBirthday(loginMember.getBirthday());
			m.setId(loginMember.getId());
			
			if (!mr.existsById(m.getId())) {
				throw new Exception();
			}
			mr.save(m);
			req.getSession().setAttribute("loginMember", m);
			req.setAttribute("result", "수정성공");
			
		} catch (Exception e) {
			req.setAttribute("result", "수정실패");
			new File(photoFolder + "/" + newphoto).delete();
		}
	}
	
	public void join(MultipartFile mf, Member m, HttpServletRequest req) {
		String photoFile = null;
		try {
			if (mf.getSize() > photoSize) {
				throw new Exception(); // catch 로 던지기
			}
			photoFile = KdyFileNameGenerator.file(mf);
			mf.transferTo(new File(photoFolder + "/" + photoFile));
		} catch (Exception e) {
			req.setAttribute("result", "가입실패(파일용량)");
			return;
		}
		
		try {
			m.setPw(bcpe.encode(m.getPw()));

			Date bd = sdf.parse(String.format("%s%02d%02d", 
								req.getParameter("y"), 
								Integer.parseInt(req.getParameter("m")), 
								Integer.parseInt(req.getParameter("d"))));
			m.setBirthday(bd);
			
			String addr = String.format("%s!%s!%s", req.getParameter("add2"),
									req.getParameter("add3"),
									req.getParameter("add1"));
			m.setAddress(addr);
			m.setPhoto(photoFile);
			
			if (mr.existsById(m.getId())) {
				throw new Exception();
			}
			mr.save(m);
			req.setAttribute("result", "가입성공");
			
		} catch (Exception e) {
			req.setAttribute("result", "가입실패");
			new File(photoFolder + "/" + photoFile).delete();
		}
	}
	
	public void login(Member m, HttpServletRequest req) {
		try {
			if (mr.existsById(m.getId())) {
				Member dbM = mr.findById((m.getId())).get();
				if(bcpe.matches(m.getPw(), dbM.getPw())) {
					req.setAttribute("result", "로그인성공");
					req.getSession().setAttribute("loginMember", dbM);
					req.getSession().setMaxInactiveInterval(6000);
				} else {
					req.setAttribute("result", "로그인실패(비번틀림)");
				}
			} else {
				req.setAttribute("result", "로그인실패(미가입)");
			}
		} catch (Exception e) {
			req.setAttribute("result", "로그인실패(DB)");
		}
	}
	
	public void logout(HttpServletRequest req) {
		req.getSession().setAttribute("loginMember", null);
	}
	
	public boolean isLogined(HttpServletRequest req) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		if (m != null) { // 로그인 됨
			req.setAttribute("loginPage", "member/logined");
			return true;
		}
		req.setAttribute("loginPage", "member/login");
		return false; // 안됨
	}
	
	public void address(HttpServletRequest req) {
		Member m = (Member) req.getSession().getAttribute("loginMember");
		req.setAttribute("addr", m.getAddress().split("!"));
	}
	
}
