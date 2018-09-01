'use strict';

App.factory('MobileSegementService', ['$http', '$q', function($http, $q){

	return {
		
			searchAllMobileSegement: function() {
					return $http.get(apiUrl +"/mobileSegment")
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
			searchAllProvinceCity: function() {
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
		   searchAllProvince: function() {
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
		    createMobileSegement: function(mobileSegment){
					return $http.post(apiUrl +"/mobileSegment", mobileSegment)
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
		    
		    updateMobileSegement: function(mobileSegment, id){
					return $http.put(apiUrl +"/mobileSegment/"+id, mobileSegment)
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
		
			deleteMobileSegement: function(id){
					return $http['delete'](apiUrl +"/mobileSegment/"+id)
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
