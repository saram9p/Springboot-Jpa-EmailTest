package com.cos.email_test.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CMRespDto<T> {
	private int code; // 1 성공, -1 실패, 통일시킴
	private String msg;
	private T body;
}
