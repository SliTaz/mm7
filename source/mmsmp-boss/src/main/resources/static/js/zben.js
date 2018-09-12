'use strict';

var apiUrl = "http://localhost:8080";
var address=window.location.hostname;
var tokenRequestHeaderKey = "Authorization";
var platform = "mmsmpboss" + "_";
var ZBappId = 1;
var tokenKey = platform + "jwttoken";
var ZBuserNameKey = platform + "ZBuserName";
var ZBuserIdKey = platform + "ZBuserId";
var ZBuserMenusKey = platform + "ZBuserMenus";
var localStoragTokenKey = platform + "epaymenttoken";
var ZBlocalStorageService = {};
var ZBalarmBean = {};
var ZBalarmMsec = 600000;// 循环获取告警信息的毫秒数
var ZBalarmInfosKey = platform + "ZBalarmInfos";// 告警本地存储的key
var ZBalarmInfosDateKey = platform + "ZBalarmInfosDate";// 告警本地存储时间的key
// alert(navigator.language);
var naLan = navigator.language;
if (naLan == undefined || naLan == "") {
	naLan = navigator.browserLanguage;
}

var dtServerSide = false;
//日志信息路径
var monitorLogInfoUrl ="http://www.baidu.com/";
//消息队列路径
var monitorMessageQueueUrl ="http://mail.163.com/";
//系统信息路径
var monitorSystenInfoUrl ="http://www.sogou.com/";
var monitorProxyStatusUrl ="http://www.sogou.com/";

//超时提示次数
var timeOutCount = 0;


//处理国际化
var file="";
var i18nData={};

if(naLan=='en'){
	file="../il8n/en.json";
}else if(naLan.indexOf("en-") >= 0){
	naLan = "en";
	file="../il8n/en.json";
}else if(naLan=='es'){
	file="../il8n/es.json";
}else if(naLan.indexOf("es-") >= 0){
	naLan = "es";
	file="../il8n/es.json";
}else if(naLan=='zh'){
	file="../il8n/zh.json";
}else if(naLan.indexOf("zh") >= 0){
	naLan = "zh";
	file="../il8n/zh.json";
}else{
	naLan = "es";
	file="../il8n/es.json";
}


$.ajaxSettings.async = false;
$.getJSON(file,function(data){i18nData=data}); 
//title的国际化
$('title').text(i18nData["common.mmsmpTitle"]);

$('#display_panel_names').text(i18nData["common.panel"]);
$('#display_notice_names').text(i18nData["common.notice"]);
$('#display_alarm_name').text(i18nData["common.alarm"]);
$('#display_calendar_name').text(i18nData["common.calendar"]);
$('#display_welcome_name').text(i18nData["common.welcome"]);
$('#display_mainface_name').text(i18nData["common.interFaceStatistics"]);
$('#display_bankface_name').text(i18nData["common.bankiInterFaceStatistics"]);
$('#display_realTimeFace_name').text(i18nData["common.realtimeStatistics"]);
$('#display_kpiFaceItem_name').text(i18nData["common.requestTopKpiStatistics"]);
$('#display_buyerFaceItem_name').text(i18nData["common.buyerStatistics"]);
$('#display_sellerFaceItem_name').text(i18nData["common.sellerStatistics"]);
$('#display_otherSellerTotalFaceItem_name').text(i18nData["common.otherSellerTotal"]);
$('#display_EPSTPSFaceItem_name').text(i18nData["common.EPSTPS"]);
$('#display_clapStoreTotalFaceItem_name').text(i18nData["common.clapStoreTotal"]);
$('#display_buyerTotalFaceItem_name').text(i18nData["common.buyerTotal"]);
$('#display_buyerIncrementalFaceItem_name').text(i18nData["common.buyerIncrementalTotal"]);
$('#display_sellerIncrementalFaceItem_name').text(i18nData["common.sellerIncrementalTotal"]);
$('#display_transactionFaceItem_name').text(i18nData["common.transactionStatistics"]);


//适配局域网访问
var ipAddrStrs=address.split('.');
var ipPix=ipAddrStrs[0]+"."+ipAddrStrs[1]+"."+ipAddrStrs[2]+".";
if(ipAddrStrs.length==4){
	if(ipAddrStrs[3]=="10"||ipAddrStrs[3]=="8"||ipAddrStrs[3]=="9"){
	         apiUrl = "http://"+ipPix+"7/api";
	}
}
var vm = this;
vm.alarmInfoOne = alarmInfoOne;
function alarmInfoOne(id){
//var obj_id=id;
alert(id);
//	getAlarmById(obj_id);
}

function i18n(key)
{	
   return i18nData[key];
}

