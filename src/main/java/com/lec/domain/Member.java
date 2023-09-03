package com.lec.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Member {

	@Id
	private String id;
	private String password;
	private String name;
	private String nickname;
	private String ssn;
	private String email;
	private String profile;
	private String addr1;
	private String addr2;
	private String addr3;
	private String p_num;

	private String role;

	// Additional fields or relationships can be added as needed
	
	@Transient
	private MultipartFile uploadFile;

	// Constructors, getters, setters, and other methods can be added as needed

}
