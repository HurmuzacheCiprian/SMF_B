/**
 * Created by cipriach on 04.12.2015.
 */
(function () {
    var app = angular.module('smf');

    var RegisterController = function ($scope,$state,RegisterService) {
        $scope.alreadyUsedUserName = false;
        $scope.register = function(userName, firstName, lastName, password) {
            RegisterService.registerUser(userName, firstName, lastName, password).then(function(data) {
                if(data.data.isOk == false) {
                    $scope.alreadyUsedUserName = true;
                } else {
                    $scope.alreadyUsedUserName = false;
                    $state.go('login');
                }
            }, function(error) {
                 $scope.alreadyUsedUserName = true;
            });

        };
    };

    app.controller('RegisterController', ['$scope','$state','RegisterService', RegisterController]);
})();