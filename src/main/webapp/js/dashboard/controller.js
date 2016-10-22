/**
 * 
 */
var app = angular.module('dashboard', ['DashboardService', 'PaginationFilter', 'SeveritySummaryService', 'DeveloperSummaryService']);

app.controller('DashboardController', function($scope, $http, DashboardTilesFactory) {
    DashboardTilesFactory.getTopViolatedMessages().then(function(response) {
        $scope.violatedMessages = response.data;
    });

    DashboardTilesFactory.getTopViolatingResources().then(function(response) {
        $scope.violatedResources = response.data;
    });
    
    $scope.showViolationsOfResource = function(resource) {
    	DashboardTilesFactory.getViolationsForResource(resource.resourcePath).then(function(response) {
    		$scope.violationsOfResource = response.data;
    		$scope.currentResource = resource.resourcePath;
    		$scope.totalPages = Math.max(1 ,Math.floor(response.data.length / 10));
    		$scope.currentPage = 1;
    		
    		$scope.previousPage = function() {
    			$scope.currentPage--;
    		}
    	    
    	    $scope.nextPage = function() {
    			$scope.currentPage++;
    		}
    	});
    }
});


app.controller('NavBarController', function($scope, NavBarFactory) {
	$scope.syncIssuesFromSonar = function() {
		NavBarFactory.syncIssuesFromSonar().then(function(response) {
			console.log(response);
		});    
	}
});


app.controller('ModalController', function($scope, NavBarFactory) {
	console.log('called the controller');
});


