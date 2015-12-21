/**
 * Created by roxana on 06.12.2015.
 */
(function() {
    var app = angular.module('smf');

    var ExpensesController = function($scope, ngTableParams, ExpensesService, LoginService, $mdDialog,$mdMedia) {

        $scope.categories = ['FOOD','CLOTHES','BOOKS','GAS','GAMES','CAR_REPARISONS','ELECTRONICS'];

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

        $scope.registerExpense = function(ev) {
                                    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && $scope.customFullscreen;
                                    $mdDialog.show({
                                              controller: function($scope, $mdDialog) {
                                                  $scope.hide = function() {
                                                    $mdDialog.hide();
                                                  };
                                                  $scope.cancel = function() {
                                                    $mdDialog.cancel();
                                                  };
                                                  $scope.answer = function(answer) {
                                                    $mdDialog.hide(answer);
                                                  }
                                              },
                                              templateUrl: "/main/expenses/registration.expense.html",
                                              parent: angular.element(document.body),
                                              targetEvent: ev,
                                              clickOutsideToClose: true,
                                              scope: $scope,
                                              preserveScope: true,
                                              clickOutsideToClose:true,
                                              fullscreen: useFullScreen
                                            })
                                            .then(function(registerData) {
                                              if(registerData != undefined && registerData.expenseName != undefined && registerData.amount != undefined && registerData.category != undefined) {
                                                ExpensesService.registerExpense(LoginService.loggedUser,registerData)
                                                      .then(function(data) {
                                                      ExpensesService.getExpenses(LoginService.loggedUser,$scope.query, success);
                                                      $scope.registeredFundFailed = false;
                                                      }, function(error) {
                                                      $scope.registeredFundFailed = true;
                                                      console.log('The registration of the fund was not ok. Try another fund name or try later.');
                                                     });
                                              }
                                            }, function() {
                                              $scope.status = 'You cancelled the dialog.';
                                            });

                }

    };

    app.controller('ExpensesController',['$scope','ngTableParams', 'ExpensesService', 'LoginService','$mdDialog','$mdMedia',ExpensesController]);

})();