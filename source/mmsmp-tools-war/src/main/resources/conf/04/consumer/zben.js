'use strict';

var apiUrl = "http://"+"www.zbensoft.com"+":8779/api";
var ZBappId = 1;
var tokenRequestHeaderKey = "Authorization";
var platform = "consumer" + "_";
var tokenKey = platform + "jwttoken";
var tokenRandomKey = platform + "randomKey";
var ZBuserNameKey = platform + "ZBuserNameConsumer";
var ZBuserIdKey = platform + "ZBuserId";
var ZBidNumber = platform + "idNumber";
var ZBuserMenusKey = platform + "ZBuserMenus";
var ZBDetailTradeSeq = platform + "ZBDetailTradeSeq";
var localStoragTokenKey = platform + "epaymenttoken";
var tradeInfoSubmitListKey = platform + "tradeOderNo";
var ZBisDefaultPassword = platform + "isDefaultPassword";
var ZBisDefaultPayPassword = platform + "isDefaultPayPassword";
var ZBemailBindStatus = platform + "emailBindStatus";
var ZBshownNotRemind = platform + "shownNotRemind";
var ZBlocalStorageService = {};
// alert(navigator.language);
var naLan = navigator.language;
if (naLan == undefined || naLan == "") {
	naLan = navigator.browserLanguage;
}

var emailBindStatus ={
		NOT_BIND:0,
		BIND:1,
		NOT_REMAIND:2,
}

var dtServerSide = false;

//超时提示次数
var timeOutCount = 0;

var helpApiUrl = "http://localhost:82";

//处理国际化
var file="";
var i18nData={};

if(naLan=='en'){
	file="./il8n/en.json";
}else if(naLan.indexOf("en-") >= 0){
	naLan = "en";
	file="./il8n/en.json";
}else if(naLan=='es'){
	file="./il8n/es.json";
}else if(naLan.indexOf("es-") >= 0){
	naLan = "es";
	file="./il8n/es.json";
}else if(naLan=='zh'){
	file="./il8n/zh.json";
}else if(naLan.indexOf("zh") >= 0){
	naLan = "zh";
	file="./il8n/zh.json";
}else{
	naLan = "es";
	file="./il8n/es.json";
}

$.ajaxSettings.async = false;
$.getJSON(file,function(data){i18nData=data}); 

//title的国际化
$('title').text(i18nData["common.title"]);



$(window).resize(function() { 
	$("#leftSide").height($("#rightSide").height());
    if($(window).width()<768){
    	$("#logimgId").css("margin-bottom","-15px");
        
    } 
    if($(window).width()>=768){
    	$("#logimgId").css("margin-bottom","");
	
    } 
      
 });	


window.onload=function(){
	$("#leftSide").height($("#rightSide").height());
    if($(window).width()<768){
    	$("#logimgId").css("margin-bottom","-15px");
        
    } 
    if($(window).width()>=768){
    	$("#logimgId").css("margin-bottom","");
	
    } 
	
}


//适配局域网访问
var address=window.location.hostname;
var ipAddrStrs=address.split('.');
if(ipAddrStrs.length==4){
	if(ipAddrStrs[3]=="1"||ipAddrStrs[3]=="2"||ipAddrStrs[3]=="3"||ipAddrStrs[3]=="7"){
		apiUrl = "http://"+address+"/api";
	}
}


function i18n(key)
{	
   return i18nData[key];
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
				text : 'columnVisble',
				className : 'btn btn-info'
			}, {
				extend : 'copy',
				text : 'copy',
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
function initltCommon(vm, localStorageService, commonService) {
	vm.merchantUserName = localStorageService.get(ZBuserNameKey);
	
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
					'commonService',
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
																+ "/"
																+ appId
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
									},exit:function(){
										return $http.patch(apiUrl + "/logout")
										.then(
												function(response) {
													ZBlocalStorageService.remove(ZBuserNameKey);
													window.location="home.html";
												
												},
												function(errResponse) {
													
												});
									},refresh:function(){
										return $http.get(apiUrl + "/refresh")
										.then(
												function(response) {
													return response.data;
												},
												function(errResponse) {
													
												});
									}

								};

							} ]);
}

function getFromSource(url) {
	var getSourceAjax = "";
	return {
		'url' : url,
		'type' : 'GET',
		'beforeSend' : function(request) {
			if (ZBlocalStorageService.get(tokenKey)) {
				var tokenRandomValue = ZBlocalStorageService.get(tokenRandomKey);
				request.setRequestHeader(tokenRequestHeaderKey,decryptByDES(ZBlocalStorageService.get(tokenKey),tokenRandomValue));
			}
		}
	};
}

