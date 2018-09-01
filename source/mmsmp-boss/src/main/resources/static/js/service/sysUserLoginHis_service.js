'use strict';

App.factory('loginHisService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAll: function() {
					return $http.get(apiUrl +"/sysUserLoginHis")
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
		    
		    create: function(userClap){
		    	
					return $http.post(apiUrl +"/sysUserLoginHis", userClap)
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
		    
		    update: function(loginHis, id){
					return $http.put(apiUrl +"/sysUserLoginHis/"+id, loginHis)
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
			deleteSysLogMany: function(id){
				return $http['delete'](apiUrl +"/sysUserLoginHis/deleteMany/"+id)
						.then(
								function(response){
								//	location.reload();
									return response.data;
								}, 
								function(errResponse){
									handleAjaxError(errResponse);
									return $q.reject(errResponse);
								}
						);
		  },
			deletes: function(id){
					return $http['delete'](apiUrl +"/sysUserLoginHis/"+id)
							.then(
									function(response){
									//	location.reload();
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
