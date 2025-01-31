package com.kdy.nwtp.gallery;

import java.util.Date;

import com.kdy.nwtp.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "pooh_gallery")
public class Gallery {
	@Id
	@SequenceGenerator(sequenceName = "pooh_gallery_seq", name = "pgs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pgs")
	@Column(name = "pg_no")
	private Integer no;
	
	@ManyToOne
	@JoinColumn(name = "pg_uploader")
	private Member member;
	
	@Column(name = "pg_title")
	private String title;
	@Column(name = "pg_photo")
	private String photo;
	@Column(name = "pg_date")
	private Date date;
	
}
