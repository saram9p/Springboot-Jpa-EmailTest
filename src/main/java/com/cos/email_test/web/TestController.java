package com.cos.email_test.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.email_test.domain.auth.AuthEmail;
import com.cos.email_test.domain.auth.AuthEmailRepository;
import com.cos.email_test.domain.user.User;
import com.cos.email_test.domain.user.UserRepository;
import com.cos.email_test.handler.ex.MyAsyncNotFoundException;
import com.cos.email_test.handler.ex.MyNotFoundException;
import com.cos.email_test.handler.ex.MyNotInsertJoinException;
import com.cos.email_test.service.AuthEmailService;
import com.cos.email_test.service.MailService;
import com.cos.email_test.service.UserService;
import com.cos.email_test.util.MyAlgorithm;
import com.cos.email_test.util.SHA;
import com.cos.email_test.util.Script;
import com.cos.email_test.util.mChkAuthKey;
import com.cos.email_test.web.dto.AuthEmailReqDto;
import com.cos.email_test.web.dto.CMRespDto;
import com.cos.email_test.web.dto.FindReqDto;
import com.cos.email_test.web.dto.JoinReqDto;
import com.cos.email_test.web.dto.LoginReqDto;
import com.cos.email_test.web.dto.MailDto;
import com.cos.email_test.web.dto.PwChangeReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TestController {

	private final UserRepository userRepository;
	private final AuthEmailRepository authEmailRepository;
	private final HttpSession session;
	private final MailService mailService;
	private final UserService userService;
	private final AuthEmailService authEmailService;

//    @GetMapping("/mail")
//    public String dispMail() {
//        return "mail";
//    }

//    @PostMapping("/mail")
//    public void execMail(MailDto mailDto) {
//        mailService.mailSend(mailDto);
//    }

	// 인덱스 페이지로 이동
	@GetMapping("/")
	public String index() {
		return "index";
	}

	// 로그인 완료 페이지로 이동
	@GetMapping("/user/detail")
	public String userDetail() {
		return "/user/detail";
	}
	
	// 로그인
	@PostMapping("/login")
	public String login(@Valid LoginReqDto dto, BindingResult bindingResult) {

    	// 유효성 검사
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드: " + error.getField());
				System.out.println("메시지: " + error.getDefaultMessage());
			}
			throw new MyNotFoundException(errorMap.toString());
		}
	
		User userEntity = userService.로그인(dto);
		
		// 세션 날라가는 조건 : 1. session.invalidate(), 2. 브라우저를 닫으면 날라감
		session.setAttribute("principal", userEntity); // 세션에 담은 이유는 인증하기 위해서

		return "redirect:/user/detail";
	}

	// 로그아웃
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	// 로그인 페이지로 이동
	@GetMapping("/user/loginForm")
	public String loginForm() {
		
		User principal = (User) session.getAttribute("principal");
		
		// 유효성검사
		// 인증 체크 (공통로직)
		if(principal != null ) { 
			return "redirect:/user/detail";
		}
		
		return "user/loginForm";
	}

	// 회원가입 페이지로 이동
	@GetMapping("/user/joinForm")
	public String joinForm() {
		return "/user/joinForm";
	}
	
	// 회원가입
	@PostMapping("/user/join")
	public @ResponseBody String join(@Valid JoinReqDto dto, BindingResult bindingResult) {
		System.out.println("에러사이즈: " + bindingResult.getFieldErrors().size());
		System.out.println("인증키: " + dto.getAuthKey());
		
		// 인증키가 DB와 일치하는지 검사
		AuthEmail AuthKey = authEmailRepository.mFindAuthKey(dto.getAuthKey());
		System.out.println(AuthKey);
		
		if(AuthKey == null)
			return Script.back("인증번호가 틀림");
		
		// 유니크 키 검사
		
    	// 유효성 검사
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드: " + error.getField());
				System.out.println("메시지: " + error.getDefaultMessage());
			}
			return Script.back(errorMap.toString());
		}
		
		userService.회원가입(dto);
		
		return Script.href("/user/loginForm", "회원가입 완료!");
	}
	
	// 아이디 찾기 페이지 이동
	@GetMapping("/user/idfind")
	public String idFind() {
		return "user/id_find";
	}
	
	// 비밀번호 찾기 페이지 이동
	@GetMapping("/user/pwfind")
	public String pwFind() {
		return "user/pw_find";
	}
	
	// 인증번호 발송 및 authKey 생성
    @PostMapping("/user/email/join")
    public @ResponseBody CMRespDto<String> emailTest(@Valid @RequestBody AuthEmailReqDto dto, Model model, BindingResult bindingResult){
    	System.out.println("email: " + dto.getEmail());
    	
    	// 유효성 검사
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("필드: " + error.getField());
				System.out.println("메시지: " + error.getDefaultMessage());
			}
			throw new MyAsyncNotFoundException(errorMap.toString());
		}
    	
		authEmailService.인증번호발송(dto);
		
       return new CMRespDto<String>(1, "성공", null);
    }
	
    // 인증번호 검증
    @PostMapping("/user/email/check")
    public @ResponseBody CMRespDto<String> authChk(@RequestBody AuthEmailReqDto dto){
    	
    	authEmailService.인증번호검증(dto);
    	
		return new CMRespDto<String>(1, "성공", null);
    	
    }
    
    // 아이디 찾기
    @PostMapping("/user/id")
    public @ResponseBody CMRespDto<String> findId(@RequestBody FindReqDto dto){
    	
    	User check = userRepository.mFindId(dto.getName(), dto.getBirth(), dto.getEmail());
    	 
    	//System.out.println(check.getUsername());
    	
    	if (check == null) {
    		throw new MyAsyncNotFoundException("데이터가 일치하지 않습니다.");
    	}
    	
    	mailService.mailSend(check);
    	
		return new CMRespDto<String>(1, "성공", null);
    	
    }


    
    // 비밀번호 찾기
    @PostMapping("/user/pw")
    public @ResponseBody CMRespDto<String> findPw(@RequestBody FindReqDto dto){
    	
    	User check = userRepository.mFindPw(dto.getUsername(), dto.getName(), dto.getBirth(), dto.getEmail());
    	 
    	//System.out.println(check.getUsername());
    	
    	if (check == null) {
    		throw new MyAsyncNotFoundException("입력한 정보가 일치하지 않아 비밀번호를 찾을 수 없습니다.");
    	}
    	
    	
    	mailService.mailPwLinkSend(check);
    	
    	
		return new CMRespDto<String>(1, "성공", null);
    	
    }
    
