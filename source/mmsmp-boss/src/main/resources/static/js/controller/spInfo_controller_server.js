'use strict';
var App = angular.module('spInfoModule', [ 'datatables', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);

/*$(function() {
	$("#alarmTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
	
	$("#handleTime").datetimepicker({
        language: "zh-CN",
        autoclose: true,             
        clearBtn: true,//清除按钮
        todayBtn: 'linked',
//        minView: 'month',
        format: timeFormat()//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});*/


function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, SpInfoService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
    vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/spInfo')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		DTColumnBuilder.newColumn('spInfoId').withTitle($translate('spInfo.spInfoId')).notSortable(),
		DTColumnBuilder.newColumn('companyCode').withTitle($translate('spInfo.companyCode')).notSortable(),
		DTColumnBuilder.newColumn('companyName').withTitle($translate('spInfo.companyName')).notSortable(),
		DTColumnBuilder.newColumn('spAccessNumber').withTitle($translate('spInfo.spAccessNumber')).notSortable(),
		DTColumnBuilder.newColumn('spAccessNumberExtend').withTitle($translate('spInfo.spAccessNumberExtend')).notSortable(),
		DTColumnBuilder.newColumn('businessTel').withTitle($translate('spInfo.businessTel')).notSortable(),
		DTColumnBuilder.newColumn('contactPerson').withTitle($translate('spInfo.contactPerson')).notSortable(),
		DTColumnBuilder.newColumn('faxNo').withTitle($translate('spInfo.faxNo')).notSortable(),
		DTColumnBuilder.newColumn('emailAddr').withTitle($translate('spInfo.emailAddr')).notSortable(),
		DTColumnBuilder.newColumn('webAddr').withTitle($translate('spInfo.webAddr')).notSortable(),
		DTColumnBuilder.newColumn('officeAddr').withTitle($translate('spInfo.officeAddr')).notSortable(),
		DTColumnBuilder.newColumn('createTime').withTitle($translate('user.createTime')).renderWith(timeRender).notSortable().notVisible(),
		DTColumnBuilder.newColumn('province').withTitle($translate('spInfo.province')).notSortable().notVisible(),
		DTColumnBuilder.newColumn('status').withTitle($translate('user.status')).renderWith(statusType).notSortable().notVisible(),
		DTColumnBuilder.newColumn('updateTime').withTitle($translate('spInfo.updateTime')).renderWith(timeRender).notSortable().notVisible(),
		DTColumnBuilder.newColumn('maxCon').withTitle($translate('spInfo.maxCon')).notSortable(),
		DTColumnBuilder.newColumn('maxFlow').withTitle($translate('spInfo.maxFlow')).notSortable(),
		DTColumnBuilder.newColumn('serlfSp').withTitle($translate('spInfo.serlfSp')).renderWith(serlfSp).notSortable(),
		DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
				.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.serlfSp = serlfSp;
	vm.statusType = statusType;
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
	
	
	function statusType(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.ordinary');
		}else if(data=='0'){
			return $translate.instant('common.pause');
		}else{
			return '';
		}
	}
	
	function serlfSp(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.ok');
		}else if(data=='0'){
			return $translate.instant('common.no');
		}else{
			return '';
		}
	}
	
	function addInit() {
		vm.modelTitle = $translate.instant('spInfo.add');
		vm.readonlyID = false;
		vm.bean = {};
		vm.bean.spInfoId=null;
		vm.bean.companyCode=null;
		vm.bean.companyName=null;
		vm.bean.businessTel=null;
		vm.bean.contactPers=null;
		vm.bean.faxNo=null;
		vm.bean.emailAddr=null;
		vm.bean.status=1;
		vm.bean.serlfSp=1;
		vm.bean.webAddr=null;
		vm.bean.officeAddr=null;
		vm.bean.province=null;
		vm.bean.maxCon=null;
		vm.bean.maxFlow=null;
		vm.statusData = [
			{value:1,text:$translate.instant('common.ordinary')},
			{value:0,text:$translate.instant('common.pause')}];
		vm.serlfSpData = [
			{value:0,text:$translate.instant('common.no')},
			{value:1,text:$translate.instant('common.ok')}];
		vm.statusCode="";
		vm.statusMessage="";
	}
	function edit(bean) {
		reloadData();
		vm.modelTitle = $translate.instant('spInfo.edit');
		vm.readonlyID = true;
		vm.bean = bean;
		vm.statusData = [
			{value:1,text:$translate.instant('common.ordinary')},
			{value:0,text:$translate.instant('common.pause')}];
		vm.serlfSpData = [
			{value:0,text:$translate.instant('common.no')},
			{value:1,text:$translate.instant('common.ok')}];
		vm.statusCode="";
		vm.statusMessage="";
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			SpInfoService.createAlarmInfo(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating AlarmLevel.');
					});
			vm.reset();
		} else {
			SpInfoService.updateAlarmInfo(vm.bean, vm.bean.spInfoId).then(onSubmitSuccess,
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
					SpInfoService.deleteAlarmInfo(bean.spInfoId).then(reloadData,
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
		var spInfoId = vm.beanSer.spInfoId;
		var companyCode = vm.beanSer.companyCode;
		var companyName = vm.beanSer.companyName;
		var status = vm.beanSer.status;
		
		vm.dtInstance.changeData(getFromSource(apiUrl + '/spInfo?id=' + getValueForSelect(spInfoId)+
				"&companyCode="+getValueForSelect(companyCode)+"&companyName="+getValueForSelect(companyName)+getValueForSelect(status,"&status=")));
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