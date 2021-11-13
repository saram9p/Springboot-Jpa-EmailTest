<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>


<div class="container">
	<div class="d-flex justify-content-center">
		<div class="d-flex flex-column" style="width:600px;">
			<div>
				<button type="button" class="btn btn-outline-secondary active" data-bs-toggle="button" aria-pressed="true"  onclick="location.href='/user/idfind'">아이디 찾기</button>
				<button type="button" class="btn btn-outline-primary" onclick="location.href='/user/pwfind'">비밀번호 찾기</button>
				<div class="border border-secondary"></div>
			</div>
			<div>
				<form onsubmit="findId(event)">
				<div class="mb-3 mt-3 d-flex justify-content-center ">
					<div class="d-flex flex-column">
						<label for="name">이름</label> 
						<input type="text" class="form-control" id="name" placeholder="이름을 입력해주세요." required="required" style="width:400px;">
					</div>
				</div>
				<br>
				<div class="mb-3 mt-3 d-flex justify-content-center">
					<div style="width: 400px;">
					<label for="birthday">생년월일</label>&emsp;
					<input type="date" name="dateofbirth"  id="birth" required="required">
					</div>
				</div>
				
				<br>
				<div class="mb-3 d-flex justify-content-center ">
					<div class="d-flex flex-column">
						<label for="email">이메일</label> 
						<input type="email" class="form-control" id="email" placeholder="등록된 이메일을 입력해주세요"	required="required" style="width:400px;">
					</div>
				</div>
				<p style="text-align: center;">등록된 이메일 주소로 아이디가 전송됩니다.</p>
				<div class="d-flex justify-content-center">
					<div id="loading"></div>
					<button type="submit" class="btn btn-primary">&emsp;찾기&emsp;</button>
					<button type="button" class="btn btn-secondary" style="margin-left: 10px" onclick="location.href='/user/loginForm'">&emsp;취소&emsp;</button>
				</div>
				</form>
			</div>
		</div>
		<!-- The Modal -->
		<div class="modal fade" id="idFindModal">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal Header -->
					<div class="modal-header">
						<h4 class="modal-title"></h4>
						<!-- <button type="button" class="btn-close" data-bs-dismiss="modal"></button>  -->
					</div>

					<!-- Modal body -->
					<div class="modal-body"></div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" id="modal-exit" class="btn btn-danger" data-bs-dismiss="modal" >닫기</button>
					</div>
				</div>
			</div>
		</div>
				
	</div>
</div>

<script>
	async function findId(event) {
		event.preventDefault();
		
		LoadingWithMask();
		
		let FindReqDto = {
				name: document.querySelector("#name").value,
				birth: document.querySelector("#birth").value,
				email: document.querySelector("#email").value,
		};
		
		let response = await fetch("http://localhost:8080/user/id", {
			method: "post",
			body: JSON.stringify(FindReqDto),
			headers: {
				"Content-Type": "application/json; charset=utf-8"
			}
		});
		
		let parseResponse = await response.json();
		console.log(parseResponse);
		
			if(parseResponse.code == 1) { // ajax는 자바스크립트에서 분기 시켜줘야 한다
				//alert("이메일로 아이디를 보냈습니다.");
				closeLoadingWithMask();
				$(".modal-title").text("아이디 찾기 성공");
				$(".modal-body").text("등록된 이메일 주소로 아이디가 전송되었습니다.");
				$("#modal-exit").attr("onclick","window.location.reload();")
				$("#idFindModal").modal('show');
				
			} else {
				closeLoadingWithMask();
				console.log(parseResponse.message);
				$(".modal-title").text("아이디 찾기 실패");
				$(".modal-body").text("입력한 정보가 일치하지 않아 아이디를 찾을 수 없습니다.");
				//$("#modal-exit").attr("onclick","history.back();")
				$("#idFindModal").modal('show');
		        //alert("입력한 정보가 일치하지 않아 아이디를 찾을 수 없습니다.");
			}

	}
</script>

<script>
	function LoadingWithMask() {
		//화면의 높이와 너비를 구함
		var maskHeight = $(document).height();
		var maskWidth = window.document.body.clientWidth;

		//화면에 출력할 마스크를 설정
		var mask = "<div id='mask' style='position:absolute; z-index:9000; background-color:#000000; display:none; left:0; top:0;'></div>";
		var loadingImg = '';

		loadingImg += "<div id='loadingImg' class='spinner-border text-muted'></div>";

		//화면에 레이어 추가
		$("body").append(mask)

		//마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채움
		$('#mask').css({
			'width' : maskWidth,
			'height' : maskHeight,
			'opacity' : '0.3'
		});

		//마스크 표시
		$('#mask').show();

		//로딩중 이미지 표시
		$("#loading").append(loadingImg)
		$('#loadingImg').show();
	}
	
	function closeLoadingWithMask() {
	    $('#mask, #loadingImg').hide();
	    $('#mask, #loadingImg').remove(); 
	}
	
</script>

<%@ include file="../layout/footer.jsp"%>