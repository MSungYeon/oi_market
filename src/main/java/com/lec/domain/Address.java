package com.lec.domain;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
@Getter
public class Address { // 혹시 모를 카카오 맵 api		

	private String zipcode;
	private String streetAdr;
	private String detailAdr;
	
	protected Address() {
		
	}
	
	public Address(String zipcode, String streetAdr, String detailAdr) {
		this.zipcode = zipcode;
		this.streetAdr = streetAdr;
		this.detailAdr = detailAdr;
	}
}
