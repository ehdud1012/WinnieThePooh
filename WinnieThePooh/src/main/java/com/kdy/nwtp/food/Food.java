package com.kdy.nwtp.food;

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
@Entity(name = "pooh_food_review")
public class Food {
	@Id
	@SequenceGenerator(sequenceName = "pooh_food_review_seq", name = "pfrs", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pfrs")
	@Column(name = "pfr_no")
	private Integer no;
	@Column(name = "pfr_name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "pfr_writer")
	private Member member;
	
	@Column(name = "pfr_score")
	private Integer score;
	@Column(name = "pfr_photo")
	private String photo;
	@Column(name = "pfr_review")
	private String review;
}
