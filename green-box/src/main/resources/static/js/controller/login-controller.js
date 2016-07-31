appGreenbox.controller("loginController", function($scope, $http, $location) {
	$scope.user = {};
	$scope.entrydata = "";
	$scope.token = "";
	
	$scope.authorize = function() {
		$http.post("/server/login/authenticate", $scope.user).then(function(response) {
			console.log("Logou");
			$scope.token = response.data.token;
			localStorage.setItem("userToken", response.data.token);
			$location.path("/userdirectory");
		}, function(response) {
			console.log("TRATA EXCEPTION AQUI.")
		});
	};
	
	$scope.loucura = function() {
		console.log("loucura");
		myuser = {"username": "ocoisa", "email":"ocoisa@gmail.com", "password":"123"}
		$http.post("/server/users/new", myuser).then(function(response) {
			console.log("Chegou aqui");
			//$scope.token = response.data.token;
			//localStorage.setItem("userToken", reponse.data.token);
		}, function(response) {
			console.log("TRATA EXCEPTION AQUI.")
		});
	}
})