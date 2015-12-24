(function() {
    var app = angular.module('smf');

    var DailyReportController = function($scope,LoginService,DailyReportService) {

        $scope.getDailyReport = function() {
            $scope.loading = true;
            DailyReportService.getDailyReport(LoginService.loggedUser)
                        .then(function(data) {
                                data = data.data;
                                $scope.expenseReport = data.expenseReports;
                                $scope.totalFunds = data.totalFunds;
                                $scope.remainingFunds = data.remainingFunds;
                                $scope.currentReportDate = data.currentReportDate;
                        });
        };

        $scope.getDailyReport();
    };



    app.controller('DailyReportController',['$scope','LoginService','DailyReportService', DailyReportController]);


})();