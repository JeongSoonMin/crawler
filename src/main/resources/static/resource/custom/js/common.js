// 영역 클리어
var fnClearArea = function(id) {
	$("#" + id).html("");
};

/*
 * ajax 호출 액션
 * 
 * var options = {
 * 		 url : "test.do"
 * 		,params : $("#formId").serialize() or "key=aaa&id=bbb"
 * 		,func : function(data) { }
 * 
 */
var fnAjax = function(options) {
	if(options.async == undefined || options.async == null) {
		options.async = true;
	}
	
	$.ajax({
		url : options.url,
		data : options.params,
		type : "POST",
		dataType : "json",
		cache : false,
		async : options.async,
		success : 
			function(data) {
				if(options.func != undefined && options.func != null && typeof options.func == "function") {
					options.func.call(this, data);
				}
			},
		complete : 
			function(data) {
			},
		error : 
			function(jqXHR, textStatus, errorThrown){
				if(options.error != undefined && options.error != null && typeof options.error == "function") {
					options.error.call(this,jqXHR);
				} else {
					alert("시스템 오류가 발생하였습니다.");
				}
			}
		}
	);
};

/*
 * AjaxPage 액션
 * var options = {
 * 		 url : "test.do"
 * 		,params : $("#formId").serialize() or "key=aaa&id=bbb"
 * 		,viewId : "testView"
 */

var fnAjaxPage = function(options) {
	if(options.async == undefined || options.async == null) {
		options.async = true;
	}
	
	$.ajax({
		url : options.url,
		data : options.params,
		type : "POST",
		dataType : "html",
		cache : false,
		async : options.async,
		success : 
			function(data) {
				$("#" + options.viewId).html(data);
			},
		complete : 
			function(data) {
			},
		error : 
			function(jqXHR, textStatus, errorThrown){
				if(options.error != undefined && options.error != null && typeof options.error == "function") {
					options.error.call(this,jqXHR);
				} else {
					alert("시스템 오류가 발생하였습니다.");
				}
			}
		}
	);
};

/*
 * Page 이동 액션
 * var options = {
 * 		 url : "test.do"
 * 		,formId : "testForm"
 */
var fnPage = function(options) {
	if(options.formId == undefined || options.formId == null || options.formId == "") {
		location.href = options.url;
	} else {
		$("#" + options.formId).attr("action", options.url);
		$("#" + options.formId).submit();
	}
};
