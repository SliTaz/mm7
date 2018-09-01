'use strict';
var App = angular.module('systemConfigModule', [ 'datatables', 'datatables.fixedcolumns',,'datatables.columnfilter',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree' ]);
configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, SystemConfigService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.applicationData =[];
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/systemConfig')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
		    DTColumnBuilder.newColumn('name').withTitle($translate('applicationName')).notSortable(),
			DTColumnBuilder.newColumn('code').withTitle($translate('code')).notSortable(),
			DTColumnBuilder.newColumn('value').withTitle($translate('value')).notSortable().renderWith(valueAll),
			DTColumnBuilder.newColumn('defaultValue').withTitle($translate('defaultValue')).notSortable(),
			DTColumnBuilder.newColumn('remark').withTitle($translate('taskType.remark')).notVisible().notSortable().renderWith(remarkDetail),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.importInit = importInit;
	vm.selFun = selFun;
	selFun();
	//双主键修改
	vm.doubleEdit = doubleEdit;
	//双主键删除
	vm.doubleDelete = doubleDelete;
	vm.doubledefault = doubledefault;
	
	//表头start
	tableHandle();
	//表头end
	initltCommon(vm,localStorageService,topleftService);
	$("#loadDiv").hide();
	
	//超长备注处理start
	function remarkDetail(data, type, full, meta){
		if(data!=null){
			return '<span class="spanFun" title="'+data+'"  style=" display: inline-block;width: 200px;white-space:nowrap;word-break:keep-all;overflow:hidden;text-overflow:ellipsis;">'+data+'</span>'
		}else{
			return '';
		}
	}
	//超长备注处理start
	function valueAll(data, type, full, meta){
		if(data!=null){
			return '<span class="spanFunValue" title="'+data+'" style=" display: inline-block;width: 200px;white-space:nowrap;word-break:keep-all;overflow:hidden;text-overflow:ellipsis;">'+data+'</span>'
		}else{
			return '';
		}
	}
	//超长备注处理end
	function actionsHtml(data, type, full, meta) {
		var key=data.applicationServerCode+"_"+data.code;
		var name=data.name;
		vm.beans[key] = data;
		return '<button class="btn btn-warning" title="'+$translate.instant('common.edit')+'" data-toggle="modal" data-target="#myModal" ng-click="ctrl.doubleEdit(\''+data.applicationServerCode+'\',\''+data.code+'\')">'
		+ '   <i class="fa fa-edit"></i>'
		+ '</button>&nbsp;'
		+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.doubleDelete(\''+data.applicationServerCode+'\',\''+data.code+'\')">'
		+ '   <i class="fa fa-trash-o"></i>'
		+ '</button>&nbsp;'
		+ '<button class="btn btn-info" title="'+$translate.instant('common.default')+'"  ng-click="ctrl.doubledefault(\''+data.applicationServerCode+'\',\''+data.code+'\',\''+data.name+'\')">'
		+ '   <i class="fa fa-reply"></i>'
		+ '</button>';

		
		changeDataForHTML();//通过调用该函数。让拼装的html代码生效
		return actionsHtml_html;
		
	}
	function changeDataForHTML(){
		//alert("changeDataForHTML");
		$scope.$applyAsync();//当在angular的上下文之外修改了界面绑定的数据时，需要调用该函数，让页面的值进行改变。
	}

	function selFun() {
		SystemConfigService.selapplication()
        .then(
				       function(d) {
					        vm.applicationData = d.body;
				       },
  					function(errResponse){
  						console.error('Error while fetching selBankInfo');
  					}
		       );
	}
	//bank下拉框
	function selectBank(){
		$("#selectCouponFun").select2();
		$("#applicationServerCode").select2();
		$("#myModal").attr("tabindex","");
		$.fn.modal.Constructor.prototype.enforceFocus = function () {};
    	//解决 select2在模态框使用，模糊输入框无效

	}
	//初始select2
	$("#queryBtn").click(function(){
		selectBank();
	})
	
	
	function importInit(){
		window.open("systemConfigKey.html");
	}
	//双主键删除
	function doubleDelete(key1,key2) {
		var key=key1+"_"+key2;
		var bean=vm.beans[key];
		BootstrapDialog.show({
			title : $translate.instant('common.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					SystemConfigService.deleteSys(bean.applicationServerCode,bean.code).then(reloadData,
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
	
	//双主键修改
	function doubleEdit(key1,key2) {
		selectBank();
		reloadData();
		var key=key1+"_"+key2;
		var bean=vm.beans[key];
		vm.modelTitle = $translate.instant('systemConfig.Edit');
		vm.readonlyID = true;
		vm.disabled = true;
		vm.statusDis = false;
		 $("#applicationServerCode").val(key1).select2();
		vm.bean = bean;
		vm.statusCode="";
		vm.statusMessage="";
	}
	
	//默认修改
	function doubledefault(key1,key2,name) {
//		var key=key1+"_"+key2;
//		var bean=vm.beans[key];
//		vm.bean = bean;
//		SystemConfigService.doubledefault(vm.bean, vm.bean.applicationServerCode, vm.bean.code).then(onSubmitSuccess,
//				function(errResponse) {
 						handleAjaxError(errResponse);
//					console.error('Error while updating taskType.');
//				});
//	}
	BootstrapDialog.show({
		title : $translate.instant('common.doubledefault'),
		message : "<h4>"+$translate.instant('common.doubledefault.message')+"</h4></br>"+
		          $translate.instant('applicationName')+":&nbsp&nbsp&nbsp"+name+"</br>"+
		          $translate.instant('code')+":&nbsp&nbsp&nbsp"+key2,
		buttons : [ {
			label : $translate.instant('common.yes'),
			cssClass : 'btn btn-danger model-tool-right',
			action : function(dialogItself) {
				var key=key1+"_"+key2;
				var name=name;
				var bean=vm.beans[key];
				vm.bean = bean;
				SystemConfigService.doubledefault(vm.bean, vm.bean.applicationServerCode, vm.bean.code).then(onSubmitSuccess,
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
	function addInit() {
		selectBank();
		vm.modelTitle = $translate.instant('systemConfig.Add');
		vm.readonlyID = false;
		vm.bean = {};
		vm.bean.name=null;
		vm.bean.applicationServerCode=null;
		$("#applicationServerCode").val("").select2();
		vm.bean.remark=null;
		vm.statusCode="";
		vm.statusMessage="";
		vm.disabled = false;
	}
	function edit(bean) {
		vm.modelTitle = $translate.instant('systemConfig.Edit');
		vm.readonlyID = true;
		vm.bean = bean;
		vm.statusCode="";
		vm.statusMessage="";
		vm.disabled = false;
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			SystemConfigService.create(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating taskType.');
					});
			vm.reset();
		} else {
			SystemConfigService.update(vm.bean, vm.bean.applicationServerCode, vm.bean.code).then(onSubmitSuccess,
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
	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('common.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					SystemConfigService.deleteSys(bean.applicationServerCode,bean.code).then(reloadData,
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
	//超长备注处理start
	$("#example").on("click", ".spanFunValue", function(){
		var remarkDetail = $(this).text();
		BootstrapDialog.show({
			title: $translate.instant('value'),
			message: function(dialog) {
				var $message=$(
						'<span id="value_detail" style="word-break:normal; width:auto; display:block; white-space:pre-wrap;word-wrap:break-word ;overflow: hidden"'+
					    '</span>'
				);
                return $message;
            },
            onshown: function(dialogRef){//打开完成
            	$("#value_detail").text(remarkDetail);
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
		var applicationServerCode = vm.beanSer.applicationServerCode;
		var code = vm.beanSer.code;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/systemConfig?applicationServerCode=' + getValueForSelect(applicationServerCode)+
				"&code="+getValueForSelect(code)));
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