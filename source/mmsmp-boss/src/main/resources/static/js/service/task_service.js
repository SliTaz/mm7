'use strict';

App.factory('TaskService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllTasks: function() {
					return $http.get(apiUrl +"/taskq")
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
		    
			createTask: function(info){
					return $http.post(apiUrl +"/taskq", info)
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
		    
		    updateTask: function(info){
					return $http.put(apiUrl +"/taskq/", info)
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
			//暂停
			pause: function(jobName,jobGroup){
				return $http.get(apiUrl +"/taskq/pause/"+jobName+"/"+jobGroup)
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
			//恢复
		resume: function(jobName,jobGroup){
			return $http.get(apiUrl +"/taskq/resume/"+jobName+"/"+jobGroup)
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
			deleteTask: function(jobName,jobGroup){
					return $http['delete'](apiUrl +"/taskq/delete/"+jobName+"/"+jobGroup)
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