function handleAjaxError(xhr, textStatus, error) {
	
	if (xhr.status == 403) {		
		window.location="403.html"
	} else if (xhr.status == 500) {
		window.location="server_err.html"
	}else if (xhr.status == 404) {
		window.location="404.html"
	}else if (xhr.status == 401) {
		//window.location = "home.html";
		$.ajax({
			url: apiUrl+'/refresh',
			type: 'GET',
            dataType: 'json',
            cache : false,  
			async : false,
			beforeSend : function(request) {
				if (ZBlocalStorageService.get(tokenKey)) {
					var tokenRandomValue = ZBlocalStorageService.get(tokenRandomKey);
					request.setRequestHeader(tokenRequestHeaderKey,decryptByDES(ZBlocalStorageService.get(tokenKey),tokenRandomValue));
				}
			},
            success: function(data) {
            	if(data.statusCode!="OK")
            	{
            		timeOutCount ++;
            		if(timeOutCount == 1){
            		alert(i18n("login.userName.not.logintolong"));
            		}
            		ZBlocalStorageService.remove(ZBuserNameKey);
            		window.location="home.html";
            	}
            },
            error:function(err){
        		timeOutCount ++;
        		if(timeOutCount == 1){
        		alert(i18n("login.userName.not.logintolong"));
        		}
            	ZBlocalStorageService.remove(ZBuserNameKey);
            	window.location="home.html";
            }
		});
		
	}else if(xhr.status==-1)
	{
		window.location="server_err.html"
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
				var tokenRandomValue = localStorageService.get(tokenRandomKey);
				config.headers[tokenRequestHeaderKey] = decryptByDES(localStorageService.get(tokenKey),tokenRandomValue);
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

function URLencode(sStr) 
{
    return escape(sStr).replace(/\+/g, '%2B').replace(/\"/g,'%22').replace(/\'/g, '%27').replace(/\//g,'%2F');
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
//时间时分秒格式yyyy-MM-dd 
function dayFormatNew(data) {
	
	if(data==null){
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
function dayFormatOld(data) {
	
	if(data==null){
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
function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
 
    if (len) {
      // Compact form
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
      // rfc4122, version 4 form
      var r;
 
      // rfc4122 requires these characters
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      uuid[14] = '4';
 
      // Fill in random data.  At i==19 set the high bits of clock sequence as
      // per rfc4122, sec. 4.1.5
      for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random()*16;
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
        }
      }
    }
 
    return uuid.join('');
}

var errCodeJson={};
$.ajaxSettings.async = false;
$.getJSON("errorCode.json",function(data){errCodeJson=data}); 

function getErrMessage(statusCode)
{
	var statusi18n=errCodeJson[statusCode];
	return i18n(statusi18n);
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

function getSearchValue(name){
	var searchValue="";
    var str=window.location.search;   //location.search是从当前URL的?号开始的字符串 例如：http://www.51job.com/viewthread.jsp?tid=22720 它的search就是?tid=22720
    var searchStr=str.substring(1);
    var paramsTmp= searchStr.split("&");
    for(var i=0;i<paramsTmp.length;i++)
    {
    	var paramObject=new Object();
    	var parmTmp=paramsTmp[i].split("=");
    	paramObject.key=parmTmp[0];
    	paramObject.value=parmTmp[1];
    	
    	if( paramObject.key == name )
    	{
    		searchValue = paramObject.value;
    		break;
    	}
    }
    
    return searchValue;
}

function resizeimg(obj,maxW,maxH)
{
         var imgW=obj.width;
         var imgH=obj.height;
         if(imgW>maxW||imgH>maxH)
         {       
                  var ratioA=imgW/maxW;
                  var ratioB=imgH/maxH;               
                  if(ratioA>ratioB)
                  {
                           imgW=maxW;
                           imgH=maxW*(imgH/imgW);
                  }
                  else
                  {
                           imgH=maxH;
                           imgW=maxH*(imgW/imgH);
                  }  
                  obj.width=imgW;
                  obj.height=imgH;
         }
}

function isEmpty(data){
	if( data == '' || data == null || data.length == 0 )
	{
		return true;
	}
	return false;
}
//help链接
function help(){
	window.location.href = helpApiUrl+"/leftMenu.html";
}
function showFirstLoginWindows(localStorageService){
	 var isDefaultPassword = localStorageService.get(ZBisDefaultPassword);
	 var isDefaultPayPassword = localStorageService.get(ZBisDefaultPayPassword);
	 var emailBindStatus = localStorageService.get(ZBemailBindStatus);
	 if(isDefaultPassword==1)
     {
		 window.location="loginPasswdReset.html";
		 return true;
     }
	 if(isDefaultPayPassword==1)
     {
		 window.location="payPasswdReset.html";
		 return true;
     }
	 if( emailBindStatus != 1 )
	 {
		 window.location="email_bind.html";
		 return true;
	 }
	 
	 return false;
}

function goExit(commonService,localStorageService)
{
	localStorageService.remove(ZBuserNameKey);
	commonService.exit();
}
function goMy(localStorageService)
{	
	if(!showFirstLoginWindows(localStorageService)){
		window.location="accountSetting.html";
	}
}
function goIndex(localStorageService)
{
	if(!showFirstLoginWindows(localStorageService)){
		window.location="index.html";
	}
}
function goMyBill(localStorageService)
{
	if(!showFirstLoginWindows(localStorageService)){
		window.location="myBill.html";
	}
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

function checkExploreVersion()
{
	var DEFAULT_VERSION = 9.0;
	var ua = navigator.userAgent.toLowerCase();
	var isIE = ua.indexOf("msie")>-1;
	var safariVersion;
	if(isIE){
	    safariVersion =  parseFloat(ua.match(/msie ([\d.]+)/)[1]);
	    if(safariVersion < DEFAULT_VERSION ){
	    	 alert(i18nData['explore.version.lower']);
	    	 return false;
	    }else{
	       return true;
	    }
	}else{
	    return true;
	}
}
checkExploreVersion();