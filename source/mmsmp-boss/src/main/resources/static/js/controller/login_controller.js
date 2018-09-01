'use strict';
var login = angular.module('loginModule', [ 'pascalprecht.translate',
		'ngSanitize', 'LocalStorageModule' ]);

configloginAppModule(login);

login.controller('loginCtrl', loginCtrl);

function loginCtrl($translate, $scope, $filter, LoginService,
		localStorageService) {
	var vm = this;
	vm.loginmessage = "";
	vm.statusCode = "";
	vm.bean = {};
	vm.login = loginTo;
	vm.beanLogin = {};
	vm.beanLogin.usesrName = "";
	vm.beanLogin.password = "";
	
	//解决登录页面key国际化从无到有start
	$('#login_name').attr('placeholder',i18nData["user.userName"]);
	
	$('#login_password').attr('placeholder',i18nData["user.password"]);
	//解决登录页面key国际化从无到有end
	

	function loginTo() {
		removeLocalStorage(localStorageService);
		$("#loginBth").hide();
		$('p').attr("id","statusTipss")
		$("#statusTips").text('');
		$("#shclDefault").show();
		$('#shclDefault').shCircleLoader();
		
		//登录和密码校验start
		if((vm.bean.userName==""||vm.bean.userName==null) && (vm.bean.password==""||vm.bean.password==null))
		{
			alert($translate.instant('login.userName.password.notempty'));
			$("#shclDefault").hide();
			$("#loginBth").show();
			angular.element("#login_name").closest("div").addClass("has-error");

			$("#shclDefault").hide();
			$("#loginBth").show();
			angular.element("#login_password").closest("div").addClass("has-error");
			
			return;
		}else
		{
			angular.element("#login_name").closest("div").removeClass("has-error");
			angular.element("#login_password").closest("div").removeClass("has-error");
		}
		
		
		if(vm.bean.userName==""||vm.bean.userName==null)
		{
			alert($translate.instant('login.userName.notempty'));
			$("#shclDefault").hide();
			$("#loginBth").show();
			angular.element("#login_name").closest("div").addClass("has-error");
			return;
		}else
		{
			angular.element("#login_name").closest("div").removeClass("has-error");
		}
		
		if(vm.bean.password==""||vm.bean.password==null)
		{
			alert($translate.instant('login.password.notempty'));
			$("#shclDefault").hide();
			$("#loginBth").show();
			angular.element("#login_password").closest("div").addClass("has-error");
			return;
		}else
		{
			angular.element("#login_password").closest("div").removeClass("has-error");
		}
		
		if(vm.bean.password.length != 6)
		{
			alert($translate.instant('login.password.length.error'));
			$("#shclDefault").hide();
			$("#loginBth").show();
			angular.element("#login_password").closest("div").addClass("has-error");
			return;
		}else
		{
			angular.element("#login_password").closest("div").removeClass("has-error");
		}
		if(/^([a-zA-Z]{6}$)/.test(vm.bean.password))
		{
			alert($translate.instant('login.password.all.number.letter'));
			$("#shclDefault").hide();
			$("#loginBth").show();
			angular.element("#login_password").closest("div").addClass("has-error");
			return;
		}else
		{
			angular.element("#login_password").closest("div").removeClass("has-error");
		}
		
		if(!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6}$/.test(vm.bean.password)&&!/^([0-9]{6}$)/.test(vm.bean.password))
		{
			alert($translate.instant('login.password.special.characters'));
			$("#shclDefault").hide();
			$("#loginBth").show();
			angular.element("#login_password").closest("div").addClass("has-error");
			return;
		}else
		{
			angular.element("#login_password").closest("div").removeClass("has-error");
		}
		//登录和密码校验end
		
		vm.loginmessage = "";
		vm.beanLogin.userName = "90_" + vm.bean.userName;
		vm.beanLogin.password = vm.bean.password;
		LoginService.login(vm.beanLogin).then(onResultData,
				function(errResponse) {
 						handleAjaxError(errResponse);
					vm.loginmessage = i18nData["common.invalidAddress"];
				});
	}
	function onResultData(data) {
		$("#loginBth").hide();
		if(data.statusCode == "LOGIN_USER_NAME_PASSWORD_ERROR")
		{
			vm.bean.remainingTimes = data.body.remainingTimes;	
		}
		
		vm.statusCode = data.statusCode;
		vm.loginmessage = data.statusMessage;
		if (data.statusCode == "OK") {
			$('p').attr("id","statusTips");
			localStorageService.set(tokenKey, data.body.token);
			localStorageService.set(ZBuserNameKey, vm.bean.userName);
			localStorageService.set(ZBuserIdKey, data.body.userId);
			localStorageService.remove(ZBuserMenusKey);
		
			window.location = "main.html";
		}else{

			$("#shclDefault").hide();
			$("#loginBth").show();
			
			
		}
	}

}
/*
 * var login = angular.module('loginModule', []); login.config([
 * '$translateProvider', translateProvider ]); login.controller('loginCtrl',
 * function($scope, $http) { $http({ method: 'POST', url: 'localhost:8080/login'
 * }).then(function successCallback(response) { $scope.names =
 * response.data.sites; }, function errorCallback(response) { // 请求失败执行代码 });
 * 
 * });
 */