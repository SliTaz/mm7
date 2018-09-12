'use strict';
var App = angular.module('userOrderHisModule', [ 'datatables',
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
	$("#orderTimeStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#orderTimeEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, UserOrderHisService, localStorageService,
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
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/userOrderHis?orderType=2')).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('userOrderHisId').withTitle($translate('userOrderHis.userOrderHisId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('provinceCityName').withTitle($translate('userOrderHis.province')).notSortable(),
			DTColumnBuilder.newColumn('productName').withTitle($translate('userOnDemandHis.productName')).notSortable(),
			DTColumnBuilder.newColumn('productInfoId').withTitle($translate('userOnDemandHis.productInfoId')).notSortable(),
			DTColumnBuilder.newColumn('fee').withTitle($translate('userOrderHis.fee')).notSortable(),
			DTColumnBuilder.newColumn('spInfoId').withTitle($translate('userOnDemandHis.spInfo')).notSortable(),
			DTColumnBuilder.newColumn('phoneNumber').withTitle($translate('userRecv.phoneNumber')).notSortable(),
			DTColumnBuilder.newColumn('chargePhoneNumber').withTitle($translate('userServiceSend.chargePhoneNumber')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('cancelTime').withTitle($translate('userOrderHis.cancelTime')).notSortable().notVisible().renderWith(timeRender),
			DTColumnBuilder.newColumn('orderRoute').withTitle($translate('userOrderHis.orderRoute')).notSortable().notVisible().renderWith(orderRouteDetail),
			DTColumnBuilder.newColumn('orderType').withTitle($translate('userOrderHis.orderType')).notSortable().notVisible().renderWith(orderTypeDetail),
			DTColumnBuilder.newColumn('orderTime').withTitle($translate('userOnDemandHis.orderTime')).notSortable().renderWith(timeRender),
			DTColumnBuilder.newColumn('phoneNumber').withTitle($translate('userRecv.phoneNumber')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('cancelRoute').withTitle($translate('userOrderHis.cancelRoute')).notSortable().notVisible().renderWith(cancelRouteDetail),
			DTColumnBuilder.newColumn('featureStr').withTitle($translate('userOrderHis.featureStr')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('priority').withTitle($translate('userOrderHis.priority')).notSortable().renderWith(priorityDetail).notVisible(),
			DTColumnBuilder.newColumn('status').withTitle($translate('userServiceSend.status')).notSortable().renderWith(statusDetail).notVisible(),
			DTColumnBuilder.newColumn('version').withTitle($translate('userOrderHis.version')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('notDisturbTime').withTitle($translate('userOrderHis.notDisturbTime')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('transactionId').withTitle($translate('userOrderHis.transactionId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('effTime').withTitle($translate('userOrderHis.effTime')).notSortable().notVisible().renderWith(timeRender),
			DTColumnBuilder.newColumn('area').withTitle($translate('userOrderHis.area')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('lastBatchId').withTitle($translate('userOrderHis.lastBatchId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('isPackage').withTitle($translate('userOrderHis.isPackage')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('notifySpFlag').withTitle($translate('userInfo.notifySpFlag')).notSortable().renderWith(notifySpFlag).notVisible(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width', '15%').notSortable().renderWith(actionsHtml) ];
//	vm.addInit = addInit;
//	vm.edit = edit;
//	vm.submit = submit;
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
		if (data == '0') {
			return '<span translate="userServiceSend.order"></span>';
		} else if (data == '1') {
			return '<span translate="common.frozen"></span>';
		} else {
			return '';
		}
	}

	function orderRouteDetail(data, type, full, meta) {
		if (data == '1') {
			return '<span translate="userServiceSend.SMS"></span>';
		} else if (data == '2') {
			return '<span translate="userServiceSend.MMS"></span>';
		} else if (data == '3') {
			return '<span translate="userOrderHis.wap"></span>';
		} else if (data == '4') {
			return '<span translate="userOrderHis.web"></span>';
		} else if (data == '5') {
			return '<span translate="userOrderHis.customerService"></span>';
		} else {
			return '';
		}
	}
	function notifySpFlag(data, type, full, meta){
		if(data=='0'){
			return $translate.instant('common.informed');
		}else if(data=='1'){
			return $translate.instant('common.notNotified');
		}else{
			return '';
		}
	}
	function orderTypeDetail(data, type, full, meta) {
			if (data == '1') {
				return '<span translate="userOrderHis.monthly"></span>';
			} else if (data == '2') {
				return '<span translate="userServiceSend.onDemand"></span>';
			} else if (data == '3') {
				return '<span translate="userServiceSend.free"></span>';
			} else {
				return '';
			}
		}
	
	function cancelRouteDetail(data, type, full, meta) {
		if (data == '1') {
			return '<span translate="userServiceSend.SMS"></span>';
		} else if (data == '2') {
			return '<span translate="userServiceSend.MMS"></span>';
		} else if (data == '3') {
			return '<span translate="userOrderHis.wap"></span>';
		} else if (data == '4') {
			return '<span translate="userOrderHis.web"></span>';
		} else if (data == '5') {
			return '<span translate="userOrderHis.customerService"></span>';
		} else {
			return '';
		}
	}

	function priorityDetail(data, type, full, meta) {
		if (data == '1') {
			return '<span translate="userOrderHis.VIP"></span>';
		} else if (data == '2') {
			return '<span translate="userOrderHis.common"></span>';
		} else if (data == '3') {
			return '<span translate="userOrderHis.experienceUser"></span>';
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
		vm.beans[data.userOrderHisId] = data;
		var actionsHtml_html = '<button class="btn btn-danger" title="'
				+ $translate.instant('common.delete')
				+ '" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.userOrderHisId
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
		
		UserOrderHisService.selSpInfo().then(function(d) {
	        vm.spInfoData = d.body;
       },
		function(errResponse){
			console.error('Error while fetching spInfo');
		}
		);
		
		
		UserOrderHisService.selUserInfo().then(function(d) {
	        vm.userInfoData = d.body;
       },
		function(errResponse){
			console.error('Error while fetching userInfo');
		}
		);
		
		
		UserOrderHisService.selProvinces().then(function(d) {
	        vm.provinceData = d.body;
       },
		function(errResponse){
			console.error('Error while fetching provinceCity');
		}
		);
		
		UserOrderHisService.selProductInfo().then(function(d) {
	        vm.productInfoData = d.body;
       },
		function(errResponse){
			console.error('Error while fetching productInfo');
		}
		);
	}
	
	//下拉框中输入框
	function selectDevice(){
		//解决 select2在模态框使用，模糊输入框无效
		$("#provinceCityId").val('').select2();
		$("#phoneNumber").val('').select2();
		$("#spInfo").val('').select2();
		$("#productInfoId").val('').select2();
		$("#myModal").attr("tabindex","");
		//解决selec2在火狐模太框中输入框不能输入start
		$.fn.modal.Constructor.prototype.enforceFocus = function () { 
		};
		//解决selec2在火狐模太框中输入框不能输入end
	}
	
	$("#queryBtn").click(function(){
		selectDevice();
	})  
	    
//	function addInit() {
//		selectDevice();
//		vm.modelTitle = $translate.instant('userServiceSend.add');
//		$("#spInfo2").val('').select2();
//		vm.readonlyID = false;
//		vm.disabled = false;
//		vm.bean = {};
//		vm.statusCode = "";
//		vm.statusMessage = "";
//	}
//	function edit(bean) {
//		selectDevice();
//		reloadData();
//		vm.readonlyID = true;
//		vm.modelTitle = $translate.instant('userServiceSend.edit');
//		$("#spInfo2").val(bean.spInfoId).select2();
//		vm.bean = bean;
//		vm.bean.serviceType = vm.bean.serviceType+"";
//		vm.bean.status = vm.bean.status+"";
//		vm.bean.sendServiceType = vm.bean.sendServiceType +"";
//		vm.statusCode = "";
//		vm.statusMessage = "";
//		vm.bean.reportTime = timeFormatOld(vm.bean.reportTime);
//	}
//
//	function submit() {
//		if (!vm.readonlyID) {
//			$.fn.dataTable.ext.errMode = 'none';
//			vm.bean.reportTime = timeFormatNew(vm.bean.reportTime);
//			UserOrderHisService.createUserServiceSend(vm.bean).then(
//					onSubmitSuccess, function(errResponse) {
//						handleAjaxError(errResponse);
//						console.error('Error while creating GovUser.');
//					});
//			vm.reset();
//		} else {
//			vm.bean.reportTime = timeFormatNew(vm.bean.reportTime);
//			UserOrderHisService.updateUserServiceSend(vm.bean,vm.bean.userServiceSendId).then(onSubmitSuccess,
//					function(errResponse) {
//						handleAjaxError(errResponse);
//						console.error('Error while updating GovUser.');
//					});
//		}
//	}
//	
//	function onSubmitSuccess(data) {
//		vm.statusCode = data.statusCode;
//		vm.statusMessage = data.statusMessage;
//		reloadData();
//	}

	
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
									UserOrderHisService.deleteUserOrderHis(bean.userOrderHisId).then(reloadData,
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
		var userOrderHisId = vm.beanSer.userOrderHisId;
		var phoneNumber = vm.beanSer.phoneNumber;
		var spInfoId = vm.beanSer.spInfoId;
		var productInfoId = vm.beanSer.productInfoId;
		var status = vm.beanSer.status;
		var orderRoute = vm.beanSer.orderRoute;
		var area = vm.beanSer.provinceCityId;
		var priority = vm.beanSer.priority;
		if(vm.beanSer.orderTimeStart >= vm.beanSer.orderTimeEnd){
			alert($translate.instant('userOrderHis.orderTimeRange'));
			return;
		}
		var orderTimeStart = timeFormatNew(vm.beanSer.orderTimeStart);
		var orderTimeEnd = timeFormatNew(vm.beanSer.orderTimeEnd);
		vm.dtInstance.changeData(getFromSource(apiUrl
				+ '/userOrderHis?userOrderHisId=' + getValueForSelect(userOrderHisId) + "&phoneNumber=" + getValueForSelect(phoneNumber) + "&orderTimeStart=" + getValueForSelect(orderTimeStart)
				+ "&orderTimeEnd=" + getValueForSelect(orderTimeEnd) + "&spInfoId=" + getValueForSelect(spInfoId) + "&productInfoId=" + getValueForSelect(productInfoId)
				+ "&area=" + getValueForSelect(area) + "&orderRoute=" + getValueForSelect(orderRoute) + getValueForSelect(2,"&orderType=") + "&priority=" + getValueForSelect(priority)));
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