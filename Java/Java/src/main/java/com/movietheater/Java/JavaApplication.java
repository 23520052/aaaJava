package com.movietheater.Java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaApplication.class, args);
		System.out.println("🎬 Hệ thống rạp chiếu phim đã sẵn sàng!");
	}
}
