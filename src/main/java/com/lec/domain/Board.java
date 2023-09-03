package com.lec.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Board {
	
	@Id @GeneratedValue
	private Long seq;
	
	private String title;
	
	@Column(updatable = false)
	private String writer;
	
	private String cate;	
	
	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	private Date regdate;	
	
	@Column(updatable = false)
	private String addr;

	private String content;
	
	private int price;

	private int liked;
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private Long cnt;
	
	private String fileName;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "memberId")
    private Member member;
	
    @ElementCollection
    private List<String> uploadFiles;
}
