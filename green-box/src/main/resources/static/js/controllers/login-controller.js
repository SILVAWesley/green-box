angular.module('app').controller("loginController", 
								['$scope', 
								 '$http', 
								 '$state', 
								 'authService', 

function($scope, $http, $state, authService) {
	
	$scope.entrydata = "";
	$scope.password = "";
	
	$scope.login = function() {
		authService.login($scope.entrydata, $scope.password, function(result) {
			if (result) {
				$state.go('dashboard');
			} else {
				window.alert("Login not successful");
			}	
		});
	}
}])