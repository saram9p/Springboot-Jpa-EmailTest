package com.cos.email_test.web.dto;

import com.cos.email_test.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FindReqDto {

	private String username;
	private String name;
	private String email;
	private String birth;
	
	public User toEntity() {
		User user = User.builder()
				.username(username)
				.name(name)
				.email(email)
				.birth(birth)
				.build();
		return user;
	}
}
