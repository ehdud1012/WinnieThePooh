package com.kdy.nwtp.sns;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

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
@Entity(name = "pooh_sns_reply")
public class Reply {
	@Id
	@SequenceGenerator(sequenceName = "pooh_sns_reply_seq", name = "psrs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "psrs")
	@Column(name = "psr_no")
	private Integer no;
	
	@ManyToOne
	@JoinColumn(name = "psr_ps_no")
	private Sns snsno;
	
	@ManyToOne
	@JoinColumn(name = "psr_writer")
	private Member writer;
	
	@Column(name = "psr_txt")
	private String txt;
	
	@CreationTimestamp
	@Column(name = "psr_date")
	private Date date;
}
