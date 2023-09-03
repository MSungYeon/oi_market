package com.lec.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EntityListeners(BoardfListeners.class)
@Getter
@Setter
@ToString
@Entity
public class Boardf {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq_f;
	
	private String title_f;
	
	@Column(updatable = false)
	private String writer_f;
	
	private String cate_f;
	
	private String content_f;
	
	@Column(updatable = false)
	private String addr_f;
	
	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	private Date createDate_f;	
	
	@Column(insertable = false, updatable = false, columnDefinition = "bigint default 0")
	private Long cnt_f;
	
	private Long boardf_ref;
	
	private Long boardf_lev;
	
	private Long boardf_seq;
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "memberId")
    private Member member;
	
    @OneToMany(mappedBy = "boardf", fetch = FetchType.EAGER,  cascade = CascadeType.REMOVE)
	@OrderBy("replyId desc")
	private List<Reply> reply;


}