function tableHandle(){
	$(window).resizeEnd({
	    delay: 300
	}, function(){
		var flag = $(".dataTables_scrollBody").css("overflow");
    	if(flag='visible'){
		}
    	if(flag='auto'){
			//水平滚动条带列名称滚动
			$(".dataTables_scroll").attr("style","overflow:auto");
			$(".dataTables_scrollHead").css("overflow", "");
			$(".dataTables_scrollBody").css("overflow", "");
			$(".dataTables_scrollBody").attr("style","border:0px");
			$("#table_id").attr("style","border-bottom:1px solid black");
			//水平滚动条带列名称滚动
		}
	    //处理代码
		$("#table_id").dataTable().fnAdjustColumnSizing(false);//重新计算列宽
	});
}
//时间时分秒格式
function timeFormat() {
var timeZH="yyyy-mm-dd hh:ii:ss";
var timeAll= "dd/mm/yyyy hh:ii:ss"
	var naLan = navigator.language;
	if (naLan == undefined || naLan == "") {
		naLan = navigator.browserLanguage;
	}	
	if(naLan=='zh'){
	return  timeZH; 
	}else if(naLan.indexOf("zh") >= 0){
		return  timeZH; 
	}else{
		return  timeAll; 	
    }
}


//时间day格式
function dayFormat() {
var timeZH="yyyy-mm-dd";
var timeAll= "dd/mm/yyyy"
	var naLan = navigator.language;
	if (naLan == undefined || naLan == "") {
		naLan = navigator.browserLanguage;
	}	
	if(naLan=='zh'){
	return  timeZH; 
	}else if(naLan.indexOf("zh") >= 0){
		return  timeZH; 
	}else{
		return  timeAll; 	
    }
}
//时间month格式
function monthFormat() {
var timeZH="yyyy-mm";
var timeAll= "mm/yyyy"
	var naLan = navigator.language;
	if (naLan == undefined || naLan == "") {
		naLan = navigator.browserLanguage;
	}	
	if(naLan=='zh'){
	return  timeZH; 
	}else if(naLan.indexOf("zh") >= 0){
		return  timeZH; 
	}else{
		return  timeAll; 	
    }
}


//时间时分秒格式yyyy-MM-dd hh:mm:ss
function timeFormatNew(data) {
	
	if(!data){
		return '';
	}else{
	var naLan = navigator.language;
	if (naLan == undefined || naLan == "") {
		naLan = navigator.browserLanguage;
	}	
	if(naLan=='zh'){
	return  data; 
	}else if(naLan.indexOf("zh") >= 0){
		return  data; 
	}else{
		
		return  data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2)+" "+data.substring(11,19); 	
    }
	
   }
}
//时间时分秒格式yyyy-MM-dd 
function dayFormatNew(data) {
	
	if(!data){
		return '';
	}else{
	var naLan = navigator.language;
	if (naLan == undefined || naLan == "") {
		naLan = navigator.browserLanguage;
	}	
	if(naLan=='zh'){
	return  data; 
	}else if(naLan.indexOf("zh") >= 0){
		return  data; 
	}else{
		
		return  data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2); 	
    }
	
   }
}
//时间时分秒格式yyyy-MM 
function monthFormatNew(data) {
	
	if(!data){
		return '';
	}else{
	var naLan = navigator.language;
	if (naLan == undefined || naLan == "") {
		naLan = navigator.browserLanguage;
	}	
	if(naLan=='zh'){
	return  data; 
	}else if(naLan.indexOf("zh") >= 0){
		return  data; 
	}else{
		
		return  data.substring(3,7)+"-"+data.substring(0,2); 	
    }
	
   }
}
//时间时分秒格式yyyy-MM-dd hh:mm:ss
function timeFormatOld(data) {
	
	if(!data){
		return '';
	}else{
	var naLan = navigator.language;
	if (naLan == undefined || naLan == "") {
		naLan = navigator.browserLanguage;
	}	
	if(naLan=='zh'){
	return  data; 
	}else if(naLan.indexOf("zh") >= 0){
		return  data; 
	}else{
		
		return  data.substring(8,10)+"/"+data.substring(5,7)+"/"+data.substring(0,4)+" "+data.substring(11,19); 	
    }
	
   }
}
//时间时分秒格式yyyy-MM-dd 
function dayFormatOld(data) {
	
	if(!data){
		return '';
	}else{
	var naLan = navigator.language;
	if (naLan == undefined || naLan == "") {
		naLan = navigator.browserLanguage;
	}	
	if(naLan=='zh'){
	return  data; 
	}else if(naLan.indexOf("zh") >= 0){
		return  data; 
	}else{
		
		return  data.substring(8,10)+"/"+data.substring(5,7)+"/"+data.substring(0,4); 	
    }
	
   }
}
//时间时分秒格式yyyy-MM 
function monthFormatOld(data) {
	
	if(!data){
		return '';
	}else{
	var naLan = navigator.language;
	if (naLan == undefined || naLan == "") {
		naLan = navigator.browserLanguage;
	}	
	if(naLan=='zh'){
	return  data; 
	}else if(naLan.indexOf("zh") >= 0){
		return  data; 
	}else{
		
		return  data.substring(5,7)+"/"+data.substring(0,4); 	
    }
	
   }
}


