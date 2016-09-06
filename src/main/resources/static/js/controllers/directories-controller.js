angular.module('app').controller("directoriesController", function($scope, $state, $localStorage, $http, $rootScope, $stateParams) {
	$scope.folderClick = function(folder) {
		$state.go('dashboard.directories', {folderPath: folder.path});
	}
	
	$scope.sharedWithMeClick = function() {
		$state.go('dashboard.directories', {folderPath: 'root/Shared with me'});
		console.log('foi');
	}
	
	$scope.myFilesClick = function() {
		console.log($scope.rootDirectory.name);
		$state.go('dashboard.directories', {folderPath: $scope.rootDirectory.folders[0].path});
		console.log('foi');
	}
	
	
	$scope.actionClick = function(item){
		$localStorage.selectedItem = item;
	}
	
	
	$scope.renameClick = function(item, type){
		$localStorage.selectedItem = item;
		$scope.currentType = type;
	}
	
	$scope.fileClick = function(file) {
		$localStorage.clickedFile = file;
		$state.go('dashboard.file');
	}
	
	$scope.share = function(sharingType){
		requestData = {};
		requestData.user = $scope.user;
		requestData.userSharedWith = {};
		requestData.userSharedWith.username = $scope.userSharedWith;
		console.log(requestData.userSharedWith);
		requestData.name = $localStorage.selectedItem.name;
		requestData.folderPath = $localStorage.session.currentPath;
		requestData.permissionLevel = sharingType;
		
		$http.post('/server/userdirectory/sharefile', requestData)
		.then(function(response){
			$localStorage.session.user = response.data;
			window.alert('File successfully shared');
		}, function(response){
			window.alert(response.data.message);
			window.alert('whoops!');
		});
	}
	
	$scope.renameItem = function(){
		requestData = {};
		requestData.user = $scope.user;
		requestData.newName = $scope.newName;
		requestData.oldName = $localStorage.selectedItem.name;
		requestData.folderPath = $localStorage.session.currentPath;
		
		if($scope.currentType == 'File'){
			renameFile();
		} else if( $scope.currentType == 'Folder'){
			renameFolder();
		} else{
			window.alert('NOT FILE NOR FOLDER');
		}
	}
	
	function renameFile(){
		$http.post('/server/userdirectory/renamefile', requestData)
		.then(function(response){
			$localStorage.session.user = response.data;
			window.alert('File renamed successfully');
			update();
			goToPath($stateParams.folderPath);
		}, function(response){
			window.alert(response.data.message);
			window.alert('whoops!');
		});
	}
	
	function renameFolder(){
		$http.post('/server/userdirectory/renamefolder', requestData)
		.then(function(response){
			$localStorage.session.user = response.data;
			window.alert('Folder renamed successfully');
			update();
			goToPath($stateParams.folderPath);
		}, function(response){
			window.alert(response.data.message);
			window.alert('whoops!');
		})
		
	}
	
	$scope.newFolder = function() {
		
		requestData = {};
		requestData.user = $scope.user;
		requestData.folderName = $scope.newFolderName;
		requestData.folderPath = $scope.currentDirectory.path;
		console.log('data');
		console.log(requestData.folderPath);
		
		$http.post('/server/userdirectory/newfolder', requestData)
			.then(function(response) {
				
				$localStorage.session.user = response.data;
				update();
				goToPath($stateParams.folderPath);
			}, function(response) {
				
				window.alert(response.data.message);
				
		});
	} 
	
	$scope.getFilesNFolders = function() {
		return $scope.getFolders().concat($scope.getFiles());
	}
	
	$scope.getFiles = function() {
		return $scope.currentDirectory.files;	
	}	

	$scope.getFolders = function() {
		return $scope.currentDirectory.folders;
	}
	
	$scope.newFilePage = function() {
		$localStorage.clickedFile = null;
		$state.go('dashboard.file');
	}
	
	
	
	function findFileOrFolderByName(name, directory) {
		var foldersNFiles = directory.folders.concat(directory.files);
		
		for (j = 0; j < foldersNFiles.length; j++) {
			if (foldersNFiles[j].name == name) {
				return foldersNFiles[j];
			}
		}
	
		return null;
	}
	
	function sharedFolder() {
		return $scope.user.sharedWithMeFolder;
	}
	
	
	function update () {
		$scope.user = $localStorage.session.user;
		$scope.rootDirectory = $scope.user.directory.rootFolder;
		$scope.currentDirectory = $scope.rootDirectory;
		$scope.openedFolders = [$scope.rootDirectory];
	}
	
	function goToPath(path) {
		var directoryNames = path.split("/");
		$scope.currentDirectory = $scope.rootDirectory;
			
		for (i = 1; i < directoryNames.length; i++) {
			console.log($scope.currentDirectory);
			$scope.currentDirectory = findFileOrFolderByName(directoryNames[i], $scope.currentDirectory);
			goForward($scope.currentDirectory);
		}
		
		$scope.filesNFoldersToShow = $scope.getFilesNFolders();
	}
	
	function goForward(folder) {
		$scope.currentDirectory = folder;
		$scope.openedFolders.push(folder);
		$scope.filesNFoldersToShow = $scope.getFilesNFolders();
	}
	
	function init() {
		$scope.user = $localStorage.session.user;
		$scope.rootDirectory = $scope.user.directory.rootFolder;
		$scope.currentDirectory = $scope.rootDirectory;
		$scope.openedFolders = [$scope.rootDirectory];
		
		$localStorage.session.currentPath = $stateParams.folderPath;
		
		$scope.newFolderName = "";
		$scope.currentType = "";
		
		$scope.filesNFoldersToShow = $scope.getFilesNFolders();
		goToPath($stateParams.folderPath);
	}
	
	init();
});