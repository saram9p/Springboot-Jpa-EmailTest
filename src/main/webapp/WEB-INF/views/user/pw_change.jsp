<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<%
	String strReferer = request.getHeader("referer");
	if(strReferer == null){
		%><script type="text/javascript">
		alert("정상적인 경로를 통해 다시 접근해 주십시오.");
		document.location.href="/user/loginForm";
		</script><%
		return;
	}
%>

<div class="container">
	<div class="d-flex justify-content-center">
		<div class="d-flex flex-column" style="width: 600px;">
			<div>
				<h1 class="text-primary text-center">비밀번호 변경</h1>
				<div class="border border-primary"></div>
			</div>
			<br>
			<div>
				<form onsubmit="pwChange(event)">
					<div class="mb-3 mt-3 d-flex justify-content-center ">
						<div class="d-flex flex-column">
							<label for="pw">인증번호</label> <input type="text"
								class="form-control" id="authKey" placeholder="인증번호를 입력해주세요."
								required="required" style="width: 400px;">
						</div>
					</div>
						<br>
					<div class="mb-3 mt-3 d-flex justify-content-center ">
						<div class="d-flex flex-column">
							<label for="pw">새 비밀번호</label> <input type="password"
								class="form-control" id="password" placeholder="비밀번호를 입력해주세요."
								required="required" style="width: 400px;">
						</div>
					</div>
					<br>
					<div class="mb-3 mt-3 d-flex justify-content-center">
						<div class="d-flex flex-column">
							<label for="pwChk">비밀번호 확인</label> <input type="password"
								class="form-control" id="chkPassword" placeholder="비밀번호를 다시 입력해주세요." required="required" style="width: 400px;">
						</div>
					</div>
					<br>

					<div class="d-flex justify-content-center">
						<button type="submit" class="btn btn-primary">&emsp;비밀번호
							변경&emsp;</button>
					</div>

				</form>
			</div>
		</div>
	</div>

<div>${pwChk.username }</div>
<div>${authKey}</div>

	<!-- The Modal -->
	<div class="modal fade" id="pwdChangeModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title"></h4>
					<!-- <button type="button" class="btn-close" data-bs-dismiss="modal"></button>  -->
				</div>

				<!-- Modal body -->
				<div class="modal-body">비밀번호 변경에 실패했습니다.</div>
				<div id="loading"></div>
				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger"
						data-bs-dismiss="modal" onclick="window.location.reload()">닫기</button>
				</div>

			</div>
		</div>
	</div>

</div>

<script>
	async function pwChange(event) {
		event.preventDefault();
		
		let PwChangeReqDto = {
				authKey: document.querySelector("#authKey").value,
				password: document.querySelector("#password").value,
				chkPassword: document.querySelector("#chkPassword").value,
		};
		
		let response = await fetch("http://localhost:8080/user/pw_change", {
			method: "post",
			body: JSON.stringify(PwChangeReqDto),
			headers: {
				"Content-Type": "application/json; charset=utf-8"
			}
		});
		
		let parseResponse = await response.json();
		console.log(parseResponse);
		
			if(parseResponse.code == 1) { // ajax는 자바스크립트에서 분기 시켜줘야 한다
				//alert("이메일로 비밀번호를 보냈습니다.");
				$(".modal-title").text("비밀번호 변경 완료");
				$(".modal-body").text("비밀번호가 변경되었습니다");
				$("#modal-exit").attr("onclick","window.location.reload();")
				$("#pwdChangeModal").modal('show');
				
			} else {
				console.log(parseResponse.msg);
				$(".modal-title").text("비밀번호 변경 실패");
				$(".modal-body").text(parseResponse.msg);
				$("#pwdChangeModal").modal('show');
		        //alert("입력한 정보가 일치하지 않아 비밀번호를 찾을 수 없습니다.");
			}

	}
</script>

<%@ include file="../layout/footer.jsp"%>