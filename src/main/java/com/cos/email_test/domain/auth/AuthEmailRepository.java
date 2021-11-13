package com.cos.email_test.domain.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cos.email_test.util.mChkAuthKey;

public interface AuthEmailRepository extends JpaRepository<AuthEmail, Integer> {

	// 인증메일 받기 클릭시 DB에 이메일, 인증키 추가
	@Query(value = "insert into User (email, authKey) values (:email, :authKey)", nativeQuery = true)
	AuthEmail mEmailInsert(String email, String authKey);
	
	// 인증키를 DB에 업데이트
	@Query(value = "Update AuthEmail Set authKey = :authKey Where email = :email", nativeQuery = true)
	AuthEmail mUpdateAuthKey(String authKey, String email);
	
	// 인증 번호 비교시 사용할 쿼리, 들고온 인증키와 이메일을 조건으로 셀렉트
	@Query(value = "SELECT id, email, authKey FROM authemail GROUP BY email having email = :email and authKey = :authKey", nativeQuery = true)
	Optional<List<mChkAuthKey>> mChkAuthKey(String authKey, String email);
	
	// 인증 완료 후에 DB에 저장된 인증키 삭제
	@Query(value = "Delete From AuthEmail Where email = :email ", nativeQuery = true)
	AuthEmail mDeleteAuthKey(String email);
	
	// DB에 저장된 인증키 불러오기 - 회원가입 클릭했을때 적은 인증키로 찾아보기
	@Query(value = "select * from AuthEmail where authKey = :authKey", nativeQuery = true)
	AuthEmail mFindAuthKey(String authKey);
}
