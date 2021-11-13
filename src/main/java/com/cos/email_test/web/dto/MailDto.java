package com.cos.email_test.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
	
    private String email;
    private String title;
    private String message;
    
}