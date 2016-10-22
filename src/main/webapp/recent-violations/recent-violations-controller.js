/**
 * 
 */

var app = angular.module('RecentViolations', ['RecentSummaryService']);

app.controller('RecentViolationController', function ($scope, RecentSummaryFactory) {
	RecentSummaryFactory.getRecentViolations().then(function(response) {
		$scope.recentViolations = response.data;
	});
});