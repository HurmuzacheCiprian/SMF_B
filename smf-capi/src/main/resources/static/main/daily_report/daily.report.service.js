(function() {
    var app = angular.module('smf');

    app.factory('DailyReportService', function($http) {


        var getDailyReport = function(userName) {
            return $http({
                url:'/api/expense/report/daily/'+userName,
                method: 'GET'
            });
        };


        return {
            getDailyReport: getDailyReport
        }

    });


})()