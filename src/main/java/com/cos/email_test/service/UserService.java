package com.cos.email_test.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.email_test.domain.auth.AuthEmailRepository;
import com.cos.email_test.domain.user.User;
import com.cos.email_test.domain.user.UserRepository;
import com.cos.email_test.handler.ex.MyNotFoundException;
import com.cos.email_test.handler.ex.MyNotInsertJoinException;
import com.cos.email_test.util.MyAlgorithm;
import com.cos.email_test.util.SHA;
import com.cos.email_test.util.mChkAuthKey;
import com.cos.email_test.web.dto.JoinReqDto;
import com.cos.email_test.web.dto.LoginReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final AuthEmailRepository authEmailRepository;
	
	@Transactional(rollbackFor = MyNotFoundException.class)
	public User 로그인 (LoginReqDto dto) {
		
		// SHA256 로 암호화
		String encPassword = SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256);
		
		// 아이디, 비밀번호 검증
		User userEntity = userRepository.mLogin(dto.getUsername(), encPassword)
				.orElseThrow(()-> new MyNotFoundException("아이디와 비밀번호가 일치하지 않습니다."));
		
		return userEntity;
	}
	
	@Transactional(rollbackFor = MyNotInsertJoinException.class)
	public User 회원가입 (JoinReqDto dto) {
		
		//인증키 확인
		List<mChkAuthKey> authEmailEntity = authEmailRepository.mChkAuthKey(dto.getAuthKey(), dto.getEmail())
				.orElseThrow(()-> new MyNotInsertJoinException("인증키를 정확히 입력해주세요!"));
		System.out.println("체크확인: " + authEmailEntity);
		
//		if(check.isEmpty()) {
//			if(check.equals(null)) {
//				throw new MyNotInsertJoinException("인증키를 정확히 입력해주세요!");
//			}
//			throw new MyNotInsertJoinException("인증키를 정확히 입력해주세요!");
//		}
		
		// SHA256으로 암호화
		String encpassword  = SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256);
		dto.setPassword(encpassword );
		
		// DB에 저장
		User userEntity = userRepository.save(dto.toEntity());
		
		// 인증키 삭제
		authEmailRepository.mDeleteAuthKey(dto.getEmail());
		
		return userEntity;
		
	}
	
}
