angular.module('app').factory('DirectoryService', function($http, 
														   SessionService, 
														   Constants, 
														   $sessionStorage, 
														   $localStorage, 
														   $state) {
	var directoryService = {};
	
	directoryService.init = function() {
		if ($sessionStorage.explorer == undefined) {
			$sessionStorage.explorer = {};
		}
		
		if ($localStorage.explorer == undefined) {
			$localStorage.explorer = {};
		}
		
		if ($sessionStorage.explorer.itemsToShow == undefined) {
			$sessionStorage.explorer.itemsToShow = [];
		}
		
		if ($localStorage.explorer.clickedItem == undefined) {
			$localStorage.explorer.clickedItem = null;
		}
		
		if ($localStorage.explorer.openedFolders == undefined) {
			$localStorage.explorer.openedFolders = [];
		}
	}
	
	directoryService.shareFile = function(userToShareUsername, permissionLevel) {
		requestData = {};
		requestData.user = SessionService.getUser();
		requestData.userSharedWith = {};
		requestData.userSharedWith.username = userToShareUsername;
		requestData.name = directoryService.getClickedItem().name;
		requestData.folderPath = directoryService.getCurrentFolder().path;
		requestData.fileExtension = directoryService.getClickedItem().extension;
		requestData.permissionLevel = permissionLevel;
		
		$http.post(Constants.POST_SHAREFILE_URL, requestData)
		.then(function(response) {
			SessionService.setUser(response.data);
			directoryService.goToPath(directoryService.getCurrentFolder().path);
			$("#fileShareModal").modal("show");
		}, function(response) {
			$("#fileShareErrorModal .modal-body").html(response.data.message)
			$("#fileShareErrorModal").modal("show");

		});
	}
	
	directoryService.renameFolder = function(newName) {
		requestData = {};
		requestData.user = SessionService.getUser();
		requestData.newName = newName;
		requestData.oldName = directoryService.getClickedItem().name;
		requestData.folderPath = directoryService.getCurrentFolder().path;
		
		$http.put(Constants.PUT_RENAMEFOLDER_URL, requestData)
		.then(function(response) {
			SessionService.setUser(response.data);
			directoryService.goToPath(directoryService.getCurrentFolder().path);
			$("#folderRenameModal").modal("show");
		}, function(response) {
			$("#folderRenameErrorModal .modal-body").html(response.data.message);
			$("#folderRenameErrorModal").modal("show");
		});
	}
	
	directoryService.renameFile = function(newName) {
		requestData = {};
		requestData.user = SessionService.getUser();
		requestData.newName = newName;
		requestData.oldName = directoryService.getClickedItem().name;
		requestData.folderPath = directoryService.getCurrentFolder().path;
		requestData.fileExtension = directoryService.getClickedItem().name.extension;
		
		$http.put(Constants.PUT_RENAMEFILE_URL, requestData)
		.then(function(response) {
			SessionService.setUser(response.data);
			directoryService.goToPath(directoryService.getCurrentFolder().path);
			$("#fileRenameModal").modal("show");		
		}, function(response) {
			$("#fileRenameErrorModal .modal-body").html(response.data.message);
			$("#fileRenameErrorModal").modal("show");		
		});
	}
	
	directoryService.newFolder = function(folderName) {
		requestData = {};
		requestData.user = SessionService.getUser();
		requestData.folderName = folderName;
		requestData.folderPath = directoryService.getCurrentFolder().path;
		
		$http.post(Constants.POST_NEWFOLDER_URL, requestData)
			.then(function(response) {
				SessionService.setUser(response.data);
				directoryService.goToPath(directoryService.getCurrentFolder().path);
				$("#folderCreateModal").modal("show");		
			}, function(response) {
				$("#folderCreateErrorModal .modal-body").html(response.data.message);
				$("#folderCreateErrorModal").modal("show");	
		});
	}
	
	directoryService.newFile = function(fileName, fileContent, fileExtension) {
		requestData = {};
		requestData.user = SessionService.getUser();
		requestData.fileName = fileName;
		requestData.fileContent = fileContent;
		requestData.fileExtension = fileExtension;
		requestData.filePath = directoryService.getCurrentFolder().path;

		$http.post(Constants.POST_NEWFILE_URL, requestData)
		.then(function(response) {
			SessionService.setUser(response.data);
			$("#fileCreateModal").modal("show");
			$state.go('dashboard.directories', {'folderPath': directoryService.getCurrentFolder().path});
		}, function(response) {
			$("#fileCreateErrorModal .modal-body").html(response.data.message);
			$("#fileCreateErrorModal").modal("show");
		});
	}
	
	directoryService.editFile = function(fileName, fileContent, fileExtension) {
		requestData = {};
		requestData.user = SessionService.getUser();
		requestData.clickedFile = directoryService.getClickedItem();
		requestData.fileName = fileName;
		requestData.fileContent = fileContent;
		requestData.fileExtension = fileExtension;
		requestData.filePath = directoryService.getCurrentFolder().path;
		
		$http.put(Constants.PUT_EDITFILE_URL, requestData)
		.then(function(response) {
			SessionService.setUser(response.data);
			$("#fileEditModal").modal("show");
			$state.go('dashboard.directories', {'folderPath': directoryService.getCurrentFolder().path});
		}, function(response){
		    $("#fileEditErrorModal .modal-body").html( response.data.message );
		    $("#fileEditErrorModal").modal("show");
		});
	}
	
	directoryService.goToMyFiles = function() {
		$state.go('dashboard.directories', {folderPath: SessionService.getUser().directory.rootFolder.folders[0].path});
	}
	
	directoryService.goToSharedWithMe = function() {
		$state.go('dashboard.directories', {folderPath: '/Shared with me'});
	}
	
	directoryService.goToPath = function(path) {
		if (path == '/' || path == '' || path == 'root') {
			directoryService.goToMyFiles();
			return;
		}
		
		folderNames = path.split(Constants.FILE_SEPARATOR);
		
		directoryService.setCurrentFolder(SessionService.getUser().directory.rootFolder);
		directoryService.resetOpenedFolders();
		
		for (i = 1; i < folderNames.length; i++) {
			var item = findItemByName(folderNames[i], directoryService.getCurrentFolder())
			
			if (item == null || item == undefined) {
				directoryService.goToMyFiles();
				return;
			}
			
			goForward(item);
		}
		
		directoryService.setItemsToShow(directoryService.getCurrentFolder().folders
							    .concat(directoryService.getCurrentFolder().files));
	}

	directoryService.getCurrentFolder = function() {
		return $localStorage.explorer.currentFolder;
	}
	
	directoryService.setCurrentFolder = function(currentFolder) {
		$localStorage.explorer.currentFolder = currentFolder;
	}
	
	directoryService.getOpenedFolders = function() {
		return $localStorage.explorer.openedFolders;
	}
	
	directoryService.addOpenedFolder = function(folder) {
		$localStorage.explorer.openedFolders.push(folder);
	}
	
	directoryService.resetOpenedFolders = function(folder) {
		$localStorage.explorer.openedFolders = [];
	}
	
	directoryService.getItemsToShow = function() {
		return $sessionStorage.explorer.itemsToShow;
	}
	
	directoryService.setItemsToShow = function(itemsToShow) {
		$sessionStorage.explorer.itemsToShow = itemsToShow;
	}
	
	directoryService.getClickedItem = function() {
		return $localStorage.explorer.clickedItem;
	}
	
	directoryService.setClickedItem = function(clickedItem) {
		$localStorage.explorer.clickedItem = clickedItem;
	}
	
	function findItemByName(name, directory) {
		var foldersNFiles = directory.folders.concat(directory.files);
		
		for (j = 0; j < foldersNFiles.length; j++) {
			if (foldersNFiles[j].name == name) {
				return foldersNFiles[j];
			}
		}
	
		return null;
	}
	
	function goForward(folder) {
		directoryService.setCurrentFolder(folder);
		directoryService.addOpenedFolder(folder);
	}
	
	return directoryService;
});