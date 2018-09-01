'use strict';

App.factory('AlarmEmailManageService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllBankInfos: function() {
					return $http.get(apiUrl +"/alarmEmailManage")
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										handleAjaxError(errResponse);
										return $q.reject(errResponse);
									}
							);
			},
		    
			createAlarmEmailManage: function(alarmEmailManage){
					return $http.post(apiUrl +"/alarmEmailManage", alarmEmailManage)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										handleAjaxError(errResponse);
										return $q.reject(errResponse);
									}
							);
		    },
		    
		    updateAlarmEmailManage: function(alarmEmailManage, id){
					return $http.put(apiUrl +"/alarmEmailManage/"+id, alarmEmailManage)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										handleAjaxError(errResponse);
										return $q.reject(errResponse);
									}
							);
			},
		    
			deleteAlarmEmailManage: function(id){
					return $http['delete'](apiUrl +"/alarmEmailManage/"+id)
							.then(
									function(response){
										return response.data;
									}, 
									function(errResponse){
										handleAjaxError(errResponse);
										return $q.reject(errResponse);
									}
							);
			}
		
	};

}]);
