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
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/cooperKey')).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('keyId').withTitle($translate('cooperKey.keyId')).notSortable(),
			DTColumnBuilder.newColumn('cooperId').withTitle($translate('cooperKey.cooperId')).notSortable(),
			DTColumnBuilder.newColumn('cooperName').withTitle($translate('cooperKey.cooperName')).notSortable(),
			DTColumnBuilder.newColumn('cooperKey').withTitle($translate('cooperKey.cooperKey')).notSortable(),
			DTColumnBuilder.newColumn('ips').withTitle($translate('cooperKey.ips')).notSortable(),
			DTColumnBuilder.newColumn('notifyUrl').withTitle($translate('cooperKey.notifyUrl')).notSortable(),
			DTColumnBuilder.newColumn('serviceTel').withTitle($translate('cooperKey.serviceTel')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('appName').withTitle($translate('cooperKey.appName')).notSortable().notVisible(),
			DTColumnBuilder.newColumn('remark').withTitle($translate('cooperKey.remark')).notSortable().notVisible(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width', '15%').notSortable().renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	// 表头start
	tableHandle();
	// 表头end
	vm.VAR_MAX_FILE_SIZE_M=1;//1M
	initltCommon(vm, localStorageService, topleftService);
	$("#loadDiv").hide();
	
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

	function addInit() {
		vm.modelTitle = $translate.instant('accessInfo.add');
		vm.readonlyID = false;
		vm.disabled = false;
		vm.bean = {};
		vm.statusCode = "";
		vm.statusMessage = "";
	}
	function edit(bean) {
		reloadData();
		vm.readonlyID = true;
		vm.disabled = true;
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
			AccessInfoService.createCooperKey(vm.bean).then(
					onSubmitSuccess, function(errResponse) {
						handleAjaxError(errResponse);
						console.error('Error while creating cpInfo.');
					});
			vm.reset();
		} else {
			vm.bean.auditTime = timeFormatNew(vm.bean.auditTime);
			AccessInfoService.updateCooperKey(vm.bean,vm.bean.keyId).then(onSubmitSuccess,
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
									AccessInfoService.deleteCooperKey(bean.keyId).then(reloadData,
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
		var cooperName = vm.beanSer.cooperName;
		var keyId = vm.beanSer.keyId;
		vm.dtInstance.changeData(getFromSource(apiUrl
				+ '/cooperKey?cooperName=' + getValueForSelect(cooperName) + "&keyId=" + getValueForSelect(keyId)));
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