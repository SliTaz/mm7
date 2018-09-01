'use strict';

App.factory('ProvinceCityService', ['$http', '$q', function($http, $q){

	return {
		
			searchAllProvinceCitys: function() {
					return $http.get(apiUrl +"/provinceCity")
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
		    createProvinceCity: function(provinceCity){
					return $http.post(apiUrl +"/provinceCity", provinceCity)
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
		    
		    updateProvinceCity: function(provinceCity, id){
					return $http.put(apiUrl +"/provinceCity/"+id, provinceCity)
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
		
			deleteProvinceCity: function(id){
					return $http['delete'](apiUrl +"/provinceCity/"+id)
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

