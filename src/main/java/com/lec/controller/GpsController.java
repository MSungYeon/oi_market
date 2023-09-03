package com.lec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GpsController {

	@GetMapping("/myGps")
	public String myGps() {

		return "gpsPage/myGps";
	}
}