function translateProvider($translateProvider) {
	$translateProvider.useStaticFilesLoader({
		prefix : 'il8n/',
		suffix : '.json'
	});
	// $translateProvider.preferredLanguage('en-us');

	// $translateProvider.preferredLanguage('zh-CN');
	$translateProvider.preferredLanguage(naLan);

	$translateProvider.useLoaderCache(false);
	$translateProvider.forceAsyncReload(false);
}
function setDtOptionsAllSide(vm, tService) {
	vm.bean = {};
	vm.beans = [];
	vm.dtInstance = {};
	vm.dtOptions.withDataProp('body').withOption('processing', true)
			.withPaginationType('full_numbers').withOption('scrollY', '100%')
			.withOption('scrollCollapse', true).withFixedColumns({
				leftColumns : 1,
				rightColumns : 1
			}).withButtons([
			// 'columnsToggle',
			{
				extend : 'colvis',
				text :i18n("common.columnVisble"),
				className : 'btn btn-info'
			}
			/*
			 * , { extend : 'excel', text : 'excel', className : 'btn btn-info' }
			 */]).withOption('language', {
				"url" : "lib/datatables/il8n/" + naLan + ".json"
			});

	vm.ltbean = {};
	vm.ltbeans = {};
}

function setDtOptionsAllSideTwo(vm, tService) {
	vm.bean = {};
	vm.beans = [];
	vm.dtInstanceTwo = {};
	vm.dtOptionsTwo.withDataProp('body').withOption('processing', true)
			.withPaginationType('full_numbers').withOption('scrollY', '100%')
			.withOption('scrollCollapse', true).withFixedColumns({
				leftColumns : 1,
				rightColumns : 1
			}).withButtons([
			// 'columnsToggle',
			{
				extend : 'colvis',
				text :i18n("common.columnVisble"),
				className : 'btn btn-info'
			}
			/*
			 * , { extend : 'excel', text : 'excel', className : 'btn btn-info' }
			 */]).withOption('language', {
				"url" : "lib/datatables/il8n/" + naLan + ".json"
			});

	vm.ltbean = {};
	vm.ltbeans = {};
}

function setDtOptionsAllSideThree(vm, tService) {
	vm.bean = {};
	vm.beans = [];
	vm.dtInstanceThree = {};
	vm.dtOptionsThree.withDataProp('body').withOption('processing', true)
			.withPaginationType('full_numbers').withOption('scrollY', '100%')
			.withOption('scrollCollapse', true).withFixedColumns({
				leftColumns : 1,
				rightColumns : 1
			}).withButtons([
			// 'columnsToggle',
			{
				extend : 'colvis',
				text :i18n("common.columnVisble"),
				className : 'btn btn-info'
			}
			/*
			 * , { extend : 'excel', text : 'excel', className : 'btn btn-info' }
			 */]).withOption('language', {
				"url" : "lib/datatables/il8n/" + naLan + ".json"
			});

	vm.ltbean = {};
	vm.ltbeans = {};
}

