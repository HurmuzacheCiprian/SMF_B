/**
 * Created by cipriach on 04.12.2015.
 */
(function () {
    var app = angular.module('smf');

    app.factory('FundsService', function ($http) {
        var getFunds = function (userName,query,callback) {
            var createdUrl = '/api/'+userName;
                createdUrl+='/funds?pageNumber='+query.page;
                createdUrl+='&perPage='+query.limit;
                createdUrl+='&direction='+(query.order.charAt(0) == '-'? 'desc' : 'asc');
                createdUrl+='&sortField='+(query.order.charAt(0) == '-'? query.order.substring(1,query.order.length) : query.order);
            return $http.get(createdUrl).then(function(data) {
                callback(data.data);
            });
        };

        var registerFund = function(userName, fundName, fundAmount) {
            return $http({
                method: 'POST',
                url: '/api/'+userName+'/register/fund',
                data: {
                    fundName: fundName,
                    fundAmount: fundAmount
                }
            })
        };

        var deleteFund = function(userName,fundId) {
            return $http({
                method: 'DELETE',
                url: '/api/'+userName+'/fund/'+fundId
            });
        };
        return {
            getFunds: getFunds,
            registerFund: registerFund,
            deleteFund: deleteFund
        }
    });
})();