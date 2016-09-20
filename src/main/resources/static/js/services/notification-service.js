angular.module('app').factory('NotificationService', function($http, 
															  Constants, 
															  SessionService) {
	var notificationService = {};
	
	notificationService.getNotifications = function() {
		return SessionService.getUser().directory.notifications;
	}
	
	notificationService.visitAll = function() {
		requestData = SessionService.getUser();
		
		$http.put(Constants.PUT_VISIT_NOTIFICATIONS, requestData)
		.then(function(response) {
			SessionService.setUser(response.data);
		}, function(response) {
			$("#serviceErrorModal .modal-body").html("'Notification' while trying to make a request to the server.");
			$("#serviceErrorModal").modal("show");
		});
	}
	
	notificationService.countNotVisitedNotifications = function() {
		notifications = notificationService.getNotifications();
		numOfNotVisitedNotifications = 0;
		
		for (i = 0; i < notifications.length; i++) {
			if (notifications[i].isVisited == false) {
				numOfNotVisitedNotifications++;
			}
		}
		
		if(numOfNotVisitedNotifications==0) {
			document.getElementById("dropdownNotifications").style.color = "#d0c91f";
			document.getElementById("dropDownHeader").style.color = "#d0c91f";
		}
		
		return numOfNotVisitedNotifications;
	}
	
	return notificationService;
});