function initltCommon(vm, localStorageService, topleftService) {
	vm.ltuserName = localStorageService.get(ZBuserNameKey);
	if (localStorageService.get(ZBuserMenusKey)) {
		vm.ltbeans = doSelectMenu(localStorageService.get(ZBuserMenusKey),
				window.location.pathname);
	} else {

		topleftService.fetchAllUserMenus(vm.ltuserName, ZBappId).then(
				function(data) {
					// data = ss;
					vm.ltbeans = data;
					localStorageService.set(ZBuserMenusKey, data);

				}, function(errResponse) {
					//
				});

	}
	
	//start
	var naLan = navigator.language;
	if (naLan == undefined || naLan == "") {
		naLan = navigator.browserLanguage;
	}
	
	if(naLan=='en'){
	}else if(naLan.indexOf("en-") >= 0){
		naLan = "en";
	}else if(naLan=='es'){
	}else if(naLan.indexOf("es-") >= 0){
		naLan = "es";
	}else if(naLan=='zh'){
	}else if(naLan.indexOf("zh") >= 0){
		naLan = "zh";
	}else{
		naLan = "es";
	}
	
	vm.lan = naLan;
	//end
	
	vm.linkto = linkto;
	function linkto(url) {
		if (url) {
			window.location = url;
		}

	}
	
	vm.exit = exit;
	function exit() {
		topleftService.exit();
	}
	vm.getCode=getCode;
	vm.topModifyPassword = topModifyPassword;
	function topModifyPassword() {
		BootstrapDialog.show({
			title : i18n("common.modifyPwd"),
			message: function(dialog) {
				var $message= $(
						  "<span style='margin-left:90px;width:120px;display:inline-block'>"+i18n("common.originPwd")+"</span>" +"<input  id='m_password' type='password' name='m_password' style='margin-left:30px' maxlength='20'>"+"<br/>"+"<br/>"+	  
						  "<span style='margin-left:90px;width:120px;display:inline-block'>"+i18n("common.newPwd")+"</span>" +"<input  id='m_newpassword' type='password' name='m_newpassword' style='margin-left:30px' maxlength='20'>"+"<br/>"+"<br/>"+ 	  
						  "<span style='margin-left:90px;width:120px;display:inline-block'>"+i18n("common.confirmPwd")+"</span>" +"<input  id='m_confirmPassword' type='password' name='m_confirmPassword' style='margin-left:30px' maxlength='20'>"+"<br/>" 	  
					);
        
                return $message;
            },
			buttons : [ {
				label : i18n("common.yes"),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					var user_id=localStorageService.get(ZBuserIdKey);
					var m_password_value=document.getElementById("m_password").value;//$("#m_password").val();
					var m_newpassword_value=document.getElementById("m_newpassword").value;//$("#m_newpassword").val();
					var m_confirmPassword_value=document.getElementById("m_confirmPassword").value;//$("#m_confirmPassword").val();
					
					vm.userRsetPasswordBean={};
					vm.userRsetPasswordBean.userId=user_id;
					vm.userRsetPasswordBean.password=m_password_value;
					vm.userRsetPasswordBean.newPassword=m_newpassword_value;
					vm.userRsetPasswordBean.confirmPassword=m_confirmPassword_value;
					
					topleftService.modifypassword(vm.userRsetPasswordBean).then(modifypasswordSuccess,
					function(errResponse) {
						console.error('Error while activating ConsumerUser.');
					});
					dialogItself.close();
				}
			}, {
				label :  i18n("common.cancel"),
				cssClass : 'btn btn-default model-tool-left',
				action : function(dialogItself) {
					dialogItself.close();
				}
			} ]
		});
	}
	
	
	vm.alarmInfoOnes = alarmInfoOnes;
	vm.getAlarmByIdSuccess=getAlarmByIdSuccess;
	function alarmInfoOnes(id) {
		var obj_id=id;
		//alert(obj_id);
		getAlarmById(obj_id);
	}
	//alarm info start by yp
	function getAlarmById(alarmId){
		topleftService.getAlarmById(alarmId).then(getAlarmByIdSuccess,
				function(errResponse) {
					console.error('Error while searching alarmInfo.');
				});
	}
	
	
	function getAlarmByIdSuccess(data){
		if (data.statusCode == "OK") {
			alarmInfoContent(data.body);
		}else{
			alert("data is not exist");
		}
	}
	
	//将告警内容进行展示
	function alarmInfoContent(bean){
		BootstrapDialog.show({
		//	title: $translate.instant('common.alarmInfoDetails'),
			message: function(dialog) {
				var $message=$(
/*						'<span id="content_detail" style="word-break:normal; width:auto; display:block; white-space:pre-wrap;word-wrap:break-word ;overflow: hidden"'+
					    '</span>'*/
						
						'<table class="table_css_new" align="center"  style="width:90%;table-layout:fixed;border:#666666 solid 1px;border-corlor: #666666;border-collapse: collapse">'+
						'<tr>'+
							'<td style="border:#666666 solid 1px;background:#f4f4f4;" width="29%" align="center" nowrap>'+i18n("alarmInfo.alarmTime")+'</td>'+
							'<td style="border:#666666 solid 1px;background: #FFFFFF;padding-left:5px;"  id="tb_alarm_time"></td>'+
						'</tr>'+						
						'<tr>'+
						   '<td style="border:#666666 solid 1px;background:#f4f4f4;" width="25%" align="center" nowrap>'+i18n("alarmInfo.alarmOrigin")+'</td>'+
						   '<td style="border:#666666 solid 1px;background: #FFFFFF;padding-left:5px;"  id="tb_alarm_origin_name"></td>'+
					    '</tr>'+					    
						'<tr>'+
						   '<td style="border:#666666 solid 1px;background:#f4f4f4;" width="25%" align="center" nowrap>'+i18n("common.alarmContent")+'</td>'+
						   '<td style="border:#666666 solid 1px;background: #FFFFFF;padding-left:5px;word-break:break-all;"  id="tb_alarm_content">'+bean.content+'</td>'+
					   '</tr>'+					   
					   '</table>'
				);
                return $message;
            },
            onshown: function(dialogRef){//打开完成
            var	alarmTimeEn=timeFormatOld(bean.alarmTime);
            	$("#tb_alarm_time").text(alarmTimeEn);
            	$("#tb_alarm_origin_name").text(bean.alarmOriginName);
            	//$("#tb_alarm_content").text(bean.content.replace(/<br\/>/g,"\r\n"));
            },
	        buttons: [
	        {
	            label: i18n("common.handle"),
	            cssClass: 'btn-primary model-tool-right',
	            action: function(dialogItself){
	            	BootstrapDialog.show({
	        			title : i18n("common.handleOpinions"),
	        			message: function(dialogItself) {
	        				var $message= $("<span>"+i18n("alarmInfo.alarmInfoCode")+":"+bean.alarmInfoCode+"<br/></span>" +
	        						"<span>"+i18n("common.alarmOrigin")+":"+bean.alarmOriginName+"<br/></span>" +
	        						"<span>"+i18n("alarmInfo.alarmTime")+":"+timeFormatOld(bean.alarmTime)+"<br/></span>" +
	        						"<span>"+i18n("common.handle.opinions")+"<br/></span>" +
	        						"<textarea type='text' id='handleView' style='height:120px'   class='form-control' id='remark' name='remark' maxlength='1000'></textarea>");
	        			
	                        return $message;
	                    },
	                    
	                    
	        			buttons : [ {
	        				label: i18n("common.yes"),
	        				cssClass : 'btn btn-danger model-tool-right',
	        				action : function(dialogItself) {
	        					var handleView=document.getElementById("handleView").value;
	        					
	        					vm.timeActivateBean={};
	        					vm.timeActivateBean.handle=getValueForSelect(handleView);
	        					
	        					topleftService.alarmHandleView(vm.timeActivateBean, bean.alarmInfoCode).then(
	        					function(data) {
	        						if(data.statusCode=="OK"){
	        							dialogItself.close();
	        							window.location="main.html";
	        						}
	        					});
	        					
	        					
	        				}

	        			}, {
	        				label :  i18n("common.cancel"),
	        				cssClass : 'btn btn-default model-tool-left',
	        				action : function(dialogItself) {
	        					dialogItself.close();
	        					
	        				}
	        			} ]
	        		});
	            	dialogItself.close();
	            }
	        }, {
	        	label :  i18n("common.cancel"),
				cssClass : 'btn btn-default model-tool-left',
				action : function(dialogItself) {
					dialogItself.close();
				}
			}
	        ]
	    });
	}
	//alarm info end  by yp
	
	
	function modifypasswordSuccess(data){
		BootstrapDialog.show({
			title : i18n("common.modifyPwd"),
			message: function(dialog) {
                return getCode(data.statusCode);
            },
			buttons : [ {
				label : i18n("common.yes"),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					dialogItself.close();
				}
			}, {
				label :  i18n("common.cancel"),
				cssClass : 'btn btn-default model-tool-left',
				action : function(dialogItself) {
					dialogItself.close();
				}
			} ]
		});
	}

	
	// 查询告警信息(在top页面上展示)
	getTopAlarmRecords(vm, localStorageService, topleftService);

}

