

'use strict';

App.factory('MenuService', ['$http', '$q', function($http, $q){

	return {
		
			fetchAllMenus: function() {
					return $http.get(apiUrl +"/menu")
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
		    
		    createMenu: function(menu){
					return $http.post(apiUrl +"/menu", menu)
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
		    
		    updateMenu: function(menu, id){
					return $http.put(apiUrl +"/menu/"+id, menu)
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
		    
			deleteMenu: function(id){
					return $http['delete'](apiUrl +"/menu/"+id)
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
