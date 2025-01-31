package com.kdy.nwtp.sns;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kdy.nwtp.member.Member;

@Repository
public interface SnsRepositoty extends CrudRepository<Sns, Integer>{
//	public abstract List<Sns> findByTxtContaining(String s);
	public abstract List<Sns> findByTxtContaining(String s, Pageable p);
	public abstract long countByTxtContaining(String s);
	public abstract List<Sns> findByWriter(Member m);
	
}