function getTopAlarmRecords(vm, localStorageService, topleftService) {

	try {
		// if表示:本地存储有,且本地存储的时间和循环的毫秒数之和大于当前时间，则直接欧从本地获取
		if (localStorageService.get(ZBalarmInfosKey)
				&& localStorageService.get(ZBalarmInfosDateKey)
				&& judgeDateIsGetForCookie(localStorageService)) {

			var data = localStorageService.get(ZBalarmInfosKey);

			// alert("cookie yes and < haomiaoshu:cookie;"+data.recordsTotal);
			getTopAlarmRecordsSuccess(vm, data,false);

		} else {
			alarmNew(vm, localStorageService, topleftService);
			
		}

	} catch (e) {
	}

	window.setTimeout(function() {
		getTopAlarmRecords(vm, localStorageService, topleftService);
	}, ZBalarmMsec);
}


function alarmNew(vm, localStorageService, topleftService){
	// 从后台获取。
	// alert("cookie no or > haomiaoshu:db");
	// 处理top页面的alarminfo start
	var status_value = 1;// 1:未处理
	ZBalarmBean.status = status_value;
	topleftService.getAlarmRecords(ZBalarmBean).then(function(data) {
		// alert("ok");
		getTopAlarmRecordsSuccess(vm, data,true);

		localStorageService.set(ZBalarmInfosKey, data);// 进行本地存储

		var cookieDateMsec = new Date().getTime();
		// alert("cookieDateMsec:"+cookieDateMsec);
		localStorageService.set(ZBalarmInfosDateKey, cookieDateMsec);// 进行本地存储

	}, function(errResponse) {
		//
	});
	
}

