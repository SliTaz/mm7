'use strict';
var App = angular.module('taskModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize' , 'LocalStorageModule', 'ui.tree']);
configAppModule(App);
App.controller('ClientSideCtrl', ClientSideCtrl);


function ClientSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, TaskService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/taskq')).withOption('createdRow', createdRow);

	setDtOptionsClientSide(vm);

	vm.dtOptions.withColumnFilter({
		aoColumns : [ {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		}, {
			type : 'text'
		},{
			type : 'text'
		} ]
	});
	vm.dtColumns = [
		    DTColumnBuilder.newColumn('jobGroup').withTitle($translate('task.jobGroup')),
		    DTColumnBuilder.newColumn('jobName').withTitle($translate('task.jobName')),
		    DTColumnBuilder.newColumn('cronExpression').withTitle($translate('task.cronExpression')),
		    DTColumnBuilder.newColumn('nextFireTime').withTitle($translate('task.nextFireTime')).renderWith(timeRender),
		    DTColumnBuilder.newColumn('finalFireTime').withTitle($translate('task.finalFireTime')).renderWith(timeRender).notVisible(),
		    DTColumnBuilder.newColumn('startTime').withTitle($translate('task.startTime')).renderWith(timeRender),
		    DTColumnBuilder.newColumn('endTime').withTitle($translate('task.endTime')).renderWith(timeRender).notVisible(),
		    DTColumnBuilder.newColumn('previousFireTime').withTitle($translate('task.previousFireTime')).renderWith(timeRender),
		    DTColumnBuilder.newColumn('jobStatus').withTitle($translate('task.jobStatus')),
		    DTColumnBuilder.newColumn('createTime').withTitle($translate('task.createTime')).notVisible(),
			DTColumnBuilder.newColumn('jobDescription').withTitle($translate('task.jobDescription')).notVisible(),
			DTColumnBuilder.newColumn('timeZone').withTitle($translate('task.timeZone')).notVisible(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width','10%').notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.deleteBean = deleteBean;
	vm.submit = submit;
	vm.pause = pause;
	vm.resume = resume;
	//表头start
	tableHandle();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
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
		var key=data.jobName+"_"+data.jobGroup;
		vm.beans[key] = data;
		var actionsHtml_html= '<button class="btn btn-warning" title="'+$translate.instant('common.edit')+'" data-toggle="modal" data-target="#myModal" ng-click="ctrl.edit(\''+data.jobName+'\',\''+data.jobGroup+'\')">'
		        + '<i class="fa fa-edit"></i>'
		        + '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(\''+data.jobName+'\',\''+data.jobGroup+'\')">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>&nbsp;'
				+'<div class="btn-group">'
				+ '<button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">'
				+ '<span class="caret"></span>'
				+ '</button>'
	         	+ '<ul class="dropdown-menu" style="padding:0px;margin-left:-50px;min-width:10px">'
	        	+ '<li><button class="btn btn-success" style="width:80px" ng-disabled="pause" ng-click="ctrl.pause(\''+data.jobName+'\',\''+data.jobGroup+'\')">'+$translate.instant('common.pause')+'</button></li>'
	        	+ '<li><button class="btn bg-maroon" style="width:80px" ng-disabled="resume" ng-click="ctrl.resume(\''+data.jobName+'\',\''+data.jobGroup+'\')">'+$translate.instant('common.resume')+'</button></li>'
            	+ '</ul>'
	            + '</div>';
		$(".dataTables_scrollBody").attr("style","position:relative;max-height:100%;width:100%");
		
		changeDataForHTML();//通过调用该函数。让拼装的html代码生效
		return actionsHtml_html;
		
	}
	function changeDataForHTML(){
		//alert("changeDataForHTML");
		$scope.$applyAsync();//当在angular的上下文之外修改了界面绑定的数据时，需要调用该函数，让页面的值进行改变。
	}
	//暂停
function pause(key1,key2){
	var key=key1+"_"+key2;
	var bean=vm.beans[key];
	BootstrapDialog.show({
		title : $translate.instant('common.pause'),
		message : $translate.instant('common.pause.message'),
		buttons : [ {
			label : $translate.instant('common.yes'),
			cssClass : 'btn btn-danger model-tool-right',
			action : function(dialogItself) {
				TaskService.pause(bean.jobName,bean.jobGroup).then(reloadData,
				function(errResponse) {
 						handleAjaxError(errResponse);
					console.error('Error while activating ConsumerUser.');
				});
				dialogItself.close();
			}

		}, {
			label :  $translate.instant('common.cancel'),
			cssClass : 'btn btn-default model-tool-left',
			action : function(dialogItself) {
				dialogItself.close();
			}
		} ]
	});
}
//恢复
function resume(key1,key2){
	var key=key1+"_"+key2;
	var bean=vm.beans[key];
	BootstrapDialog.show({
		title : $translate.instant('common.resume'),
		message : $translate.instant('common.resume.message'),
		buttons : [ {
			label : $translate.instant('common.yes'),
			cssClass : 'btn btn-danger model-tool-right',
			action : function(dialogItself) {
				TaskService.resume(bean.jobName,bean.jobGroup).then(reloadData,
				function(errResponse) {
 						handleAjaxError(errResponse);
					console.error('Error while activating ConsumerUser.');
				});
				dialogItself.close();
			}

		}, {
			label :  $translate.instant('common.cancel'),
			cssClass : 'btn btn-default model-tool-left',
			action : function(dialogItself) {
				dialogItself.close();
			}
		} ]
	});
}
	function addInit() {
		vm.modelTitle = $translate.instant('task.add');
		vm.readonlyID = false;
		vm.bean = {};
		vm.bean.cronExpression=null;
		vm.bean.jobDescription=null;
		vm.disabled = false;
	    vm.statusMessage="";
	    vm.statusCode="";
	}
	function edit(key1,key2) {
		reloadData();
		vm.modelTitle = $translate.instant('task.edit');
		var key=key1+"_"+key2;
		var bean=vm.beans[key];
		vm.readonlyID = true;
		vm.disabled = true;
		vm.bean = bean;
	    vm.statusMessage="";
	    vm.statusCode="";
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			TaskService.createTask(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating Menu.');
					});
			vm.reset();
		} else {
			TaskService.updateTask(vm.bean, vm.bean.jobGroup).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating Menu.');
					});
		}
	}
   function onSubmitSuccess(data){
	    vm.statusCode=data.statusCode;
		vm.statusMessage=data.statusMessage;
		reloadData();
	}

    function deleteBean(key1,key2) {
		var key=key1+"_"+key2;
		var bean=vm.beans[key];
		BootstrapDialog.show({
			title : $translate.instant('common.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					TaskService.deleteTask(bean.jobName,bean.jobGroup).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating AlarmLevelType.');
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
	
	function reloadData() {
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
