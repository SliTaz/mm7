'use strict';
var App = angular.module('userInfoModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

$(function() {
	$("#birthday").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});


function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, UserInfoService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
    vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/userInfo')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		DTColumnBuilder.newColumn('phoneNumber').withTitle($translate('userInfo.phoneNumber')).notSortable(),
		DTColumnBuilder.newColumn('chargePhoneNumber').withTitle($translate('userInfo.chargePhoneNumber')).notSortable(),
		DTColumnBuilder.newColumn('terminalType').withTitle($translate('userInfo.terminalType')).notSortable().renderWith(terminalType),
		DTColumnBuilder.newColumn('province').withTitle($translate('userInfo.province')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('city').withTitle($translate('userInfo.city')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('createTime').withTitle($translate('user.createTime')).renderWith(timeRender).notSortable(),
		DTColumnBuilder.newColumn('status').withTitle($translate('user.status')).renderWith(statusType).notSortable().notVisible(),
		//DTColumnBuilder.newColumn('userPassword').withTitle($translate('userInfo.userPassword')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('userName').withTitle($translate('userInfo.userName')).notSortable(),
		DTColumnBuilder.newColumn('nickName').withTitle($translate('userInfo.nickName')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('userSex').withTitle($translate('userInfo.userSex')).notSortable().notVisible().renderWith(userSex),
		DTColumnBuilder.newColumn('birthday').withTitle($translate('userInfo.birthday')).notSortable().notVisible().renderWith(timeRender),
		DTColumnBuilder.newColumn('profession').withTitle($translate('userInfo.profession')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('userEmail').withTitle($translate('userInfo.userEmail')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('userAddr').withTitle($translate('userInfo.userAddr')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('hobby').withTitle($translate('userInfo.hobby')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('postalCode').withTitle($translate('userInfo.postalCode')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('country').withTitle($translate('userInfo.country')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('nationality').withTitle($translate('userInfo.nationality')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('marriage').withTitle($translate('userInfo.marriage')).notSortable().notVisible().renderWith(marriage),
		DTColumnBuilder.newColumn('certificateType').withTitle($translate('userInfo.certificateType')).notSortable().notVisible().renderWith(certificateType),
		DTColumnBuilder.newColumn('certificateNumber').withTitle($translate('userInfo.certificateNumber')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('brand').withTitle($translate('userInfo.brand')).notSortable().notVisible(),
		DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
				.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.statusType = statusType;
	vm.terminalType = terminalType;
	vm.marriage = marriage;
	vm.certificateType = certificateType;
	vm.userSex = userSex;
	vm.passwords = passwords;
	vm.getPwd=getPwd;
	//表头start
	tableHandle();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
	$("#loadDiv").hide();
	
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.phoneNumber] = data;
		var actionsHtml_html='<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.phoneNumber
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.phoneNumber
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>&nbsp;'
		        + '<div class="btn-group">'
		        + '<button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown">'
		        + ' <span class="caret"></span>'
                +'</button>'
                + '<ul class="dropdown-menu" style="padding:0px;min-width:10px">'
		        + '<li><button class="btn bg-maroon" style="width:120px"  ng-click="ctrl.passwords(ctrl.beans[\''+data.phoneNumber+'\'])">'+$translate.instant('common.passwords')+'</button></li>'
                + '</ul>'
                + '</div>'
          $(".dataTables_scrollBody").attr("style","position:relative;max-height:100%;width:100%");
		
		changeDataForHTML();//通过调用该函数。让拼装的html代码生效
		return actionsHtml_html;
	} 
	
	function changeDataForHTML(){
		//alert("changeDataForHTML");
		$scope.$applyAsync();//当在angular的上下文之外修改了界面绑定的数据时，需要调用该函数，让页面的值进行改变。
	}
	//初始登陆密码
	function passwords(bean) {

		 bean.userPassword =getPwd();
		BootstrapDialog.show({
			title : $translate.instant('common.passwords'),
			message : $translate.instant('common.passwords.message')+bean.userPassword,
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					UserInfoService.resetDefaultPassword(bean,bean.phoneNumber).then(reloadData,
					function(errResponse) {
	 						handleAjaxError(errResponse);
						console.error('Error while activating ConsumerUser.');
					});
					dialogItself.close();
			
				}
		

			}, {
				label :  $translate.instant('common.cancel'),
				cssClass : 'btn btn-default model-tool-left',
				action : function(dialogItself) {
					dialogItself.close();
				}
			} ]
		});
	}
	
	//登陆密码
	function getPwd(){
		var pwdFirst='';
		  var arr = [];
	    for (var i=0;i<1000;i++) {
	        var n = Math.random().toString(36).substr(2,6);
	        var reg = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
	        if (!reg.test(n))
	        {  
	        	 
	         }else{  
	        	 $("#userPassword").val(n);
	           
	             vm.bean.userPassword =n;
	          	break;
	         }
	        arr.push(n);
	    } 
	    //vm.bean.password =n;
	    	pwdFirst=n;
	    	return pwdFirst;
	    
	  
	}
	
	function timeRender(data, type, full, meta) {
		if(data==null||data==''){
			return '';
		}else{
			var naLan = navigator.language;
			if (naLan == undefined || naLan == "") {
				naLan = navigator.browserLanguage;
			}	
			if(naLan=='zh'){
			return  moment(data).format("YYYY-MM-DD HH:mm:ss"); 
			}else if(naLan.indexOf("zh") >= 0){
				return  moment(data).format("YYYY-MM-DD HH:mm:ss"); 
			}else{
				return  moment(data).format("DD/MM/YYYY HH:mm:ss"); 
			}

		}
		
	}
	
	
	function statusType(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.normals');
		}else if(data=='2'){
			return $translate.instant('common.downtime');
		}else if(data=='3'){
			return $translate.instant('common.arrears');
		}else{
			return '';
		}
	}
	
	function marriage(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.married');
		}else if(data=='0'){
			return $translate.instant('common.unmarried');
		}else{
			return '';
		}
	}
	function userSex(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.male');
		}else if(data=='2'){
			return $translate.instant('common.female');
		}else{
			return '';
		}
	}
	function terminalType(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.mmsTerminal');
		}else if(data=='2'){
			return $translate.instant('common.unknownTerminal');
		}else if(data=='3'){
			return $translate.instant('common.nonmmsterminal');
		}else{
			return '';
		}
	}
	function certificateType(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.idCard');
		}else if(data=='2'){
			return $translate.instant('common.studentCard');
		}else if(data=='3'){
			return $translate.instant('common.oldCard');
		}else{
			return '';
		}
	}
	
	function addInit() {
		vm.modelTitle = $translate.instant('userInfo.add');
		vm.readonlyID = false;
		vm.disabled = true;
		vm.bean = {};
		vm.bean.phoneNumber=null;
		vm.bean.chargePhoneNumber=null;
		vm.bean.terminalType=1;
		vm.bean.province=null;
		vm.bean.city=null;
		vm.bean.status=1;
		vm.bean.userPassword=null;
		vm.bean.userName=null;
		vm.bean.nickName=null;
		vm.bean.userSex=null;
		vm.bean.birthday=null;
		vm.bean.profession=null;
		vm.bean.userEmail=null;
		vm.bean.userAddr=null;
		vm.bean.hobby=null;
		vm.bean.postalCode=null;
		vm.bean.country=null;
		vm.bean.nationality=null;
		vm.bean.marriage=0;
		vm.bean.certificateType=1;
		vm.bean.certificateNumber=null;
		vm.bean.brand=null;
		vm.statusData = [
			{value:1,text:$translate.instant('common.normals')},
			{value:2,text:$translate.instant('common.downtime')},
			{value:3,text:$translate.instant('common.arrears')}];
		vm.marriageData = [
			{value:1,text:$translate.instant('common.married')},
			{value:0,text:$translate.instant('common.unmarried')}];
		vm.userSexData = [
			{value:1,text:$translate.instant('common.male')},
			{value:2,text:$translate.instant('common.female')}];
		vm.certificateTypeData = [
			{value:1,text:$translate.instant('common.idCard')},
			{value:2,text:$translate.instant('common.studentCard')},
			{value:3,text:$translate.instant('common.oldCard')}];
		vm.terminalTypeData = [
			{value:1,text:$translate.instant('common.mmsTerminal')},
			{value:2,text:$translate.instant('common.unknownTerminal')},
			{value:3,text:$translate.instant('common.nonmmsterminal')}];
		vm.statusCode="";
		vm.statusMessage="";
		$("#pwd_div").show();
	}
	function edit(bean) {
		reloadData();
		vm.modelTitle = $translate.instant('userInfo.edit');
		vm.readonlyID = true;
		vm.disabled = false;
		vm.bean = bean;
		bean.userPassword="111111"
	   $("#pwd_div").hide();
		vm.statusData = [
			{value:1,text:$translate.instant('common.normals')},
			{value:2,text:$translate.instant('common.downtime')},
			{value:3,text:$translate.instant('common.arrears')}];
		vm.terminalTypeData = [
			{value:1,text:$translate.instant('common.mmsTerminal')},
			{value:2,text:$translate.instant('common.unknownTerminal')},
			{value:3,text:$translate.instant('common.nonmmsterminal')}];
		vm.userSexData = [
			{value:1,text:$translate.instant('common.male')},
			{value:2,text:$translate.instant('common.female')}];
		vm.marriageData = [
			{value:1,text:$translate.instant('common.married')},
			{value:0,text:$translate.instant('common.unmarried')}];
		vm.certificateTypeData = [
			{value:1,text:$translate.instant('common.idCard')},
			{value:2,text:$translate.instant('common.studentCard')},
			{value:3,text:$translate.instant('common.oldCard')}];
		vm.bean.birthday = timeFormatOld(vm.bean.birthday);
		vm.statusCode="";
		vm.statusMessage="";
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			vm.bean.birthday = timeFormatNew(vm.bean.birthday);
			UserInfoService.createAlarmInfo(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating AlarmLevel.');
					});
			vm.reset();
		} else {
			vm.bean.birthday = timeFormatNew(vm.bean.birthday);
			UserInfoService.updateAlarmInfo(vm.bean, vm.bean.phoneNumber).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmLevel.');
					});
		}
	}

	function onSubmitSuccess(data){
		vm.statusCode=data.statusCode;
		vm.statusMessage=data.statusMessage;
		reloadData();
	}

	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('common.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					UserInfoService.deleteAlarmInfo(bean.phoneNumber).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmInfo.');
					});
					dialogItself.close();
				}

			}, {
				label : $translate.instant('common.cancel'),
				cssClass : 'btn btn-default model-tool-left',
				action : function(dialogItself) {
					dialogItself.close();
				}
			} ]
		});
		
	}
	
	//超长备注处理start
	function remarkDetail(data, type, full, meta){
		if(data!=null){
			return '<span class="spanFun" style=" display: inline-block;width: 200px;white-space:nowrap;word-break:keep-all;overflow:hidden;text-overflow:ellipsis;">'+data+'</span>'
		}else{
			return '';
		}
	}
	$("#example").on("click", ".spanFun", function(){
		var remarkDetail = $(this).text();
		BootstrapDialog.show({
			title: $translate.instant('user.remark'),
			message: function(dialog) {
				var $message=$(
						'<span id="content_detail" style="word-break:normal; width:auto; display:block; white-space:pre-wrap;word-wrap:break-word ;overflow: hidden"'+
					    '</span>'
				);
                return $message;
            },
            onshown: function(dialogRef){//打开完成
            	$("#content_detail").text(remarkDetail);
            },
	        buttons: [
	        {
	            label: $translate.instant('common.yes'),
	            cssClass: 'btn-primary',
	            action: function(dialogItself){
	            	dialogItself.close();
	            }
	        }]
	    });
	});
	//超长备注处理end
	  //解决查询后保持列的显示start
	$('#table_id').on( 'init.dt', function ( e, settings, column, state ) {
		vm.columnStatusData = settings.aoColumns;
	} );
	//解决查询后保持列的显示end
	//start
    $('#table_id').on('draw.dt',function() {
    	setTimeout(function(){
    		$("#loadDiv").hide();
    		$("#bth-searchDate").attr("disabled",false);
    	},500);
    });
    
    $("#bth-searchDate").click(function(){
    	$("#loadDiv").show();
    	
    })
    //end
	
	function reloadData() {
    	$("#bth-searchDate").attr("disabled",true);
		 //解决查询后保持列的显示start
		var columuFinalStatus = vm.columnStatusData;
		if(columuFinalStatus.length>0){
		  for(var i = 0; i < columuFinalStatus.length; i++){
			  vm.dtColumns[i].bVisible = columuFinalStatus[i].bVisible;
			  }
		}
		//解决查询后保持列的显示end
		var phoneNumber = vm.beanSer.phoneNumber;
		var chargePhoneNumber = vm.beanSer.chargePhoneNumber;
		var terminalType = vm.beanSer.terminalType;
		var status = vm.beanSer.status;
		
		vm.dtInstance.changeData(getFromSource(apiUrl + '/userInfo?id=' + getValueForSelect(phoneNumber)+
				"&chargePhoneNumber="+getValueForSelect(chargePhoneNumber)+"&terminalType="+getValueForSelect(terminalType)+getValueForSelect(status,"&status=")));
		var resetPaging = false;
		vm.dtInstance.reloadData(callback, resetPaging);
	}
	function callback(json) {
	}
	function createdRow(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}
	vm.reset = function() {
		addInit();
	};
}