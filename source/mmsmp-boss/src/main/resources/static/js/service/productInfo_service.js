'use strict';

App.factory('productInfoService', ['$http', '$q', function($http, $q){

	return {
		    
			searchAllSpInfo: function() {
					return $http.get(apiUrl +"/spInfo")
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
			  enable: function(id){
					return $http.put(apiUrl +"/productInfo/enable/"+id)
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
			searchAllProductInfo: function() {
				return $http.get(apiUrl +"/productInfo")
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
		    createProductInfo: function(productInfo){
					return $http.post(apiUrl +"/productInfo", productInfo)
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
		    
		    updateProductInfo: function(productInfo, id){
					return $http.put(apiUrl +"/productInfo/"+id, productInfo)
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
		
			deleteProductInfo: function(id){
					return $http['delete'](apiUrl +"/productInfo/"+id)
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