//	// 비밀번호 찾기 페이지 이동
//	@GetMapping("/user/pwChange")
//	public @ResponseBody String pwChange(@RequestParam Map<String, String> map ) {
//		
//		User check = userRepository.mFindPw(map.get("username"), map.get("name"), map.get("birth"), map.get("email"));
//		
//		if (check == null) {
//			throw new MyAsyncNotFoundException("데이터가 일치하지 않습니다.");
//		}
//		
//		return Script.pwChange("/user/pwfind");
//	}
        
    // 비밀번호 변경 페이지로 갈 수 있는 권한 확인
    @GetMapping("/user/pwChange")
    public @ResponseBody  String pwChangeLinkForm(@RequestParam Map<String, String> map ){
    	System.out.println(map.get("username"));
    	User check = userRepository.mFindPw(map.get("username"), map.get("name"), map.get("birth"), map.get("email"));
    	 
    	//System.out.println(check.getUsername());
    	
    	if (check == null) {
    		throw new MyAsyncNotFoundException("데이터가 일치하지 않습니다.");
    	}
    	
    	session.setAttribute("pwChk", check);
    	session.setAttribute("authKey", map.get("authKey"));
    	
		return Script.href("/user/pw_changeForm");
    	
    }
    
    // 비밀번호 변경 페이지 이동
    @GetMapping("/user/pw_changeForm")
    public String  pwChangeLink() {
    	
    	return "user/pw_change";
    }
    
    // 비밀번호 변경
    @PostMapping("/user/pw_change")
    public @ResponseBody CMRespDto<String> pwChange(@RequestBody PwChangeReqDto dto){
    	User pwChk = (User) session.getAttribute("pwChk");
    	String authKey = (String) session.getAttribute("authKey");
    	
    	if (!authKey.equals(dto.getAuthKey())) {
    		throw new MyAsyncNotFoundException("인증키가 일치하지 않습니다.");
    	}
    	
    	if (!dto.getPassword().equals(dto.getChkPassword())) {
    		throw new MyAsyncNotFoundException("두 비밀번호가 일치하지 않습니다.");
    	}  	
    	
    	// 비밀번호 암호화해서 변경
    	String encpassword = SHA.encrypt(dto.getPassword(), MyAlgorithm.SHA256);
   	    userRepository.mChangePw(encpassword, pwChk.getUsername(), pwChk.getName(), pwChk.getBirth(), pwChk.getEmail());
   	    
//    	userRepository.mChangePw(dto.getPassword(), pwChk.getUsername(), pwChk.getName(), pwChk.getBirth(), pwChk.getEmail())
//    			.orElseThrow(()-> new MyAsyncNotFoundException("비밀번호를 변경할 수 없습니다."));
//    	User userEntity = userRepository.findById(pwChk.getId()).orElseThrow(()-> new MyAsyncNotFoundException("비밀번호를 변경할 수 없습니다."));
//    	userEntity.setPassword(dto.getPassword()); // 더티 체킹, 트랜잭션 처리 중에 가능
    	
   	    // 인증키, 비밀번호 찾기에 사용된 회원정보 세션 제거
    	session.removeAttribute("authKey");
    	session.removeAttribute("pwChk");
    	
		return new CMRespDto<String>(1, "성공", null);
    	
    }
    
    
    
}
	

