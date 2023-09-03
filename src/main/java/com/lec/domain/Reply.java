package com.lec.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int replyId;

    @Column(nullable = false, length = 120)
    String content;

    @ManyToOne
    @JoinColumn(name="boardfId")
    private Boardf boardf;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "memberId")
    private Member member;

	@Column(insertable = false, updatable = false, columnDefinition = "date default now()")
	private Date createDate_r;

	private String writer_r;
	
	private String profile;


};
