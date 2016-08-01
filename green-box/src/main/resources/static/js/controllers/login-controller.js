angular.module('app').controller("loginController", ['$scope', '$http', '$state', function($scope, $http, $state) {
	
	/*
	 * This scope variable is meant to store the data of the login request.
	 * It may have an username or an email. It will be checked if "identification"
	 * has an '@' symbol to decide if it is email or not.
	 * 
	 */
	$scope.user = {username: "",
				   email: "",
				   password: ""};
	
	$scope.identification = "";

	$scope.doLogin = function() {
		
		fillUsernameOrEmail();
		console.log($scope.user);
		
		$http.post("/server/login/authenticate", $scope.user)
		.then(function(response) {
			
			sessionStorage.setItem("user-token", response.data.token);
			sessionStorage.setItem("logged-user", response.data.user);
			$state.go('dashboard');
			
		}, function(response) {
			
			window.alert(response.data.message);
			
		});
		
	};

	function fillUsernameOrEmail() {
		
		if ($scope.identification.indexOf('@') != -1) {
			$scope.user.email = $scope.identification;
		} else {
			$scope.user.username = $scope.identification;
		}
		
	}
	
}])