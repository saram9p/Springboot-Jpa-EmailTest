package com.cos.email_test.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //PK (자동증가 번호)
	
	@Column(nullable = false, length = 20, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 70)
	private String password; // 비밀번호
	
	@Column(nullable = false, length = 20)
	private String name;     // 이름
	
	@Column(nullable = false, length = 20)
	private String nickname; // 닉네임
	
	@Column(nullable = false, length = 50, unique = true)
	private String email;    // 이메일
	
	@Column(nullable = false, length = 5)
	private String gender;   // 성별
	
	@Column(nullable = false, length = 50)
	private String birth;    // 생년월일
	
}
