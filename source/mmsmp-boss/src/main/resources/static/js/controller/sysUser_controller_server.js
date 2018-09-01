'use strict';
var App = angular.module('sysUserModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree' ]);

configAppModule(App);

App.controller('ServerSideCtrl', ServerSideCtrl);


$(window).on('load', function () {
    $('#userIds').selectpicker({
        'selectedText': 'cat'
    });
});

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, SysUserService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.reloadData = reloadData;
	vm.selectData=[];
    vm.appData=[];
    //列的状态start
    vm.columnStatusData=[];
    var pwdFirst;
    //列的状态end
	vm.beanSer = {};
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/sysUser')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('userId').withTitle(
					$translate('user.userId')).notSortable(),
			
					DTColumnBuilder.newColumn('userName').withTitle($translate('user.userName')).notSortable(),
					DTColumnBuilder.newColumn('status').withTitle($translate('user.status')).renderWith(status).notSortable(),
					DTColumnBuilder.newColumn('lockedPassword').withTitle($translate('common.loginLockedStatus')).renderWith(lockStatus).notSortable(),
					
					DTColumnBuilder.newColumn('isFirstLogin').withTitle($translate('user.isFirstLogin')).notVisible().renderWith(isFirstLogin).notSortable(),
					DTColumnBuilder.newColumn('createTime').withTitle($translate('user.createTime')).notVisible().notSortable().renderWith(timeRender),
					
					DTColumnBuilder.newColumn('remark').notVisible().withTitle($translate('user.remark')).renderWith(remarkDetail).notSortable(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).withOption('width','15%').notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.activate =activate;
	vm.lockedLoginPassword = lockedLoginPassword;
	vm.inactivate = inactivate;
	vm.passwords = passwords;
	vm.selData = selData;
	vm.getPwd=getPwd;
	selData();
	//表头start
	tableHandle();
	//表头end
	/*vm.statusData = [
		{value:0,text:'启用'},
		{value:1,text:'停用'}];

	vm.isFirstLoginData = [
		{value:1,text:'是'},
		{value:2,text:'否'}];
	
	$scope.status = [
		{id:0,name:'启用'},
		{id:1,name:'停用'}];
	$scope.isFirstLogin = [
		{id:1,name:'是'},
	    {id:2,name:'否'}];
	*/

	
	function status(data, type, full, meta){
		if(data=='0'){
			return '<span style="color:green">'+$translate.instant('common.activate')+'</span>';
		}else if(data=='1'){
			return '<span style="color:red">'+$translate.instant('common.inactivate')+'</span>';
		}else{
			return '';
		}
	}

	function isFirstLogin(data, type, full, meta){
		if(data=='1'){
			return $translate.instant('common.ok');
		}else if(data=='2'){
			return $translate.instant('common.no');
		}else{
			return '';
		}
	}
	
	
	function lockStatus(data, type, full, meta){
		if(data==false){
			return '<span style="color:green">'+$translate.instant('common.unlocked')+'</span>';
		}else if(data==true){
			return '<span style="color:red">'+$translate.instant('common.islocked')+'</span>';
		}else{
			return '';
		}
	}

	initltCommon(vm,localStorageService,topleftService);
	$("#loadDiv").hide();
	
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
		vm.beans[data.userId] = data;
		var actionsHtml_html='<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
		+ data.userId
		+ '\'])">'
		+ '   <i class="fa fa-edit"></i>'
		+ '</button>&nbsp;'
		+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
		+ data.userId
		+ '\'])">'
		+ '   <i class="fa fa-trash-o"></i>'
		+ '</button>&nbsp;'
		+ '<div class="btn-group">'
		+ '<button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown">'
		+ ' <span class="caret"></span>'
        +'</button>'
        + '<ul class="dropdown-menu" style="padding:0px;margin-left:-180px;min-width:10px">'
     	+ '<li><button class="btn btn-success" style="width:220px"  ng-disabled="'+data.status+'==0" ng-click="ctrl.activate(ctrl.beans[\''+data.userId+'\'])">'+$translate.instant('common.activo')+'</button></li>'
		+ '<li><button class="btn bg-maroon" style="width:220px"  ng-disabled="'+data.status+'==1"ng-click="ctrl.inactivate(ctrl.beans[\''+data.userId+'\'])">'+$translate.instant('common.inactivo')+'</button></li>'
		+ '<li><button class="btn bg-maroon" style="width:220px"  ng-click="ctrl.passwords(ctrl.beans[\''+data.userId+'\'])">'+$translate.instant('common.password')+'</button></li>'
		+ '<li><button class="btn bg-maroon"  style="width:220px" ng-click="ctrl.lockedLoginPassword(ctrl.beans[\''+data.userId+'\'])">'+$translate.instant('user.unlockedLoginPassword')+'</button></li>'
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
	function selData() {
		SysUserService.fetchAllSysRoles()
        .then(
				       function(d) {
					        vm.selectData = d.body;
				       },
  					function(errResponse){
  						console.error('Error while fetching fetchAllSysRoles');
  					}
		       );
	}
	
	function addInit() {
		vm.statusData = [
			{value:0,text:$translate.instant('common.activate')},
			{value:1,text:$translate.instant('common.inactivate')}];

		vm.isFirstLoginData = [
			{value:1,text:$translate.instant('common.ok')},
			{value:2,text:$translate.instant('common.no')}];
		
		$scope.status = [
			{id:0,name:$translate.instant('common.activate')},
			{id:1,name:$translate.instant('common.inactivate')}];
		$scope.isFirstLogin = [
			{id:1,name:$translate.instant('common.ok')},
		    {id:2,name:$translate.instant('common.no')}];
		$('.selectpicker').selectpicker('val', '');
		$('.selectpicker').selectpicker('refresh');
		vm.modelTitle =$translate.instant('user.useradd');
		vm.readonlyID = false;
		vm.disabled = true;
		vm.bean = {};
		vm.bean.status=0;
	   
		vm.bean.isFirstLogin=1;
		vm.statusCode="";
		vm.statusMessage="";
		
	     $("#pwd_div").show();
		
		

	}
	
	function edit(bean) {
		reloadData();
		bean.password="111111"
		$("#pwd_div").hide();
		
		vm.statusData = [
			{value:0,text:$translate.instant('common.activate')},
			{value:1,text:$translate.instant('common.inactivate')}];

		vm.isFirstLoginData = [
			{value:1,text:$translate.instant('common.ok')},
			{value:2,text:$translate.instant('common.no')}];
		
		$scope.status = [
			{id:0,name:$translate.instant('common.activate')},
			{id:1,name:$translate.instant('common.inactivate')}];
		$scope.isFirstLogin = [
			{id:1,name:$translate.instant('common.ok')},
		    {id:2,name:$translate.instant('common.no')}];
		$('.selectpicker').selectpicker('val', '');
		$('.selectpicker').selectpicker('refresh');
		SysUserService.selectByUserId(bean.userId)
		 .then(
			       function(d) {
				      vm.appData = d.body;
				      var str = "";
	                  if(vm.appData!=null){
	                  for (var i = 0; i < vm.appData.length; i++){
	                	  var a =vm.appData[i].roleId;
	                	  str += a + ",";
	                  }
	                  }
					  var arr=str.split(',');
					  $('#userIds').selectpicker('val', arr);
		vm.modelTitle =$translate.instant('user.useredit');
		vm.readonlyID = true;
		vm.disabled = false;
		bean.roleId =arr;
		vm.bean = bean;
		
		vm.statusCode="";
		vm.statusMessage="";
			       },
					function(errResponse){
						console.error('Error while fetching appGateways');
					}
		       );
		}

	function submit() {
		// if (vm.bean.userId == null) {
		if (!vm.readonlyID) {
			SysUserService.createSysUser(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating SysUser.');
					});
			vm.reset();
		} else {
			SysUserService.updateSysUser(vm.bean, vm.bean.userId).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating SysUser.');
					});
		}
	}
