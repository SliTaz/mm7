'use strict';

var App = angular.module('myApp',[]);

//���ҳ�����غ�,�������������д�λ������start
$(".sidebar-toggle").click(function(){
	var timesRun = 0;
	var interval = setInterval(function(){
	timesRun += 1;
	if(timesRun === 1){
	clearInterval(interval);
	}
	try{
	  $("#table_id").dataTable().fnAdjustColumnSizing(false);//���¼����п�
	}catch(e){
		
	 }
	}, 300);
	
})
//���ҳ�����غ�,�������������д�λ������end
