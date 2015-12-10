/**
 * Created by cipriach on 04.12.2015.
 */
(function () {
    var app = angular.module('smf');

    app.factory('FundsService', function ($http) {
        var getFunds = function (userName) {

            return $http.get('/api/'+userName+'/funds');
        };

        return {
            getFunds: getFunds
        }
    });
})();