function onSubmitSuccess(data){
	vm.statusCode=data.statusCode;
		vm.statusMessage=data.statusMessage;
		reloadData();
	}
//登陆密码
function getPwd(){
	var pwdFirst='';
	  var arr = [];
    for (var i=0;i<1000;i++) {
        var n = Math.random().toString(36).substr(2,6);
        var reg = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
        if (!reg.test(n))
        {  
        	 
         }else{  
        	 $("#password").val(n);
           
             vm.bean.password =n;
          	changeDataForHTML();
          	break;
         }
        arr.push(n);
    } 
    //vm.bean.password =n;
    	pwdFirst=n;
    	return pwdFirst;
    
  
}
//初始登陆密码
function passwords(bean) {

	 bean.password =getPwd();
	BootstrapDialog.show({
		title : $translate.instant('common.password'),
		message : $translate.instant('common.password.message')+bean.password,
		buttons : [ {
			label : $translate.instant('common.yes'),
			cssClass : 'btn btn-danger model-tool-right',
			action : function(dialogItself) {
				SysUserService.resetDefaultPassword(bean,bean.userId).then(reloadData,
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
//启用
function activate(bean) {
	
	BootstrapDialog.show({
		title : $translate.instant('common.activate'),
		message : $translate.instant('common.activate.message'),
		buttons : [ {
			label : $translate.instant('common.yes'),
			cssClass : 'btn btn-danger model-tool-right',
			action : function(dialogItself) {
				SysUserService.enable(bean.userId).then(reloadData,
				function(errResponse) {
 						handleAjaxError(errResponse);
					console.error('Error while activating SysUser.');
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

//解锁登陆密码
function lockedLoginPassword(bean) {
	
	BootstrapDialog.show({
		title : $translate.instant('user.unlockedLoginPassword'),
		message : $translate.instant('common.lockedLoginPassword.message'),
		buttons : [ {
			label : $translate.instant('common.yes'),
			cssClass : 'btn btn-danger model-tool-right',
			action : function(dialogItself) {
				SysUserService.lockedLoginPassword(bean.userId,bean.userName).then(reloadData,
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
	function inactivate(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('common.inactivate'),
			message : $translate.instant('common.inactivate.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					SysUserService.disable(bean.userId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while inactivating SysUser.');
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
					SysUserService.deleteSysUser(bean.userId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating SysUser.');
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
		var userId = vm.beanSer.userId;
		var userName = encodeURI(encodeURI(vm.beanSer.userName));
		var status = vm.beanSer.status;
		var remark = vm.beanSer.remark;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/sysUser?id=' + getValueForSelect(userId)+
				"&userName="+getValueForSelect(userName)+getValueForSelect(status,"&status=")+
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