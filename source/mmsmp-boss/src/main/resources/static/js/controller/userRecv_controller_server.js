'use strict';
var App = angular.module('userRecvModule', [ 'datatables',
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
	$("#recvTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#recvTimeStart").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#recvTimeEnd").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
        //minView: 'month',
        format: timeFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, UserRecvService, localStorageService,
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
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/userRecv')).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('userRecvId').withTitle($translate('userRecv.userRecvId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('phoneNumber').withTitle($translate('userRecv.phoneNumber')).notSortable(),
			DTColumnBuilder.newColumn('messageType').withTitle($translate('userRecv.messageType')).notSortable().renderWith(messageTypeDetail),
			DTColumnBuilder.newColumn('isCorrect').withTitle($translate('userRecv.isCorrect')).notSortable().renderWith(isCorrectDetail).notVisible(),
			DTColumnBuilder.newColumn('isOrder').withTitle($translate('userRecv.isOrder')).notSortable().renderWith(isOrderDetail),
			DTColumnBuilder.newColumn('recvTime').withTitle($translate('userRecv.recvTime')).notSortable().renderWith(timeRender),
			DTColumnBuilder.newColumn('messageContent').withTitle($translate('userRecv.messageContent')).notSortable().notVisible().renderWith(remarkDetail),
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
	function messageTypeDetail(data, type, full, meta) {
		if (data == '1') {
			return '<span translate="userRecv.SMS"></span>';
		} else if (data == '2') {
			return '<span translate="userRecv.MMS"></span>';
		} else {
			return '';
		}
	}
	function isCorrectDetail(data, type, full, meta) {
		if (data == '0') {
			return '<span translate="common.no"></span>';
		} else if (data == '1') {
			return '<span translate="common.ok"></span>';
		} else {
			return '';
		}
	}
	function isOrderDetail(data, type, full, meta) {
		if (data == '0') {
			return '<span translate="common.no"></span>';
		} else if (data == '1') {
			return '<span translate="common.ok"></span>';
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
		vm.beans[data.userRecvId] = data;
		var actionsHtml_html = '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'
				+ $translate.instant('common.edit')
				+ '" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.userRecvId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'
				+ $translate.instant('common.delete')
				+ '" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.userRecvId
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
		UserRecvService.selUserInfo().then(function(d) {
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
		$("#phoneNumber2").val('').select2();
		vm.modelTitle = $translate.instant('userRecv.add');
		vm.readonlyID = false;
		vm.disabled = false;
		vm.bean = {};
		vm.statusCode = "";
		vm.statusMessage = "";
	}
	function edit(bean) {
		reloadData();
		selectDevice();
		$("#phoneNumber2").val(bean.phoneNumber).select2();
		vm.readonlyID = true;
		vm.modelTitle = $translate.instant('userRecv.edit');
		vm.bean = bean;
		vm.bean.messageType = vm.bean.messageType+"";
		vm.bean.isCorrect = vm.bean.isCorrect+"";
		vm.bean.isOrder = vm.bean.isOrder +"";
		vm.statusCode = "";
		vm.statusMessage = "";
		vm.bean.auditTime = timeFormatOld(vm.bean.auditTime);
	}
	
	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			vm.bean.recvTime = timeFormatNew(vm.bean.recvTime);
			UserRecvService.createUserRecv(vm.bean).then(
					onSubmitSuccess, function(errResponse) {
						handleAjaxError(errResponse);
						console.error('Error while creating userRecv.');
					});
			vm.reset();
		} else {
			vm.bean.auditTime = timeFormatNew(vm.bean.auditTime);
			UserRecvService.updateUserRecv(vm.bean,vm.bean.userRecvId).then(onSubmitSuccess,
					function(errResponse) {
						handleAjaxError(errResponse);
						console.error('Error while updating userRecv.');
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
									UserRecvService.deleteUserRecv(bean.userRecvId).then(reloadData,
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
	//超长备注处理start
	function remarkDetail(data, type, full, meta){
		if(data!=null){
			return '<span class="spanFun" style=" display: inline-block;width: 200px;white-space:nowrap;word-break:keep-all;overflow:hidden;text-overflow:ellipsis;">'+data+'</span>'
		}else{
			return '';
		}
	}
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
		var userRecvId = vm.beanSer.userRecvId;
		var phoneNumber = vm.beanSer.phoneNumber;
		var messageType = vm.beanSer.messageType;
		var isCorrect = vm.beanSer.isCorrect;
		if(vm.beanSer.recvTimeStart >= vm.beanSer.recvTimeEnd){
			alert($translate.instant('userRecv.recvTimeRange'));
			return;
		}
		var recvTimeStart = timeFormatNew(vm.beanSer.recvTimeStart);
		var recvTimeEnd = timeFormatNew(vm.beanSer.recvTimeEnd);
		vm.dtInstance.changeData(getFromSource(apiUrl
				+ '/userRecv?userRecvId=' + getValueForSelect(userRecvId) + "&phoneNumber=" + getValueForSelect(phoneNumber)+ "&messageType="  + getValueForSelect(messageType)
				+ "&isCorrect=" + getValueForSelect(isCorrect)+"&recvTimeStart="+getValueForSelect(recvTimeStart)+"&recvTimeEnd="+getValueForSelect(recvTimeEnd)));
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