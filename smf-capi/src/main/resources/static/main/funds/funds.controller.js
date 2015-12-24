/**
 * Created by cipriach on 04.12.2015.
 */
(function () {
    var app = angular.module('smf');

    var FundsController = function ($scope,FundsService, LoginService,$mdDialog,$mdMedia) {
        $scope.customFullscreen = $mdMedia('sm');
        $scope.registeredFundFailed = false;

        function init() {
            FundsService.getFunds(LoginService.loggedUser,$scope.query, success);
        }

        $scope.selected = [];

          $scope.query = {
            filter: '',
            order: 'name',
            limit: 5,
            page: 1
          };

          function success(data) {
            $scope.funds = data.funds;
            $scope.totalElements = data.totalElements;
          }

          $scope.search = function (predicate) {
            $scope.query.filter = predicate;
            $scope.deferred = FundsService.getFunds(LoginService.loggedUser,$scope.query, success);
          };

          $scope.onOrderChange = function (order) {
            $scope.query.order = order;
            return FundsService.getFunds(LoginService.loggedUser,$scope.query, success);
          };

          $scope.onPaginationChange = function (page, limit) {
            $scope.query.page = page;
            $scope.query.limit = limit;
            return FundsService.getFunds(LoginService.loggedUser,$scope.query, success);
          };

          init();


          $scope.registerFund = function(ev) {
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
                                      templateUrl: "/main/funds/registration.window.html",
                                      parent: angular.element(document.body),
                                      targetEvent: ev,
                                      clickOutsideToClose: true,
                                      scope: $scope,
                                      preserveScope: true,
                                      clickOutsideToClose:true,
                                      fullscreen: useFullScreen
                                    })
                                    .then(function(registerData) {
                                      if(registerData != undefined && registerData.fundName != undefined && registerData.fundAmount != undefined) {
                                        FundsService.registerFund(LoginService.loggedUser,registerData.fundName,registerData.fundAmount)
                                              .then(function(data) {
                                              FundsService.getFunds(LoginService.loggedUser,$scope.query, success)
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

          $scope.deleteFund = function(fundId) {
            FundsService.deleteFund(LoginService.loggedUser,fundId).then(function(data) {
                FundsService.getFunds(LoginService.loggedUser,$scope.query, success);
            });
          }
    };

    app.controller('FundsController', ['$scope', 'FundsService', 'LoginService','$mdDialog','$mdMedia', FundsController]);

})();