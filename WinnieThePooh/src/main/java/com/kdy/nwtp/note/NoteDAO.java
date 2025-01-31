package com.kdy.nwtp.note;

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
public class NoteDAO {
	@Autowired
	private NoteRepository nr;
	@Value("${note.photo.folder}")
	private String photoFolder;
	@Value("${note.photo.size}")
	private int photoSize;
	
	public Resource getPhoto(String f) {
		try {
			return new UrlResource("file:" + photoFolder + "/" + f);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public void writeNote(Note n, MultipartFile mf, HttpServletRequest req) {
		String photoFile = null;
		try {
			if (mf.getSize() > photoSize) {
				throw new Exception();
			}
			photoFile = KdyFileNameGenerator.file(mf);
			mf.transferTo(new File(photoFolder + "/" + photoFile));
		} catch (Exception e) {
			req.setAttribute("result", "노트등록실패(파일용량)");
			return;
		}
		try {
			Member m =(Member) req.getSession().getAttribute("loginMember");
			n.setWriter(m);
			n.setIcon(photoFile);
			nr.save(n);
			req.setAttribute("result", "노트등록성공");
		} catch (Exception e) {
			req.setAttribute("result", "노트등록실패(DB)");
			new File(photoFolder + "/" + photoFile).delete();
		}
	}
	public void getNote(HttpServletRequest req) {
		String category = req.getParameter("folder");
		req.setAttribute("notes", nr.findByCategory(category));
	}
	public void openNote(HttpServletRequest req) {
		int no = Integer.parseInt(req.getParameter("no"));
		req.setAttribute("note", nr.findById(no).get());
	}
	public void updateNote(MultipartFile mf, Note n, HttpServletRequest req) {
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
			int no = Integer.parseInt(req.getParameter("no"));
			Note dbn = nr.findById(no).get();
			dbn.setTitle(n.getTitle());
			dbn.setTxt(n.getTxt());
			dbn.setColor(n.getColor());
			oldphoto = dbn.getIcon();
			if (newphoto == null) {
				newphoto = oldphoto;
			} else {
				new File(photoFolder + "/" + oldphoto).delete();
			}
			dbn.setIcon(newphoto);
			
			System.out.println(dbn.getIcon());
			
			if (!nr.existsById(dbn.getNo())) {
				throw new Exception();
			}
			nr.save(dbn);
			req.setAttribute("result", "수정성공");
			req.setAttribute("note", dbn);
		} catch (Exception e) {
			req.setAttribute("result", "수정실패");
			new File(photoFolder + "/" + newphoto).delete();
		}
	}
}
