

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app>
<head>
<title>Goals Report</title>
	<style type="text/css">
		.cell {
			display: inline-block;
			vertical-align: top;
			width: 100px;
		}
		.cell-header .cell {
			font-weight: bold;
		}
	</style>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.8/angular.min.js"></script>
	<script>
		function GoalReports($scope, $http) {
			$http.get('GoalReports.json').
					success(function(data) {
						$scope.goalReports = data;
					});
		}
	</script>

</head>
<body>

<div class="navbar navbar-fixed-top navbar-inverse">
	<div class="navbar-inner">
		<div class="container">
			<a class="brand" href="/">
				Home
			</a>
			<ul class="nav">
			</ul>
		</div>
	</div>
</div>

<h3>Reports: {{goalReports.length}}</h3>
	
	<div class="cell-header">
		<div class="cell">Minutes</div>
		<div class="cell">Exercise Minutes</div>
		<div class="cell">Activity</div>
	</div>


	<div ng-controller="GoalReports">

		<div class="goalReports-container">
			<div ng-repeat="goalReport in goalReports">
				<div class="cell">{{goalReport.goalMinutes}}</div>
				<div class="cell">{{goalReport.exerciseMinutes}}</div>
				<div class="cell">{{goalReport.exerciseActivity}}</div>
			</div>
		</div>
	</div>


<script src="js/bootstrap.js"></script>
</body>
</html>