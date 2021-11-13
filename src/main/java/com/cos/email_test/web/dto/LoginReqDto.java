package com.cos.email_test.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqDto {
	@Size(min = 2, max = 20)
	@NotBlank
	private String username;

	@Size(min = 4, max = 20)
	@NotBlank
	private String password;
}
