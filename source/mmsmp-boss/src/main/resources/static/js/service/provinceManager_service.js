'use strict';

App.factory('ProvinceService', ['$http', '$q', function($http, $q){

	return {
		
			searchAllProvinces: function() {
					return $http.get(apiUrl +"/province")
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
	
		    createProvince: function(provinceCity){
					return $http.post(apiUrl +"/province", provinceCity)
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
		    
		    updateProvince: function(provinceCity, id){
					return $http.put(apiUrl +"/province/"+id, provinceCity)
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
		
			deleteProvince: function(id){
					return $http['delete'](apiUrl +"/province/"+id)
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

