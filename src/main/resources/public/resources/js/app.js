var app = angular.module('piApp', []);

//var serverUrl = 'localhost:8080';
var serverUrl = '10.0.0.245:8080';

app.controller('TemperatureController', ['$scope', '$http', function ($scope, $http) {
	
    google.load("visualization", "1", {packages:["gauge"]});
    
    function drawChart() {

	    	var data = google.visualization.arrayToDataTable([
	        ['Label', 'Value'],
	        ['Temperature', 0],
	        ]);
	
			var options = {
				width: 400, height: 120,
			    redFrom: 35, redTo: 50,
			    yellowFrom: 25, yellowTo: 35,
			    greenFrom: 12, greenTo: 25,
			    max: 50, minorTicks: 5
			    
			};
	
			var chart = new google.visualization.Gauge(document.getElementById('chart'));
	
			chart.draw(data, options);
		
			setInterval(function() {
				$http.get('http://'+serverUrl+'/temperature')
					.success(function(result) {
		    	  		$scope.temperature = result;
		    	  		$scope.temperature.value = Math.round($scope.temperature.value * 100) / 100; 
		    	  		data.setValue(0, 1, $scope.temperature.value);
		    	  		chart.draw(data, options);
		    	  	});
			}, 2500);
    	}
    drawChart();
    
}]);

app.controller('LedController', ['$scope', '$http', function ($scope, $http) {
	
	$scope.on = function() {
		$http.post('http://'+serverUrl+'/on').
        success(function(data) {
            $scope.ledState = data;
        });
	};
	
	$scope.off = function() {
		$http.post('http://'+serverUrl+'/off').
        success(function(data) {
            $scope.ledState = data;
        });
	};
	
	setInterval(function() {
	    $http.get('http://'+serverUrl+'/state').
	        success(function(data) {
	            $scope.ledState = data;
	        });
	},5000);
	
}]);

app.controller('PinController', ['$scope', '$http', function ($scope, $http) {
    $http.get('http://'+serverUrl+'/pins').
        success(function(data) {
            $scope.pins = data;
        });
}]);