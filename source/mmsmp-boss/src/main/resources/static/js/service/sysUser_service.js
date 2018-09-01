'use strict';

App.factory('SysUserService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllSysUsers: function() {
					return $http.get(apiUrl +"/sysUser")
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
		    
		    createSysUser: function(sysUser){
		    	var a =sysUser;
		    	if(a.roleId!=undefined){
			    	var str =a.roleId.join(",");
			    	}
		    	a.roleId=str;
					return $http.post(apiUrl +"/sysUser", sysUser)
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
		    updateSysUser: function(sysUser, id){
		    	var a =sysUser;
		    	if(a.roleId!=undefined){
		    		if (typeof a.roleId === 'object'){
		             	var str =a.roleId.join(",");
		             	a.roleId=str;
		    		}
		    	}
					return $http.put(apiUrl +"/sysUser/"+id, sysUser)
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
			
			//重置密码
			resetDefaultPassword: function(sysUser, id){
		    	var a =sysUser;
		    	if(a.roleId!=undefined){
			    	var str =a.roleId.join(",");
			    	}
		    	a.roleId=str;
					return $http.put(apiUrl +"/sysUser/reset/defaultPassword/"+id, sysUser)
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
			// 解锁登陆密码
			lockedLoginPassword: function(userId,userName){
				return $http.put(apiUrl +"/sysUser/unlockLoginPassowrd?userId=" + getValueForSelect(userId)+"&userName="+getValueForSelect(userName))
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
				return $http.put(apiUrl +"/sysUser/enable/"+id)
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
			return $http.put(apiUrl +"/sysUser/disable/"+id)
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
		    
			fetchAllSysRoles: function() {
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
		     
		     
			selectByUserId: function(id){
					return $http.get(apiUrl +"/sysRoleUser/"+id)
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
		    
			deleteSysUser: function(id){
					return $http['delete'](apiUrl +"/sysUser/"+id)
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
