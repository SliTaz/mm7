'use strict';
var App = angular.module('roleModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree'  ]);

configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);


function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, RoleService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.beanSer = {};
	   //列的状态start
    vm.columnStatusData=[];
    //列的状态end
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/role')).withOption(
			'createdRow', createdRow);

	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('roleId').withTitle(
					$translate('sysRole.roleId')).notSortable(),
					DTColumnBuilder.newColumn('code').withTitle($translate('role.code')).notSortable(),
			DTColumnBuilder.newColumn('name').withTitle($translate('sysRole.name')).notSortable(),
			DTColumnBuilder.newColumn('remark').notVisible().withTitle($translate('sysRole.remark')).renderWith(remarkDetail).notSortable(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
					.renderWith(actionsHtml) ];

	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
    vm.permissio =permissio;	
    vm.success_color="green";
    vm.error_color="red";
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
		vm.beans[data.roleId] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.roleId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.roleId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>&nbsp;'
		        + '<button class="btn btn-menu bg-green" ng-click="ctrl.permissio(ctrl.beans[\''
		        + data.roleId
		        + '\'])">'
		        + '   <i class="fa fa-sun-o"></i>'								
		        + '</button>';
	}
	
	function permissio(bean){
		//alert("5 roleId:"+bean.roleId);
		
		RoleService.permissioRole(bean.roleId).then(permissioSuccess,
				function(errResponse) {
 						handleAjaxError(errResponse);
					console.error('Error permissio.');
				});
		
	}
	
	function permissioSuccess(data){
		//console.info(data);
		//alert("2 ok:"+data.roleId+";"+data.resources);
		var roleId_value=data.roleId;
		var zTreeNodes_list=data.zTreeNodes;
		
		var url="./permissioRole.html";
		BootstrapDialog.show({
			title: $translate.instant('common.selectMenu'),
			message: function(dialog) {
                var $message = $('<div></div>').load(url);
        
                return $message;
            },
	        data:{
                'roleId': roleId_value
            },
            onshown: function(dialogRef){//打开完成
                //alert('Dialog is popped up.');
            	//alert($("#m_role_id").val());
            	//alert(roleId_value);
                $("#m_role_id").val(roleId_value);
            	//alert($("#m_role_id").val());
            	
            	
            	var setting = {
            	        check: {
            	            enable: true
            	        },
            	        data: {
            	            simpleData: {
            	                enable:true,
            	                idKey: "id",
            	                pIdKey: "pId",
            	                rootPId: "-1"
            	            }
            	        }
            	    };
            	var zNodes = [];
            	//alert(zTreeNodes_list.length);
            	for(var i=0;i<zTreeNodes_list.length;i++){
            		var zTreeNode=zTreeNodes_list[i];
            		
            		var menuNameArr;
            		var menuName;
            		if(zTreeNode.name){
            			menuNameArr= zTreeNode.name.split(",");
            		}
            		//start
            		var naLan = navigator.language;
					if (naLan == undefined || naLan == "") {
						naLan = navigator.browserLanguage;
					}
					
					if(naLan=='en'){
						menuName = menuNameArr[1];
					}else if(naLan.indexOf("en-") >= 0){
						naLan = "en";
						menuName = menuNameArr[1];
					}else if(naLan=='es'){
						menuName = menuNameArr[2];
					}else if(naLan.indexOf("es-") >= 0){
						naLan = "es";
						menuName = menuNameArr[2];
					}else if(naLan=='zh'){
						menuName = menuNameArr[0];
					}else if(naLan.indexOf("zh") >= 0){
						naLan = "zh";
						menuName = menuNameArr[0];
					}else{
						naLan = "es";
						menuName = menuNameArr[2];
					}
            		zNodes.push({ id: zTreeNode.id, pId: zTreeNode.pId, name: menuName,checked: zTreeNode.checked});
            	}
            	
            	var t = $("#tree");
            	t = $.fn.zTree.init(t, setting, zNodes);  
            	//var treeObj = $.fn.zTree.getZTreeObj("tree");
    	        //treeObj.expandAll(true);//展开全部
            },
	        buttons: [
	        {
	            label: $translate.instant('common.yes'),
	            cssClass: 'btn-primary',
	            id: 'm_btn_save',
	            action: function(dialogItself){
	            	
	            	dialogItself.getButton('m_btn_save').disable();//不可点击
	            	
	        		var zTree = $.fn.zTree.getZTreeObj("tree");
					var nodeArrays = zTree.getCheckedNodes(true);
					var m_ids="";
	            	if(nodeArrays.length<=0){
	            		alert("Do not assign permissions yet!");
	            		dialogItself.getButton('m_btn_save').enable();//可点击
	            		return ;
	            	}else{
	            		
						for(var i=0;i<nodeArrays.length;i++){
							if(i==0){
								m_ids=nodeArrays[i].id;
							}else{
								m_ids=m_ids+","+nodeArrays[i].id;
							}
						}
	                	//alert(m_ids);
	            		var m_role_id_value=document.getElementById("m_role_id").value;
	                	//alert(selected_val+";"+m_user_id_value);
	            		if(m_role_id_value!= ""){//不为空
	            			
	          			  $.ajax({
	          		      		async:true, //是否是异步(默认为true)。改为同步会锁死整个浏览器
	          		      		type : "POST",
	          		      		data:{roleId:m_role_id_value,rescId:m_ids},
	          		      		url: apiUrl+"/role/saveRoleRescoursForRole",
	          		      		dataType:'json',
		          		      	beforeSend : function(request) {
		            				//alert("beforeSend:"+localStorageService.get(tokenKey));
		            				if (localStorageService.get(tokenKey)) {
		            					request.setRequestHeader(tokenRequestHeaderKey,localStorageService.get(tokenKey));
		            				}
		            				$('#m_warning_div_id').text($translate.instant('common.file.progress')).css("color",vm.success_color);
		            			},
	          		      		success: function(data){
	          		      			//console.info(data);
	          		      			if(data.statusCode=="OK"){
	          		      			    $('#m_warning_div_id').text($translate.instant('common.menu.success')).css("color",vm.success_color);
	          		      				alert("The assignment is successful and must be logged in again!");
	          		      				dialogItself.close();
	          		      			}else{
	          		      			    $('#m_warning_div_id').text($translate.instant('common.menu.failure')).css("color",vm.error_color);
	          		      				alert($translate.instant('common.menu.failure'));
	          		      				dialogItself.getButton('m_btn_save').enable();//可点击
	          		      			}
	          		       		},
	          		       		error:function(err){
	          		       			$('#m_warning_div_id').text($translate.instant('common.menu.failure')).css("color",vm.error_color);
	          		       			dialogItself.getButton('m_btn_save').enable();//可点击
	          		       		}
	          		      	});
	          		   }else{
	          			 dialogItself.getButton('m_btn_save').enable();//可点击
	              	   }
	
	            		   
	            	}
	            	
	            }
	        },{
	            label: $translate.instant('common.close'),
	            action: function(dialogItself){
	                dialogItself.close();
	            }
	        }]
	    });
		
		
		
	}

	function addInit() {
		vm.modelTitle = $translate.instant('manegeruserRole.add');
		vm.readonlyID = false;
		vm.bean = {};
		vm.statusCode="";
		vm.statusMessage="";
	}
	function edit(bean) {
		reloadData();
		vm.modelTitle = $translate.instant('manegeruserRole.edit');
		vm.readonlyID = true;
		vm.bean = bean;
		vm.statusCode="";
		vm.statusMessage="";
	}

	function submit() {
		// if (vm.bean.roleId == null) {
		if (!vm.readonlyID) {
			RoleService.createRole(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating Role.');
					});
			vm.reset();
		} else {
			RoleService.updateRole(vm.bean, vm.bean.roleId).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating Role.');
					});
		}
	}

    
	//原有的
function onSubmitSuccess(data){
	vm.statusCode=data.statusCode;
		vm.statusMessage=data.statusMessage;
		reloadData();
	}
	function deleteBean(bean) {
		
		BootstrapDialog.show({
			title : $translate.instant('sysRole.delete'),
			message : $translate.instant('common.delete.message'),
			buttons : [ {
				label : $translate.instant('common.yes'),
				cssClass : 'btn btn-danger model-tool-right',
				action : function(dialogItself) {
					RoleService.deleteRole(bean.roleId).then(reloadData,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while updating Role.');
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
		var roleId = vm.beanSer.roleId;
		var name = encodeURI(encodeURI(vm.beanSer.name));
		var remark = vm.beanSer.remark;
		vm.dtInstance.changeData(getFromSource(apiUrl + '/role?id=' + getValueForSelect(roleId)+
				"&remark="+getValueForSelect(remark)+
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
