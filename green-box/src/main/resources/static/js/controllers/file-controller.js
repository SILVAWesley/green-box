angular.module('app').controller('fileController', function($localStorage, $scope, $http, $rootScope, $state) {
	$scope.path = $localStorage.session.currentPath;
	$scope.user = $localStorage.session.user;
	$scope.content = "";
	$scope.extensions = ["txt", "md"];
	$scope.currentExtension = "txt";
	
	$scope.newFileName = "";
	
	if ($localStorage.clickedFile != undefined) {
		$scope.newFileName = $localStorage.clickedFile.name;
		$scope.content = $localStorage.clickedFile.content;
	}
	
	
	$scope.saveFile = function() {
		
		requestData = {};
		requestData.user = $scope.user;
		requestData.fileName = $scope.newFileName;
		requestData.fileExtension = $scope.currentExtension;
		requestData.fileContent = $scope.content;
		requestData.filePath = $scope.path;
		
		
		if (requestData.fileName == "") {
			
			window.alert('File name cannot be empty.');
			
		} else if ($localStorage.clickedFile == undefined){
			createNewFile();
		} else {
			editFile();
		}
	}
	
	function editFile(){
		$http.put('/server/userdirectory/editfile', requestData, $localStorage.clickedFile)
		.then(function(response){
			
			$localStorage.session.user = response.data;
			window.alert('File successfully edited');
			$state.go('dashboard.directories', {'folderPath': $localStorage.session.currentPath});
			
		}, function(response){
			window.alert(response.data.message);
		});
	}
	
	function createNewFile(){
		$http.post('/server/userdirectory/newfile', requestData)
		.then(function(response) {
			
			$localStorage.session.user = response.data;
			window.alert('File successfully created.');
			$state.go('dashboard.directories', {'folderPath': $localStorage.session.currentPath});
			$scope.path = $localStorage.session.currentPath;
			
		}, function(response) {
			
			window.alert(response.data.message);
			
		});
	}
	
	$scope.directoriesView = function() {
		$state.go('dashboard.directories', {folderPath: $localStorage.session.currentPath});
	}
});