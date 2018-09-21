'use strict';
var App = angular.module('provinceModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule', 'ui.tree'  ]);

configAppModule(App);
App.controller('ServerSideCtrl', ServerSideCtrl);

function ServerSideCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, ProvinceService,localStorageService,topleftService) {
	var vm = this;
	vm.modelTitle = "";
	vm.readonlyID = false;
	vm.beanSer = {};
    vm.columnStatusData=[];
	vm.reloadData = reloadData;
	vm.dtOptions = DTOptionsBuilder.fromSource(getFromSource(apiUrl + '/province')).withOption(
			'createdRow', createdRow);
	setDtOptionsServerSide(vm);
	vm.dtColumns = [
			DTColumnBuilder.newColumn('provinceCityId').withTitle($translate('mmsmp.provinceCity.id')).notSortable(),
			DTColumnBuilder.newColumn('provinceCityName').withTitle($translate('mmsmp.province.name')).notSortable(),
			DTColumnBuilder.newColumn(null).withTitle($translate('Actions')).notSortable()
			.renderWith(actionsHtml) ];
	vm.addInit = addInit;
	vm.edit = edit;
	vm.submit = submit;
	vm.deleteBean = deleteBean;
	vm.importInit = importInit;
	vm.VAR_MAX_FILE_SIZE_M = 1;
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
		vm.beans[data.provinceCityId] = data;
		return '<button class="btn btn-warning" data-toggle="modal" data-target="#myModal" title="'+$translate.instant('common.edit')+'" ng-click="ctrl.edit(ctrl.beans[\''
				+ data.provinceCityId
				+ '\'])">'
				+ '   <i class="fa fa-edit"></i>'
				+ '</button>&nbsp;'
				+ '<button class="btn btn-danger" title="'+$translate.instant('common.delete')+'" ng-click="ctrl.deleteBean(ctrl.beans[\''
				+ data.provinceCityId
				+ '\'])">'
				+ '   <i class="fa fa-trash-o"></i>'
				+ '</button>&nbsp;'
                '</div>'

	}
	function addInit() {
		vm.bean = {};
		vm.modelTitle = $translate.instant('mmsmp.provinceCity.add');
		vm.readonlyID = false;
		vm.statusCode="";
		vm.statusMessage="";
	}
	function edit(bean) {
		reloadData();
		vm.modelTitle = $translate.instant('mmsmp.provinceCity.edit');
		vm.readonlyID = true;
		vm.bean = bean;	
		vm.bean.status = vm.bean.status+"";
		vm.statusCode="";
		vm.statusMessage="";
	}
		

	function submit() {
		if (!vm.readonlyID) {
			ProvinceService.createProvince(vm.bean).then(onSubmitSuccess,
					function(errResponse) {
 						handleAjaxError(errResponse);
						console.error('Error while creating Role.');
					});
			vm.reset();
		} else {
			ProvinceService.updateProvince(vm.bean, vm.bean.provinceCityId).then(onSubmitSuccess,
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
					ProvinceService.deleteProvince(bean.provinceCityId).then(reloadData,
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
	
	//导入
	function importInit(){
		BootstrapDialog.show({
			title:$translate.instant('common.import'),
			message: function(dialog) {
                //var $message = $('<div></div>').load(url);
				var $message=$("<span>"+$translate.instant('common.file.message')+"<br/></span>" +
						"<span style='margin-left:10px'>"+$translate.instant('common.file.format')+"UTF-8<br/></span>" +
						"<span style='margin-left:10px'>"+$translate.instant('common.file.type')+".txt<br/></span>" +
						"<span style='margin-left:10px'>"+$translate.instant('common.file.quantity')+"1<br/></span>" +
						"<span style='margin-left:10px'>"+$translate.instant('common.file.size')+"<label id='file_max_size_id' style='font-weight:400'></label><br/></span>" +
						"<div><div id='picker'>"+$translate.instant('common.selDocument')+"</div><div id='m_warning_div_id'></div></div>");
                return $message;
            },
            onshown: function(dialogRef){//打开完成
            	
            	var file_max_size_id_value=vm.VAR_MAX_FILE_SIZE_M+""+"M";
            	$("#file_max_size_id").text(file_max_size_id_value);
            	
            	//导入 start
            	var uploader = WebUploader.create({
            	    // swf文件路径
            		//swf: BASE_URL + '/js/Uploader.swf',
            		auto: true,
            	    // 文件接收服务端。
            	    server: apiUrl+'/province/Import',
            	    method: 'POST',
            		formData:{"timestamp":(new Date()).valueOf()},
            	    headers:{"Authorization":localStorageService.get(tokenKey)},
            	    // 选择文件的按钮。可选。
            	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            	    pick: {id :'#picker',multiple:false},//设置id和设置只能选择一个文件,multiple:表示是否多选
            	    sendAsBinary : true,
            	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            	    resize: false,
            	    duplicate: true,//是否可重复上传,true表示可重复上传。false表示不可重复上传。默认不可重复上传
            	    fileNumLimit: 1,
            	    timeout: 0,
            	    fileSingleSizeLimit: vm.VAR_MAX_FILE_SIZE_M * 1024 * 1024,//1M
            	    accept: {
                        title: 'Files',
                        extensions: 'txt',
                        mimeTypes: 'txt'
                    }
            	});
            	
            	// 文件上传过程中创建进度条实时显示。
                uploader.on( 'uploadProgress', function( file, percentage ) {
                    //进度条
                	$('#m_warning_div_id').text($translate.instant('common.file.progress')).css("color",vm.success_color);
                });
            	
            	uploader.on( 'error', function( type ) {
            		$('#m_warning_div_id').text($translate.instant('common.file.error')).css("color",vm.success_color);
                    //alert("error:"+type);
                    if(type=="F_EXCEED_SIZE"){//文件大小
                        alert("The file size can not be exceeded"+vm.VAR_MAX_FILE_SIZE_M+"M");
                        return ;
                    }
                    
                    if (type=="Q_TYPE_DENIED"){
                        alert("Please upload txt type of file");
                        return ;
                    }
                    
                    /*if (type=="Q_EXCEED_NUM_LIMIT"){//设置了multiple后即可忽略该语句
                        alert("每次上传只能上传一个文件,超过个数会默认取第一个文件");
                        return ;
                    }*/
                    
                    alert("Can only upload a file, the file size is"+vm.VAR_MAX_FILE_SIZE_M+"M,The file type is txt type");
                });
            	
            	uploader.on( 'uploadSuccess', function( file,response) {
//            		selTree();
            		if(response.importNum!=0){
            			$('#m_warning_div_id').text($translate.instant('common.file.success')).css("color",vm.success_color);
                		var message=$translate.instant('common.file.success')+"<span style='margin-left:3px'></span>"+response.importNum+"<span style='margin-left:3px'></span>"+$translate.instant('common.file.data');
                		BootstrapDialog.show({
                			title:$translate.instant('common.import'),		
                			message : message,
                            onhidden: function(dialogItself){//关闭完成
                            	vm.isTips = false;
                            	dialogRef.close();
                            },
                				buttons : [ {
                					label : $translate.instant('common.yes'),
                					cssClass : 'btn btn-success model-tool-right',
                					action : function(dialogItself) {
                						vm.isTips=true;
                						reloadData();
                						dialogItself.close();
                						dialogRef.close();
                					}
                				}]
                					
                		});
            		}else{
            			$('#m_warning_div_id').text($translate.instant('common.file.failure')).css("color",vm.success_color);
                		var message=$translate.instant('common.file.failure');
                		BootstrapDialog.show({
                			title:$translate.instant('common.import'),	
                			message : message,
                			onhidden: function(dialogItself){//关闭完成
                				vm.isTips = false;
                            	dialogRef.close();
                            },
                				buttons : [ {
                					label : $translate.instant('common.yes'),
                					cssClass : 'btn btn-success model-tool-right',
                					action : function(dialogItself) {
                						vm.isTips=true;
                						reloadData();
                						dialogItself.close();
                						dialogRef.close();
                					}
                				}]
                		});
            		}
            		
            	});
            	
            	uploader.on( 'uploadError', function( file ) {
            		$('#m_warning_div_id').text($translate.instant('common.file.failure')).css("color",vm.success_color);
            		var message=$translate.instant('common.file.failure');
            		BootstrapDialog.show({
            			title:$translate.instant('common.import'),	
            			message : message,
            			onhidden: function(dialogItself){//关闭完成
            				vm.isTips = false;
                        	dialogRef.close();
                        },
            				buttons : [ {
            					label : $translate.instant('common.yes'),
            					cssClass : 'btn btn-success model-tool-right',
            					action : function(dialogItself) {
            						vm.isTips=true;
            						reloadData();
            						dialogItself.close();
            						dialogRef.close();
            					}
            				}]
            		});
            	});
            	//导入 end
            },
	        buttons: [
	        {
	            label: $translate.instant('common.close'),
	            cssClass: 'btn-primary',
	            action: function(dialogItself){
	                dialogItself.close();
	            }
	        }]
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
		
		var provinceCityId = vm.beanSer.provinceCityId;
		var provinceCityName = encodeURI(encodeURI(vm.beanSer.provinceCityName));
		var parentProvinceCityId="-1";
	
		
		vm.dtInstance.changeData(getFromSource(apiUrl + '/province?provinceCityId=' + getValueForSelect(provinceCityId)+
				"&provinceCityName="+getValueForSelect(provinceCityName)));
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