// true表示本次存储的时间和循环的毫秒数之和大于当前时间，直接从本地取。false表示当前时间已经很大了。需要从后台取
function judgeDateIsGetForCookie(localStorageService) {
	try {
		if (localStorageService.get(ZBalarmInfosDateKey)) {
			var cookieDateMsec = localStorageService.get(ZBalarmInfosDateKey);
			var currentDate = new Date();
			var currentDateMsec = currentDate.getTime();
			// alert("cookieDateMsec:"+cookieDateMsec+";ZBalarmMsec:"+ZBalarmMsec+";(cookieDateMsec+ZBalarmMsec):"+(cookieDateMsec+ZBalarmMsec)+";currentDateMsec:"+currentDateMsec);

			// 如果不加5000,到这里之前会耗时。故需要加。5000是个任意数.如果不加会出现：第一次从后台。第二次从cookie。第三次从后台。第四次从cookie
			// cookieDateMsec:1498706731977;ZBalarmMsec:20000;(cookieDateMsec+ZBalarmMsec):1498706751977;currentDateMsec:1498706751552
			if ((cookieDateMsec + ZBalarmMsec) > (currentDateMsec + 5000)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	} catch (e) {
		return false;
	}
}

function getTopAlarmRecordsSuccess(vm, data,isChangeTime) {
	try {
		// alert("getTopAlarmRecordsSuccess");
		var alarmList = [];
		var alarmStatus = [];
		if (data.statusCode == "OK") {
			vm.ZBalarmRecords = [];
			alarmList = data.body;
			for(var i=0;i<alarmList.length;i++){
				if(alarmList[i].status==1){
					alarmStatus.push(alarmList[i]);
				}
				
			}
			if(isChangeTime){
				vm.ZBalarmRecords = alarmListTime(alarmStatus);
			}else{
				vm.ZBalarmRecords = alarmStatus;
			}
			vm.topAlarmWarningSize = data.recordsTotal;
		} else {
			vm.ZBalarmRecords = [];
			vm.topAlarmWarningSize = 0;
		}

		// 实时刷新 start
		// 非常棒的方法：通过外部函数获取$scope。
		// 为什么要使用:解决数据发生变化。但是页面没有实时改变。
		var appElement = document.querySelector('[ng-controller]');
		var $scope = angular.element(appElement).scope();
		$scope.$applyAsync();
		// 实时刷新 end
	} catch (e) {
	}
}
//top告警时间
function alarmListTime(data){
	for(var i=0;i<data.length;i++){
		data[i].content=data[i].content.replace(/<br\/>/g,"\r\n");
		}
	var naLan = navigator.language;
	if (naLan == undefined || naLan == "") {
		naLan = navigator.browserLanguage;
	}	
	if(naLan=='zh'){
	return  data; 
	}else if(naLan.indexOf("zh") >= 0){
		return  data; 
	}
	else{
		for(var i=0;i<data.length;i++){
			data[i].alarmTime=data[i].alarmTime.substring(8,10)+"/"+data[i].alarmTime.substring(5,7)+"/"+data[i].alarmTime.substring(0,4)+" "+data[i].alarmTime.substring(11,19)
			}
		return  data; 	
	}
}

function onltResultData(vm, data) {
	vm.ltbeans = data;
	localStorageService.set(ZBuserMenusKey, data);
}

function setDtOptionsClientSide(vm) {
	setDtOptionsAllSide(vm);
}
function setDtOptionsServerSide(vm) {
	setDtOptionsAllSide(vm);
	vm.dtOptions.withOption('serverSide', true).withOption('searching', false);

	dtServerSide = true;

}

function setDtOptionsServerSideTwo(vm) {
	setDtOptionsAllSideTwo(vm);
	vm.dtOptionsTwo.withOption('serverSide', true).withOption('searching', false);

	dtServerSide = true;

}

function setDtOptionsServerSideThree(vm) {
	setDtOptionsAllSideThree(vm);
	vm.dtOptionsThree.withOption('serverSide', true).withOption('searching', false);

	dtServerSide = true;

}

function configloginAppModule(appModule) {
	appModule.config([ '$translateProvider', translateProvider ]);
	appModule.config(function(localStorageServiceProvider) {
		localStorageServiceProvider.setPrefix(localStoragTokenKey)
				.setStorageType('localStorage').setNotify(true, true)
	});
}
function configAppModule(appModule) {
	appModule.config([ '$translateProvider', translateProvider ]);
	appModule.config(function(localStorageServiceProvider) {
		localStorageServiceProvider.setPrefix(localStoragTokenKey)
				.setStorageType('localStorage').setNotify(true, true)
	});

	appModule.config([ '$httpProvider', function($httpProvider) {
		$httpProvider.interceptors.push(HttpInterceptor);
	} ]);

	appModule
			.factory(
					'topleftService',
					[
							'$http',
							'$q',
							function($http, $q) {
								return {

									fetchAllUserMenus : function(userName,
											appId) {
										return $http
												.get(
														apiUrl
																+ "/menu/userMenus/"
																+ userName
																// + "/"
																// + appId
																+ "?weburl="
																+ window.location.pathname)
												.then(
														function(response) {
															return response.data;
														},
														function(errResponse) {
															console
																	.error('Error while fetching users');
															return $q
																	.reject(errResponse);
														});
									},
									getAlarmRecords : function(alarmInfo) {
										return $http
												.post(
														apiUrl
																+ "/alarmInfo/getAlarmRecords",
														alarmInfo)
												.then(
														function(response) {
															return response.data;
														},
														function(errResponse) {
															console
																	.error('Error while fetching users');
															return $q
																	.reject(errResponse);
														});
									},exit:function(){
										return $http.patch(apiUrl + "/logout")
										.then(
												function(response) {
													window.location = "/";
													return response.data;
													
												},
												function(errResponse) {
													
												});
										
									},
									  getAlarmById: function(id){
											return $http.get(apiUrl +"/alarmInfo/"+id)
													.then(
															function(response){
																return response.data;
															}, 
															function(errResponse){
																handleAjaxError(errResponse);
																return $q.reject(errResponse);
															}
													);
								    },
								    alarmHandleView: function(alarmInfo, id){
										return $http.put(apiUrl +"/alarmInfo/handleView/"+id, alarmInfo)
												.then(
														function(response){
															return response.data;
														}, 
														function(errResponse){
															handleAjaxError(errResponse);
															return $q.reject(errResponse);
														}
												);
								},
									modifypassword: function(userRsetPassword){
											return $http.put(apiUrl +"/sysUser/modify/password", userRsetPassword)
													.then(
															function(response){
																console.info('updateSysUser '+response);
																return response.data;
															}, 
															function(errResponse){
																console.error('Error while updating sysUser');
																return $q.reject(errResponse);
															}
													);
									}

								};

							} ]);
	/*
	 * appModule.config(function Config($httpProvider, jwtInterceptorProvider, ) { //
	 * $httpProvider.defaults.withCredentials = true;
	 * jwtInterceptorProvider.tokenGetter = function() { return
	 * $localStorageService.get(tokenKey); };
	 * $httpProvider.interceptors.push('jwtInterceptor'); });
	 */
}

function getFromSource(url) {
	var getSourceAjax = "";
	return {
		'url' : url,
		'type' : 'GET',
		'beforeSend' : function(request) {
			if (ZBlocalStorageService.get(tokenKey)) {
				request.setRequestHeader(tokenRequestHeaderKey,
						ZBlocalStorageService.get(tokenKey));
			}
		},
		"error" : handleAjaxError
	};
}

function handleAjaxError(xhr, textStatus, error) {
	if (xhr.status == 403) {
		alert(i18n("common.noAuth"));
	} else if (xhr.status == 500) {
		alert(i18n("common.error"));
	} else if (xhr.status == 401) {
		timeOutCount ++;
		if(timeOutCount == 1){
			alert(i18n("common.pleaseLogin"));
		}
		window.location = "/";
	}
}
//密码提示
function getCode(code){
	if(code=="PASSWORD_SAME"){
		return i18n("common.password.same");
	}
	if(code=="PASSWORD_NOT_SAME"){
		return  i18n("common.password.notsame");
	}
	if(code=="PASSWORD_NOT_NULL"){
		return  i18n("common.password.notnull");
	}
	if(code=="PASSWORD_NOT_USER"){
		return  i18n("common.no.correspondinguser");
	}
	if(code=="PASSWORD_OLD_ERROR"){
		return i18n("common.oldpassword.error");
	}
	if(code=="PASSWORD_USER_ENABLE"){
		return  i18n("common.user.notenabled");
	}
	if(code=="PASSWORD_LOGIN_FORMAT_ERROR"){
		return i18n("common.login.format");
	}
	if(code=="OK"){
		return  i18n("common.update.passwordsuccess");
	}
}

function HttpInterceptor($rootScope, $q, localStorageService) {
	ZBlocalStorageService = localStorageService;
	return {
		request : function(config) {

			// Header - Token
			config.headers = config.headers || {};
			if (localStorageService.get(tokenKey)) {
				// config.headers.tokenRequestHeaderKey = localStorageService
				// .get(tokenKey);
				config.headers[tokenRequestHeaderKey] = localStorageService
						.get(tokenKey);
			}

			return config;
		},

		response : function(response) {
			return response || $q.when(response);
		},

		responseError : function(response) {

			return $q.reject(response);
		}
	};
}

// add by xieqiang 2016-06-08 增加菜单选中逻辑 start

function doSelectMenu(ltbeans, pathname) {
	if (pathname == null || pathname == "" || pathname == undefined
			|| pathname.length < 5) {
		return;
	}
	pathname = pathname.substring(1, pathname.indexOf(".html") + 5);

	doSelectMenuActive(ltbeans, pathname);
	return ltbeans;
}
function doSelectMenuActive(ltbeans, pathname) {
	var returnBoolean = false;
	angular.forEach(ltbeans, function(data, index, array) {
		data.activeClass = "";
		if (data.menuProces != null && data.menuProces != undefined
				&& data.menuProces.length > 5 && pathname != null
				&& pathname != undefined) {
			if (pathname.indexOf(data.menuProces) >= 0) {
				data.activeClass = "active";
				returnBoolean = true;
				return;
			}
		}

		if (data.nodes != null && data.nodes.length > 0) {
			if (doSelectMenuActive(data.nodes, pathname)) {
				data.activeClass = "active";
				returnBoolean = true;
				return;
			}
		}
	});
	return returnBoolean;
}

// add by xieqiang 2016-06-08 增加菜单选中逻辑 end

// js 获取url重的值
function orgPic(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

function URLencode(sStr) 
{
    return escape(sStr).replace(/\+/g, '%2B').replace(/\"/g,'%22').replace(/\'/g, '%27').replace(/\//g,'%2F');
}



// //////////////////////////////////top////left///////////////////////////////////////
/*
 * var topleftapp = angular.module('topleftapp', [ 'pascalprecht.translate',
 * 'ngSanitize', 'LocalStorageModule' ]);
 * 
 * configAppModule(topleftapp);
 * 
 * topleftapp.factory('topleftService', [ '$http', '$q', function($http, $q) {
 * return {
 * 
 * fetchAllUserMenus : function(userName, appId) { return $http.get( apiUrl +
 * "/menu/userMenus/" + userName + "/" + appId).then(function(response) { return
 * response.data; }, function(errResponse) { console.error('Error while fetching
 * users'); return $q.reject(errResponse); }); } }; } ]);
 * 
 * topleftapp.controller('topleftctrl', loginCtrl);
 * 
 * function loginCtrl($translate, $scope, $filter, topleftService,
 * localStorageService) { var vm = this; vm.userName =
 * localStorageService.get(ZBuserNameKey); vm.bean = {}; vm.beans = {}; if
 * (localStorageService.get(ZBuserMenusKey)) { vm.beans =
 * localStorageService.get(ZBuserMenusKey); } else {
 * topleftService.fetchAllUserMenus(vm.userName, ZBappId).then( onResultData,
 * function(errResponse) { // }); }
 * 
 * function onResultData(data) { vm.beans = data;
 * localStorageService.set(ZBuserMenusKey, data); } }
 */

function getValueForSelect(value, key) {
	if (value == null || value == undefined) {
		return "";
	} else {
		if (key != undefined && key != null) {
			return key + value;
		}
	}
	return value;
}



/**
* 实时动态强制更改用户录入
* arg1 inputObject
**/
function amount(th){
    var regStrs = [
        ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
        ['[^\\d\\,]+$', ''], //禁止录入任何非数字和点
        ['\\,,(\\d?)\\,+', ',$1'], //禁止录入两个以上的点
        ['^(\\d+\\,\\d{2}).+', '$1'] //禁止录入小数点后两位以上
    ];
    for(var i=0; i<regStrs.length; i++){
        var reg = new RegExp(regStrs[i][0]);
        th.value = th.value.replace(reg, regStrs[i][1]);
    }
}
 
/**
* 录入完成后，输入模式失去焦点后对录入进行判断并强制更改，并对小数点进行0补全
* arg1 inputObject
* 这个函数写得很傻，是我很早以前写的了，没有进行优化，但功能十分齐全，你尝试着使用
* 其实有一种可以更快速的JavaScript内置函数可以提取杂乱数据中的数字：
* parseFloat('10');
**/
function overFormat(th){
    var v = th.value;
    if(v === ''){
        v = '0,00';
    }else if(v === '0'){
        v = '0,00';
    }else if(v === '0,'){
        v = '0,00';
    }
    var amount=v.replace(/\./g,"").replace(/,/g,".");
    th.value = outputmoney(amount); 
}


function outputmoney(number) {
	if(number==0) return "0,00";	
    if (isNaN(number) || number == "") return "";  
    number = Math.round(number * 100) / 100;  
    if (number < 0)  
        return '-' + outputdollars(Math.floor(Math.abs(number) - 0) + '') + outputcents(Math.abs(number) - 0);  
    else  
        return outputdollars(Math.floor(number - 0) + '') + outputcents(number - 0);  
}  
//格式化金额   
function outputdollars(number) {
    if (number.length <= 3)  
        return (number == '' ? '0' : number);  
    else {  
        var mod = number.length % 3;  
        var output = (mod == 0 ? '' : (number.substring(0, mod)));  
        for (var i = 0; i < Math.floor(number.length / 3); i++) {  
            if ((mod == 0) && (i == 0))
            {
                output += number.substring(mod + 3 * i, mod + 3 * i + 3);
            }
            else
            {
                output += '.' + number.substring(mod + 3 * i, mod + 3 * i + 3);  
            }
        }  
        return (output);  
    }  
}  



function outputcents(amount) {  
    amount = Math.round(((amount) - Math.floor(amount)) * 100);  
    return (amount < 10 ? ',0' + amount : ',' + amount); 
}



//字符串转成字节数组
function stringToBytes ( str ) {  
	  var ch, st, re = [];  
	  for (var i = 0; i < str.length; i++ ) {  
	    ch = str.charCodeAt(i);   
	    st = [];                   
	    do {  
	      st.push( ch & 0xFF );  
	      ch = ch >> 8;           
	    }    
	    while ( ch );  
	    re = re.concat( st.reverse() );  
	  }  
	  return re;  
	} 

function postFromSource(url) {
	var getSourceAjax = "";
	return {
		'url' : url,
		'type' : 'POST',
		'beforeSend' : function(request) {
			if (ZBlocalStorageService.get(tokenKey)) {
				request.setRequestHeader(tokenRequestHeaderKey,
						ZBlocalStorageService.get(tokenKey));
			}
		},
		"error" : handleAjaxError
	};
}

//DES 解密 加密
function encryptByDES(message, key) {
      var keyHex = CryptoJS.enc.Utf8.parse(key);
      var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
          mode: CryptoJS.mode.ECB,
          padding: CryptoJS.pad.Pkcs7
      });
      return encrypted.toString();
  }
  //DES 解密

function decryptByDES(ciphertext, key) {
	try{
		if(key == null){
			return "";
		}
	    var keyHex = CryptoJS.enc.Utf8.parse(key);
	    // direct decrypt ciphertext
	    var decrypted = CryptoJS.DES.decrypt({
	        ciphertext: CryptoJS.enc.Base64.parse(ciphertext)
	    }, keyHex, {
	        mode: CryptoJS.mode.ECB,
	        padding: CryptoJS.pad.Pkcs7
	    });
	    return decrypted.toString(CryptoJS.enc.Utf8);
	}catch(e){
		return "";
	}
}
function randomWord(randomFlag, min, max){
  var str = "",
      range = min,
      arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

  // 随机产生
  if(randomFlag){
      range = Math.round(Math.random() * (max-min)) + min;
  }
  for(var i=0; i<range; i++){
      var pos = Math.round(Math.random() * (arr.length-1));
      str += arr[pos];
  }
  return str;
}

var htmlLocationUrl = String(window.document.location.href);
function getHtmlRequestParamStr(str) {
	var rs = new RegExp("(^|)" + str + "=([^&]*)(&|$)", "gi").exec(htmlLocationUrl), tmp;
	if (tmp = rs) {
	return tmp[2];
	}
	// parameter cannot be found
	return "";
}

function removeLocalStorage(localStorageService){
	localStorageService.clearAll(platform + "*");
}