/**
 * 
 */
var app = angular.module('ViolationStatus', ['ViolationStatusService']);

app.controller('ViolationStatusController', function($scope, ViolationStatusFactory) {
	ViolationStatusFactory.getStatusesOfViolations().then(function(stats){
    	google.charts.setOnLoadCallback(drawBarChart);
        function drawBarChart() {
            var data = google.visualization.arrayToDataTable([
                    [ 'Total Issues', 'Open', 'Closed' ], [ 'Count', stats[0].count, stats[1].count]]);

            var barChart = new google.charts.Bar(document.getElementById('openCloseGraph'));
            
            var options = {'width':300, 'colors': ['red', 'green']};


            barChart.draw(data, options);
        }
    })
});