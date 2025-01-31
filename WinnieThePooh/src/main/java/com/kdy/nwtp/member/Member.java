package com.kdy.nwtp.member;

import java.util.Date;
import java.util.List;

import com.kdy.nwtp.gallery.Gallery;
import com.kdy.nwtp.sns.Sns;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "pooh_member")
public class Member {
	
	@Id
	@Column(name = "pm_id")
	private String id;
	@Column(name = "pm_pw")
	private String pw;
	@Column(name = "pm_name")
	private String name;
	@Column(name = "pm_birthday")
	private Date birthday;
	@Column(name = "pm_address")
	private String address;
	@Column(name = "pm_photo")
	private String photo;
}
