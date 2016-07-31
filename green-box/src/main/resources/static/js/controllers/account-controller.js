appGreenbox.controller("accountController", function($scope, $http) {
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
	$scope.path="/";
	
	$scope.directoryclick = function(clickedDirectory) {
		console.log($scope.userdirectory);
		$scope.userdirectory = clickedDirectory;
		console.log($scope.userdirectory);
	}
})