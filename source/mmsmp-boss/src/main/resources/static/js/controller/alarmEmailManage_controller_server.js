'use strict';
var App = angular.module('alarmEmailManageModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree']);
configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);



function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, AlarmEmailManageService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.beanSer = {};
	vm.reloadData = reloadData;
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/alarmEmailManage')).withOption(
			'createdRow', createdRow);
	$("#loadDiv").hide();
	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('alarmEmailManageId').withTitle(
					$translate('alarmEmailManageId')).notSortable(),
			DTColumnBuilder.newColumn('name').withTitle($translate('bankInfo.name')).notSortable(),
			DTColumnBuilder.newColumn('recvPersonMail').withTitle($translate('recvPersonMail')).notSortable(),
			DTColumnBuilder.newColumn('ccPersonMail').withTitle($translate('ccPersonMail')).notSortable(),
			DTColumnBuilder.newColumn('bccPersonMail').withTitle($translate('bccPersonMail')).notSortable(),
			DTColumnBuilder.newColumn('remark').notVisible().withTitle($translate('bankInfo.remark')).renderWith(remarkDetail).notSortable(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	//表头start
	tableHandle();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
	//超长备注处理start
	function remarkDetail(data, type, full, meta){
		if(data!=null){
			return '<span class="spanFun" style=" display: inline-block;width: 200px;white-space:nowrap;word-break:keep-all;overflow:hidden;text-overflow:ellipsis;">'+data+'</span>'
		}else{
			return '';
		}
	}
	//超长备注处理end
	function actionsHtml(data, type, full, meta) {
		vm.beans[data.alarmEmailManageId] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.alarmEmailManageId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.alarmEmailManageId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>';
	}

	function addInit() {
		vm.modelTitle = $translate.instant('AlarmEmailManage.Add');
		vm.readonlyID = false;
		vm.bean = {};
		vm.bean.name=null;
		vm.bean.remark=null;
		vm.bean.recvPersonMail=null;
		vm.bean.ccPersonMail=null;
		vm.bean.bccPersonMail=null;
		vm.statusCode="";
		vm.statusMessage="";
	}
	function edit(bean) {
		reloadData();
		vm.modelTitle = $translate.instant('AlarmEmailManage.Edit');
		vm.readonlyID = true;
		vm.bean = bean;
		vm.statusCode="";
		vm.statusMessage="";
	}

	function submit() {
		
		// if (vm.bean.userId == null) {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			AlarmEmailManageService.createAlarmEmailManage(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating BankInfo.');
					});
			vm.reset();
		} else {
			AlarmEmailManageService.updateAlarmEmailManage(vm.bean, vm.bean.alarmEmailManageId).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating BankInfo.');
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
					AlarmEmailManageService.deleteAlarmEmailManage(bean.alarmEmailManageId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating BankInfo.');
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
		var alarmEmailManageId = vm.beanSer.alarmEmailManageId;
		var name = vm.beanSer.name;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/alarmEmailManage?id=' + getValueForSelect(alarmEmailManageId)+
				"&name="+getValueForSelect(name)));
		var resetPaging = false;
		vm.dtInstance.reloadData(callback, resetPaging);
	}
	function callback(json) {
		//console.log(json);
	}
	function createdRow(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}
	vm.reset = function() {
		addInit();
	};
}