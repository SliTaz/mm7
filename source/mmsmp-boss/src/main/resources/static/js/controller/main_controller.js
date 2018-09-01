'use strict';
var mainApp = angular.module('mainModule', [ 'datatables',
		'datatables.columnfilter', 'datatables.fixedcolumns',
		'datatables.buttons', 'pascalprecht.translate', 'ngSanitize', 'LocalStorageModule','ui.tree' ]);

configAppModule(mainApp);

mainApp.controller('mainCtrl', mainCtrl);

function mainCtrl(DTOptionsBuilder, DTColumnBuilder, $translate, $scope,
		$filter, $compile, MainService,localStorageService,topleftService) {
	var vm = this;
	vm.noticeRecords=[];
	vm.alarmRecords=[];
	vm.interfaceStatisticsData=[];
	vm.bankInterfaceStatisticsData=[];
	vm.kpiStatisticsData=[];
	vm.BuyerStatisticsData=[];
	vm.SellerStatisticsData=[];
	vm.TotalKpiData=[];
	vm.transactionStatistics=[];
	vm.Date_format_yyyyMMddHHmm=Date_format_yyyyMMddHHmm;
	vm.levelText=levelText;
	vm.realTimeData=[];
	var timeAll=[];
	var reqCount=[];
	var succCount=[];
	

	
	//告警内容展示
	vm.alarmInfoContent = alarmInfoContent;
	vm.setting=setting;
	vm.testCollapse=testCollapse;
	//vm.renderFinish=renderFinish;
	vm.userId=localStorageService.get(ZBuserIdKey);
	vm.userDashboardConfig=vm.userId+"_dashboardConfig";
	vm.dashboardConfig={left: [{name: 'RealTimeFace',status : 'expand',m_show:'false'},{name: 'Alarm',status : 'expand',m_show:'false'}
	                          ,{name: 'Notice',status : 'expand',m_show:'false'},{name: 'OtherSellerTotalFace',status : 'expand',m_show:'false'} ,{name: 'TransactionFace',status : 'expand',m_show:'false'},{name: 'SellerIncrementalFace',status : 'expand',m_show:'false'}],
			            right: [{name: 'Calendar',status : 'hidden',m_show:'true'},{name: 'KpiFace',status : 'expand',m_show:'false'} ,{name: 'MainFace',status : 'expand',m_show:'false'},{name: 'BankFace',status : 'expand',m_show:'false'}
			                      ,{name: 'BuyerFace',status : 'expand',m_show:'false'},{name: 'SellerFace',status : 'expand',m_show:'false'}
			                      ,{name: 'BuyerIncrementalFace',status : 'expand',m_show:'false'}  ]};
	
	
	initltCommon(vm,localStorageService,topleftService);
	


	//alert(vm.userDashboardConfig);
	if(localStorageService.get(vm.userDashboardConfig)){
		//alert("cookie yes");
		vm.dashboardConfig=localStorageService.get(vm.userDashboardConfig);
	}else{
		//alert("cookie no");
		localStorageService.set(vm.userDashboardConfig, vm.dashboardConfig);
	}
	//alert("vm.userDashboardConfig:"+vm.userDashboardConfig+";get:"+vm.dashboardConfig.left.length+";"+vm.dashboardConfig.right.length);
	//console.info(vm.dashboardConfig);
	
	
	$("#calendar").datepicker({todayHighlight: true});//高亮显示今天
	
	$(".connectedSortable").sortable({
		stop: function( event, ui ) {
	    	//alert("ddddddd");
	    	//alert("userDashboardConfig:"+userDashboardConfig);
	    	var dashboardConfigTmp = {
	    			left: [],
	    			right: []
	    		};
	    	
	    	if($('.left > .box').length > 0){
	    		$('.left > .box').each(function(index){
	    			var itemList = ['RealTimeFace','Notice','Alarm','Calendar','MainFace','BankFace','TransactionFace'
	    				,'KpiFace','BuyerFace','SellerFace','OtherSellerTotalFace','BuyerIncrementalFace','SellerIncrementalFace'];
	    			if(itemList.length > 0){
	    				for ( var i = 0; i < itemList.length; i++) {
	    					var status = '';
	    					var m_show = '';
							if($(this).hasClass(itemList[i])){
								if($(this).css('display') == 'none'){
									status = 'hidden';
									m_show="true";
								} else {
									if($(this).hasClass('collapsed-box')){//收缩
										//修改值
										status = 'collapsed';
										m_show="false";
									} else {
										status = 'expand';
										m_show="false";
									}
								}
								dashboardConfigTmp.left.push({
									name: itemList[i],
									status : status,
									m_show:m_show
								})
								break;
							}
						}
	    			}
	    		})
	    	}
	    	
	    	if($('.right > .box').length > 0){
	    		$('.right > .box').each(function(index){
	    			var itemList = ['RealTimeFace','Notice','Alarm','Calendar','MainFace','BankFace','TransactionFace'
	    				,'KpiFace','BuyerFace','SellerFace','OtherSellerTotalFace','BuyerIncrementalFace','SellerIncrementalFace'];
	    			if(itemList.length > 0){
	    				for ( var i = 0; i < itemList.length; i++) {
	    					var status = '';
	    					var m_show = '';
	    					if($(this).hasClass(itemList[i])){
	    						if($(this).css('display') == 'none'){
	    							status = 'hidden';
	    							m_show="true";
	    						} else {
	    							if($(this).hasClass('collapsed-box')){//收缩
	    								//修改值
	    								status = 'collapsed';
	    								m_show="false";
	    							} else {
	    								status = 'expand';
	    								m_show="false";
	    							}
	    						}
	    						dashboardConfigTmp.right.push({
	    							name: itemList[i],
	    							status : status,
	    							m_show:m_show
	    						})
	    						break;
	    					}
	    				}
	    			}
	    		})
	    	}
	    	
	    	//
	    	vm.dashboardConfig=dashboardConfigTmp;
	    	localStorageService.set(vm.userDashboardConfig, vm.dashboardConfig);
	    	//alert(vm.userDashboardConfig);
	    	//alert("vm.userDashboardConfig:"+vm.userDashboardConfig+";set:"+vm.dashboardConfig.left.length+";"+vm.dashboardConfig.right.length);
	    	//console.info(vm.dashboardConfig);
	    }
	});
	
	exp_col_remove_setting();
	
	function changeDataForHTML(){
		//alert("changeDataForHTML");
		$scope.$applyAsync();//当在angular的上下文之外修改了界面绑定的数据时，需要调用该函数，让页面的值进行改变。
	}
	
	
	function exp_col_remove_setting(){
		$('button[data-widget="remove"]').on('click',function(){
			//alert("remove_click");
			var $box = $(this).parents('.box');
			var itemList = ['RealTimeFace','Notice','Alarm','Calendar','MainFace','BankFace','TransactionFace'
				,'KpiFace','BuyerFace','SellerFace','OtherSellerTotalFace','BuyerIncrementalFace','SellerIncrementalFace'];
			if(itemList.length>0){
				for(var i = 0; i < itemList.length; i++){
					if($box.hasClass(itemList[i])){
						setBoxStatus(itemList[i], "hidden","true");
					}
				}
			}
			
		});
		
		$('button[data-widget="collapse"]').on('click',function(){
			//alert("button expand or collapse");
			var $box = $(this).parents('.box');
			
			//折叠展开只用+-标识start
			$(this).children(":first")
	                .removeClass()
	                .addClass('fa fa-minus').addClass('fa fa-plus');
			//折叠展开只用+-标识end
				  
			var itemList = ['RealTimeFace','Notice','Alarm','Calendar','MainFace','BankFace','TransactionFace'
				,'KpiFace','BuyerFace','SellerFace','OtherSellerTotalFace','BuyerIncrementalFace','SellerIncrementalFace'];
			if(itemList.length>0){
				for(var i = 0; i < itemList.length; i++){
					if($box.hasClass(itemList[i])){
						if($box.hasClass('collapsed-box')){//收缩
							//修改值
							setBoxStatus(itemList[i], "expand","false");
							
						} else {
							setBoxStatus(itemList[i], "collapsed","false");
						}
					}
				}
			}
		});
		
		$('#notice_more_id').on('click',function(){
			showNoticeMore();
		});
		
		$('#alarm_more_id').on('click',function(){
			showAlarmMore();
		});
		
		$('.notice_span_pointer').on('click',function(){
			var obj_id=$(this).attr('id');
			//alert(obj_id);
			getNoticeById(obj_id);
		});
		//notice_span_pointer
		vm.noticeOne=function(id){
			var obj_id=id;
			//alert(obj_id);
			getNoticeById(obj_id);
		}
		/*vm.alarmInfoOne=function(id){
			var obj_id=id;
			alert(obj_id);
		//	getAlarmById(obj_id);
		}*/
		//alarm_span_pointer 2017-08-05
		$('.alarm_span_pointer').on('click',function(){
			var obj_id=$(this).attr('id');
			getAlarmById(obj_id);
		});
	}
	
	function getNoticeById(noticeId){
		MainService.getNoticeById(noticeId).then(getNoticeByIdSuccess,
				function(errResponse) {
 						handleAjaxError(errResponse);
					console.error('Error while creating Role.');
				});
	}
	
	
	
	//alarm info start by yp
	function getAlarmById(alarmId){
		MainService.getAlarmById(alarmId).then(getAlarmByIdSuccess,
				function(errResponse) {
 						handleAjaxError(errResponse);
					console.error('Error while searching alarmInfo.');
				});
	}
	
	
	function getAlarmByIdSuccess(data){
		if (data.statusCode == "OK") {
			alarmInfoContent(data.body);
		}else{
			alert("data is not exist");
		}
	}
	
	//将告警内容进行展示
	function alarmInfoContent(bean){
		BootstrapDialog.show({
			title: $translate.instant('common.alarmInfoDetails'),
			message: function(dialog) {
				var $message=$(
/*						'<span id="content_detail" style="word-break:normal; width:auto; display:block; white-space:pre-wrap;word-wrap:break-word ;overflow: hidden"'+
					    '</span>'*/
						
						'<table class="table_css_new" align="center"  style="width:90%;table-layout:fixed;border:#666666 solid 1px;border-corlor: #666666;border-collapse: collapse">'+
						'<tr>'+
							'<td style="border:#666666 solid 1px;background:#f4f4f4;" width="29%" align="center" nowrap>'+$translate.instant('alarmInfo.alarmTime')+'</td>'+
							'<td style="border:#666666 solid 1px;background: #FFFFFF;padding-left:5px;"  id="tb_alarm_time"></td>'+
						'</tr>'+						
						'<tr>'+
						   '<td style="border:#666666 solid 1px;background:#f4f4f4;" width="25%" align="center" nowrap>'+$translate.instant('alarmInfo.alarmOrigin')+'</td>'+
						   '<td style="border:#666666 solid 1px;background: #FFFFFF;padding-left:5px;"  id="tb_alarm_origin_name"></td>'+
					    '</tr>'+					    
						'<tr>'+
						   '<td style="border:#666666 solid 1px;background:#f4f4f4;" width="25%" align="center" nowrap>'+$translate.instant('common.alarmContent')+'</td>'+
						   '<td style="border:#666666 solid 1px;background: #FFFFFF;padding-left:5px;word-break:break-all;"  id="tb_alarm_content">'+bean.content+'</td>'+
					   '</tr>'+					   
					   '</table>'
				);
                return $message;
            },
            onshown: function(dialogRef){//打开完成
            var	alarmTimeEn=timeFormatOld(bean.alarmTime);
            	$("#tb_alarm_time").text(alarmTimeEn);
            	$("#tb_alarm_origin_name").text(bean.alarmOriginName);
            	//$("#tb_alarm_content").text(bean.content.replace(/<br\/>/g,"\r\n"));
            },
	        buttons: [
	        {
	            label: $translate.instant('common.handle'),
	            cssClass: 'btn-primary model-tool-right',
	            action: function(dialogItself){
	            	BootstrapDialog.show({
	        			title : $translate.instant('common.handleOpinions'),
	        			message: function(dialogItself) {
	        				var $message= $("<span>"+$translate.instant('alarmInfo.alarmInfoCode')+":"+bean.alarmInfoCode+"<br/></span>" +
	        						"<span>"+$translate.instant('common.alarmOrigin')+":"+bean.alarmOriginName+"<br/></span>" +
	        						"<span>"+$translate.instant('alarmInfo.alarmTime')+":"+timeFormatOld(bean.alarmTime)+"<br/></span>" +
	        						"<span>"+$translate.instant('common.handle.opinions')+"<br/></span>" +
	        						"<textarea type='text' id='handleView' style='height:120px'   class='form-control' id='remark' name='remark' maxlength='1000'></textarea>");
	        			
	                        return $message;
	                    },
	                    
	                    
	        			buttons : [ {
	        				label : $translate.instant('common.yes'),
	        				cssClass : 'btn btn-danger model-tool-right',
	        				action : function(dialogItself) {
	        					var handleView=document.getElementById("handleView").value;
	        					
	        					vm.timeActivateBean={};
	        					vm.timeActivateBean.handle=getValueForSelect(handleView);
	        					
	        					MainService.alarmHandleView(vm.timeActivateBean, bean.alarmInfoCode).then(
	        					function(data) {
	        						if(data.statusCode=="OK"){
	        							dialogItself.close();
	        							window.location="main.html";
	        						}
	        					});
	        					
	        					
	        				}

	        			}, {
	        				label : $translate.instant('common.cancel'),
	        				cssClass : 'btn btn-default model-tool-left',
	        				action : function(dialogItself) {
	        					dialogItself.close();
	        					
	        				}
	        			} ]
	        		});
	            	dialogItself.close();
	            }
	        }, {
				label : $translate.instant('common.cancel'),
				cssClass : 'btn btn-default model-tool-left',
				action : function(dialogItself) {
					dialogItself.close();
				}
			}
	        ]
	    });
	}
	//alarm info end  by yp
	
	
	
	
	
	
	function getNoticeByIdSuccess(data){
		//alert("ok");
		if (data.statusCode == "OK") {
			//data.body
			showDetail(data.body);
		}else{
			alert("data is not exist");
		}
	}
	
	//测试折叠。ok
	function testCollapse(){
		//alert("555");
		//var $box = $('.Calendar');
		//$box.toggleBox();// 第一次可以。但是有了cooke之后就不行了
		
		//$('.Calendar').collapse();//error
		
		collapse_box($('.Calendar button[data-widget="collapse"]'));//ok
	}
	
	function collapse_box(element){
		//alert(element.html());
		
		var _this_icons_collapse="fa-minus";
		var _this_icons_open="fa-plus";
		var _this_icons_collapse_zb="fa-angle-double-up";
		var _this_icons_open_zb="fa-angle-double-down";
		var _this_animationSpeed=500;
		
	      //Find the box parent
	      var box = element.parents(".box").first();
	      //Find the body and the footer
	      var box_content = box.find("> .box-body, > .box-footer, > form  >.box-body, > form > .box-footer");
	      if (!box.hasClass("collapsed-box")) {//进行折叠操作
	    	  //alert("1");
	        //Convert minus into plus
	        if(element.children(":first").hasClass(_this_icons_collapse)){
		        element.children(":first")
		          .removeClass(_this_icons_collapse)
		          .addClass(_this_icons_open);
	        }else{
	        	element.children(":first")
		          .removeClass(_this_icons_collapse)
		          .addClass(_this_icons_open);
	        }
	        //Hide the content
	        box_content.slideUp(_this_animationSpeed, function () {
	          box.addClass("collapsed-box");
	        });
	      } else {//进行展开操作
	    	  //alert("2");
	        //Convert plus into minus
	        if(element.children(":first").hasClass(_this_icons_open)){
		        element.children(":first")
		          .removeClass(_this_icons_open)
		          .addClass(_this_icons_collapse);
	        }else{
	        	element.children(":first")
		          .removeClass(_this_icons_open)
		          .addClass(_this_icons_collapse);
	        }
	        //Show the content
	        box_content.slideDown(_this_animationSpeed, function () {
	          box.removeClass("collapsed-box");
	        });
	      }
	}
	
	function setBoxStatus(name, status,m_show){
		var dashboardConfig = vm.dashboardConfig;
		var leftDashboardConfig = dashboardConfig.left;
		var rightDashboardConfig = dashboardConfig.right;
		if(leftDashboardConfig && leftDashboardConfig.length > 0){
			for ( var i = 0; i < leftDashboardConfig.length; i++) {
				var boxConfig = leftDashboardConfig[i];
				
				if(boxConfig.name == name){
					boxConfig.status = status;
					boxConfig.m_show = m_show;
					//直接set值
					vm.dashboardConfig=dashboardConfig;
					localStorageService.set(vm.userDashboardConfig, vm.dashboardConfig);
					//alert("setBoxStatus vm.userDashboardConfig:"+vm.userDashboardConfig+";set:"+vm.dashboardConfig.left.length+";"+vm.dashboardConfig.right.length);
					return;
				}
			}
		}
		
		if(rightDashboardConfig && rightDashboardConfig.length > 0){
			for ( var i = 0; i < rightDashboardConfig.length; i++) {
				var boxConfig = rightDashboardConfig[i];
				
				if(boxConfig.name == name){
					boxConfig.status = status;
					boxConfig.m_show = m_show;
					//直接set值
					vm.dashboardConfig=dashboardConfig;
					localStorageService.set(vm.userDashboardConfig, vm.dashboardConfig);
					//alert("setBoxStatus vm.userDashboardConfig:"+vm.userDashboardConfig+";set:"+vm.dashboardConfig.left.length+";"+vm.dashboardConfig.right.length);
					return;
				}
			}
		}
	}
	
	function levelText(value){
		if((value==null||value=="")&&typeof(value) == "string"){//添加(前提条件是字符串才进行非空的判断):&&typeof(data) == "string"
			return "";
		}else{
			if(value==1){
				return $translate.instant('common.general');//"一般";
			}
			
			if(value==2){
				return $translate.instant('common.urgent');//"紧急";
			}
			
			if(value==3){
				return $translate.instant('common.important');//"重要";
			}
		}
	}
	
	function Date_format_yyyyMMddHHmm(value){
		
		if(value==null||value==""){
			return "";
		}else{
			try
			{
				return value.substr(0,16);
			}
			catch(e)
			{
				return "";
			}
		}
	}
	
	function getNoticeRecords(){
		var noticeStatus_value=2;//已发布
		var isViewSys_value=1;//显示
		
		vm.noticeBean={};
		vm.noticeBean.noticeStatus=noticeStatus_value;
		vm.noticeBean.isViewSys=isViewSys_value;
		
		MainService.getNoticeRecords(vm.noticeBean).then(getNoticeRecordsSuccess,
				function(errResponse) {
 						handleAjaxError(errResponse);
					console.error('Error while creating bookkeepking.');
				});
		
	}
	
	function getNoticeRecordsSuccess(data){
		//alert("ok");
		//console.info(data);
		
		var noticeList=[];
		
		if (data.statusCode == "OK") {
			vm.noticeRecords=[];
			
			noticeList=data.body;
			
			vm.noticeRecords=getNoticeTime(noticeList);
			
		}
		
		if(noticeList.length==0){//如果后台没有数据，这时onFinishRenderFilters不会被触发。故需要特殊处理
			//load_box();
			
			getAlarmRecords();
		}
		
	}

	function getAlarmRecords(){
		var status_value=1;//1:未处理
		
		vm.alarmBean={};
		vm.alarmBean.status=status_value;
		
		MainService.getAlarmRecords(vm.alarmBean).then(getAlarmRecordsSuccess,
				function(errResponse) {
 						handleAjaxError(errResponse);
					console.error('Error while creating bookkeepking.');
				});
		
	}
	
	function getAlarmRecordsSuccess(data){
		//alert("ok");
		//console.info(data);
		
		var alarmList=[];
		if (data.statusCode == "OK") {
			vm.alarmRecords=[];
			alarmList=data.body;
			vm.alarmRecords=alarmListTime(alarmList);
		}
		
		/*if(alarmList.length==0){//如果后台没有数据，这时onAlarmFinishRenderFilters不会被触发。故需要特殊处理
			//load_box();
			getNoticeRecords();
		}*/
		//解决首页告警不一致
		 getTopAlarmRecordsSuccess(vm, data);
		 localStorageService.set(ZBalarmInfosKey, data);
		
	}
	//通知时间
	function getNoticeTime(data){
		var naLan = navigator.language;
		if (naLan == undefined || naLan == "") {
			naLan = navigator.browserLanguage;
		}	
		if(naLan=='zh'){
		return  data; 
		}else if(naLan.indexOf("zh") >= 0){
			return  data; 
		}
		else{
			for(var i=0;i<data.length;i++){
				data[i].releaseTime=data[i].releaseTime.substring(8,10)+"/"+data[i].releaseTime.substring(5,7)+"/"+data[i].releaseTime.substring(0,4)+" "+data[i].releaseTime.substring(11,19);
				}
			return  data; 	
		}
	    }	
	/*function alarmListTime(data){
		var naLan = navigator.language;
		if (naLan == undefined || naLan == "") {
			naLan = navigator.browserLanguage;
		}	
		if(naLan=='zh'){
		return  data; 
		}else if(naLan.indexOf("zh") >= 0){
			return  data; 
		}
		else{
			for(var i=0;i<data.length;i++){
				data[i].alarmTime=data[i].alarmTime.substring(8,10)+"/"+data[i].alarmTime.substring(5,7)+"/"+data[i].alarmTime.substring(0,4)+" "+data[i].alarmTime.substring(11,19);
				}
			return  data; 	
		}
	    }*/
	
	function load_box(){
		//alert("load_box");

		var leftDashboardConfig = vm.dashboardConfig.left;
		var rightDashboardConfig = vm.dashboardConfig.right;
		
		if(leftDashboardConfig && leftDashboardConfig.length > 0){
			for ( var i = 0; i < leftDashboardConfig.length; i++) {
				var boxConfig = leftDashboardConfig[i];
				var name=boxConfig.name;
				
				var box_html=$('.' + name).prop('outerHTML');
				//alert($('.Notice .todo-list').prop('outerHTML'));
				$('.' + name).hide();
				$('.' + name).remove();
				$('.left').append(box_html);
				
				if(boxConfig.status == 'hidden'){
					$('.' + name).hide();
				}
				
				if(boxConfig.status == 'expand'){
					$('.' + name).show();
				}
				
				if(boxConfig.status == 'collapsed'){
					$('.' + name).show();
					//进行折叠
					collapse_box($('.'+name+' button[data-widget="collapse"]'));
				}
				
			}
			
		/*	
			setTimeout(function(){
			showInterface();
			showBankInterface();
			},1000);*/
	}
		
		if(rightDashboardConfig && rightDashboardConfig.length > 0){
			for ( var i = 0; i < rightDashboardConfig.length; i++) {
				var boxConfig = rightDashboardConfig[i];
				var name=boxConfig.name;
				
				var box_html=$('.' + name).prop('outerHTML');
				//alert(box_html);
				$('.' + name).hide();
				$('.' + name).remove();
				$('.right').append(box_html);
				
				
				if(boxConfig.status == 'hidden'){
					$('.' + name).hide();
				}
				
				if(boxConfig.status == 'expand'){
					$('.' + name).show();
				}
				
				if(boxConfig.status == 'collapsed'){
					$('.' + name).show();
					//进行折叠
					collapse_box($('.'+name+' button[data-widget="collapse"]'));
				}
				
			}
		}
		
		
		exp_col_remove_setting();
		
		//changeDataForHTML();
		
	}
	
	function showDetail(bean){
		//alert(bean.title+";"+bean.content);
		var url="./showDetailForNotice.html";
		BootstrapDialog.show({
			title: $translate.instant('common.notice'),
			message: function(dialog) {
                //var $message = $('<div></div>').load(url);
				var $message=$(
						'<table class="table_css_new" align="center"  style="width:90%;table-layout:fixed;border:#666666 solid 1px;border-corlor: #666666;border-collapse: collapse">'+
						'<tr>'+
							'<td style="border:#666666 solid 1px;background:#f4f4f4;" width="26%" align="center" nowrap>'+$translate.instant('notice.title')+'</td>'+
							'<td style="border:#666666 solid 1px;background: #FFFFFF;padding-left:5px;"  id="m_title_id"></td>'+
						'</tr>'+						
						'<tr>'+
						   '<td style="border:#666666 solid 1px;background:#f4f4f4;" width="25%" align="center" nowrap>'+$translate.instant('notice.content')+'</td>'+
						   '<td style="border:#666666 solid 1px;background: #FFFFFF;padding-left:5px;word-break:break-all;"  id="m_content_id">'+bean.content.replace(/\r\n/g, '<br/>')+'</td>'+
					    '</tr>'+	
						'<tr style="display:none" id="imageDiv">'+
						   '<td style="border:#666666 solid 1px;background:#f4f4f4;" width="25%" align="center" nowrap>'+$translate.instant('common.image')+'</td>'+
						   '<td style="border:#666666 solid 1px;background: #FFFFFF;padding-left:5px;word-break:break-all;"><img id="m_image_id"  src="" style="width:250px;height:200px"></td>'+
					    '</tr>'+
						'<tr>'+
						   '<td style="border:#666666 solid 1px;background:#f4f4f4;" width="25%" align="center" nowrap>'+$translate.instant('notice.releaseTime')+'</td>'+
						   '<td style="border:#666666 solid 1px;background: #FFFFFF;padding-left:5px;"  id="m_releaseTime_id"></td>'+
					   '</tr>'+					   
					   '<tr>'+
					     '<td style="border:#666666 solid 1px;background:#f4f4f4;" width="25%" align="center" nowrap>'+$translate.instant('notice.releaseUser')+'</td>'+
					     '<td style="border:#666666 solid 1px;background: #FFFFFF;padding-left:5px;"  id="m_releaseUser_id"></td>'+
				       '</tr>'+
					   '</table>'
						
				
				);
                return $message;
            },
            onshown: function(dialogRef){//打开完成
            	var releaseTime=timeFormatOld(bean.releaseTime);
                //alert('Dialog is popped up.');
            	$("#m_title_id").text(bean.title);
            	//$("#m_content_id").text(bean.content);
            	$("#m_releaseTime_id").text(releaseTime);
            	$("#m_releaseUser_id").text(bean.releaseUser);
            	
            	//展示图片信息start
            	if(bean.contentImgUrl){
            		$("#imageDiv").show();
    				var path= apiUrl + "/notice/get/noticepicture/path/"+bean.contentImgUrl;
    				angular.element(document).find('#m_image_id').eq(0).attr("src",path);
            	}
            	//展示图片信息end

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
	}
	
	function setting(){
		var url="./settingForMain.html";
		BootstrapDialog.show({
			title:$translate.instant('common.setting'),
			message: function(dialog) {
                var $message = $('<div></div>').load(url);
                return $message;
            },
            onshown: function(dialogRef){//打开完成
                //alert('Dialog is popped up.');
            	//console.info(vm.dashboardConfig);
            	settingMPanelHiddenOrShow();
            	
            	$('.RealTimeFaceItem').on('click',function(){
            		showDashboard('RealTimeFace');
            	});
            	$('.NoticeItem').on('click',function(){
            		showDashboard('Notice');
            	});
            	
            	$('.AlarmItem').on('click',function(){
            		showDashboard('Alarm');
            	});
            	
            	$('.CalendarItem').on('click',function(){
            		showDashboard('Calendar');
            	});
            	$('.MainFaceItem').on('click',function(){
            		showDashboard('MainFace');
            	});
            	$('.BankFaceItem').on('click',function(){
            		showDashboard('BankFace');
            	});
            	
            	$('.KpiFaceItem').on('click',function(){
            		showDashboard('KpiFace');
            	});
            	$('.BuyerFaceItem').on('click',function(){
            		showDashboard('BuyerFace');
            	});
            	$('.SellerFaceItem').on('click',function(){
            		showDashboard('SellerFace');
            	});
            	$('.OtherSellerTotalFaceItem').on('click',function(){
            		showDashboard('OtherSellerTotalFace');
            	});
            	$('.EPSTPSFaceItem').on('click',function(){
            		showDashboard('EPSTPSFace');
            	});
            	$('.ClapStoreTotalFaceItem').on('click',function(){
            		showDashboard('ClapStoreTotalFace');
            	});
            	$('.BuyerTotalFaceItem').on('click',function(){
            		showDashboard('BuyerTotalFace');
            	});
            	$('.BuyerIncrementalFaceItem').on('click',function(){
            		showDashboard('BuyerIncrementalFace');
            	});
            	$('.SellerIncrementalFaceItem').on('click',function(){
            		showDashboard('SellerIncrementalFace');
            	});
            	$('.TransactionFaceItem').on('click',function(){
            		showDashboard('TransactionFace');
            	});
            	
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
	}
	
	function settingMPanelHiddenOrShow(){
		var dashboardConfig = vm.dashboardConfig;
		var leftDashboardConfig = dashboardConfig.left;
		var rightDashboardConfig = dashboardConfig.right;
		if(leftDashboardConfig && leftDashboardConfig.length > 0){
			for ( var i = 0; i < leftDashboardConfig.length; i++) {
				var boxConfig = leftDashboardConfig[i];
				
				var m_show=boxConfig.m_show;
				var name=boxConfig.name;
				if(m_show=="true"){
					$('.' + name + "Item").show();
				}else{
					$('.' + name + "Item").hide();
				}
			}
		}
		
		if(rightDashboardConfig && rightDashboardConfig.length > 0){
			for ( var i = 0; i < rightDashboardConfig.length; i++) {
				var boxConfig = rightDashboardConfig[i];
				
				var m_show=boxConfig.m_show;
				var name=boxConfig.name;
				if(m_show=="true"){
					$('.' + name + "Item").show();
				}else{
					$('.' + name + "Item").hide();
				}
			}
		}
	}
	
	
	function showDashboard(name){
		$('.' + name).show();
		$('.' + name + "Item").hide();
		setBoxStatus(name, "expand","false");
	}
	
	function showNoticeMore(){
		window.location = "notice.html";
	}
	
	function showAlarmMore(){
		window.location = "alarmInfo.html";
	}
	
	
	function renderFinish(){
		//alert("ngRepeatFinished");
		//load_box();
		
		//执行获取告警的数据
		getAlarmRecords();
	}
	
	function renderAlarmFinish(){
		//alert("ngRepeatAlarmFinished");
		load_box();
	}
	//实时数据
	function really(){
		
		MainService.realTime().then(
			       function(d) {
				        vm.realTimeData = d.body;
						var date = new Date();
						date.setSeconds(date.getSeconds());
					    var nowTime = getBeforeTimeFormatDate(date);
				        showTableTime(vm.realTimeData,nowTime);
			       },
					function(errResponse){
						console.error('Error while fetching selBankInfo');
					}
		);
	
	}
	function showTableTime(realTimeData,nowTime){


		var myChart = echarts.init(document.getElementById('realTime'));
		
		var nameArr=[];
		nameArr=[$translate.instant('requestNum'),$translate.instant('requestSuccNum')]
		timeAll.push(nowTime);
		if(timeAll.length>60){
		var removed = timeAll.splice(0,1); 
		}
		var objArr=[];
		for(var i=0;i<realTimeData.length;i++)
	     {
	    	 var statisticsReqCount=realTimeData[i].statisticsReqCount;
	    	 var statisticsSuccCount=realTimeData[i].statisticsSuccCount;
	    	 reqCount.push(statisticsReqCount);
	    	 succCount.push(statisticsSuccCount);
	    	 
	    	 
	    	 
	    		if(reqCount.length>60){
	    			var removed = reqCount.splice(0,1); 
	    			}
	    		if(succCount.length>60){
	    			var removed = succCount.splice(0,1); 
	    			}
	    	 
	     }
		   		var obj1=new Object();
		   		obj1.name=i18n('requestNum');
		   		obj1.type='line';
		   		obj1.data=reqCount;
		   		var obj2=new Object();
		   		obj2.name=i18n('requestSuccNum');
		   		obj2.type='line';
		   		obj2.data=succCount;
                objArr=[obj1,obj2]
		
		 var option = {
		            title: {
		                text: i18n('common.realtimeStatistics'),
                        x:'center',
                        y:'top',
		            },
		            tooltip: {  trigger: 'axis'},
		            legend: {
		                data:nameArr,
		                x:'center',
                        y:'bottom'
		            },
		            //解决y轴刻度显示不全
		            grid:{
		            	　　x:100,
		            },
		            xAxis: [
		                {
		                	
		                type: 'category',
			            boundaryGap: false,
		                data: timeAll
		            }],
		            yAxis: [{
		                splitLine: {show: false}
		            }],
		            series:objArr,
		            showSymbol: false
		            
		        };

		
		 myChart.setOption(option);
		 //解决折线超出范围start
		 $('#realTime').resize(function () {
			 window.onresize = myChart.resize; 
	       });
		 //解决折线超出范围end
		 
	}


	
	//查询一周前时间start
	function getBeforeTimeFormatDate(date) {
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    var h = date.getHours();
	    var m = date.getMinutes();
	    var s = date.getSeconds();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    if (h >= 0 && h <= 9) {
	    	h = "0" + h;
	    }
	    if (m >= 0 && m <= 9) {
	    	m = "0" + m;
	    }
	    if (s >= 0 && s <= 9) {
	    	s = "0" + s;
	    }
	    var currentdate = h+seperator2+m+seperator2+s;
	
	    return currentdate;
	}
	

	    
			
			
	//查询一周前时间start
	function getBeforeDayFormatDate(date) {
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    var h = date.getHours();
	    var m = date.getMinutes();
	    var s = date.getSeconds();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    if (h >= 0 && h <= 9) {
	    	h = "0" + h;
	    }
	    if (m >= 0 && m <= 9) {
	    	m = "0" + m;
	    }
	    if (s >= 0 && s <= 9) {
	    	s = "0" + s;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
	
	    return currentdate;
	}
	//查询一周前时间end
	
	
	    //TotalKpi
	    function showTotalKpi(){
		   MainService.TotalKpiStatistics()
	        .then(
					function(d) {
						 vm.TotalKpiData = d.body;
						 //console.log(vm.TotalKpiData);
						 vm.oneBean={};
						 vm.twoBean={};
						 vm.threeBean={};
						 vm.fourBean={};
						 for(var i=0;i<vm.TotalKpiData.length;i++){
							 if(vm.TotalKpiData[i].kpiName=="Total Other Seller"){
								 vm.oneBean= vm.TotalKpiData[i];
							 }if(vm.TotalKpiData[i].kpiName=="EPS TPS"){
								 vm.twoBean= vm.TotalKpiData[i];
							 }if(vm.TotalKpiData[i].kpiName=="Total CLAP Store"){
								 vm.threeBean= vm.TotalKpiData[i];
							 }if(vm.TotalKpiData[i].kpiName=="Total Users"){
								 vm.fourBean= vm.TotalKpiData[i]; 
							 }


						 }
						// console.log( vm.oneBean);
						 showTotalKpiTableOne(vm.oneBean);
						 showTotalKpiTableTwo(vm.twoBean);
						 showTotalKpiTableThree(vm.threeBean);
						 showTotalKpiTableFour(vm.fourBean);
					   },
	  					function(errResponse){
	  						console.error('Error while fetching showKpiData');
	  					}
			       );
			   }
	    
	    //otherSellerTotal
	    function showTotalKpiTableOne(oneBean){
			
			
	    	var seriesDataArr=[];
			var  typeNameList=[];
			
				var typeObjectOne=new Object();
				typeObjectOne.name=i18n('common.occupy');
				typeObjectOne.value=oneBean.occupy;
				
				var typeObjectTwo=new Object();
				typeObjectTwo.name=i18n('common.available');
				typeObjectTwo.value=(oneBean.capacity-oneBean.occupy);
				
				seriesDataArr=[typeObjectOne,typeObjectTwo]
				
				
				typeNameList=[i18n('common.occupy'),i18n('common.available')]
			
			
				
		/*	//释放图表实例**
			echarts.dispose(document.getElementById('monthNow'));*/
			
			 var myCharts1 = echarts.init(document.getElementById('otherOne'));
			 var  option1 = {
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
					    title: {
			                text: i18n('common.otherSellerTotal'),
	                        x:'center',
	                        y:'top',
			            },
					    legend: {
					        orient: 'horizontal',
					        top:'bottom',
					        data: typeNameList
					    },
					    series : [
					    	  {
						            name: i18n('common.otherSellerTotal'),
						            type: 'pie',
						            radius : '65%',
						            center: ['50%', '50%'],
						            label:{            //饼图图形上的文本标签
			                            normal:{
			                                show:true,
			                                position:'inner', //标签的位置
			                                textStyle : {
			                                    fontWeight : 300 ,
			                                    fontSize : 14    //文字的字体大小
			                                },
			                                formatter:'{d}%'

			                                
			                            }
			                        },
						            data:seriesDataArr,
						            itemStyle: {
						                emphasis: {
						                    shadowBlur: 10,
						                    shadowOffsetX: 0,
						                    shadowColor: 'rgba(0, 0, 0, 0.5)'
						                }
					            }
					        }
					    ]
					};

			
			 myCharts1.setOption(option1);
			 $('#otherSellerTotalId').resize(function () {
				 myCharts1.resize();
		 });
			
			
			
		}
	    
	 //EPS TPS   
   function showTotalKpiTableTwo(oneBean){
			
			
	    	var seriesDataArr=[];
			var  typeNameList=[];
			
			var typeObjectOne=new Object();
			typeObjectOne.name=i18n('common.occupy');
			typeObjectOne.value=oneBean.occupy;
			
			var typeObjectTwo=new Object();
			typeObjectTwo.name=i18n('common.available');
			typeObjectTwo.value=(oneBean.capacity-oneBean.occupy);
				
				seriesDataArr=[typeObjectOne,typeObjectTwo]
				
				
				typeNameList=[i18n('common.occupy'),i18n('common.available')]
			
			
				
		/*	//释放图表实例**
			echarts.dispose(document.getElementById('monthNow'));*/
			
			 var myCharts1 = echarts.init(document.getElementById('otherTwo'));
			 var  option1 = {
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
					    title: {
			                text: i18n('common.EPSTPS'),
	                        x:'center',
	                        y:'top',
			            },
					    legend: {
					        orient: 'horizontal',
					        top:'bottom',
					        data: typeNameList
					    },
					    series : [
					    	  {
						            name: i18n('common.EPSTPS'),
						            type: 'pie',
						            radius : '65%',
						            center: ['50%', '50%'],
						            label:{            //饼图图形上的文本标签
			                            normal:{
			                                show:true,
			                                position:'inner', //标签的位置
			                                textStyle : {
			                                    fontWeight : 300 ,
			                                    fontSize : 14    //文字的字体大小
			                                },
			                                formatter:'{d}%'

			                                
			                            }
			                        },
						            data:seriesDataArr,
						            itemStyle: {
						                emphasis: {
						                    shadowBlur: 10,
						                    shadowOffsetX: 0,
						                    shadowColor: 'rgba(0, 0, 0, 0.5)'
						                }
					            }
					        }
					    ]
					};

			
			 myCharts1.setOption(option1);
			 $('#EPSTPSId').resize(function () {
				 myCharts1.resize();
		 });
			
			
			
		}  
   //clapStoreTotal
   function showTotalKpiTableThree(oneBean){
		
		
   	var seriesDataArr=[];
		var  typeNameList=[];
		
		var typeObjectOne=new Object();
		typeObjectOne.name=i18n('common.occupy');
		typeObjectOne.value=oneBean.occupy;
		
		var typeObjectTwo=new Object();
		typeObjectTwo.name=i18n('common.available');
		typeObjectTwo.value=(oneBean.capacity-oneBean.occupy);
			
			seriesDataArr=[typeObjectOne,typeObjectTwo]
			
			
			typeNameList=[i18n('common.occupy'),i18n('common.available')]
		
		
			
	/*	//释放图表实例**
		echarts.dispose(document.getElementById('monthNow'));*/
		
		 var myCharts1 = echarts.init(document.getElementById('otherThree'));
		 var  option1 = {
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)"
				    },
				    title: {
		                text: i18n('common.clapStoreTotal'),
                        x:'center',
                        y:'top',
		            },
				    legend: {
				        orient: 'horizontal',
				        top:'bottom',
				        data: typeNameList
				    },
				    series : [
				    	  {
					            name: i18n('common.clapStoreTotal'),
					            type: 'pie',
					            radius : '65%',
					            center: ['50%', '50%'],
					            label:{            //饼图图形上的文本标签
		                            normal:{
		                                show:true,
		                                position:'inner', //标签的位置
		                                textStyle : {
		                                    fontWeight : 300 ,
		                                    fontSize : 14    //文字的字体大小
		                                },
		                                formatter:'{d}%'

		                                
		                            }
		                        },
					            data:seriesDataArr,
					            itemStyle: {
					                emphasis: {
					                    shadowBlur: 10,
					                    shadowOffsetX: 0,
					                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					                }
				            }
				        }
				    ]
				};

		
		 myCharts1.setOption(option1);
		 $('#clapStoreTotalId').resize(function () {
			 myCharts1.resize();
	 });
		
	} 
	
   //buyer total
   function showTotalKpiTableFour(oneBean){
		
		
	   	var seriesDataArr=[];
			var  typeNameList=[];
			
			var typeObjectOne=new Object();
			typeObjectOne.name=i18n('common.occupy');
			typeObjectOne.value=oneBean.occupy;
			
			var typeObjectTwo=new Object();
			typeObjectTwo.name=i18n('common.available');
			typeObjectTwo.value=(oneBean.capacity-oneBean.occupy);
				
				seriesDataArr=[typeObjectOne,typeObjectTwo]
				
				
				typeNameList=[i18n('common.occupy'),i18n('common.available')]
			
			
				
		/*	//释放图表实例**
			echarts.dispose(document.getElementById('monthNow'));*/
			
			 var myCharts1 = echarts.init(document.getElementById('otherFour'));
			 var  option1 = {
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
					    title: {
			                text: i18n('common.buyerTotal'),
	                        x:'center',
	                        y:'top',
			            },
					    legend: {
					        orient: 'horizontal',
					        top:'bottom',
					        data: typeNameList
					    },
					    series : [
					    	  {
						            name: i18n('common.buyerTotal'),
						            type: 'pie',
						            radius : '65%',
						            center: ['50%', '50%'],
						            label:{            //饼图图形上的文本标签
			                            normal:{
			                                show:true,
			                                position:'inner', //标签的位置
			                                textStyle : {
			                                    fontWeight : 300 ,
			                                    fontSize : 14    //文字的字体大小
			                                },
			                                formatter:'{d}%'

			                                
			                            }
			                        },
						            data:seriesDataArr,
						            itemStyle: {
						                emphasis: {
						                    shadowBlur: 10,
						                    shadowOffsetX: 0,
						                    shadowColor: 'rgba(0, 0, 0, 0.5)'
						                }
					            }
					        }
					    ]
					};

			
			 myCharts1.setOption(option1);
			 $('#buyerTotalId').resize(function () {
				 myCharts1.resize();
		 });
			
		} 
   
   
	//主页showTransactionStatistics
	function showTransactionStatistics(){
		var date = new Date();
		date.setDate(date.getDate()-1);
	    var todayTime = getBeforeDayFormatDate(date);
	    var weekDate = new Date();
	    weekDate.setDate(weekDate.getDate()-7);
		var weekTime = getBeforeDayFormatDate(weekDate);
		vm.statisticsTimeStart=weekTime;
		vm.statisticsTimeEnd=todayTime;
		vm.showTransactionData = showTransactionData;
		showTransactionData(getValueForSelect(weekTime),getValueForSelect(todayTime));
			
	    //初始化数据
	    function showTransactionData(weekTime,todayTime){
		   MainService.TransactionStatisticsAll()
	        .then(
					function(d) {
						 vm.transactionStatistics = d.body;
						 //console.log(vm.transactionStatistics);
						 showTransactionTable(vm.transactionStatistics,getValueForSelect(weekTime),getValueForSelect(todayTime));
					   },
	  					function(errResponse){
	  						console.error('Error while fetching showKpiData');
	  					}
			       );
			   }
	   }  
function showTransactionTable(SellerStatisticsData,dataStart,dataEnd){
		
		
		var myChart = echarts.init(document.getElementById('transactionStatisticsSum'));
		
		var sellerNameArr=[];
		sellerNameArr=[i18n('common.recharge'),i18n('common.payment'),i18n('common.withdraw')]
		var monthArr=getAll(dataStart,dataEnd);
		var sellerDataArr=[];
		
		        //请求数
		   		var requestObj=new Object();
		   		requestObj.name=i18n('common.recharge');
		   		requestObj.type='bar';
		   		requestObj.data=geneateTransactionNumArrOne(SellerStatisticsData,monthArr);
		   		
		   		var requestSuccObj=new Object();
		   		requestSuccObj.name=i18n('common.payment');
		   		requestSuccObj.type='bar';
		   		requestSuccObj.data=geneateTransactionNumArrTwo(SellerStatisticsData,monthArr);
		   		
		   		
		   		var requestSuccObjThree=new Object();
		   		requestSuccObjThree.name=i18n('common.withdraw');
		   		requestSuccObjThree.type='bar';
		   		requestSuccObjThree.data=geneateTransactionNumArrThree(SellerStatisticsData,monthArr);
		   		
		   		sellerDataArr=[requestObj,requestSuccObj,requestSuccObjThree]
		
		   var option = {
		            title: {
		                text: i18n('common.transactionStatistics'),
                        x:'center',
                        y:'top',
		            },
		            tooltip: {  trigger: 'axis'},
		            legend: {
		                data:sellerNameArr,
		                x:'center',
                        y:'bottom'
		            },
		            //解决y轴刻度显示不全
		            grid:{
		            	　　x:80,
		            },
		            xAxis: {
		            	type: 'category',
		              //  boundaryGap: false,
		                data: getformatmonth(monthArr)
		            },
		            yAxis: [{
		                splitLine: {show: false}
		            }],
		            series:sellerDataArr,
		            showSymbol: false,
		            barGap:"1%"
		            
		        };

		
		 myChart.setOption(option);
		 $('#transactionStatisticsId').resize(function () {
			 myChart.resize();
	 });
		
		
		
	}
	
	
	//获取Buyer请求数 
	function geneateTransactionNumArrOne(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchTransactionStatiscTimeOne(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchTransactionStatiscTimeOne(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         {
	    			 return selectData[i].rechargeNum;
	         }
	     }
	}
	
	//获取Buyer请求数 end
	
	
	
	//获取Buyer请求数 
	function geneateTransactionNumArrTwo(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchTransactionStatiscTimeTwo(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchTransactionStatiscTimeTwo(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         {
	    			 return selectData[i].paymentNum;
	         }
	     }
	}
	
	//获取Buyer请求数 end
	
	//获取Buyer请求数 
	function geneateTransactionNumArrThree(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchTransactionStatiscTimeThree(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchTransactionStatiscTimeThree(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         {
	    			 return selectData[i].chargeNum;
	         }
	     }
	}
	
	//获取Buyer请求数 end	
	
	//主页showSeller
	function showSeller(){
		var date = new Date();
		date.setDate(date.getDate()-1);
	    var todayTime = getBeforeDayFormatDate(date);
	    var weekDate = new Date();
	    weekDate.setDate(weekDate.getDate()-7);
		var weekTime = getBeforeDayFormatDate(weekDate);
		vm.statisticsTimeStart=weekTime;
		vm.statisticsTimeEnd=todayTime;
		vm.showSellerData = showSellerData;
		showSellerData(getValueForSelect(weekTime),getValueForSelect(todayTime));
			
	    //初始化数据
	    function showSellerData(weekTime,todayTime){
		   MainService.SellerStatistics()
	        .then(
					function(d) {
						 vm.SellerStatisticsData = d.body;
						// console.log(vm.BuyerStatisticsData);
						 showSellerTable(vm.SellerStatisticsData,getValueForSelect(weekTime),getValueForSelect(todayTime));
						 showSellerTableOther(vm.SellerStatisticsData,getValueForSelect(weekTime),getValueForSelect(todayTime));
					   },
	  					function(errResponse){
	  						console.error('Error while fetching showKpiData');
	  					}
			       );
			   }
	   }
function showSellerTable(SellerStatisticsData,dataStart,dataEnd){
		
		
		var myChart = echarts.init(document.getElementById('sellerSum'));
		
		var sellerNameArr=[];
		sellerNameArr=[i18n('common.sellerTotal'),i18n('common.clapStoreTotal'),i18n('common.otherSellerTotal')]
		var monthArr=getAll(dataStart,dataEnd);
		var sellerDataArr=[];
		
		        //请求数
		   		var requestObj=new Object();
		   		requestObj.name=i18n('common.sellerTotal');
		   		requestObj.type='bar';
		   		requestObj.data=geneateSellerNumArrOne(SellerStatisticsData,monthArr);
		   		
		   		var requestSuccObj=new Object();
		   		requestSuccObj.name=i18n('common.clapStoreTotal');
		   		requestSuccObj.type='bar';
		   		requestSuccObj.data=geneateSellerNumArrTwo(SellerStatisticsData,monthArr);
		   		
		   		
		   		var requestSuccObjThree=new Object();
		   		requestSuccObjThree.name=i18n('common.otherSellerTotal');
		   		requestSuccObjThree.type='bar';
		   		requestSuccObjThree.data=geneateSellerNumArrThree(SellerStatisticsData,monthArr);
		   		
		   		sellerDataArr=[requestObj,requestSuccObj,requestSuccObjThree]
		
		   var option = {
		            title: {
		                text: i18n('common.sellerStatistics'),
                        x:'center',
                        y:'top',
		            },
		            tooltip: {  trigger: 'axis'},
		            legend: {
		                data:sellerNameArr,
		                x:'center',
                        y:'bottom'
		            },
		            //解决y轴刻度显示不全
		            grid:{
		            	　　x:80,
		            },
		            xAxis: {
		            	type: 'category',
		              //  boundaryGap: false,
		                data: getformatmonth(monthArr)
		            },
		            yAxis: [{
		                splitLine: {show: false}
		            }],
		            series:sellerDataArr,
		            showSymbol: false,
		            barGap:"1%"
		            
		        };

		
		 myChart.setOption(option);
		 $('#sellerSumId').resize(function () {
			 myChart.resize();
	 });
		
		
		
	}
	
	
	//获取Buyer请求数 
	function geneateSellerNumArrOne(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchSellerStatiscTimeOne(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchSellerStatiscTimeOne(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         {
	    			 return selectData[i].seller;
	         }
	     }
	}
	
	//获取Buyer请求数 end
	
	
	
	//获取Buyer请求数 
	function geneateSellerNumArrTwo(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchSellerStatiscTimeTwo(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchSellerStatiscTimeTwo(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         {
	    			 return selectData[i].clapStore;
	         }
	     }
	}
	
	//获取Buyer请求数 end
	
	//获取Buyer请求数 
	function geneateSellerNumArrThree(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchSellerStatiscTimeThree(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchSellerStatiscTimeThree(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         {
	    			 return selectData[i].otherSeller;
	         }
	     }
	}
	
	//获取Buyer请求数 end	
	

function showSellerTableOther(SellerStatisticsData,dataStart,dataEnd){
		
		
		var myChart = echarts.init(document.getElementById('sellerIncrementalSum'));
		
		var sellerNameArr=[];
		sellerNameArr=[i18n('common.sellerIncremental'),i18n('common.clapStoreIncremental'),i18n('common.otherSellerIncremental')]
		var monthArr=getAll(dataStart,dataEnd);
		var sellerDataArr=[];
		
		        //请求数
		   		var requestObj=new Object();
		   		requestObj.name=i18n('common.sellerIncremental');
		   		requestObj.type='line';
		   		requestObj.data=geneateSellerNumArrOneOther(SellerStatisticsData,monthArr);
		   		
		   		var requestSuccObj=new Object();
		   		requestSuccObj.name=i18n('common.clapStoreIncremental');
		   		requestSuccObj.type='line';
		   		requestSuccObj.data=geneateSellerNumArrTwoOther(SellerStatisticsData,monthArr);
		   		
		   		
		   		var requestSuccObjThree=new Object();
		   		requestSuccObjThree.name=i18n('common.otherSellerIncremental');
		   		requestSuccObjThree.type='line';
		   		requestSuccObjThree.data=geneateSellerNumArrThreeOther(SellerStatisticsData,monthArr);
		   		
		   		sellerDataArr=[requestObj,requestSuccObj,requestSuccObjThree]
		
		   var option = {
		            title: {
		                text: i18n('common.sellerIncrementalTotal'),
                        x:'center',
                        y:'top',
		            },
		            tooltip: {  trigger: 'axis'},
		            legend: {
		                data:sellerNameArr,
		                x:'center',
                        y:'bottom'
		            },
		            //解决y轴刻度显示不全
		            grid:{
		            	　　x:80,
		            },
		            xAxis: {
		            	type: 'category',
		              //  boundaryGap: false,
		                data: getformatmonth(monthArr)
		            },
		            yAxis: [{
		                splitLine: {show: false}
		            }],
		            series:sellerDataArr,
		            showSymbol: false
		            
		        };

		
		 myChart.setOption(option);
		 $('#sellerIncrementalId').resize(function () {
			 myChart.resize();
	 });
		
		
		
	}
	
	
	//获取Buyer请求数 
	function geneateSellerNumArrOneOther(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchSellerStatiscTimeOneOther(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchSellerStatiscTimeOneOther(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         {
	    		 
	    		 if(i+ 1<selectData.length){
		    	       var increateDate =selectData[i].seller-selectData[i+1].seller;
		    	   }else{
		    		   var increateDate =selectData[i].seller
		    	   }
	    			 return increateDate;
	         }
	     }
	}
	
	//获取Buyer请求数 end
	
	
	
	//获取Buyer请求数 
	function geneateSellerNumArrTwoOther(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchSellerStatiscTimeTwoOther(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchSellerStatiscTimeTwoOther(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         {
	    		 
	    		 if(i+ 1<selectData.length){
		    	       var increateDate =selectData[i].clapStore-selectData[i+1].clapStore;
		    	   }else{
		    		   var increateDate =selectData[i].clapStore
		    	   }
	    			 return increateDate;
	         }
	     }
	}
	
	//获取Buyer请求数 end
	
	//获取Buyer请求数 
	function geneateSellerNumArrThreeOther(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchSellerStatiscTimeThreeOther(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchSellerStatiscTimeThreeOther(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         { 
	    		 
	    		 if(i+ 1<selectData.length){
		    	       var increateDate =selectData[i].otherSeller-selectData[i+1].otherSeller;
		    	   }else{
		    		   var increateDate =selectData[i].otherSeller
		    	   }
	    			 return increateDate;
	         }
	     }
	}
	
	//获取Buyer请求数 end	
	

	
	
	
	//主页Buyer Start
	function showBuyer(){
		var date = new Date();
		date.setDate(date.getDate()-1);
	    var todayTime = getBeforeDayFormatDate(date);
	    var weekDate = new Date();
	    weekDate.setDate(weekDate.getDate()-7);
		var weekTime = getBeforeDayFormatDate(weekDate);
		vm.statisticsTimeStart=weekTime;
		vm.statisticsTimeEnd=todayTime;
		vm.showBuyerData = showBuyerData;
		showBuyerData(getValueForSelect(weekTime),getValueForSelect(todayTime));
			
	    //初始化数据
	    function showBuyerData(weekTime,todayTime){
		   MainService.BuyerStatistics(weekTime,todayTime)
	        .then(
					function(d) {
						 vm.BuyerStatisticsData = d.body;
						// console.log(vm.BuyerStatisticsData);
						 showBuyerTable(vm.BuyerStatisticsData,getValueForSelect(weekTime),getValueForSelect(todayTime));
						 showBuyerTableOther(vm.BuyerStatisticsData,getValueForSelect(weekTime),getValueForSelect(todayTime));
					   },
	  					function(errResponse){
	  						console.error('Error while fetching showKpiData');
	  					}
			       );
			   }
	   }
function showBuyerTable(BuyerStatisticsData,dataStart,dataEnd){
		
		
		var myChart = echarts.init(document.getElementById('buyerSum'));
		
		var buyerNameArr=[];
		buyerNameArr=[i18n('consumerUserActiveStatistics.totalUserNum')+"(k)",i18n('consumerUserActiveStatistics.totalActiveUserNum')+"(k)"]
		var monthArr=getAll(dataStart,dataEnd);
		var buyerDataArr=[];
		
		        //请求数
		   		var requestObj=new Object();
		   		requestObj.name=i18n('consumerUserActiveStatistics.totalUserNum')+"(k)";
		   		requestObj.type='bar';
		   		requestObj.data=geneateBuyerNumArr(BuyerStatisticsData,monthArr);
		   		
		   		var requestSuccObj=new Object();
		   		requestSuccObj.name=i18n('consumerUserActiveStatistics.totalActiveUserNum')+"(k)";
		   		requestSuccObj.type='bar';
		   		requestSuccObj.data=geneateBuyerSuccessNumArr(BuyerStatisticsData,monthArr);
		   		buyerDataArr=[requestObj,requestSuccObj]
		
		   var option = {
		            title: {
		                text: i18n('common.buyerStatistics'),
                        x:'center',
                        y:'top',
		            },
		            tooltip: {  trigger: 'axis'},
		            legend: {
		                data:buyerNameArr,
		                x:'center',
                        y:'bottom'
		            },
		            //解决y轴刻度显示不全
		            grid:{
		            	　　x:80,
		            },
		            xAxis: {
		            	type: 'category',
		              //  boundaryGap: false,
		                data: getformatmonth(monthArr)
		            },
		            yAxis: [{
		                splitLine: {show: false}
		            }],
		            series:buyerDataArr,
		            showSymbol: false,
		            barGap:"1%"
		            
		        };

		
		 myChart.setOption(option);
		 $('#buyerSumId').resize(function () {
			 myChart.resize();
	 });
		
		
		
	}
	
	
	//获取Buyer请求数 
	function geneateBuyerNumArr(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchBuyerStatiscTime(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchBuyerStatiscTime(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         {
	    			 return selectData[i].totalUserNum;
	         }
	     }
	}
	
	//获取Buyer请求数 end
	
	
	//Buyer请求成功数
	function geneateBuyerSuccessNumArr(selectData,monthArr)
	
	{
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchBuyerSuccStatiscTime(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}
	
	
	
	function searchBuyerSuccStatiscTime(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();
	    	 if( date== time)
	         {
	    		return selectData[i].totalActiveUserNum;
	         }
	     }
	}
	//主页buyer Start
	
	
function showBuyerTableOther(BuyerStatisticsData,dataStart,dataEnd){
		
		
		var myChart = echarts.init(document.getElementById('buyerIncrementalSum'));
		
		var buyerNameArr=[];
		buyerNameArr=[i18n('common.buyerIncremental')+"(k)",i18n('common.buyerActiveIncremental')+"(k)"]
		var monthArr=getAll(dataStart,dataEnd);
		var buyerDataArr=[];
		
		        //请求数
		   		var requestObj=new Object();
		   		requestObj.name=i18n('common.buyerIncremental')+"(k)";
		   		requestObj.type='line';
		   		requestObj.data=geneateBuyerNumArrOther(BuyerStatisticsData,monthArr);
		   		
		   		var requestSuccObj=new Object();
		   		requestSuccObj.name=i18n('common.buyerActiveIncremental')+"(k)";
		   		requestSuccObj.type='line';
		   		requestSuccObj.data=geneateBuyerSuccessNumArrOther(BuyerStatisticsData,monthArr);
		   		buyerDataArr=[requestObj,requestSuccObj]
		
		   var option = {
		            title: {
		                text: i18n('common.buyerIncrementalTotal'),
                        x:'center',
                        y:'top',
		            },
		            tooltip: {  trigger: 'axis'},
		            legend: {
		                data:buyerNameArr,
		                x:'center',
                        y:'bottom'
		            },
		            //解决y轴刻度显示不全
		            grid:{
		            	　　x:80,
		            },
		            xAxis: {
		            	type: 'category',
		              //  boundaryGap: false,
		                data: getformatmonth(monthArr)
		            },
		            yAxis: [{
		                splitLine: {show: false}
		            }],
		            series:buyerDataArr,
		            showSymbol: false
		            
		        };

		
		 myChart.setOption(option);
		 $('#buyerIncrementalId').resize(function () {
			 myChart.resize();
	 });
		
		
		
	}
	
	
	//获取Buyer请求数 
	function geneateBuyerNumArrOther(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchBuyerStatiscTimeOther(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchBuyerStatiscTimeOther(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         {
	    		 if(i+ 1<selectData.length){
		    	       var increateDate =selectData[i].totalUserNum-selectData[i+1].totalUserNum;
		    	   }else{
		    		   var increateDate =selectData[i].totalUserNum
		    	   }
	    			 return increateDate;
	         }
	     }
	}
	
	//获取Buyer请求数 end
	
	
	//Buyer请求成功数
	function geneateBuyerSuccessNumArrOther(selectData,monthArr)
	
	{
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchBuyerSuccStatiscTimeOther(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}
	
	
	
	function searchBuyerSuccStatiscTimeOther(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
	    	 
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();
	    	 if( date== time)
	         {
	    		 
	    		 if(i+ 1<selectData.length){
		    	       var increateDate =selectData[i].totalActiveUserNum-selectData[i+1].totalActiveUserNum;
		    	   }else{
		    		   var increateDate =selectData[i].totalActiveUserNum
		    	   }
	    		 
	    		return increateDate;
	         }
	     }
	}
	//主页buyer Start
	
	//主页KPI Start
	function showKpi(){
		var date = new Date();
		date.setDate(date.getDate());
	    var todayTime = getBeforeDayFormatDate(date);
	    var weekDate = new Date();
	    weekDate.setDate(weekDate.getDate()-29);
		var weekTime = getBeforeDayFormatDate(weekDate);
		vm.statisticsTimeStart=weekTime;
		vm.statisticsTimeEnd=todayTime;
		vm.showKpiData = showKpiData;
		showKpiData(getValueForSelect(weekTime),getValueForSelect(todayTime));
			
	    //初始化数据
	    function showKpiData(weekTime,todayTime){
		   MainService.KpiStatistics(weekTime,todayTime)
	        .then(
					function(d) {
						 vm.kpiStatisticsData = d.body;
						 showKpiTable(vm.kpiStatisticsData,getValueForSelect(weekTime),getValueForSelect(todayTime));
					   },
	  					function(errResponse){
	  						console.error('Error while fetching showKpiData');
	  					}
			       );
			   }
	   }
	
	
	
	function showKpiTable(kpiStatisticsData,dataStart,dataEnd){
		
		
		var myChart = echarts.init(document.getElementById('kpi'));
		
		var kpiNameArr=[];
		kpiNameArr=[i18n('requestNum'),i18n('requestSuccNum')]
		var monthArr=getAll(dataStart,dataEnd);
		var kpiDataArr=[];
		
		        //请求数
		   		var requestObj=new Object();
		   		requestObj.name=i18n('requestNum');
		   		requestObj.type='line';
		   		requestObj.data=geneateKPiNumArr(kpiStatisticsData,monthArr);
		   		
		   		var requestSuccObj=new Object();
		   		requestSuccObj.name=i18n('requestSuccNum');
		   		requestSuccObj.type='line';
		   		requestSuccObj.data=geneateKpiSuccessNumArr(kpiStatisticsData,monthArr);
		   		kpiDataArr=[requestObj,requestSuccObj]
		
		   var option = {
		            title: {
		                text: i18n('common.requestTopKpiStatistics'),
                        x:'center',
                        y:'top',
		            },
		            tooltip: {  trigger: 'axis'},
		            legend: {
		                data:kpiNameArr,
		                x:'center',
                        y:'bottom'
		            },
		            //解决y轴刻度显示不全
		            grid:{
		            	　　x:80,
		            },
		            xAxis: {
		            	type: 'category',
		                boundaryGap: false,
		                data: getformatmonth(monthArr)
		            },
		            yAxis: [{
		                splitLine: {show: false}
		            }],
		            series:kpiDataArr,
		            showSymbol: false
		            
		        };

		
		 myChart.setOption(option);
		 $('#kpiId').resize(function () {
			 myChart.resize();
	 });
		
		
		
	}
	
	
	//获取kpi请求数 
	function geneateKPiNumArr(selectData,monthArr)
	{
		//console.log(selectData);
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchKpiStatiscTime(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}

	function searchKpiStatiscTime(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
	    	 if( date== time)
	         {
	    			 return selectData[i].requestNumber;
	         }
	     }
	}
	
	//获取kpi请求数 end
	
	
	//kpi请求成功数
	function geneateKpiSuccessNumArr(selectData,monthArr)
	
	{
	    var succStr=[];
		for(var j=0;j<monthArr.length;j++)
		{
			 var successNum=searchKpiSuccStatiscTime(selectData,monthArr[j]);
			if(successNum)
			{
				succStr.push(successNum);	
			}else{
				succStr.push(0);
			}
		}
		return succStr;
	}
	
	
	
	function searchKpiSuccStatiscTime(selectData,time)
	{
	     for(var i=0;i<selectData.length;i++)
	     {
				var ab = selectData[i].statisticsTime.split("-");
				var db = new Date();
				db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
		    	 var date=db.format();
	    	 if( date== time)
	         {
	    		return selectData[i].requestSuccNumber;
	         }
	     }
	}
	//主页KPI Start
	
	function showInterface(){
	var date = new Date();
	date.setDate(date.getDate());
    var todayTime = getBeforeDayFormatDate(date);
    var weekDate = new Date();
    weekDate.setDate(weekDate.getDate()-6);
		var weekTime = getBeforeDayFormatDate(weekDate);
		vm.statisticsTimeStart=weekTime;
		vm.statisticsTimeEnd=todayTime;
		vm.showData = showData;
		showData(getValueForSelect(weekTime),getValueForSelect(todayTime));
		
		//初始化数据
		   function showData(weekTime,todayTime){
			   MainService.InterfaceStatistics(weekTime,todayTime)
		        .then(
						       function(d) {
							        vm.interfaceStatisticsData = d.body;
							    	showTable(vm.interfaceStatisticsData,getValueForSelect(weekTime),getValueForSelect(todayTime));
						       },
		  					function(errResponse){
		  						console.error('Error while fetching showData');
		  					}
				       );
		  
		   }
	}
			function showTable(interfaceStatisticsData,dataStart,dataEnd){
				//echarts.dispose(document.getElementById('main'));
			
				var myChart = echarts.init(document.getElementById('main'));
				
				var bankNameArr=[];
				bankNameArr=[i18n('requestNum'),i18n('requestSuccNum')]
				var monthArr=getAll(dataStart,dataEnd);
				var bankArr=[];
				
				   		var bank1=new Object();
				   		bank1.name=i18n('requestNum');
				   		bank1.type='line';
				   		bank1.data=geneateNumArr(interfaceStatisticsData,monthArr);
				   		var bank2=new Object();
				   		bank2.name=i18n('requestSuccNum');
				   		bank2.type='line';
				   		bank2.data=geneateSuccessNumArr(interfaceStatisticsData,monthArr);
                        bankArr=[bank1,bank2]
				
				 var option = {
				            title: {
				                text: i18n('common.interFaceStatistics'),
                                x:'center',
                                y:'top',
				            },
				            tooltip: {  trigger: 'axis'},
				            legend: {
				                data:bankNameArr,
				                x:'center',
                                y:'bottom'
				            },
				            //解决y轴刻度显示不全
				            grid:{
				            	　　x:80,
				            },
				            xAxis: {
				            	type: 'category',
				                boundaryGap: false,
				                data: getformatmonth(monthArr)
				            },
				            yAxis: [{
				                splitLine: {show: false}
				            }],
				            series:bankArr,
				            showSymbol: false
				            
				        };

				
				 myChart.setOption(option);
				 $('#mainId').resize(function () {
					 myChart.resize();
			 });
				
			}

			function showBankInterface(){
				var date = new Date();
				date.setDate(date.getDate());
			    var todayTime = getBeforeDayFormatDate(date);
			    var weekDate = new Date();
			    weekDate.setDate(weekDate.getDate()-6);
					var weekTime = getBeforeDayFormatDate(weekDate);
					vm.statisticsTimeStart=weekTime;
					vm.statisticsTimeEnd=todayTime;
					vm.showData = showData;
					showData(getValueForSelect(weekTime),getValueForSelect(todayTime));
					
					//初始化数据
					   function showData(weekTime,todayTime){
						   MainService.BankInterfaceStatistics(weekTime,todayTime)
					        .then(
									       function(d) {
										        vm.bankInterfaceStatisticsData = d.body;
										    	showTableBank(vm.bankInterfaceStatisticsData,getValueForSelect(weekTime),getValueForSelect(todayTime));
									       },
					  					function(errResponse){
					  						console.error('Error while fetching showData');
					  					}
							       );
					  
					   }
				}
						function showTableBank(bankInterfaceStatisticsData,dataStart,dataEnd){

							var myChart = echarts.init(document.getElementById('bank'));
							
							var bankNameArr=[];
							bankNameArr=[i18n('requestNum'),i18n('requestSuccNum')]
							var monthArr=getAll(dataStart,dataEnd);
							var bankArr=[];
							
							   		var bank1=new Object();
							   		bank1.name=i18n('requestNum');
							   		bank1.type='line';
							   		bank1.data=geneateNumArr(bankInterfaceStatisticsData,monthArr);
							   		var bank2=new Object();
							   		bank2.name=i18n('requestSuccNum');
							   		bank2.type='line';
							   		bank2.data=geneateSuccessNumArr(bankInterfaceStatisticsData,monthArr);
			                        bankArr=[bank1,bank2]
							
							 var option = {
							            title: {
							                text: i18n('common.bankiInterFaceStatistics'),
			                                x:'center',
			                                y:'top'
							            },
							            tooltip: {  trigger: 'axis'},
							            legend: {
							                data:bankNameArr,
							                x:'center',
			                                y:'bottom'
							            },
							            //解决y轴刻度显示不全
							            grid:{
							            	　　x:80,
							            },
							            xAxis: {
							            	type: 'category',
							                boundaryGap: false,
							                data: getformatmonth(monthArr)
							            },
							            yAxis: [{
							                splitLine: {show: false}
							            }],
							            series:bankArr,
							            showSymbol: false
							            
							        };

							
							 myChart.setOption(option);
							 $('#bankId').resize(function () {
								 myChart.resize();
						 });
							
						}		
			
			
			
			function geneateSuccessNumArr(selectData,monthArr)
			{
			    var succStr=[];
				for(var j=0;j<monthArr.length;j++)
				{
					 var successNum=searchBankIdAndStatiscTime(selectData,monthArr[j]);
					//bankId selectData[i].bankd
					if(successNum)
					{
						succStr.push(successNum);	
					}else{
						succStr.push(0);
					}
						
				}
				return succStr;
			}
			
			function getformatmonth(data){
				var naLan = navigator.language;
				if (naLan == undefined || naLan == "") {
					naLan = navigator.browserLanguage;
				}	
				if(naLan=='zh'){
				return  data; 
				}else if(naLan.indexOf("zh") >= 0){
					return  data; 
				}else{
					var monthData=[];
					for(var i=0;i<data.length;i++){
						2017-9-8
						if(data[i].length==8){
							monthData[i]="0"+data[i].substring(7,8)+"/0"+data[i].substring(5,6)+"/"+data[i].substring(0,4)
						}if(data[i].length==9){
							if(data[i].substring(6,7)=="-"){
								monthData[i]=data[i].substring(7,9)+"/0"+data[i].substring(5,6)+"/"+data[i].substring(0,4);
							}else{
							    monthData[i]="0"+data[i].substring(8,9)+"/"+data[i].substring(5,7)+"/"+data[i].substring(0,4);
							}
						}
						if(data[i].length==10){
								monthData[i]=data[i].substring(8,10)+"/"+data[i].substring(5,7)+"/"+data[i].substring(0,4); 
							}
						}
					return monthData;
				 }
				}

			function searchBankIdAndStatiscTime(selectData,time)
			{
			     for(var i=0;i<selectData.length;i++)
			     {

					var ab = selectData[i].statisticsTime.split("-");
					var db = new Date();
					db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
			    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
			    	 if( date== time)
			         {
			    		  
			    			 return selectData[i].countRequestSuccNum;
			    		 
			         }
			     }
			}
			
			
			
			function geneateNumArr(selectData,monthArr)
			{
			    var succStr=[];
				for(var j=0;j<monthArr.length;j++)
				{
					 var successNum=searchStatiscTime(selectData,monthArr[j]);
					//bankId selectData[i].bankd
					if(successNum)
					{
						succStr.push(successNum);	
					}else{
						succStr.push(0);
					}
						
				}
				return succStr;
			}


			function searchStatiscTime(selectData,time)
			{
			     for(var i=0;i<selectData.length;i++)
			     {
						var ab = selectData[i].statisticsTime.split("-");
						var db = new Date();
						db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
				    	 var date=db.format();//new Date(selectData[i].statisticsTime.substring(0,10)).format("yy-MM-dd");
			    	 if( date== time)
			         {
			    		  
			    			 return selectData[i].countRequestNum;
			    		 
			         }
			     }
			}
			Date.prototype.format=function (){
			    var s='';
			    s+=this.getFullYear()+'-';          // 获取年份。
			    s+=(this.getMonth()+1)+"-";         // 获取月份。
			    s+= this.getDate();                 // 获取日。
			    return(s);                          // 返回日期。
			};

			function getAll(begin,end){
				var dataArr=[];
			    var ab = begin.split("-");
			    var ae = end.split("-");
			    var db = new Date();
			    db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
			    var de = new Date();
			    de.setUTCFullYear(ae[0], ae[1]-1, ae[2]);
			    var unixDb=db.getTime();
			    var unixDe=de.getTime();
			    for(var k=unixDb;k<=unixDe;k=k+24*60*60*1000){
			   // console.log((new Date(parseInt(k))).format());
			    dataArr.push((new Date(parseInt(k))).format());
			    
			    }
			    
			    return dataArr;
			}
		

	
	$scope.$on('ngRepeatFinished', function (ngRepeatFinishedEvent) {  
        //下面是在table render完成后执行的js  
        //FormEditable.init();  
        //Metronic.stopPageLoading();  
  
        //$(".simpleTab").show();  
		renderFinish();
  
    }); 
	
	$scope.$on('ngRepeatAlarmFinished', function (ngRepeatFinishedEvent) {  
		renderAlarmFinish();
    }); 
	
}

mainApp.directive('onFinishRenderFilters', function ($timeout) {  
    return {  
        restrict: 'A',  
        link: function(scope,element,attr) {  
            if (scope.$last === true) {  
                $timeout(function() {  
                    scope.$emit('ngRepeatFinished');  
                });  
  
            }  
        }  
    };  
});

mainApp.directive('onAlarmFinishRenderFilters', function ($timeout) {  
    return {  
        restrict: 'A',  
        link: function(scope,element,attr) {  
            if (scope.$last === true) {  
                $timeout(function() {  
                    scope.$emit('ngRepeatAlarmFinished');  
                });  
  
            }  
        }  
    };  
});



