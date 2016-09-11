angular.module('app').factory('Constants', function() {
	var constants = {};
	
	constants.GET_USER_URL = '/server/users/get/';
	constants.POST_VISIT_NOTIFICATIONS = '/server/userdirectory/visit_notifications';
	
	
	return constants;
});
