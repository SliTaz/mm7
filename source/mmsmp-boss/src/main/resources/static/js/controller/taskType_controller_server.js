'use strict';
var App = angular.module('taskTypeModule', [ 'datatables', 'datatables.fixedcolumns',,'datatables.columnfilter',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree' ]);
configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, TaskTypeService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/taskType')).withOption(
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
			DTColumnBuilder.newColumn('taskTypeId').withTitle(
					$translate('taskType.taskTypeId')).notSortable(),
			DTColumnBuilder.newColumn('name').withTitle($translate('taskType.name')).notSortable(),
			DTColumnBuilder.newColumn('handleClass').withTitle($translate('taskType.handleClass')).notSortable(),
			DTColumnBuilder.newColumn('status').withTitle($translate('taskType.status')).renderWith(status).notSortable(),
			DTColumnBuilder.newColumn('remark').withTitle($translate('taskType.remark')).notVisible().notSortable().renderWith(remarkDetail),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.activate =activate;
	vm.inactivate = inactivate;
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
		vm.beans[data.taskTypeId] = data;
		var actionsHtml_html='<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
		+ data.taskTypeId
		+ '\'])">'
		+ '   <i class="fa fa-edit"></i>'
		+ '</button>&nbsp;'
		+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
		+ data.taskTypeId
		+ '\'])">'
		+ '   <i class="fa fa-trash-o"></i>'
		+ '</button>&nbsp;'
		+ '<div class="btn-group">'
		+ '<button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown">'
		+ ' <span class="caret"></span>'
        +'</button>'
        + '<ul class="dropdown-menu" style="padding:0px;margin:0px;min-width:10px">'
    	+ '<li><button class="btn btn-success"  style="width:100px" ng-disabled="'+data.status+'==0" ng-click="ctrl.activate(ctrl.beans[\''+data.taskTypeId+'\'])">'+$translate.instant('common.activo')+'</button></li>'
		+ '<li><button class="btn bg-maroon"  style="width:100px" ng-disabled="'+data.status+'==1"ng-click="ctrl.inactivate(ctrl.beans[\''+data.taskTypeId+'\'])">'+$translate.instant('common.inactivo')+'</button></li>'
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
	
	

	function status(data, type, full, meta){
		if(data=='0'){
			return '<span style="color:green">'+$translate.instant('common.activate')+'</span>';
		}else if(data=='1'){
			return '<span style="color:red">'+$translate.instant('common.inactivate')+'</span>';
		}else{
			return '';
		}
	}

	function addInit() {
		$scope.status = [
			{id:0,name:$translate.instant('common.activate')},
			{id:1,name:$translate.instant('common.inactivate')}];
		vm.modelTitle = $translate.instant('taskInstance.taskTypeAdd');
		vm.readonlyID = false;
		vm.bean = {};
		vm.bean.name=null;
		vm.bean.handleClass=null;
		vm.bean.remark=null;
		vm.statusCode="";
		vm.statusMessage="";
		vm.disabled = true;
		vm.bean.status=0;
	}
	function edit(bean) {
		reloadData();
		$scope.status = [
			{id:0,name:$translate.instant('common.activate')},
			{id:1,name:$translate.instant('common.inactivate')}];
		vm.modelTitle = $translate.instant('taskInstance.taskTypeEdit');
		vm.readonlyID = true;
		vm.bean = bean;
		vm.statusCode="";
		vm.statusMessage="";
		vm.disabled = false;
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			TaskTypeService.createTaskType(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating taskType.');
					});
			vm.reset();
		} else {
			TaskTypeService.updateTaskType(vm.bean, vm.bean.taskTypeId).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating taskType.');
					});
		}
	}
	
	function onSubmitSuccess(data){
		vm.statusCode=data.statusCode;
		vm.statusMessage=data.statusMessage;
		reloadData();
	}
	//启用
	function activate(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('common.activate'),
			message : $translate.instant('common.activate.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					TaskTypeService.enable(bean.taskTypeId).then(reloadData,
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
	//停用
function inactivate(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('common.inactivate'),
			message : $translate.instant('common.inactivate.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					TaskTypeService.disable(bean.taskTypeId).then(reloadData,
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
						TaskTypeService.deleteTaskType(bean.taskTypeId).then(reloadData,
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
		var taskTypeId = vm.beanSer.taskTypeId;
		var name = vm.beanSer.name;
		var handleClass = vm.beanSer.handleClass;
		var status = vm.beanSer.status;
		var remark = vm.beanSer.remark;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/taskType?id=' + getValueForSelect(taskTypeId)+
				"&name="+getValueForSelect(name)+"&handleClass="+getValueForSelect(handleClass)+getValueForSelect(status,"&status=")+
				"&remark="+getValueForSelect(remark)));
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