appGreenbox.controller("accountController", function($scope, $http, $rootScope) {
	function File(name, size, date) {
		this.name = name;
		this.size = size;
		this.date = date;
		this.children = [];
	}
	
	var folder1 = new File("loucura", "200kb", "08/11/1996");
	folder1.children.push(new File("hello.txt", "200 kb", "08/11/1996"));
	folder1.children.push(new File("hello2.txt", "200 kb", "sdadasds"));
	
	var wholeuserdirectory = [folder1];
	$scope.userdirectory = wholeuserdirectory;
	$scope.user = $rootScope.user;
	$scope.path= [$rootScope.user.username];
	
	$scope.directoryclick = function(clickedDirectory) {
		console.log($scope.userdirectory);
		$scope.userdirectory = clickedDirectory.children;
		$scope.path.push($scope.userdirectory.name);
		console.log($scope.userdirectory);
	}
})