package com.kdy.nwtp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

//새로고침했을 때 같은거 또 등록 방지하는 법
//
//- 직전 요청을 한번 더 하는 것
//
//- 새 메뉴 등록 vs 새로고침
//	-> 새 메뉴를 등록요청때마다 유니크한 값이 있다면 ? = token
//
//=> token 만들기 (없음 만들기 KdyTokenGenerator)
//	- 토큰을 만드는 전략은 다양함 : 회사마다 다를 수 있음
//	
//	강사님 전략 (영원히 중복 안될 )
//		날짜 / 시간 : 2024/10/11 17:37

@Service
public class KdyTokenGenerator {
	private SimpleDateFormat sdf;
	
	public KdyTokenGenerator() {
		sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
	}
	
	public void generate(HttpServletRequest req) {
		Date now = new Date();
		String token = sdf.format(now);
		req.setAttribute("token", token);
	}
}
