package com.kdy.nwtp.gallery;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kdy.nwtp.member.Member;

@Repository
public interface GalleryRepository extends CrudRepository<Gallery, Integer>{
	public abstract List<Gallery> findAll(Pageable p);
	public abstract List<Gallery> findByTitleContaining(String s);
	public abstract List<Gallery> findByMember(Member m);
}
