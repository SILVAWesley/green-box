angular.module('app').controller('registrationController', ['$scope', '$http', function($scope, $http) {
	
	/* 
	 * An user requires username, email and password to be created 
	 * This variable should be filled with ng-model. Will be used
	 * when the doRegistration() function is called. 
	 */
	$scope.user = {username: "", 
				   email: "", 
				   password: ""};
	
	/*
	 * This is the function that will create a new user.
	 * For that, it sends an http request to the API.
	 * 
	 * Request Info:
	 * URL: /server/users/new
	 * METHOD: POST
	 * 		- Data sent: $scope.user
	 * SUCCESS:
	 * Create a new user and place it in the database.
	 * 
	 * FAILURE:
	 * Show a window alert with the error message.
	 */
	$scope.doRegister = function() {
		
		$http.post("/server/users/new", $scope.user)
		.then(function(response) {
			
			window.alert("Congratulations! You now have a greenbox account to store you files. " +
						 "Username: " + response.data.username + " "
						 + "Email: " + response.data.email);
			
			$scope.user = {username: "", 
					   	   email: "", 
					   	   password: ""}
			$scope.registrationForm.$setPristine();
			
		}, function(response) {
			
			window.alert("Failure: " + response.data.message);
			
		});
		
	};
	
}])