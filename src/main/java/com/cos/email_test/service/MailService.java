package com.cos.email_test.service;

import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cos.email_test.domain.user.User;
import com.cos.email_test.handler.MailHandler;
import com.cos.email_test.handler.ex.MyAsyncNotFoundException;
import com.cos.email_test.web.dto.AuthEmailReqDto;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class MailService {
	private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "lele17q@gmail.com";

	// 인증키 생성
	private String getKey(int size) {
		return getAuthCode(size);
	}

	// 인증코드 난수 발생
	private String getAuthCode(int size) {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		int num = 0;

		while (buffer.length() < size) {
			num = random.nextInt(10);
			buffer.append(num);
		}

		return buffer.toString();
	}

//	public String sendAuthMail(AuthMailReqDto dto) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		// 6자리 난수 인증번호 생성
//		String authKey = getKey(6);
//		
//		// 받는 사람
//		message.setTo(dto.getEmail());
//
//		// 보내는 사람
//		message.setFrom(MailService.FROM_ADDRESS);
//
//		// 제목
//		String TitleContent = "이메일 인증";
//		message.setSubject(TitleContent);
//
//		// 메시지
//		String htmlContent = "<h1>[이메일 인증]</h1><p>인증 번호에 아래 글자를 입력하시면 이메일 인증이 완료됩니다.</p>";
//		htmlContent += "<p>" + authKey + "</p>";
//		message.setText(htmlContent);
//
//        mailSender.send(message);
//        
//		return authKey;
//    }
	
	// 인증메일 보내기
	public String sendAuthMail(AuthEmailReqDto dto) {
		// 6자리 난수 인증번호 생성
		String authKey = getKey(6);

		try {
			MailHandler mailHandler = new MailHandler(mailSender);

			// 받는 사람
			mailHandler.setTo(dto.getEmail());
			// 보내는 사람
			mailHandler.setFrom(MailService.FROM_ADDRESS);
			// 제목
			String TitleContent = "이메일 인증";
			mailHandler.setSubject(TitleContent);
			// HTML Layout // +"<p> <img src='cid:sample-img'>"
			String htmlContent = "<h1>[이메일 인증]</h1><p>해당 인증 번호을 인증번호 확인란에 기입하여 주세요.</p>";
			htmlContent += "<p>" + authKey + "</p>";
			
//			htmlContent += "<a href='http://localhost:8080/member/signUpConfirm?email=" + dto.getEmail();
//			htmlContent += "&authKey=" + authKey;
//			htmlContent += "target='_blenk'>이메일 인증 확인</a>";

			mailHandler.setText(htmlContent, true);

            mailHandler.send();
        }
        catch(Exception e){
            throw new MyAsyncNotFoundException("이메일을 찾지 못했습니다.");
        }
		
		return authKey;
	}
    
	// 이메일로 아이디를 보내기
    public void mailSend(User check) {
    	System.out.println("아이디: " + check.getUsername());
    	
        try {
            MailHandler mailHandler = new MailHandler(mailSender);            
            
            // 받는 사람
           mailHandler.setTo(check.getEmail());
            // 보내는 사람
           mailHandler.setFrom(MailService.FROM_ADDRESS);
            // 제목
           String TitleContent = "아이디를 전송하였습니다";
           mailHandler.setSubject(TitleContent);
            // HTML Layout //  +"<p> <img src='cid:sample-img'>"
            String htmlContent = "<h1>[아이디]</h1><p>찾으시는 아이디</p>";
            htmlContent += "<p>" + check.getUsername() + "</p>";
            mailHandler.setText(htmlContent, true);
            // 첨부 파일
//           mailHandler.setAttach("newTest.txt", "static/originTest.txt");
            // 이미지 삽입
//           mailHandler.setInline("Screenshot_1.png", "static/Screenshot_1.png");

            mailHandler.send();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

	// 이메일로 비밀번호 변경 링크 보내기
    public void mailPwLinkSend(User check) {
    	System.out.println("아이디: " + check.getUsername());
    	String authKey = getKey(6);
        try {
            MailHandler mailHandler = new MailHandler(mailSender);            
            
            // 받는 사람
           mailHandler.setTo(check.getEmail());
            // 보내는 사람
           mailHandler.setFrom(MailService.FROM_ADDRESS);
            // 제목
           String TitleContent = "비밀번호 변경 링크입니다.";
           mailHandler.setSubject(TitleContent);
            // HTML Layout //  +"<p> <img src='cid:sample-img'>"
           String htmlContent = "<h1>[비밀번호 변경 링크]</h1><br><p>인증키는 " + authKey + " 입니다.</p><p>아래 링크를 클릭하시면 비밀번호 변경 페이지로 이동합니다.</p>"
                   + "<a href='http://localhost:8080/user/pwChange?username=" 
                   + check.getUsername() + "&email=" + check.getEmail() + "&name=" + check.getName() + "&birth=" + check.getBirth() + "&authKey=" + authKey +  "' target='_blenk' onclick='pwChange();'>이메일 인증 확인</a>";
            mailHandler.setText(htmlContent, true);
            // 첨부 파일
//           mailHandler.setAttach("newTest.txt", "static/originTest.txt");
            // 이미지 삽입
//           mailHandler.setInline("Screenshot_1.png", "static/Screenshot_1.png");

            mailHandler.send();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
   