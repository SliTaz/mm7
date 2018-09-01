'use strict';

App.factory('AlarmTypeService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllAlarmTypes: function() {
					return $http.get(apiUrl +"/alarmType")
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
		    
		    createAlarmType: function(alarmType){
					return $http.post(apiUrl +"/alarmType", alarmType)
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
		    
		    updateAlarmType: function(alarmType, id){
					return $http.put(apiUrl +"/alarmType/"+id, alarmType)
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
		    
			deleteAlarmType: function(id){
					return $http['delete'](apiUrl +"/alarmType/"+id)
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
