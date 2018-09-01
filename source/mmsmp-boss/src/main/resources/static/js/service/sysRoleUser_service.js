'use strict';

App.factory('SysRoleUserService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllSysRoleUsers: function() {
					return $http.get(apiUrl +"/sysRoleUser")
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
		    
		    createSysRoleUser: function(sysRoleUser){
					return $http.post(apiUrl +"/sysRoleUser", sysRoleUser)
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
		    
		    updateSysRoleUser: function(sysRoleUser, roleId,userId){
					return $http.put(apiUrl +"/sysRoleUser/"+roleId+"/"+userId, sysRoleUser)
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
		    
			deleteSysRoleUser: function(roleId,userId){
					return $http['delete'](apiUrl +"/sysRoleUser/"+roleId+"/"+userId)
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

