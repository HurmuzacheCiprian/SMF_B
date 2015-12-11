/**
 * Created by cipriach on 03.12.2015.
 */
(function () {
    var app = angular.module('smf');

    var LoginController = function ($scope, $state, LoginService, $stateParams) {
        $scope.incorrectCredentials = false;
        $scope.cameFromRegister = false;

        (function() {
                if($stateParams.fromPage != null && $stateParams.fromPage == 'register') {
                   $scope.cameFromRegister = true;
                } else {
                   $scope.cameFromRegister = false;
                }
              }
        )();

        $scope.signIn = function (userName, password) {

            LoginService.checkLoginCredentials(userName, password)
                .then(function (data) {
                    if (data.data.ok == true) {
                        $scope.incorrectCredentials = false;
                        LoginService.loggedUser = userName;

                        $state.go('home');
                    } else {
                        console.log('Redirecting to login');
                        $scope.incorrectCredentials = true;
                        $state.go('login');
                    }
                }, function (error) {
                    $scope.incorrectCredentials = true;
                    console.log('Redirecting to login');
                    $state.go('login');
                })
        }
    };

    app.controller('LoginController', ['$scope', '$state', 'LoginService','$stateParams', LoginController]);


})();