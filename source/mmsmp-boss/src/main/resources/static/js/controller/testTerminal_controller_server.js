'use strict';
var App = angular.module('testTerminalModule', [ 'datatables',
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
		$filter, $compile, TestTerminalService, localStorageService,
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
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/testTerminal')).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('testTerminalId').withTitle($translate('testTerminal.testTerminalId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('msisdn').withTitle($translate('testTerminal.msisdn')).notSortable(),
			DTColumnBuilder.newColumn('proudctName').withTitle($translate('testTerminal.proudctName')).notSortable(),
			DTColumnBuilder.newColumn('remark').withTitle($translate('testTerminal.remark')).notSortable().renderWith(remarkDetail),
			DTColumnBuilder.newColumn('cpInfoId').withTitle($translate('testTerminal.cpInfoId')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('createUser').withTitle($translate('testTerminal.createUser')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('createTime').withTitle($translate('testTerminal.createTime')).notSortable().renderWith(timeRender).notVisible(),
			DTColumnBuilder.newColumn('auditUser').withTitle($translate('testTerminal.auditUser')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('auditTime').withTitle($translate('testTerminal.auditTime')).notSortable().notVisible().renderWith(timeRender),
			DTColumnBuilder.newColumn('status').withTitle($translate('testTerminal.status')).notSortable().renderWith(statusDetail).notVisible(),
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
			return '<span translate="common.moderated"></span>';
		} else if (data == '2') {
			return '<span translate="common.aduitPass"></span>';
		} else if (data == '3') {
			return '<span translate="common.aduitFail"></span>';
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
		TestTerminalService.selProductInfo().then(function(d) {
	        vm.productInfoData = d.body;
       },
		function(errResponse){
			console.error('Error while fetching spInfo');
		}
		);
		TestTerminalService.selCPInfo().then(function(d) {
	        vm.cpInfoData = d.body;
       },
		function(errResponse){
			console.error('Error while fetching spInfo');
		}
		);
		
		
	}
	
	//下拉框中输入框
	function selectDevice(){
		//解决 select2在模态框使用，模糊输入框无效
		$("#proudctInfoId").val('').select2();
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
		$("#productInfoId2").val('').select2();
		$("#cpInfoId").val('').select2();
		vm.modelTitle = $translate.instant('testTerminal.add');
		vm.readonlyID = false;
		vm.disabled = false;
		vm.bean = {};
		vm.statusCode = "";
		vm.statusMessage = "";
	}
	function edit(bean) {
		reloadData();
		selectDevice();
		TestTerminalService.selByTestTerminalProduct(bean.testTerminalId).then(function(d) {
	        vm.testTerminalData = d.body;
	        bean.productInfoId = vm.testTerminalData.productInfoId;
	    	$("#productInfoId2").val(bean.productInfoId).select2();
		},
		function(errResponse){
			console.error('Error while fetching spInfo');
		}
		);
		$("#cpInfoId").val(bean.cpInfoId).select2();
		vm.readonlyID = true;
		vm.modelTitle = $translate.instant('testTerminal.edit');
		vm.bean = bean;
		vm.bean.status = vm.bean.status+"";
		vm.statusCode = "";
		vm.statusMessage = "";
	}
	
	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			vm.bean.createUser = vm.ltuserName;
			TestTerminalService.createTestTerminal(vm.bean).then(
					onSubmitSuccess, function(errResponse) {
						handleAjaxError(errResponse);
						console.error('Error while creating userRecv.');
					});
			vm.reset();
		} else {
			TestTerminalService.updateTestTerminal(vm.bean,vm.bean.testTerminalId).then(onSubmitSuccess,
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
									TestTerminalService.deleteTestTerminal(bean.testTerminalId).then(reloadData,
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
		var msisdn = vm.beanSer.msisdn;
		var proudctInfoId = vm.beanSer.proudctInfoId;
//		var recvTimeStart = timeFormatNew(vm.beanSer.recvTimeStart);
//		var recvTimeEnd = timeFormatNew(vm.beanSer.recvTimeEnd);
		vm.dtInstance.changeData(getFromSource(apiUrl
				+ '/testTerminal?phoneNumber=' + getValueForSelect(msisdn) + "&proudctInfoId=" + getValueForSelect(proudctInfoId)));
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