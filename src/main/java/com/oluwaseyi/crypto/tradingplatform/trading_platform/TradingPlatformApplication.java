package com.oluwaseyi.crypto.tradingplatform.trading_platform;

import com.oluwaseyi.crypto.tradingplatform.trading_platform.utils.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradingPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingPlatformApplication.class, args);
	}

	@Autowired
	private EmailService emailService;


	public void testEmailSending() {
		emailService.sendOtpEmail("your-test-email@gmail.com", "123456");
	}

}
