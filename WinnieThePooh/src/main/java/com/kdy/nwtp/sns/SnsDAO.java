package com.kdy.nwtp.sns;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kdy.nwtp.member.Member;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SnsDAO {
	
	@Autowired
	private SnsRepositoty sr;
	@Autowired
	private ReplyRepository rr;
	private int snsPostPerpage;
	
	public SnsDAO() {
		snsPostPerpage = 5;
	}
	public void writeSnsReply(Reply r, Sns s, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String oldSuccessToken = (String) (req.getSession().getAttribute("successToken"));
			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				req.setAttribute("result", "댓글등록실패(새로고침)");
				return;
			}
			r.setSnsno(s);
			Member m = (Member) req.getSession().getAttribute("loginMember");
			r.setWriter(m);
			rr.save(r);
			req.setAttribute("result", "댓글등록성공");
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "댓글등록실패");
		}
	}
	
	public void deleteSnsReply(HttpServletRequest req) {
		try {
			int no = Integer.parseInt(req.getParameter("no"));
			rr.deleteById(no);
			req.setAttribute("result", "댓글삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "댓글삭제실패");
		}
	}
	
	public void updateSnsReply(Reply r, HttpServletRequest req) {
		try {
			Reply dbr = rr.findById(r.getNo()).get();
			dbr.setTxt(r.getTxt());
			rr.save(dbr);
			req.setAttribute("result", "댓글수정성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "댓글수정실패");
		}
	}
	public void clearSearch(HttpServletRequest req) {
		req.getSession().setAttribute("search", null);
	}
	
	public void getSns(int page, HttpServletRequest req) {
		try {
			String searchWord = (String) req.getSession().getAttribute("search");
			if (searchWord == null) {
				searchWord = "";
			}
			Sort sort = Sort.by(Sort.Order.asc("no"));
			Pageable p = PageRequest.of(page - 1, snsPostPerpage, sort);
			long allSnsPostCount = sr.countByTxtContaining(searchWord);
			int pageCount = (int) Math.ceil(allSnsPostCount / (double) snsPostPerpage);
			req.setAttribute("s_pageCount", pageCount);
			req.setAttribute("s_page", page);
			
			List<Sns> s = sr.findByTxtContaining(searchWord, p);
			for (Sns sns : s) {
				sns.setReply(rr.findBySnsno(sns));
			}
			req.setAttribute("snsPost", s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void memberSnsGet(HttpServletRequest req) {
		try {
			Member m = (Member) req.getSession().getAttribute("loginMember");
			req.setAttribute("memberSnsPost", sr.findByWriter(m));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setSearch(HttpServletRequest req) {
		try {
			String search = req.getParameter("search");
			req.getSession().setAttribute("search", search);
		} catch (Exception e) {
			
		}
	}
	
	public void snsDelete(Sns s, HttpServletRequest req) {
		try {
			int no = Integer.parseInt(req.getParameter("no"));
			sr.deleteById(no);
			req.setAttribute("result", "삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "삭제실패");
		}
	}
	
	public void snsUpdate(Sns s, HttpServletRequest req) {
		try {
			Sns dbs = sr.findById(s.getNo()).get();
			dbs.setTxt(s.getTxt().replace("\r\n", "<br>"));
			sr.save(dbs);
			req.setAttribute("result", "수정성공");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "수정실패");
		}
	}
	public void writeSns(Sns s, HttpServletRequest req) {
		try {
			String token = req.getParameter("token");
			String oldSuccessToken = (String) (req.getSession().getAttribute("successToken"));
			if (oldSuccessToken != null && token.equals(oldSuccessToken)) {
				req.setAttribute("result", "등록실패(새로고침)");
				return;
			}
			s.setTxt(s.getTxt().replace("\r\n", "<br>"));
			s.setWriter((Member) req.getSession().getAttribute("loginMember"));
			s.setDate(new Date());
			sr.save(s);
			req.setAttribute("result", "등록성공");
			req.getSession().setAttribute("successToken", token);
		} catch (Exception e) {
			req.setAttribute("result", "등록실패(DB)");
		}
	}
}
