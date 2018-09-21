'use strict';
var App = angular.module('platformRevenueModule', [ 'datatables',
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
		$filter, $compile, localStorageService,
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
	vm.appData = [];
	var date = new Date();
	date.setDate(date.getDate());
    var todayTime = getBeforeDayFormatDate(date);
    var weekDate = new Date();
        weekDate.setDate(weekDate.getDate()-30);
		var weekTime = getBeforeDayFormatDate(weekDate);
		vm.beanSer.orderTimeStart=dayFormatOld(weekTime);
		vm.beanSer.orderTimeEnd=dayFormatOld(todayTime);
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/userOrderHis/platformRevenue?orderTimeStart=' + getValueForSelect(weekTime)+"&orderTimeEnd="+getValueForSelect(todayTime))).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		    DTColumnBuilder.newColumn('yearMonth').withTitle($translate('userOrderHis.YearMonth')).notSortable(),
			DTColumnBuilder.newColumn('orderCount').withTitle($translate('userOrderHis.orderCount')).notSortable(),
			DTColumnBuilder.newColumn('orderSum').withTitle($translate('userOrderHis.orderSum')).notSortable(),
			DTColumnBuilder.newColumn('buyCount').withTitle($translate('userOrderHis.buyCount')).notSortable(),
			DTColumnBuilder.newColumn('buySum').withTitle($translate('userOrderHis.buySum')).notSortable(),
			DTColumnBuilder.newColumn('sum').withTitle($translate('userOrderHis.sum')).notSortable(),
			DTColumnBuilder.newColumn('ratioByNearMonth').withTitle($translate('userOrderHis.totalRatioByNearMonth')).notSortable()];


	// 表头start
	tableHandle();
	// 表头end
	vm.VAR_MAX_FILE_SIZE_M=1;//1M
	initltCommon(vm, localStorageService, topleftService);
	$("#loadDiv").hide();   

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
	function getformatmonth(data){
		var naLan = navigator.language;
		if (naLan == undefined || naLan == "") {
			naLan = navigator.browserLanguage;
		}	
		if(naLan=='zh'){
		return  data; 
		}else if(naLan.indexOf("zh") >= 0){
			return  data; 
		}else{
			var monthData=[];
			for(var i=0;i<data.length;i++){
				if(data[i].length==8){
					monthData[i]="0"+data[i].substring(7,8)+"/0"+data[i].substring(5,6)+"/"+data[i].substring(0,4)
				}if(data[i].length==9){
					if(data[i].substring(6,7)=="-"){
						monthData[i]=data[i].substring(7,9)+"/0"+data[i].substring(5,6)+"/"+data[i].substring(0,4);
					}else{
					    monthData[i]="0"+data[i].substring(8,9)+"/"+data[i].substring(5,7)+"/"+data[i].substring(0,4);
					}
				}
				if(data[i].length==10){
						monthData[i]=data[i].substring(8,10)+"/"+data[i].substring(5,7)+"/"+data[i].substring(0,4); 
					}
				}
			return monthData;
		 }
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

	});
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
		var orderTimeStart = timeFormatNew(vm.beanSer.orderTimeStart);
		var orderTimeEnd = timeFormatNew(vm.beanSer.orderTimeEnd);
		vm.dtInstance.changeData(getFromSource(apiUrl
				+ '/userOrderHis/platformRevenue?orderTimeStart=' + getValueForSelect(orderTimeStart)
				+ "&orderTimeEnd=" + getValueForSelect(orderTimeEnd)));
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