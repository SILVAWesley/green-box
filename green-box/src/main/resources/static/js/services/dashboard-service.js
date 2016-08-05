angular.module('app').factory('dashboardService', function($http, $localStorage) {
	var service = {};

	service.user = $localStorage.session.user;
	service.rootDirectory = service.user.userDirectory;
	service.currentDirectory = service.rootDirectory;
	service.foldersPath = [service.rootDirectory];
	
	service.getFilesNFolders = function() {
		return service.getFolders().concat(service.getFiles());
	}
	
	service.getFiles = function() {
		return service.currentDirectory.files;	
	}	

	service.getFolders = function() {
		return service.currentDirectory.children;
	}

	service.newFolder = function(folderName, callback) {
		$http.post('/server/userdirectory/newfolder/' + folderName, service.user)
			.then(function(response) {
				
				$localStorage.session.user = response.data;
				update();
				callback(true);
			}, function(response) {
				
				window.alert(response.data.message);
				callback(false);
				
		})
	} 

	service.goFoward = function(folder) {
		service.currentDirectory = folder;
		service.foldersPath.push(folder);
	}

	return service;

	function update() {
		service.user = $localStorage.session.user;
		service.rootDirectory = service.user.userDirectory;
	}
});
