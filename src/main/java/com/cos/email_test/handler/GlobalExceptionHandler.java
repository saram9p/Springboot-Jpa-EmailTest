package com.cos.email_test.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.email_test.handler.ex.MyAsyncNotFoundException;
import com.cos.email_test.handler.ex.MyNotFoundException;
import com.cos.email_test.handler.ex.MyNotInsertJoinException;
import com.cos.email_test.util.Script;
import com.cos.email_test.web.dto.CMRespDto;


//@ControllerAdvice 이 친구는 1. 익셉션 핸들링, 2. @Controller 의 역할까지 한다.
@ControllerAdvice
public class GlobalExceptionHandler {

	// 어떤 익셉션은 파일~~~
	// 어떤 익셉션은 데이터~~~
	// 어떤 익셉션은 뒤로 가기
	// 어떤 익셉션은 메인 페이지로 가게!!!
	
	@ExceptionHandler(value = MyNotInsertJoinException.class)
	public @ResponseBody String error3(MyNotInsertJoinException e) {  // 의존성 주입
		System.out.println("오류 터졌어: " + e.getMessage());
		return Script.back(e.getMessage());
		//return Script.href("/user/joinForm", e.getMessage());
	}
	
	@ExceptionHandler(value = MyNotFoundException.class)
	public @ResponseBody String error1(MyNotFoundException e) {  // 의존성 주입
		System.out.println("오류 터졌어: " + e.getMessage());
		return Script.href("/", e.getMessage());
	}
	
	@ExceptionHandler(value = MyAsyncNotFoundException.class)
	public @ResponseBody CMRespDto error2(MyAsyncNotFoundException e) {  // 의존성 주입
		System.out.println("오류 터졌어: " + e.getMessage());
		
		return new CMRespDto(-1, e.getMessage(), null);
	}
	
}