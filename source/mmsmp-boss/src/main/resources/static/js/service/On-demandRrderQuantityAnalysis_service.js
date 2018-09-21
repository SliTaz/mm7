'use strict';

App.factory('OndemandRrderQuantityAnalysisService', ['$http', '$q', function($http, $q){

	return {				      		     		     
//			selectByUserId: function(id){
//					return $http.get(apiUrl +"/consumerGasCoupon/"+id)
//							.then(
//									function(response){
//										return response.data;
//									}, 
//									function(errResponse){
//										handleAjaxError(errResponse);
//										return $q.reject(errResponse);
//									}
//							);
//			},	
			selProductInfo: function() {
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
		
	};

}]);
