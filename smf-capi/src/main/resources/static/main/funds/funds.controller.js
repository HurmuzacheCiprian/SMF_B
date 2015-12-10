/**
 * Created by cipriach on 04.12.2015.
 */
(function () {
    var app = angular.module('smf');

    var FundsController = function ($scope, ngTableParams, FundsService, LoginService) {
        $scope.tableParams = new ngTableParams(
            {
                filter: {
                    fundName: "C"
                }
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
    };

    app.controller('FundsController', ['$scope', 'ngTableParams', 'FundsService', 'LoginService', FundsController]);

})();