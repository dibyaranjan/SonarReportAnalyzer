/**
 * 
 */
var service = angular.module('RecentSummaryService', []);

service.factory('RecentSummaryFactory', function($http) {
	var factory = {};
    factory.getRecentViolations = function() {
    	$('#loadingRecentViolations').show();
    	return $http.get("/sonarreport/app/stats/getRecentViolations").then(function(response){
    		return response.data;
    	});
    }
    
    return factory;
});

