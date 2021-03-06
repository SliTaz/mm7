'use strict';

App.factory('SysRoleMenuService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllRoles: function() {
					return $http.get(apiUrl +"/role")
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
		    
		    createRole: function(role){
					return $http.post(apiUrl +"/role", role)
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
		    
		    updateRole: function(role, id){
					return $http.put(apiUrl +"/role/"+id, role)
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
		    
			deleteRole: function(id){
					return $http['delete'](apiUrl +"/role/"+id)
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

