'use strict';

mainApp.factory('MainService', ['$http', '$q', function($http, $q){

	return {
		
		getNoticeRecords: function(notice){
				return $http.post(apiUrl +"/notice/getNoticeRecords", notice)
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
	    getAlarmRecords: function(alarmInfo){
				return $http.post(apiUrl +"/alarmInfo/getAlarmRecords", alarmInfo)
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
	    
	    InterfaceStatistics: function(weekTime,todayTime) {
			return $http.get( apiUrl + '/interfaceStatistics/daily?statisticsTimeStart=' + getValueForSelect(weekTime)+"&statisticsTimeEnd="+getValueForSelect(todayTime)+"&type=DAY")
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
	BankInterfaceStatistics: function(weekTime,todayTime) {
		return $http.get( apiUrl + '/bankInterfaceStatistics/daily/day?statisticsTimeStart=' + getValueForSelect(weekTime)+"&statisticsTimeEnd="+getValueForSelect(todayTime)+"&type=DAY")
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



   KpiStatistics: function(weekTime,todayTime) {
	   
		return $http.get( apiUrl + '/requestTopKpi?statisticsTimeStart=' + getValueForSelect(weekTime)+"&statisticsTimeEnd="+getValueForSelect(todayTime))
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
	
	TotalKpiStatistics: function() {
		   
			return $http.get( apiUrl + '/requestTopKpi/kpiStatisticsStatistics' )
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
	BuyerStatistics: function(weekTime,todayTime) {
		   
			return $http.get( apiUrl + '/consumerUserActiveStatistics/buyerStatistics')
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
		
		SellerStatistics: function() {
			   
			return $http.get( apiUrl + '/merchantActiveStatistics/sellerStatistics')
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
		
		TransactionStatisticsAll: function() {
			   
			return $http.get( apiUrl + '/interfaceStatistics/transactionStatistics')
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
		     
realTime: function() {
	return $http.get( apiUrl + "/bankInterfaceStatistics/realTimeData")
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
	    getNoticeById: function(id){
				return $http.post(apiUrl +"/notice/getNoticeById?id="+id)
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
	    getAlarmById: function(id){
			return $http.get(apiUrl +"/alarmInfo/"+id)
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
    alarmHandleView: function(alarmInfo, id){
		return $http.put(apiUrl +"/alarmInfo/handleView/"+id, alarmInfo)
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