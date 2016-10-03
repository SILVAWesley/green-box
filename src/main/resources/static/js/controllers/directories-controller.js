angular.module('app').controller("directoriesController", function($scope, 
																   $state,   
																   $stateParams, 
																   SessionService, 
																   DirectoryService) {
	$scope.clickOnFolder = function(folder) {
		$state.go('dashboard.directories', {folderPath: folder.path});
	}
	
	$scope.clickOnFile = function(file) {
		DirectoryService.setClickedItem(file);
		$state.go('dashboard.file');
	}
	
	$scope.clickOnSharedWithMe = function() {
		DirectoryService.goToSharedWithMe();
	}
	
	$scope.clickOnMyFiles = function() {
		DirectoryService.goToMyFiles();
	}
	
	$scope.clickOnTrash = function() {
		DirectoryService.goToTrash();
	}
	
	$scope.clickOnRenameFile = function(item){
		DirectoryService.setClickedItem(item);
	}
	
	$scope.clickOnRenameFolder = function(item) {
		DirectoryService.setClickedItem(item);
	}
	
	$scope.clickOnShareFile = function(item) {
		DirectoryService.setClickedItem(item);
	}
	
	$scope.clickOnDeleteFile = function(item){
		DirectoryService.setClickedItem(item);
	}
	
	$scope.clickOnDeleteFolder = function(item){
		DirectoryService.setClickedItem(item);
	}
	
	$scope.clickOnDeletePermanentlyFile = function(item){
		DirectoryService.setClickedItem(item);
	}
	
	$scope.clickOnDeletePermanentlyFolder = function(item){
		DirectoryService.setClickedItem(item);
	}
	
	$scope.renameFile = function() {
		DirectoryService.renameFile($scope.newName);
	}
	
	$scope.renameFolder = function() {
		DirectoryService.renameFolder($scope.newName);
	}
	
	$scope.share = function(sharingType) {
		DirectoryService.shareFile($scope.userSharedWith, sharingType);
	}
	
	$scope.renameFolder = function() {
		DirectoryService.renameFolder($scope.newName);
	}
	
	$scope.renameFile = function() {
		DirectoryService.renameFile($scope.newName);
	}
	
	$scope.sendFileToTrash = function() {
		DirectoryService.sendFileToTrash();
	}
	
	$scope.sendFolderToTrash = function() {
		DirectoryService.sendFolderToTrash();
	}
	
	$scope.finalFileDelete = function(){
		DirectoryService.finalFileDelete();
	}
	
	$scope.finalFolderDelete = function(){
		DirectoryService.finalFolderDelete();
	}
	
	$scope.newFolder = function() {
		DirectoryService.newFolder($scope.newFolderName);
	}
	
	$scope.cleanTrash = function(){
		DirectoryService.cleanTrash
	}
	
	$scope.getFolders = function() {
		return DirectoryService.getCurrentFolder().folders;
	}
	
	$scope.getFiles = function() {
		return DirectoryService.getCurrentFolder().files;
	}
	
	$scope.getCurrentFolder = function() {
		return DirectoryService.getCurrentFolder();
	}
	
	
	
	$scope.newFilePage = function() {
		DirectoryService.setClickedItem(null);
		$state.go('dashboard.file');
	}
	
	$scope.getOpenedFolders = function() {
		return DirectoryService.getOpenedFolders().slice(0, DirectoryService.getOpenedFolders().length - 1); 
	}
	
	function init() {
		DirectoryService.init();
		$scope.newFolderName = "";
		DirectoryService.goToPath($stateParams.folderPath);
	}
	
	init();
});