'use strict';

App.factory('TaskInstanceService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllTaskInstances: function() {
					return $http.get(apiUrl +"/taskInstance")
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
		    
			createTaskInstance: function(taskInstance){
					return $http.post(apiUrl +"/taskInstance", taskInstance)
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
		    
		    updateTaskInstance: function(taskInstance, id){
					return $http.put(apiUrl +"/taskInstance/"+id, taskInstance)
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
		    
			seltaskInstance: function() {
				return $http.get(apiUrl +"/task")
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
     //用户未执行
     unexecuted: function(id){
			return $http.put(apiUrl +"/taskInstance/unexecuted/"+id)
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
	//执行中
	executed: function(id){
		return $http.put(apiUrl +"/taskInstance/executed/"+id)
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
//成功
success: function(id){
	return $http.put(apiUrl +"/taskInstance/success/"+id)
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
//失败
failure: function(id){
	return $http.put(apiUrl +"/taskInstance/failure/"+id)
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
		
deleteTaskInstanceId: function(id){
					return $http['delete'](apiUrl +"/taskInstance/"+id)
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

