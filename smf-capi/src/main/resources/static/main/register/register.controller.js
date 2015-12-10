/**
 * Created by cipriach on 04.12.2015.
 */
(function () {
    var app = angular.module('smf');

    var RegisterController = function ($scope) {
        $scope.alreadyUsedUserName = false;

        $scope.register = function(userName, firstName, lastName, password) {
            checkAlreadyRegisteredUser(userName);

        };


        //TODO this will be moved to the RegisterService
        function checkAlreadyRegisteredUser(userName) {
            if(userName == 'gigi') {
                $scope.alreadyUsedUserName = true;
            } else {
                $scope.alreadyUsedUserName = false;
            }
        }

    };

    app.controller('RegisterController', ['$scope', RegisterController]);
})();