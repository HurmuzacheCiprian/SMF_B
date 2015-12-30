(function() {

    var app = angular.module('smf');

    var PeriodicController = function($scope,PeriodicService,LoginService) {
        $scope.periods = [3,4,6];
        $scope.series = ['Expenses Amount', 'Remaining funds'];
        $scope.data = [];
        $scope.period = 6;



        $scope.init = function() {
            PeriodicService.getPeriodicReport(LoginService.loggedUser, $scope.period).then(function(data) {
                    $scope.labels = data.data.months;

                    $scope.data.push(data.data.expensesAmount);
                    $scope.data.push(data.data.remainingFunds);
            });
        };

        $scope.getPeriodRep = function(period) {
             $scope.data = [];
             PeriodicService.getPeriodicReport(LoginService.loggedUser, period).then(function(data) {
                     $scope.labels = data.data.months;

                     $scope.data.push(data.data.expensesAmount);
                     $scope.data.push(data.data.remainingFunds);
             });
        }

        $scope.init();
    };

    app.controller('PeriodicController',['$scope','PeriodicService','LoginService', PeriodicController]);


})();