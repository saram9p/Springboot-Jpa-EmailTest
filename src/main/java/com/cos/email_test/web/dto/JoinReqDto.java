package com.cos.email_test.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class JoinReqDto {

	@NotBlank(message = "아이디를 입력해주세요!")
	@Size(message = "아이디 길이는 최소 2자, 최대 20자 입니다.", min = 2, max = 20)
	private String username;
	
	@NotBlank(message = "비밀번호를 입력해주세요!")
	@Size(message = "비밀번호는 최소 4자, 최대 20자 입니다.", min = 4, max = 20)
	private String password;
	
	@NotBlank(message = "이름을 입력해주세요!")
	private String name;
	
	@NotBlank(message = "닉네임을 입력해주세요!")
	private String nickname;
	
	@NotBlank(message = "이메일을 입력해주세요!")
	private String email;
	
	@NotBlank(message = "성별을 클릭해주세요!")
	private String gender;
	
	@NotBlank(message = "생년월일을 입력해주세요!")
	private String birth;
	
	@NotBlank(message = "인증번호를 입력해주세요!")
	private String authKey;
	
	public User toEntity() {
		User user = User.builder()
				.username(username)
				.password(password)
				.name(name)
				.nickname(nickname)
				.email(email)
				.gender(gender)
				.birth(birth)
				.build();
		return user;
	}
}
