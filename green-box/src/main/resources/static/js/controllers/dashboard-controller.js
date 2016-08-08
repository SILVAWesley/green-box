angular.module('app').controller('dashboardController', function($scope, $localStorage, authService) {
	console.log($localStorage.session.user.username);
	$scope.username =  $localStorage.session.user.username;
	
	$scope.logout = function() {
		authService.logout();
	}	
})