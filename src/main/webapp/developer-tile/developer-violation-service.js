/**
 * 
 */
var service = angular.module('DeveloperSummaryService', []);
service.factory('DeveloperSummaryFactory', function($http) {
	var factory = {};
    factory.getViolationsForAllDevelopers = function() {
        return $http.get("/sonarreport/app/users/").then(function(response) {
        	var payLoad = response.data;
    		if (!payLoad.successful) {
    			return;
    		}
    		
            return payLoad.data;
        });
    }
    
    return factory;
});