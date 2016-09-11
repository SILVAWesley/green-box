angular.module('app').controller('dashboardController', function($scope, 
																 $localStorage, 
																 $state, 
																 $http, 
																 authService, 
																 SessionService, 
																 NotificationService) {
	SessionService.fetchUser();
	
	$scope.username =  SessionService.getUser().username;
	$scope.notifications = NotificationService.getNotifications();
	
	$scope.logout = function() {
		authService.logout();
	}
	
	$scope.numberOfNotVisitedNotifications = function() {
		return NotificationService.countNotVisitedNotifications();
	}
	
	$scope.actionClick = function() {
		NotificationService.visitAll();
	}
	
	$scope.userClick = function(){
		$state.go('dashboard.user');
	}
})