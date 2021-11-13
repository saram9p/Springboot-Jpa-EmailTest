package com.cos.email_test.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.email_test.domain.auth.AuthEmail;
import com.cos.email_test.domain.auth.AuthEmailRepository;
import com.cos.email_test.handler.ex.MyAsyncNotFoundException;
import com.cos.email_test.util.mChkAuthKey;
import com.cos.email_test.web.dto.AuthEmailReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthEmailService {

	private final AuthEmailRepository authEmailRepository;
	private final MailService mailService;
	
	@Transactional(rollbackFor = MyAsyncNotFoundException.class)
	public AuthEmail 인증번호발송(AuthEmailReqDto dto) {
		
    	authEmailRepository.save(dto.toEntity());
    	
        //임의의 authKey 생성 & 이메일 발송
        String authKey = mailService.sendAuthMail(dto);
        dto.setAuthKey(authKey);
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("email", dto.getEmail());
        map.put("authKey", dto.getAuthKey());
        System.out.println(map);
        
      //DB에 authKey 업데이트
        AuthEmail authEmailEntity = authEmailRepository.mUpdateAuthKey(dto.getAuthKey(), dto.getEmail());
        
		return authEmailEntity;
		
	}
	
	@Transactional(rollbackFor = MyAsyncNotFoundException.class)
	public void 인증번호검증(AuthEmailReqDto dto) {
	
		
	   	 List<mChkAuthKey> authEmailEntity = authEmailRepository.mChkAuthKey(dto.getAuthKey(), dto.getEmail())
	   			 .orElseThrow(()-> new MyAsyncNotFoundException("인증번호가 일치하지 않습니다."));
	 	
	   	 if (authEmailEntity.isEmpty()) {
	   		 throw new MyAsyncNotFoundException("인증번호가 일치하지 않습니다.");
	   	 
	   	 }
	   	 System.out.println(authEmailEntity);
   	 
	}
}
	
