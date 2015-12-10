/**
 * Created by cipriach on 04.12.2015.
 */
(function () {
    var app = angular.module('smf');

    app.factory('FundsService', function ($http) {
        var getFunds = function (userName) {

            return $http.get('/api/'+userName+'/funds');
        };

        var registerFund = function(userName, fundName, fundAmount) {
        console.log('Registering fund '+fundName+' with fund amount '+fundAmount)
            return $http({
                method: 'POST',
                url: '/api/'+userName+'/register/fund',
                data: {
                    fundName: fundName,
                    fundAmount: fundAmount
                }
            })
        }

        return {
            getFunds: getFunds,
            registerFund: registerFund
        }
    });
})();