'use strict';

App.factory('TaskTypeService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllTaskTypes: function() {
					return $http.get(apiUrl +"/taskType")
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
		    
			createTaskType: function(taskType){
					return $http.post(apiUrl +"/taskType", taskType)
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
		    
		    updateTaskType: function(taskType, id){
					return $http.put(apiUrl +"/taskType/"+id, taskType)
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
			  //用户启用
		    enable: function(id){
				return $http.put(apiUrl +"/taskType/enable/"+id)
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
		//用户停用
	    disable: function(id){
			return $http.put(apiUrl +"/taskType/disable/"+id)
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
			deleteTaskType: function(id){
					return $http['delete'](apiUrl +"/taskType/"+id)
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
	
		
	};

}]);

