/**
 * Created by cipriach on 04.12.2015.
 */
(function () {
    var app = angular.module('smf');

    var FundsController = function ($scope, ngTableParams, FundsService, LoginService,$mdDialog,$mdMedia) {
        $scope.customFullscreen = $mdMedia('sm');
        $scope.registeredFundFailed = false;
        $scope.tableParams = new ngTableParams(
            {
                page: 1,
                count: 10
            }, {
                getData: function (params) {
                    return FundsService.getFunds(LoginService.loggedUser).then(
                        function (data) {
                            params.total(data.data.funds.length);
                            return data.data.funds;
                        }, function (error) {
                            console.log(error);
                        }
                    )
                }
            }
        );
        $scope.registerFund = function(ev) {
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
                                      clickOutsideToClose:true
                                    })
                                    .then(function(registerData) {
                                      if(registerData != undefined && registerData.fundName != undefined && registerData.fundAmount != undefined) {
                                        FundsService.registerFund(LoginService.loggedUser,registerData.fundName,registerData.fundAmount)
                                                                                        .then(function(data) {
                                                                                            $scope.registeredFundFailed = false;
                                                                                            $scope.tableParams.reload();
                                                                                            $scope.hide();
                                                                                        }, function(error) {
                                                                                            $scope.registeredFundFailed = true;
                                                                                            console.log('The registration of the fund was not ok. Try another fund name or try later.');
                                                                                        });
                                      }
                                    }, function() {
                                      $scope.status = 'You cancelled the dialog.';
                                    });
                                    $scope.$watch(function() {
                                      return $mdMedia('sm');
                                    }, function(sm) {
                                      $scope.customFullscreen = (sm === true);
                                    });

        }
    };

    app.controller('FundsController', ['$scope', 'ngTableParams', 'FundsService', 'LoginService','$mdDialog','$mdMedia', FundsController]);

})();