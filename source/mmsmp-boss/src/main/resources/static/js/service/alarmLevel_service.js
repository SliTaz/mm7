'use strict';

App.factory('AlarmLevelService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllAlarmLevels: function() {
					return $http.get(apiUrl +"/alarmLevel")
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
		    
		    createAlarmLevel: function(alarmLevel){
					return $http.post(apiUrl +"/alarmLevel", alarmLevel)
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
		    
		    updateAlarmLevel: function(alarmLevel, id){
					return $http.put(apiUrl +"/alarmLevel/"+id, alarmLevel)
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
		    
			deleteAlarmLevel: function(id){
					return $http['delete'](apiUrl +"/alarmLevel/"+id)
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
