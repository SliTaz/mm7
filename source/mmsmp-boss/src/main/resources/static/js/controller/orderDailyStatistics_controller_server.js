'use strict';
var App = angular.module('orderDailyStatisticsModule', [ 'datatables',
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
	     minView: 'month',
	     format: dayFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	$("#orderTimeEnd").datetimepicker({
		 language: "zh-CN",
	     autoclose: true,             
	     clearBtn: true,//清除按钮
	     todayBtn: 'linked',
	     minView: 'month',
	     format: dayFormat() //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});
function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, OrderDailyStatisticsService, localStorageService,
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
	var date = new Date();
	date.setDate(date.getDate());
    var todayTime = getBeforeDayFormatDate(date);
    var weekDate = new Date();
    weekDate.setDate(weekDate.getDate()-6);
		var weekTime = getBeforeDayFormatDate(weekDate);
		vm.beanSer.orderTimeStart=dayFormatOld(weekTime);
		vm.beanSer.orderTimeEnd=dayFormatOld(todayTime);
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/onDemandDailyStatistics/userOrderHis?orderType=1'+"&orderTimeStart="+ getValueForSelect(weekTime)+"&orderTimeEnd="+getValueForSelect(todayTime))).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		    DTColumnBuilder.newColumn('productServiceId').withTitle($translate('mmsmp.productService.productServiceId')).notSortable(),
		    DTColumnBuilder.newColumn('productName').withTitle($translate('userOrderHis.productName')).notSortable(),
			DTColumnBuilder.newColumn('productInfoId').withTitle($translate('userServiceSend.productInfoId')).notSortable(),
			DTColumnBuilder.newColumn('companyCode').withTitle($translate('spInfo.companyCode')).notSortable(),
			DTColumnBuilder.newColumn('spInfoId').withTitle($translate('userOrderHis.spInfo')).notSortable(),
			DTColumnBuilder.newColumn('newOrderNumber').withTitle($translate('mmsmp.productInfo.newOrderNumber')).notSortable(),
			DTColumnBuilder.newColumn('orderTimes').withTitle($translate('mmsmp.productInfo.orderTimes')).notSortable(),
			DTColumnBuilder.newColumn('unsubscribe').withTitle($translate('mmsmp.productInfo.unsubscribe')).notSortable()
			//DTColumnBuilder.newColumn('currentChargeOrder').withTitle($translate('mmsmp.productInfo.currentChargeOrder')).notSortable(),
			];
	vm.selData = selData;
	selData();
	// 表头start
	tableHandle();
	// 表头end
	initltCommon(vm, localStorageService, topleftService);
	$("#loadDiv").hide();
	/*function actionsHtml(data, type, full, meta) {
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

	}*/
	function changeDataForHTML() {
		// alert("changeDataForHTML");
		$scope.$applyAsync();// 当在angular的上下文之外修改了界面绑定的数据时，需要调用该函数，让页面的值进行改变。
	}
	
	function selData(){
		
		OrderDailyStatisticsService.selSpInfo().then(function(d) {
	        vm.spInfoData = d.body;
       },
		function(errResponse){
			console.error('Error while fetching spInfo');
		}
		);
		
		
		OrderDailyStatisticsService.selUserInfo().then(function(d) {
	        vm.userInfoData = d.body;
       },
		function(errResponse){
			console.error('Error while fetching spInfo');
		}
		);
		
		
		OrderDailyStatisticsService.selProductInfo().then(function(d) {
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
		$("#spInfo").val('').select2();
		$("#productInfoId").val('').select2();
		$("#productInfoIds").val('').select2();
		$("#myModal").attr("tabindex","");
		//解决selec2在火狐模太框中输入框不能输入start
		$.fn.modal.Constructor.prototype.enforceFocus = function () { 
		};
		//解决selec2在火狐模太框中输入框不能输入end
	}
	
	$("#queryBtn").click(function(){
		selectDevice();
	})  
	    

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
//查询一周前时间start
	function getBeforeDayFormatDate(date) {
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    var h = date.getHours();
	    var m = date.getMinutes();
	    var s = date.getSeconds();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    if (h >= 0 && h <= 9) {
	    	h = "0" + h;
	    }
	    if (m >= 0 && m <= 9) {
	    	m = "0" + m;
	    }
	    if (s >= 0 && s <= 9) {
	    	s = "0" + s;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
	
	    return currentdate;
	}
	//查询一周前时间end
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
		var spInfoId = vm.beanSer.spInfoId;
		var productInfoId = vm.beanSer.productInfoId;
		var orderType = vm.beanSer.orderType;
		var orderTimeStart = timeFormatNew(vm.beanSer.orderTimeStart);
		var orderTimeEnd = timeFormatNew(vm.beanSer.orderTimeEnd);
		vm.dtInstance.changeData(getFromSource(apiUrl
				+ '/onDemandDailyStatistics/userOrderHis?orderTimeStart=' + getValueForSelect(orderTimeStart)
				+ "&orderTimeEnd=" + getValueForSelect(orderTimeEnd) + "&spInfoId=" + getValueForSelect(spInfoId) + "&productInfoId=" + getValueForSelect(productInfoId)
				+  getValueForSelect(1,"&orderType=")));
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