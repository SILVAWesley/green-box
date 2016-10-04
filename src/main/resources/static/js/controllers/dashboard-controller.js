angular.module('app').controller('dashboardController', function($scope, 
																 $localStorage, 
																 $state, 
																 $http, 
																 authService, 
																 SessionService, 
																 NotificationService,
																 $rootScope) {
	
	SessionService.fetchUser();
	
	$scope.logout = function() {
		authService.logout();
	}
	
	$scope.getUsername = function() {
		return SessionService.getUser().username;
	}
	
	$scope.getNotifications = function() {
		return NotificationService.getNotifications();
	}
	
	$scope.numberOfNotVisitedNotifications = function() {
		return NotificationService.countNotVisitedNotifications();
	}
	
	$scope.actionClick = function() {
		NotificationService.visitAll();
	}
	
	$scope.aboutClick = function(){
		$state.go('dashboard.about');
	}
	
	$scope.contactClick = function(){
		$state.go('dashboard.contact');
	}
})