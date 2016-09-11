angular.module('app').factory('NotificationService', function($http, Constants, SessionService) {
	var notificationService = {};
	
	notificationService.getNotifications = function() {
		return SessionService.getUser().directory.notifications;
	}
	
	notificationService.visitAll = function() {
		requestData = SessionService.getUser();
		
		$http.post(Constants.POST_VISIT_NOTIFICATIONS, requestData)
		.then(function(response) {
			SessionService.setUser(response.data);
		}, function(response) {
			window.alert("Error at Service: 'Notification' while trying to make a request to the server.");
		});
	}
	
	notificationService.countNotVisitedNotifications = function() {
		notifications = notificationService.getNotifications();
		numOfNotVisitedNotifications = 0;
	
		console.log("EXECUTOU ISSO AQUI");
		
		for (i = 0; i < notifications.length; i++) {
			if (notifications[i].isVisited == false) {
				numOfNotVisitedNotifications++;
			}
		}
		
		return numOfNotVisitedNotifications;
	}
	
	return notificationService;
});