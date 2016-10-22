/**
 * 
 */
var service = angular.module('ViolationStatusService', []);
service.factory('ViolationStatusFactory', function($http) {
    var factory = {};
    factory.getStatusesOfViolations = function() {
    	return $http.get("/sonarreport/app/statuses/").then(function(response) {
        	var payLoad = response.data;
    		if (!payLoad.successful) {
    			return;
    		}
    		
            return payLoad.data;
    	})
    }
    return factory;
});