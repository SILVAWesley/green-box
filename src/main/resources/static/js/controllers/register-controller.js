angular.module('app').controller('registrationController', ['$scope', 
                                                            '$http',
                                                            'authService',
function($scope, $http, authService) {
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
		authService.register($scope.user.username,
							 $scope.user.email,
							 $scope.user.password,
							 function(result) {
								if (result) {
									$scope.user = {username: "", 
												   email: "", 
												   password: ""}
									$scope.registrationForm.$setPristine();
								}
							 });
	};
	
}])