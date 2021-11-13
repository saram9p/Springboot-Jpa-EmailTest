package com.cos.email_test.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PwChangeReqDto {
	    private String authKey;
	    private String password;
	    private String chkPassword;
	    
}
