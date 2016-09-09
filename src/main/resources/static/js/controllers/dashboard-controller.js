angular.module('app').controller('dashboardController', function($scope, $localStorage, $state, $http, authService) {
	$scope.username =  $localStorage.session.user.username;
	$scope.notifications = $localStorage.session.user.directory.notifications;
	console.log($scope.notifications);
	$scope.logout = function() {
		authService.logout();
	}
	
	$scope.numberOfNotVisitedNotifications = function() {
		numOfNotifications = 0;
		
		for (i = 0; i < $scope.notifications.length; i++) {
			if ($scope.notifications[i].isVisited == false) {
				numOfNotifications++;
			}
		}
		
		return numOfNotifications;
	}
	
	
	$scope.actionClick = function(){
		requestData = $localStorage.session.user;
		
		$http.post('/server/userdirectory/visit_notifications', requestData)
		.then(function(response) {
			console.log("Notifications: " + response.data);
			$localStorage.session.user = response.data;
			$scope.notifications = $localStorage.session.user.directory.notifications;
			$scope.numberOfNotVisitedNotifications();
		}, function(response) {
			window.alert("Notification error!");
		});
	}
	
	$scope.userClick = function(){
		$state.go('dashboard.user');
	}
})