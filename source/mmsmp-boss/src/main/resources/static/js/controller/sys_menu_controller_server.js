'use strict';
var App = angular.module('menuModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize' , 'LocalStorageModule', 'ui.tree']);
configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);


function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, SysMenuService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.beanSer = {};
	vm.readonlyID = false;
	   //列的状态start
    vm.columnStatusData=[];
    //列的状态end
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/menu')).withOption('createdRow', createdRow);

	setDtOptionsServerSide(vm);

	vm.dtColumns = [
			DTColumnBuilder.newColumn('menuId').withTitle(
					$translate('sysMenu.menuId')).notVisible().notSortable(),
					DTColumnBuilder.newColumn('menuKeyWord').withTitle($translate('sysMenu.menuKeyWord')).notSortable(),
					DTColumnBuilder.newColumn('menuSortno').withTitle($translate('sysMenu.menuSortno')).notSortable(),
			DTColumnBuilder.newColumn('menuNameCn').withTitle($translate('sysMenu.menuNameCn')).notSortable(),
			DTColumnBuilder.newColumn('menuNameEn').withTitle($translate('sysMenu.menuNameEn')).notSortable(),
			DTColumnBuilder.newColumn('menuNameEs').withTitle($translate('sysMenu.menuNameEs')).notSortable(),
			DTColumnBuilder.newColumn('menuPic').withTitle($translate('sysMenu.menuPic')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('menuType').withTitle($translate('sysMenu.menuType')).notSortable().renderWith(menuType),
			DTColumnBuilder.newColumn('preMenuId').withTitle($translate('sysMenu.preMenuId')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('menuProces').withTitle($translate('sysMenu.menuProces')).notVisible().notSortable(),
			DTColumnBuilder.newColumn('remark').withTitle($translate('sysMenu.remark')).notVisible().renderWith(remarkDetail).notSortable(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width','10%').notSortable()
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
	
	
	vm.statusData = [
		{value:0,text:'停用'},
		{value:1,text:'启用'}];
	
	function status(data, type, full, meta){
		if(data=='0'){
			return $translate.instant('common.inactivate');
		}else if(data=='1'){
			return $translate.instant('common.activate');
		}
	}
	
	function menuType(data, type, full, meta){
		if(data=='1'){
			return '<span">'+$translate.instant('common.menu')+'</span>';
		}else if(data=='2'){
			return '<span">'+$translate.instant('common.function')+'</span>';
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
		vm.beans[data.menuId] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.menuId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.menuId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
		        + '</button>';
	}

	function addInit() {
		vm.modelTitle = $translate.instant('sysMenu.menuadd');
		vm.menuTypeData = [
			{value:1,text:$translate.instant('common.menu')},
			{value:2,text:$translate.instant('common.function')}];
		vm.readonlyID = false;
		vm.bean = {};
		vm.bean.menuId=null;
		vm.bean.menuKeyWord=null;
		vm.bean.menuSortno=null;
		vm.bean.menuNameCn=null;
		vm.bean.menuNameEn=null;
		vm.bean.menuNameEs=null;
		vm.bean.menuPic=null;
		vm.bean.menuType=null;
		vm.bean.preMenuId=null;
		vm.bean.menuProces=null;
		vm.bean.remark=null;
	vm.statusMessage="";
	vm.statusCode="";
	}
	function edit(bean) {
		reloadData();
//		vm.menuTypeData = [
//			{value:1,text:$translate.instant('common.menu')},
//			{value:2,text:$translate.instant('common.function')}];
		vm.modelTitle = $translate.instant('sysMenu.menuedit');
		vm.readonlyID = true;
		vm.bean = bean;
	vm.statusMessage="";
	vm.statusCode="";
	}

	function submit() {
		// if (vm.bean.menuId == null) {
		if (!vm.readonlyID) {
			$.fn.dataTable.ext.errMode = 'none';
			SysMenuService.createMenu(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating Menu.');
					});
			vm.reset();
		} else {
			SysMenuService.updateMenu(vm.bean, vm.bean.menuId).then(onSubmitSuccess,
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
	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('sysMenu.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					SysMenuService.deleteMenu(bean.menuId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating Menu.');
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
		var menuId = vm.beanSer.menuId;
		var menuKeyWord = vm.beanSer.menuKeyWord;
		var menuSortno = vm.beanSer.menuSortno;
		var menuNameCn = vm.beanSer.menuNameCn;
		var menuNameEn = vm.beanSer.menuNameEn;
		var menuNameEs = encodeURI(encodeURI(vm.beanSer.menuNameEs));
		var menuPic = vm.beanSer.menuPic;
		var menuType = vm.beanSer.menuType;
		var preMenuId = vm.beanSer.preMenuId;
		var menuProces = vm.beanSer.menuProces;
		var remark = vm.beanSer.remark;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/menu?id=' + getValueForSelect(menuId)+
				"&menuKeyWord="+getValueForSelect(menuKeyWord)+getValueForSelect(menuSortno,"&menuSortno=")+
				"&menuPic="+getValueForSelect(menuPic)+"&preMenuId="+getValueForSelect(preMenuId)+
				"&menuProces="+getValueForSelect(menuProces)+"&remark="+getValueForSelect(remark)+
				"&menuNameCn="+getValueForSelect(menuNameCn)+"&menuNameEn="+getValueForSelect(menuNameEn)+
				"&menuNameEs="+getValueForSelect(menuNameEs)+
				"&menuType="+getValueForSelect(menuType)));
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
