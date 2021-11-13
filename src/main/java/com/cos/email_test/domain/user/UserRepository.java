package com.cos.email_test.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	// 로그인
	@Query(value = "select * from user where username = :username and password = :password", nativeQuery = true)
	Optional<User> mLogin(String username, String password);
	
	// 아이디 찾기
	@Query(value = "select * from user where name = :name and birth = :birth and email = :email", nativeQuery = true)
	User mFindId(String name, String birth, String email);
	
	// 비밀번호 찾기
	@Query(value = "select * from user where username = :username and name = :name and birth = :birth and email = :email", nativeQuery = true)
	User mFindPw(String username, String name, String birth, String email);
	
	// 비밀번호 변경
	@Query(value = "Update user Set password = :password Where username = :username and name = :name and birth = :birth and email = :email", nativeQuery = true)
	Optional<User> mChangePw(String password, String username, String name, String birth, String email);
	
}
