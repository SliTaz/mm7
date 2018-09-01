'use strict';

App.factory('AlarmLevelTypeService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllAlarmLevelTypes: function() {
					return $http.get(apiUrl +"/alarmLevelType")
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
		    
		    createAlarmLevelType: function(alarmLevelType){
		    	var a =alarmLevelType;
		    	var str =a.alarmTypeCode.join(",");
		    	a.alarmTypeCode=str;
					return $http.post(apiUrl +"/alarmLevelType", alarmLevelType)
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
		    
		    updateAlarmLevelType: function(alarmLevelType,alarmLevelCode,alarmTypeCode){
			    	var a =alarmLevelType;
			    	var str =a.alarmTypeCode.toString();
			    	a.alarmTypeCode=str;
					return $http.put(apiUrl +"/alarmLevelType/"+alarmLevelCode+"/"+alarmTypeCode, alarmLevelType)
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
			selAlarmLevelType: function() {
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
		     
			selectByAlarmLevelId: function(id){
					return $http.get(apiUrl +"/alarmLevelType/"+id)
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
		    
			deleteAlarmLevelType: function(alarmLevelCode,alarmTypeCode){
					return $http['delete'](apiUrl +"/alarmLevelType/"+alarmLevelCode+"/"+alarmTypeCode)
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
