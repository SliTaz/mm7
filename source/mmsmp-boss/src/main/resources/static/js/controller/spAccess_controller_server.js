'use strict';
var App = angular.module('spAccessModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

$(function() {
	$("#expTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#effTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});


function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, SpAccessService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
    vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/spAccess')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		DTColumnBuilder.newColumn('spInfoId').withTitle($translate('spInfo.spInfoId')).notSortable(),
		DTColumnBuilder.newColumn('companyCode').withTitle($translate('spInfo.companyCode')).notSortable(),
		DTColumnBuilder.newColumn('companyName').withTitle($translate('spInfo.companyName')).notSortable(),
		DTColumnBuilder.newColumn('spAccessNumber').withTitle($translate('spInfo.spAccessNumber')).notSortable(),
		DTColumnBuilder.newColumn('spAccessNumberExtend').withTitle($translate('spInfo.spAccessNumberExtend')).notSortable(),
		DTColumnBuilder.newColumn('spCity').withTitle($translate('spInfo.spCity')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('isTrust').withTitle($translate('spInfo.isTrust')).notSortable().renderWith(statusType).notVisible(),
		DTColumnBuilder.newColumn('spOrderNotifyUrl').withTitle($translate('spInfo.spOrderNotifyUrl')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('orderKey').withTitle($translate('spInfo.orderKey')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('synOrderFunc').withTitle($translate('spInfo.synOrderFunc')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('effTime').withTitle($translate('spInfo.effTime')).renderWith(timeRender).notSortable().notVisible(),
		DTColumnBuilder.newColumn('expTime').withTitle($translate('spInfo.expTime')).renderWith(timeRender).notSortable().notVisible(),
		DTColumnBuilder.newColumn('reportMessageUrl').withTitle($translate('spInfo.reportMessageUrl')).notSortable().notVisible(),
		DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
				.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.statusType = statusType;
	vm.selData = selData;
	selData();
	vm.spInfoData = [];
	//表头start
	tableHandle();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
	$("#loadDiv").hide();
	
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.spInfoId] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.spInfoId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.spInfoId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>';
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
	function selData() {
		SpAccessService.fetchSpInfo().then(function(d) {
			vm.spInfoData = d.body;
		}, function(errResponse) {
			console.error('Error while fetching fetchAllCoupons');
		});
	}
	
	function statusType(data, type, full, meta){
		if(data=='0'){
			return $translate.instant('common.no');
		}else if(data=='1'){
			return $translate.instant('common.ok');
		}else{
			return '';
		}
	}
	function selectDevice(){
		//解决 select2在模态框使用，模糊输入框无效
		$("#spInfoId").select2();
		$("#spInfoIds").select2();
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
		$("#spInfoId").val("").select2();
		$("#spInfoIds").val("").select2();
		vm.modelTitle = $translate.instant('spAccess.add');
		vm.readonlyID = false;
		vm.disabledID = false;
		vm.bean = {};
		vm.bean.spInfoId=null;
		vm.bean.spAccessNumber=null;
		vm.bean.spAccessNumberExtend=null;
		vm.bean.spCity=null;
		vm.bean.spOrderNotifyUrl=null;
		vm.bean.orderKey=null;
		vm.bean.reportMessageUrl=null;
		vm.bean.isTrust=1;
		vm.bean.synOrderFunc=null;
		vm.bean.expTime=null;
		vm.bean.effTime=null;
		vm.isTrustData = [
			{value:0,text:$translate.instant('common.no')},
			{value:1,text:$translate.instant('common.ok')}];
		vm.statusCode="";
		vm.statusMessage="";
	}
	function edit(bean) {
		selectDevice();
		reloadData();
		$("#spInfoId").val(bean.spInfoId).select2();
		vm.modelTitle = $translate.instant('spAccess.edit');
		vm.readonlyID = true;
		vm.disabledID = true;
		vm.bean = bean;
		vm.isTrustData = [
			{value:0,text:$translate.instant('common.no')},
			{value:1,text:$translate.instant('common.ok')}];
		vm.bean.effTime = timeFormatOld(vm.bean.effTime);
		vm.bean.expTime = timeFormatOld(vm.bean.expTime);
		vm.statusCode="";
		vm.statusMessage="";
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			vm.bean.effTime = timeFormatNew(vm.bean.effTime);
			vm.bean.expTime = timeFormatNew(vm.bean.expTime);
			SpAccessService.createAlarmInfo(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating AlarmLevel.');
					});
			vm.reset();
		} else {
			vm.bean.effTime = timeFormatNew(vm.bean.effTime);
			vm.bean.expTime = timeFormatNew(vm.bean.expTime);
			SpAccessService.updateAlarmInfo(vm.bean, vm.bean.spInfoId).then(onSubmitSuccess,
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
					SpAccessService.deleteAlarmInfo(bean.spInfoId).then(reloadData,
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
		var companyName = vm.beanSer.companyName;
		var spAccessNumber = vm.beanSer.spAccessNumber;
		var spAccessNumberExtend = vm.beanSer.spAccessNumberExtend;
		var companyCode = vm.beanSer.companyCode;
		
		vm.dtInstance.changeData(getFromSource(apiUrl + '/spAccess?companyName=' + getValueForSelect(companyName)+
				"&spAccessNumber="+getValueForSelect(spAccessNumber)+"&spAccessNumberExtend="+getValueForSelect(spAccessNumberExtend)+"&companyCode="+getValueForSelect(companyCode)));
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