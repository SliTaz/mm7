'use strict';
var App = angular.module('taskInstanceModule', [ 'datatables', 'datatables.fixedcolumns','datatables.columnfilter',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree' ]);
configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, TaskInstanceService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.taskInstanceData =[];
	vm.beanSer = {};
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/taskInstance')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtOptions.withColumnFilter({
		aoColumns : [ {
			type : 'text'
		}, {
			type : 'text'
		}, {
            type: 'text'
           
		},{
			type : 'text'
		} ]
	});
	vm.dtColumns = [
		DTColumnBuilder.newColumn('taskInstanceId').withTitle($translate('taskInstance.taskInstanceId')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('taskId').withTitle($translate('task.taskId')).notSortable(),
			DTColumnBuilder.newColumn('status').withTitle($translate('taskType.status')).renderWith(status).notSortable(),
			DTColumnBuilder.newColumn('progress').withTitle($translate('taskInstance.progress')).renderWith(progress).notSortable(),
			DTColumnBuilder.newColumn('message').withTitle($translate('taskInstance.message')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('remark').withTitle($translate('taskType.remark')).notVisible().notSortable().renderWith(remarkDetail),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.selFun = selFun;
	selFun();
	vm.unexecuted =unexecuted;
	vm.executed = executed;
	vm.success = success;
	vm.failure = failure;
	
	//表头start
	tableHandle();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
	function status(data, type, full, meta){
		if(data=='0'){
			return '<span style="color:green">'+$translate.instant('common.unexecuted')+'</span>';
		}else if(data=='1'){
			return '<span style="color:red">'+$translate.instant('common.executed')+'</span>';
		}else if(data=='3'){
			return '<span style="color:green">'+$translate.instant('common.success')+'</span>';
		}else if(data=='4'){
			return '<span style="color:red">'+$translate.instant('common.failure')+'</span>';
		}
		else{
			return '';
		}
	}
	function progress(data, type, full, meta){
		if(data=='1'){
			return '<span style="color:green">'+$translate.instant('common.onepercent')+'</span>';
		}else if(data=='50'){
			return '<span style="color:green">'+$translate.instant('common.fiftypercent')+'</span>';
		}else if(data=='100'){
			return '<span style="color:green">'+$translate.instant('common.hundredpercent')+'</span>';
		}else{
			return '';
		}
	}
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
		vm.beans[data.taskInstanceId] = data;
		var actionsHtml_html='<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
		+ data.taskInstanceId
		+ '\'])">'
		+ '   <i class="fa fa-edit"></i>'
		+ '</button>&nbsp;'
		+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
		+ data.taskInstanceId
		+ '\'])">'
		+ '   <i class="fa fa-trash-o"></i>'
		+ '</button>&nbsp;'
		+ '<div class="btn-group">'
		+ '<button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown">'
		+ ' <span class="caret"></span>'
        +'</button>'
        + '<ul class="dropdown-menu" style="padding:0px;margin:0px;min-width:10px">'
    	+ '<li><button class="btn btn-success"  style="width:100px" ng-disabled="'+data.status+'==0" ng-click="ctrl.unexecuted(ctrl.beans[\''+data.taskInstanceId+'\'])">'+$translate.instant('common.unexecuted')+'</button></li>'
		+ '<li><button class="btn bg-maroon"  style="width:100px" ng-disabled="'+data.status+'==1"ng-click="ctrl.executed(ctrl.beans[\''+data.taskInstanceId+'\'])">'+$translate.instant('common.executed')+'</button></li>'
		+ '<li><button class="btn bg-maroon"  style="width:100px" ng-disabled="'+data.status+'==3"ng-click="ctrl.success(ctrl.beans[\''+data.taskInstanceId+'\'])">'+$translate.instant('common.success')+'</button></li>'
		+ '<li><button class="btn bg-maroon"  style="width:100px" ng-disabled="'+data.status+'==4"ng-click="ctrl.failure(ctrl.beans[\''+data.taskInstanceId+'\'])">'+$translate.instant('common.failure')+'</button></li>'
        + '</ul>'
        + '</div>'

    	$(".dataTables_scrollBody").attr("style","position:relative;max-height:100%;width:100%");
		
		changeDataForHTML();//通过调用该函数。让拼装的html代码生效
		return actionsHtml_html;
		
	}
	function changeDataForHTML(){
		//alert("changeDataForHTML");
		$scope.$applyAsync();//当在angular的上下文之外修改了界面绑定的数据时，需要调用该函数，让页面的值进行改变。
	}
	function selFun() {
		TaskInstanceService.seltaskInstance()
        .then(
				       function(d) {
					        vm.taskInstanceData = d.body;
				       },
  					function(errResponse){
  						console.error('Error while fetching seltaskInstance');
  					}
		       );
	}
	function addInit() {
		$scope.status = [
			{id:0,name:$translate.instant('common.unexecuted')},
			{id:1,name:$translate.instant('common.executed')},
			{id:3,name:$translate.instant('common.success')},
			{id:4,name:$translate.instant('common.failure')}];
		$scope.progress = [
			{id:1,name:$translate.instant('common.onepercent')},
			{id:50,name:$translate.instant('common.fiftypercent')},
			{id:100,name:$translate.instant('common.hundredpercent')}];
		vm.modelTitle = $translate.instant('taskInstance.taskInstanceAdd');
		vm.readonlyID = false;
		vm.bean = {};
		vm.bean.progress=null;
		vm.bean.message=null;
		vm.bean.remark=null;
		vm.statusCode="";
		vm.disabled = true;
		vm.bean.status=0;
		vm.bean.progress=1;
		vm.statusMessage="";
	}
	function edit(bean) {
		reloadData();
		$scope.status = [
			{id:0,name:$translate.instant('common.unexecuted')},
			{id:1,name:$translate.instant('common.executed')},
			{id:3,name:$translate.instant('common.success')},
			{id:4,name:$translate.instant('common.failure')}];
		$scope.progress = [
			{id:1,name:$translate.instant('common.onepercent')},
			{id:50,name:$translate.instant('common.fiftypercent')},
			{id:100,name:$translate.instant('common.hundredpercent')}];
		vm.modelTitle = $translate.instant('taskInstance.taskInstanceEdit');
		vm.readonlyID = true;
		vm.bean = bean;
		vm.disabled = false;
		vm.statusCode="";
		vm.statusMessage="";
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			TaskInstanceService.createTaskInstance(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating taskInstance.');
					});
			vm.reset();
		} else {
			TaskInstanceService.updateTaskInstance(vm.bean, vm.bean.taskInstanceId).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating taskInstance.');
					});
		}
	}
	
	function onSubmitSuccess(data){
		vm.statusCode=data.statusCode;
		vm.statusMessage=data.statusMessage;
		reloadData();
	}

	//未执行
	function unexecuted(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('common.unexecuted'),
			message : $translate.instant('common.unexecuted.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					TaskInstanceService.unexecuted(bean.taskInstanceId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while activating Task.');
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
	//执行中
function executed(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('common.executed'),
			message : $translate.instant('common.executed.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					TaskInstanceService.executed(bean.taskInstanceId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while inactivating Task.');
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
//成功
function success(bean) {
	
	BootstrapDialog.show({
		title : $translate.instant('common.success'),
		message : $translate.instant('common.success.message'),
		buttons : [ {
			label : $translate.instant('common.yes'),
			cssClass : 'btn btn-danger model-tool-right',
			action : function(dialogItself) {
				TaskInstanceService.success(bean.taskInstanceId).then(reloadData,
						function(errResponse) {
 						handleAjaxError(errResponse);
					console.error('Error while inactivating Task.');
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
//失败
function failure(bean) {
	
	BootstrapDialog.show({
		title : $translate.instant('common.failure'),
		message : $translate.instant('common.failure.message'),
		buttons : [ {
			label : $translate.instant('common.yes'),
			cssClass : 'btn btn-danger model-tool-right',
			action : function(dialogItself) {
				TaskInstanceService.failure(bean.taskInstanceId).then(reloadData,
						function(errResponse) {
 						handleAjaxError(errResponse);
					console.error('Error while inactivating Task.');
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

	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('common.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					TaskInstanceService.deleteTaskInstanceId(bean.taskInstanceId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating taskType.');
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
	function reloadData() {
		var taskInstanceId = vm.beanSer.taskInstanceId;
		var taskId = vm.beanSer.taskId;
		var progress = vm.beanSer.progress;
		var message = vm.beanSer.message;
		var status = vm.beanSer.status;
		var remark = vm.beanSer.remark;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/taskInstance?id=' + getValueForSelect(taskInstanceId)+
				"&taskId="+getValueForSelect(taskId)+getValueForSelect(progress,"&progress=")+
				"&message="+getValueForSelect(message)+getValueForSelect(status,"&status=")+
				"&remark="+getValueForSelect(remark)));
		var resetPaging = false;
		var status = vm.beanSer.status;
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