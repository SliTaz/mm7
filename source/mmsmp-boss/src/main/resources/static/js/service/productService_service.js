'use strict';

App.factory('productServiceService', ['$http', '$q', function($http, $q){

	return {
		
			searchAllProductService: function() {
					return $http.get(apiUrl +"/productService")
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
		    createProductService: function(productService){
					return $http.post(apiUrl +"/productService", productService)
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
		    
		    updateProductService: function(productService, id){
					return $http.put(apiUrl +"/productService/"+id, productService)
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
		
			deleteProductService: function(id){
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