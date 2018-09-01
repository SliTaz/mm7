'use strict';
var App = angular.module('userServiceSendModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize',
		'LocalStorageModule', 'ui.tree' ]);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

$(window).on('load', function() {
	$('#userIds').selectpicker({
		'selectedText' : 'cat'
	});
});

$(function() {
	$("#reportTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#reportTimeStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#reportTimeEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, UserServiceSendService, localStorageService,
		topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.beanSer = {};
	// 列的状态start
	vm.columnStatusData = [];
	// 列的状态end
	var pwdFirst;
	vm.reloadData = reloadData;
	vm.selectData = [];
	vm.appData = [];
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/userServiceSend')).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('userServiceSendId').withTitle($translate('userServiceSend.userServiceSendId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('companyName').withTitle($translate('cpInfo.spInfo')).notSortable(),
			DTColumnBuilder.newColumn('productInfoId').withTitle($translate('userServiceSend.productInfoId')).notSortable(),
			DTColumnBuilder.newColumn('requestId').withTitle($translate('userServiceSend.requestId')).notSortable(),
			DTColumnBuilder.newColumn('contentInfoId').withTitle($translate('userServiceSend.contentInfoId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('chargePhoneNumber').withTitle($translate('userServiceSend.chargePhoneNumber')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('phoneNumber').withTitle($translate('userRecv.phoneNumber')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('sendTime').withTitle($translate('userServiceSend.sendTime')).notSortable().renderWith(timeRender).notVisible(),
			DTColumnBuilder.newColumn('status').withTitle($translate('userServiceSend.status')).notSortable().renderWith(statusDetail),
			DTColumnBuilder.newColumn('serviceType').withTitle($translate('userServiceSend.serviceType')).notSortable().renderWith(serviceTypeDetail),
			DTColumnBuilder.newColumn('reportTime').withTitle($translate('userServiceSend.reportTime')).notSortable().renderWith(timeRender),
			DTColumnBuilder.newColumn('sendServiceType').withTitle($translate('userServiceSend.sendServiceType')).notSortable().renderWith(sendServiceTypeDetail),
			DTColumnBuilder.newColumn('sendServiceRelId').withTitle($translate('userServiceSend.sendServiceRelId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('errorInfo').withTitle($translate('userServiceSend.errorInfo')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('sendType').withTitle($translate('userServiceSend.sendType')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('mmsPath').withTitle($translate('userServiceSend.mmsPath')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('batchId').withTitle($translate('userServiceSend.batchId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width', '15%').notSortable().renderWith(actionsHtml) ];
	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.selData = selData;
	selData();
	// 表头start
	tableHandle();
	// 表头end
	vm.VAR_MAX_FILE_SIZE_M=1;//1M
	initltCommon(vm, localStorageService, topleftService);
	$("#loadDiv").hide();
	function statusDetail(data, type, full, meta) {
		if (data == '1') {
			return '<span translate="userServiceSend.issued"></span>';
		} else if (data == '2') {
			return '<span translate="userServiceSend.successSubmit"></span>';
		} else if (data == '3') {
			return '<span translate="userServiceSend.unknownStatus"></span>';
		} else if (data == '4') {
			return '<span translate="userServiceSend.AuthenticationFailure"></span>';
		} else if (data == '5') {
			return '<span translate="userServiceSend.successRecv"></span>';
		} else if (data == '6') {
			return '<span translate="userServiceSend.successSubmitPay"></span>';
		} else if (data == '7') {
			return '<span translate="userServiceSend.issueException"></span>';
		} else if (data == '8') {
			return '<span translate="userServiceSend.isagQuenu"></span>';
		} else {
			return '';
		}
	}
	function serviceTypeDetail(data, type, full, meta) {
		if (data == '1') {
			return '<span translate="userServiceSend.MMS"></span>';
		} else if (data == '2') {
			return '<span translate="userServiceSend.SMS"></span>';
		} else if (data == '3') {
			return '<span translate="userServiceSend.wapPush"></span>';
		} else {
			return '';
		}
	}
	function sendServiceTypeDetail(data, type, full, meta) {
		if (data == '0') {
			return '<span translate="userServiceSend.onDemand"></span>';
		} else if (data == '1') {
			return '<span translate="userServiceSend.order"></span>';
		} else if (data == '2') {
			return '<span translate="userServiceSend.free"></span>';
		} else if (data == '3') {
			return '<span translate="userServiceSend.bulkHair"></span>';
		} else if (data == '4') {
			return '<span translate="userServiceSend.cancelOrder"></span>';
		} else {
			return '';
		}
	}
	function timeRender(data, type, full, meta) {
		if(data==null){
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
	
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.userServiceSendId] = data;
		var actionsHtml_html = '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'
				+ $translate.instant('common.edit')
				+ '" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.userServiceSendId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'
				+ $translate.instant('common.delete')
				+ '" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.userServiceSendId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>' + '</button>&nbsp;'

		$(".dataTables_scrollBody").attr("style",
				"position:relative;max-height:100%;width:100%");
		changeDataForHTML();// 通过调用该函数。让拼装的html代码生效
		return actionsHtml_html;

	}
	function changeDataForHTML() {
		// alert("changeDataForHTML");
		$scope.$applyAsync();// 当在angular的上下文之外修改了界面绑定的数据时，需要调用该函数，让页面的值进行改变。
	}
	
	function selData(){
		UserServiceSendService.selSpInfo().then(function(d) {
	        vm.spInfoData = d.body;
       },
		function(errResponse){
			console.error('Error while fetching spInfo');
		}
		);
		UserServiceSendService.selUserInfo().then(function(d) {
	        vm.userInfoData = d.body;
       },
		function(errResponse){
			console.error('Error while fetching spInfo');
		}
		);
	}
	
	//下拉框中输入框
	function selectDevice(){
		//解决 select2在模态框使用，模糊输入框无效
		$("#phoneNumber").val('').select2();
		$("#myModal").attr("tabindex","");
		//解决selec2在火狐模太框中输入框不能输入start
		$.fn.modal.Constructor.prototype.enforceFocus = function () { 
		};
		//解决selec2在火狐模太框中输入框不能输入end
	}
	
	$("#queryBtn").click(function(){
		selectDevice();
	})  
	    
	function addInit() {
		selectDevice();
		vm.modelTitle = $translate.instant('userServiceSend.add');
		$("#spInfo2").val('').select2();
		$("#phoneNumber2").val('').select2();
		vm.readonlyID = false;
		vm.disabled = false;
		vm.bean = {};
		vm.statusCode = "";
		vm.statusMessage = "";
	}
	function edit(bean) {
		selectDevice();
		reloadData();
		vm.readonlyID = true;
		vm.modelTitle = $translate.instant('userServiceSend.edit');
		$("#spInfo2").val(bean.spInfoId).select2();
		$("#phoneNumber2").val(bean.phoneNumber).select2();
		vm.bean = bean;
		vm.bean.serviceType = vm.bean.serviceType+"";
		vm.bean.status = vm.bean.status+"";
		vm.bean.sendServiceType = vm.bean.sendServiceType +"";
		vm.bean.phoneNumber = vm.bean.phoneNumber + "";
		vm.statusCode = "";
		vm.statusMessage = "";
		vm.bean.reportTime = timeFormatOld(vm.bean.reportTime);
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			vm.bean.reportTime = timeFormatNew(vm.bean.reportTime);
			UserServiceSendService.createUserServiceSend(vm.bean).then(
					onSubmitSuccess, function(errResponse) {
						handleAjaxError(errResponse);
						console.error('Error while creating GovUser.');
					});
			vm.reset();
		} else {
			vm.bean.reportTime = timeFormatNew(vm.bean.reportTime);
			UserServiceSendService.updateUserServiceSend(vm.bean,vm.bean.userServiceSendId).then(onSubmitSuccess,
					function(errResponse) {
						handleAjaxError(errResponse);
						console.error('Error while updating GovUser.');
					});
		}
	}
	function onSubmitSuccess(data) {
		vm.statusCode = data.statusCode;
		vm.statusMessage = data.statusMessage;
		reloadData();
	}

	
	function deleteBean(bean) {
		BootstrapDialog
				.show({
					title : $translate.instant('common.delete'),
					message : $translate.instant('common.delete.message'),
					buttons : [
							{
								label : $translate.instant('common.yes'),
								cssClass : 'btn btn-danger model-tool-right',
								action : function(dialogItself) {
									UserServiceSendService.deleteUserServiceSend(bean.userServiceSendId).then(reloadData,
													function(errResponse) {
														handleAjaxError(errResponse);
														console.error('Error while updating GovUser.');
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
	// 超长备注处理start
	$("#example")
			.on(
					"click",
					".spanFun",
					function() {
						var remarkDetail = $(this).text();
						BootstrapDialog
								.show({
									title : $translate.instant('user.remark'),
									message : function(dialog) {
										var $message = $('<span id="content_detail" style="word-break:normal; width:auto; display:block; white-space:pre-wrap;word-wrap:break-word ;overflow: hidden"'
												+ '</span>');
										return $message;
									},
									onshown : function(dialogRef) {// 打开完成
										$("#content_detail").text(remarkDetail);
									},
									buttons : [ {
										label : $translate
												.instant('common.yes'),
										cssClass : 'btn-primary',
										action : function(dialogItself) {
											dialogItself.close();
										}
									} ]
								});
					});
	// 超长备注处理end
	// 解决查询后保持列的显示start
	$('#table_id').on('init.dt', function(e, settings, column, state) {
		var flag = $(".dataTables_scrollBody").css("overflow");
    	if(flag='visible'){
		}
    	if(flag='auto'){
			$(".dataTables_scroll").attr("style","overflow:auto");
			$(".dataTables_scrollHead").css("overflow", "");
			$(".dataTables_scrollBody").css("overflow", "");
			$(".dataTables_scrollBody").attr("style","border:0px");
			$("#table_id").attr("style","border-bottom:1px solid black");
		}
		vm.columnStatusData = settings.aoColumns;
	});
	// 解决查询后保持列的显示end
	// start
	$('#table_id').on('draw.dt', function() {
		setTimeout(function() {
			$("#loadDiv").hide();
			$("#bth-searchDate").attr("disabled", false);
		}, 500);
	});

	$("#bth-searchDate").click(function() {
		$("#loadDiv").show();

	})
	// end

	function reloadData() {
		$("#bth-searchDate").attr("disabled", true);
		// 解决查询后保持列的显示start
		var columuFinalStatus = vm.columnStatusData;
		if (columuFinalStatus.length > 0) {
			for (var i = 0; i < columuFinalStatus.length; i++) {
				vm.dtColumns[i].bVisible = columuFinalStatus[i].bVisible;
			}
		}
		// 解决查询后保持列的显示end
		var userServiceSendId = vm.beanSer.userServiceSendId;
		var phoneNumber = vm.beanSer.phoneNumber;
		if(vm.beanSer.reportTimeStart >= vm.beanSer.reportTimeEnd){
			alert($translate.instant('userServiceSend.reportTimeRange'));
			return;
		}
		var reportTimeStart = timeFormatNew(vm.beanSer.reportTimeStart);
		var reportTimeEnd = timeFormatNew(vm.beanSer.reportTimeEnd);
		var spInfoId = vm.beanSer.spInfoId;
		var productInfoId = vm.beanSer.productInfoId;
		var status = vm.beanSer.status;
		var serviceType = vm.beanSer.serviceType;
		var sendServiceType = vm.beanSer.sendServiceType;
		vm.dtInstance.changeData(getFromSource(apiUrl
				+ '/userServiceSend?userServiceSendId=' + getValueForSelect(userServiceSendId) + "&phoneNumber=" + getValueForSelect(phoneNumber) + "&reportTimeStart=" + getValueForSelect(reportTimeStart)
				+ "&reportTimeEnd=" + getValueForSelect(reportTimeEnd) + "&spInfoId=" + getValueForSelect(spInfoId) + "&productInfoId=" + getValueForSelect(productInfoId)
				+ "&status=" + getValueForSelect(status) + "&serviceType=" + getValueForSelect(serviceType) + "&sendServiceType=" + getValueForSelect(sendServiceType)));
		var resetPaging = false;
		vm.dtInstance.reloadData(callback, resetPaging);
	}
	function callback(json) {
		// console.log(json);
	}
	function createdRow(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}
	vm.reset = function() {
		addInit();
	};
}