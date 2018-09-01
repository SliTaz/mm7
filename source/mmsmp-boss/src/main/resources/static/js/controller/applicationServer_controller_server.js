'use strict';
var App = angular.module('applicationServerModule', [ 'datatables', 'datatables.fixedcolumns',,'datatables.columnfilter',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree' ]);
configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);
//自定义格式化日期格式指令(date-format) start
//用法：<input type="text" class="form-control" id="billDate" placeholder="日期" ng-model="ctrl.bean.billDate" date-format>
App.directive('dateFormat', ['$filter',function($filter) {  
var dateFilter = $filter('date');  
return {  
require: 'ngModel',  
link: function(scope, elm, attrs, ctrl) {  

function formatter(value) {  
return dateFilter(value, 'yyyy-MM-dd HH:mm:ss'); //format  
} 
ctrl.$formatters.push(formatter);  

}  
};  
}]);
//自定义格式化日期格式指令(date-format) end


$(function() {
	$("#lastTime").datetimepicker({
language: "zh-CN",
autoclose: true,             
clearBtn: true,//清除按钮
todayBtn: 'linked',
//minView: 'month',
format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	});
});
function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, ApplicationServerService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.beanSer = {};
    //列的状态start
    vm.columnStatusData=[];
    //列的状态end
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/applicationServer')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('applicationServerCode').withTitle($translate('applicationServerCode')).notSortable(),
			DTColumnBuilder.newColumn('ipAddr').withTitle($translate('ipAddr')).notSortable(),
			DTColumnBuilder.newColumn('name').withTitle($translate('name')).notSortable(),
			DTColumnBuilder.newColumn('lastTime').withTitle($translate('lastTime')).notSortable().renderWith(timeRender),
			DTColumnBuilder.newColumn('remark').withTitle($translate('taskType.remark')).notVisible().notSortable().renderWith(remarkDetail),
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
	$("#loadDiv").hide();
	
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
		vm.beans[data.applicationServerCode] = data;
		var actionsHtml_html='<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
		+ data.applicationServerCode
		+ '\'])">'
		+ '   <i class="fa fa-edit"></i>'
		+ '</button>&nbsp;'
		+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
		+ data.applicationServerCode
		+ '\'])">'
		+ '   <i class="fa fa-trash-o"></i>'
		+ '</button>';

		
		changeDataForHTML();//通过调用该函数。让拼装的html代码生效
		return actionsHtml_html;
		
	}
	function changeDataForHTML(){
		//alert("changeDataForHTML");
		$scope.$applyAsync();//当在angular的上下文之外修改了界面绑定的数据时，需要调用该函数，让页面的值进行改变。
	}
	
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

	function addInit() {
		vm.modelTitle = $translate.instant('ApplicationServer.Add');
		vm.readonlyID = false;
		vm.bean = {};
		vm.bean.name=null;
		vm.bean.remark=null;
		vm.statusCode="";
		vm.statusMessage="";
		vm.disabled = true;
	}
	function edit(bean) {
		reloadData();
		vm.modelTitle = $translate.instant('ApplicationServer.Edit');
		vm.readonlyID = true;
		vm.bean = bean;
		vm.statusCode="";
		vm.statusMessage="";
		vm.disabled = false;
	}

	function submit() {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			ApplicationServerService.create(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating taskType.');
					});
			vm.reset();
		} else {
			ApplicationServerService.update(vm.bean, vm.bean.applicationServerCode).then(onSubmitSuccess,
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
					ApplicationServerService.deleteAPP(bean.applicationServerCode).then(reloadData,
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
		var name = vm.beanSer.name;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/applicationServer?applicationServerCode=' + getValueForSelect(applicationServerCode)+
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