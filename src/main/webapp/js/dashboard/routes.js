/**
 * 
 */

var app = angular.module('CustomRouter', ['ui.router', 'dashboard', 'SeveritySummary', 'DeveloperViolations', 'RecentViolations', 'ViolationStatus']);

app.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider.state('dashboard', {
		url : '',
		views : { 
			'severity@' : {
				templateUrl: 'severity-tile/severitySummary.html',
				controller : 'SeveritySummaryController'
			},
			'recentViolations@' : {
				templateUrl: 'recent-violations/recent-violations.html',
				controller : 'RecentViolationController'
			},
			'developerViolations@' : {
				templateUrl: 'developer-tile/developerViolations.html',
				controller : 'DeveloperViolationController'
			},
			'violationStats@' : {
				templateUrl: 'violation-status/violation-status.html',
				controller : 'ViolationStatusController'
			}
		}
	});
});