package com.kdy.nwtp.sns;

import java.util.Date;
import java.util.List;

import com.kdy.nwtp.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "pooh_sns")
public class Sns {
	@Id
	@SequenceGenerator(sequenceName = "pooh_sns_seq", name = "pss", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pss")
	@Column(name = "ps_no")
	private Integer no;
	@Column(name = "ps_txt")
	private String txt;
	
	@ManyToOne
	@JoinColumn(name = "ps_writer")
	private Member writer;
	
//	@CreationTimestamp // insert때 : sysdate
//	@UpdateTimestamp // insert때 + update때 마다 시간 변경: sysdate
	@Column(name = "ps_date")
	private Date date;
	@Column(name = "ps_color")
	private String color;
	
	@OneToMany(mappedBy = "snsno")
	private List<Reply> reply;
}
