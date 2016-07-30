appGreenbox.controller("loginController", function($scope, $http, $location) {
	$scope.user = {};
	$scope.token = "";
	
	$scope.authorize = function() {
		$http.post("/server/login/authenticate", $scope.user).then(function(response) {
			$scope.token = response.data.token;
			localStorage.setItem("userToken", reponse.data.token);
			console.log("Chegou aqui!");
			$location.redirect("/userdirectory");
		}, function(response) {
			console.log("TRATA EXCEPTION AQUI.")
		});
	};
	
})