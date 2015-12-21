/**
 * Created by roxana on 06.12.2015.
 */
(function() {
    var app = angular.module('smf');

    var ExpensesController = function($scope, ngTableParams, ExpensesService, LoginService, $mdMedia) {

        function init(){
            ExpensesService.getExpenses(LoginService.loggedUser, $scope.query, success);
        };

        $scope.selected = [];
        $scope.query = {
            filter: '',
            order: 'expenseName',
            limit: 5,
            page: 1
        };

        function success(data){
            $scope.expenses = data.expenses;

            $scope.totalElements = data.totalElements;

        };

        $scope.search = function (predicate){
            $scope.query.filter = predicate;
            $scope.deferred = ExpensesService.getExpenses(LoginService.loggedUser, $scope.query, success);
        };

        $scope.onOrderChange = function (order){
            $scope.query.order = order;
            return ExpensesService.getExpenses(LoginService.loggedUser,$scope.query, success);
        };

        $scope.onPaginationChange = function(page, limit){
            $scope.query.page = page;
            $scope.query.limit = limit;
            return ExpensesService.getExpenses(LoginService.loggedUser, $scope.query, success);
        };

        init();

    };

    app.controller('ExpensesController',['$scope','ngTableParams', 'ExpensesService', 'LoginService', '$mdMedia',ExpensesController]);

})();