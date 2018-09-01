'use strict';

App.factory('AlarmMangerService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllAlarmMangers: function() {
					return $http.get(apiUrl +"/alarmManger")
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
		    
		    createAlarmManger: function(alarmManger){
					return $http.post(apiUrl +"/alarmManger", alarmManger)
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
		    
		    updateAlarmManger: function(alarmManger, id){
					return $http.put(apiUrl +"/alarmManger/"+id, alarmManger)
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
		     selalarmEmailManage: function() {
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
		     
			deleteAlarmManger: function(id){
					return $http['delete'](apiUrl +"/alarmManger/"+id)
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
