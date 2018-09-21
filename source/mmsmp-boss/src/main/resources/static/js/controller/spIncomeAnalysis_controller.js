'use strict';
var App = angular.module('spIncomeAnalysisReportModule', [ 'datatables',
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
		$filter, $compile, spIncomeAnalysisService, localStorageService,
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
        weekDate.setDate(weekDate.getDate()-30);
		var weekTime = getBeforeDayFormatDate(weekDate);
		vm.beanSer.orderTimeStart=dayFormatOld(weekTime);
		vm.beanSer.orderTimeEnd=dayFormatOld(todayTime);
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/userOrderHis/provincialTotalReport?orderTimeStart=' + getValueForSelect(weekTime)+"&orderTimeEnd="+getValueForSelect(todayTime))).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [		
			DTColumnBuilder.newColumn('province').withTitle($translate('userOrderHis.province')).notSortable(),
			DTColumnBuilder.newColumn('spInfoId').withTitle($translate('userOrderHis.spName')).notSortable(),
			DTColumnBuilder.newColumn('newOrderSum').withTitle($translate('userOrderHis.newOrderSum')).notSortable(),
			DTColumnBuilder.newColumn('newBuySum').withTitle($translate('userOrderHis.newBuySum')).notSortable(),
			DTColumnBuilder.newColumn('newTotal').withTitle($translate('userOrderHis.newTotal')).notSortable()];
//	vm.addInit = addInit;
//	vm.edit = edit;
//	vm.submit = submit;
	
	vm.selData = selData;
	selData();
	// 表头start
	tableHandle();
	// 表头end
	vm.VAR_MAX_FILE_SIZE_M=1;//1M
	initltCommon(vm, localStorageService, topleftService);
	$("#loadDiv").hide();   
//	$("#orderTimeStart").change(function() {
//		$("#orderTimeStart").val();
//            
//      
//	  
//	});

	
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

	
	function selData(){
		
		spIncomeAnalysisService.selSpInfo().then(function(d) {
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
		$("#spInfo").val('').select2();
		
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
		// 解决查询后保持列的显示end
		var spInfoId = vm.beanSer.spInfoId;
	
		var orderTimeStart = timeFormatNew(vm.beanSer.orderTimeStart);
		var orderTimeEnd = timeFormatNew(vm.beanSer.orderTimeEnd);

		vm.dtInstance.changeData(getFromSource(apiUrl
				+ '/userOrderHis/provincialTotalReport?orderTimeStart=' + getValueForSelect(orderTimeStart)
				+ "&orderTimeEnd=" + getValueForSelect(orderTimeEnd)+ "&spInfoId=" + getValueForSelect(spInfoId) ));
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