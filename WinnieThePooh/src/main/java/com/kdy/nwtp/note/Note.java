package com.kdy.nwtp.note;

import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;

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
@Entity(name = "pooh_note")
public class Note {
	@Id
	@SequenceGenerator(sequenceName = "pooh_note_seq", name = "pns", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pns")
	@Column(name = "pn_no")
	private Integer no;
	@Column(name = "pn_title")
	private String title;
	@Column(name = "pn_txt")
	private String txt;
	@Column(name = "pn_icon")
	private String icon;
	@Column(name = "pn_category")
	private String category;
	
	@ManyToOne
	@JoinColumn(name = "pn_writer")
	private Member writer;
	
	@UpdateTimestamp
	@Column(name = "pn_date")
	private Date date;
	
	@Column(name = "pn_color")
	private String color;
}
