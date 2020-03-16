var totalImgCnt = 0;
var fnsImgCnt = 0;
var progressPer = 0;
var lastChkTime = "";
var chkFnsBl = true;

// 초기화
var initView = function() {
	$("#runBtn").show(); // 실행버튼 표시
	$("#progressViewDiv").hide(); // 진행율 표시 숨기기
	$("#urlDataViewDiv").hide(); // 시간 표시 숨기기
	$("#imageListViewDiv").hide(); // 이미지 목록 숨기기
	
	$("#progressPerViewDiv").html(""); // 진행율 숫자 제거
	$("#stsCdViewSpan").html(""); // 상태코드 제거
	$("#checkTimeViewSpan").html(""); // 응답시간 제거
	$("#startTimeViewSpan").html(""); // 시작시간 제거
	$("#endTimeViewSpan").html(""); // 종료시간 제거
	$("#imageRowViewTbody").html(""); // 이미지목록 제거
	
	totalImgCnt = 0;
	fnsImgCnt = 0;
	progressPer = 0;
};

var goProgressUpd = function() {
	if(totalImgCnt == 0) {
		progressPer = 0;
	} else {
		progressPer = fnsImgCnt / totalImgCnt * 100;
	}
	
	$("#progressPerViewDiv").html(parseInt(progressPer) + "%"); // 진행율 숫자 지정
	$("#progressPerViewDiv").css("width", parseInt(progressPer) + "%"); // 진행율 숫자 지정
	
	if(parseInt(progressPer) == 100) {
		goUrlCheckComp();
	}
};

// 실행
var goUrlCheck = function() {
	if(chkFnsBl) {
		initView();
		
		chkFnsBl = false;
		
		var url = $("input[name=url]").val();
		
		if(url == "") {
			alert("대상 주소가 지정 되지 않았습니다.");
			$("input[name=url]").focus();
			return;
		}
		
		if(url.indexOf("http://") != 0 && url.indexOf("https://") != 0) {
			alert("대상 주소가 잘못 지정되었습니다.\nhttp:// or https:// 로 시작되어야 합니다.");
			$("input[name=url]").focus();
			return;
		}
		
		$("#runBtn").hide(); // 실행버튼 숨기기
		
		$("#progressViewDiv").show(); // 진행율 표시
		$("#urlDataViewDiv").show(); // 데이터 표시
		$("#imageListViewDiv").show(); // 이미지 목록 표시
		
		// 진행율 초기화
		totalImgCnt = 0;
		fnsImgCnt = 0;
		goProgressUpd();
		
		var options = {
			 url : "/crawler/urlCheck.json"
			,params : $("#UrlCheckForm").serialize()
			,func : function(data) {
				if(data.result) {
					$("#stsCdViewSpan").text(data.stsCd);
					$("#checkTimeViewSpan").text(data.responseTime + "ms");
					$("#startTimeViewSpan").text(data.startTime);
					
					totalImgCnt = data.list.length;
					
					if(data.list != null && data.list.length > 0) {
						for(var i=0;i<data.list.length;i++) {
							goUrlImageCheck(data.list[i], i);
						}
					}
				} else {
					initView();
					alert(data.resultMsg);
					chkFnsBl = true;
				}
			}
		};
		fnAjax(options);
	} else {
		alert("진행중입니다. 완료 후 다시 실행하여 주시기 바랍니다.");
	}
};

// 이미지 개별 확인 요청
var goUrlImageCheck = function(img, seq) {
	var html = "";
	
	html += "<tr id=\"imageRowViewTr_" + seq + "\">";
	html += "<td>";
	html += seq + 1;
	html += "</td>";
	html += "<td>";
	html += img.url;
	html += "</td>";
	html += "<td>";
	html += img.fileName;
	html += "</td>";
	html += "<td id=\"imageRowStsViewTd_" + seq + "\">";
	html += "</td>";
	html += "<td id=\"imageRowRspsViewTd_" + seq + "\">";
	html += "</td>";
	html += "<td id=\"imageRowSizeViewTd_" + seq + "\">";
	html += "</td>";
	html += "<td id=\"imageRowEndTimeViewTd_" + seq + "\">";
	html += "</td>";
	html += "<td id=\"imageRowPrgViewTd_" + seq + "\">";
	html += "<span style=\"color:BLUE;\">진행중</span>";
	html += "</td>";
	html += "</tr>";
	html += "<tr style=\"display:none;\" id=\"imageRowThumbViewTr_" + seq + "\">";
	html += "<td colspan=\"8\" id=\"imageRowThumbViewTd_" + seq + "\">";
	html += "</td>";
	html += "</tr>";
	
	$("#imageRowViewTbody").append(html);
	
	img["seq"] = seq;
	
	var options = {
		 url : "/crawler/urlImgCheck.json"
		,params : img
		,func : function(data) {
			var seq = data.seq;
			
			if(data.result) {
				var imgHtml = "";
				imgHtml += "<img src=\"";
				imgHtml += data.imgThumbUrl;
				imgHtml += "\" />";
				$("#imageRowThumbViewTd_" + seq).html(imgHtml);
				$("#imageRowThumbViewTr_" + seq).show();
				
				var imgPrgBtnHtml = "";
				imgPrgBtnHtml += "<span style=\"color:GREEN;\">완료</span><br/>";
				imgPrgBtnHtml += "<a clss=\"bg-success\" href=\"" + data.imgPath + "\" target=\"_blank\">다운로드</a>";
				$("#imageRowPrgViewTd_" + seq).html(imgPrgBtnHtml);
				$("#imageRowEndTimeViewTd_" + seq).html(data.chkTime);
				
				lastChkTime = data.chkTime;
			} else {
				$("#imageRowPrgViewTd_" + seq).html("<span style=\"color:RED;\">" + data.resultMsg + "</span>");
			}
			
			if(data.fileSize != undefined && data.fileSize != null) {
				$("#imageRowSizeViewTd_" + seq).html(data.fileSize);
			}
			if(data.stsCd != undefined && data.stsCd != null) {
				$("#imageRowStsViewTd_" + seq).html(data.stsCd);
			}
			if(data.responseTime != undefined && data.responseTime != null) {
				$("#imageRowRspsViewTd_" + seq).html(data.responseTime + "ms");
			}
			
			fnsImgCnt++;
			goProgressUpd();
		}
	};
	fnAjax(options);
};


// 실행 완료
var goUrlCheckComp = function(endTime) {
	$("#runBtn").show(); // 실행버튼 표시
	
	$("#endTimeViewSpan").text(lastChkTime);
	
	chkFnsBl = true;
};

// 초기화
var goInit = function() {
	if(chkFnsBl) {
		initView();
	} else {
		alert("진행중입니다. 완료 후 다시 실행하여 주시기 바랍니다.");
	}
};

$(function(){
	initView();
});