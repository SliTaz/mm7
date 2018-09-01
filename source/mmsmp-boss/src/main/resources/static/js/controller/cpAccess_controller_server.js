'use strict';
var App = angular.module('accessInfoModule', [ 'datatables',
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
	$("#auditTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#auditTimeStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#auditTimeEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, AccessInfoService, localStorageService,
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
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/cpInfo?type=2')).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('cpName').withTitle($translate('cpInfo.cpName')).notSortable(),
			DTColumnBuilder.newColumn('legalPerson').withTitle($translate('cpInfo.legalPerson')).notSortable(),
			DTColumnBuilder.newColumn('cpInfoId').withTitle($translate('cpInfo.accessId')).notSortable(),
			DTColumnBuilder.newColumn('webAddr').withTitle($translate('cpInfo.webAddr')).notSortable(),
			DTColumnBuilder.newColumn('businessPerson').withTitle($translate('cpInfo.businessPerson')).notSortable(),
			DTColumnBuilder.newColumn('businessTel').withTitle($translate('cpInfo.businessTel')).notSortable(),
			DTColumnBuilder.newColumn('status').withTitle($translate('cpInfo.status')).renderWith(statusDetail).notSortable().notVisible(),
			DTColumnBuilder.newColumn('spName').withTitle($translate('cpInfo.spInfo')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('cpAddr').withTitle($translate('cpInfo.cpAddr')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('bankName').withTitle($translate('cpInfo.bankName')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('bankAccount').withTitle($translate('cpInfo.bankAccount')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('faxNo').withTitle($translate('cpInfo.faxNo')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('businessMobile').withTitle($translate('cpInfo.businessMobile')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('businessEmail').withTitle($translate('consumerGasCoupon.used')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('customerPerson').notVisible().withTitle($translate('cpInfo.customerPerson')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('customerTel').withTitle($translate('cpInfo.customerTel')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('customerMobile').withTitle($translate('cpInfo.customerMobile')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('customerEmail').withTitle($translate('consumerGasCoupon.consumerUserClapId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('loginUser').withTitle($translate('user.status')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('isEnable').withTitle($translate('cpInfo.isEnable')).notVisible().notSortable().renderWith(isEnableDetail),
			DTColumnBuilder.newColumn('registerEmail').withTitle($translate('cpInfo.registerEmail')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('cpCode').notVisible().withTitle($translate('cpInfo.cpCode')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('rejectReason').withTitle($translate('cpInfo.rejectReason')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('tecPerson').withTitle($translate('cpInfo.tecPerson')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('tecTel').withTitle($translate('cpInfo.tecTel')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('tecMobile').withTitle($translate('cpInfo.tecMobile')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('tecEmail').withTitle($translate('consumerGasCoupon.total')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('financePerson').withTitle($translate('cpInfo.financePerson')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('financeTel').notVisible().withTitle($translate('cpInfo.financeTel')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('financeMobile').notVisible().withTitle($translate('cpInfo.financeMobile')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('financeEmail').withTitle($translate('consumerGasCoupon.couponId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('auditTime').withTitle($translate('cpInfo.auditTime')).notVisible().notSortable().renderWith(timeRender),
			DTColumnBuilder.newColumn('cpLogo').withTitle($translate('cpInfo.cpLogo')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('cpRemark').withTitle($translate('cpInfo.cpRemark')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('registerTime').withTitle($translate('consumerGasCoupon.used')).notVisible().notSortable().renderWith(timeRender),
			DTColumnBuilder.newColumn('cpType').withTitle($translate('cpInfo.cpType')).notSortable().renderWith(cpTypeDetail).notVisible(),
			DTColumnBuilder.newColumn('proportion').withTitle($translate('cpInfo.proportion')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('aear').withTitle($translate('cpInfo.aear')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('type').withTitle($translate('app.type')).notSortable().renderWith(typeDetail).notVisible(),
			DTColumnBuilder.newColumn('cpAccessId').withTitle($translate('cpInfo.cpAccessId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('cpAccessIdExtend').withTitle($translate('cpInfo.cpAccessIdExtend')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('cpAccessUrl').withTitle($translate('cpInfo.cpAccessUrl')).notVisible().notSortable(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width', '15%').notSortable().renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	// 表头start
	tableHandle();
	// 表头end
	vm.selData = selData;
	selData();
	vm.VAR_MAX_FILE_SIZE_M=1;//1M
	initltCommon(vm, localStorageService, topleftService);
	$("#loadDiv").hide();
	function statusDetail(data, type, full, meta) {
		if (data == '0') {
			return '<span translate="common.apply"></span>';
		} else if (data == '1') {
			return '<span translate="common.ordinary"></span>';
		} else if (data == '2') {
			return '<span translate="common.pause"></span>';
		} else if (data == '3') {
			return '<span translate="cpInfo.logout"></span>';
		} else if (data == '4') {
			return '<span translate="cpInfo.applyReject"></span>';
		} else {
			return '';
		}
	}
	function isEnableDetail(data, type, full, meta) {
		if (data == '1') {
			return '<span translate="common.activate"></span>';
		} else if (data == '2') {
			return '<span translate="common.inactivate"></span>';
		} else {
			return '';
		}
	}
	function cpTypeDetail(data, type, full, meta) {
		if (data == '1') {
			return '<span translate="cpInfo.selfOperatedCP"></span>';
		} else if (data == '2') {
			return '<span translate="cpInfo.cooperationCP"></span>';
		} else {
			return '';
		}
	}
	function typeDetail(data, type, full, meta) {
		if (data == '1') {
			return '<span translate="cpInfo.CP"></span>';
		} else if (data == '2') {
			return '<span translate="cpInfo.groupCustomer"></span>';
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
		vm.beans[data.cpInfoId] = data;
		var actionsHtml_html = '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'
				+ $translate.instant('common.edit')
				+ '" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.cpInfoId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'
				+ $translate.instant('common.delete')
				+ '" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.cpInfoId
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
	//初始化遍历spInfo
	function selData(){
		AccessInfoService.selSpInfo().then(function(d) {
					        vm.spInfoData = d.body;
				       },
  					function(errResponse){
  						console.error('Error while fetching spInfo');
  					}
		       );
	}
	//下拉框中输入框
	function selectDevice(){
		//解决 select2在模态框使用，模糊输入框无效
		$("#spInfo").select2();
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
		$("#spInfo2").val("").select2();
		vm.modelTitle = $translate.instant('accessInfo.add');
		vm.readonlyID = false;
		vm.disabled = false;
		vm.bean = {};
		vm.statusCode = "";
		vm.statusMessage = "";
	}
	function edit(bean) {
		reloadData();
		selectDevice();
		$("#spInfo2").val(bean.spInfoId).select2();
		vm.readonlyID = true;
		vm.modelTitle = $translate.instant('accessInfo.edit');
		vm.bean = bean;
		vm.bean.status = vm.bean.status+"";
		vm.bean.cpType = vm.bean.cpType+"";
		vm.bean.type = vm.bean.type +"";
		vm.bean.isEnable = vm.bean.isEnable+"";
		vm.statusCode = "";
		vm.statusMessage = "";
		vm.bean.auditTime = timeFormatOld(vm.bean.auditTime);
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			vm.bean.auditTime = timeFormatNew(vm.bean.auditTime);
			vm.bean.type = 2;
			AccessInfoService.createCpInfo(vm.bean).then(
					onSubmitSuccess, function(errResponse) {
						handleAjaxError(errResponse);
						console.error('Error while creating cpInfo.');
					});
			vm.reset();
		} else {
			vm.bean.auditTime = timeFormatNew(vm.bean.auditTime);
			AccessInfoService.updateCpInfo(vm.bean,vm.bean.cpInfoId).then(onSubmitSuccess,
					function(errResponse) {
						handleAjaxError(errResponse);
						console.error('Error while updating cpInfo.');
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
									AccessInfoService.deleteCpInfo(bean.cpInfoId).then(reloadData,
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
		var cpName = vm.beanSer.cpName;
		var cpInfoId = vm.beanSer.cpInfoId;
		vm.dtInstance.changeData(getFromSource(apiUrl
				+ '/cpInfo?cpName=' + getValueForSelect(cpName) + "&cpInfoId=" + getValueForSelect(cpInfoId) +"&type="  + getValueForSelect(2)));
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