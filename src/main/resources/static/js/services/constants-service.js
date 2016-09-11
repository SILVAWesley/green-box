angular.module('app').factory('Constants', function() {
	var constants = {};
	
	constants.GET_USER_URL = '/server/users/get/';
	constants.PUT_VISIT_NOTIFICATIONS = '/server/userdirectory/visit_notifications';
	constants.POST_NEWFOLDER_URL = '/server/userdirectory/newfolder';
	constants.PUT_RENAMEFILE_URL = '/server/userdirectory/renamefile';
	constants.PUT_RENAMEFOLDER_URL = '/server/userdirectory/renamefolder';
	constants.POST_SHAREFILE_URL = '/server/userdirectory/sharefile';
	constants.POST_NEWFILE_URL = '/server/userdirectory/newfile';
	constants.PUT_EDITFILE_URL = '/server/userdirectory/editfile';
	constants.POST_LOGIN_URL = '/server/authentication/login';
	constants.POST_REGISTER_URL = '/server/users/new';
	
	constants.FILE_SEPARATOR = '/';
	
	return constants;
});
