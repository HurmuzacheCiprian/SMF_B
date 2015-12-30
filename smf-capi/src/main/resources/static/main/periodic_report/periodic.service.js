(function() {
    var app = angular.module('smf');

    app.factory('PeriodicService', function($http) {

          var getPeriodicReport = function(userName,period) {
                return $http({
                    method: 'GET',
                    url:'/api/expense/report/periodic/'+userName+'?period='+period
                });
          };


          return {
            getPeriodicReport: getPeriodicReport
          }


    });

})();