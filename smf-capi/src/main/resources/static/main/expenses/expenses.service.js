/**
 * Created by roxana on 06.12.2015.
 */
(function(){

    var app = angular.module('smf');

    app.factory('ExpensesService', function($http){
        var getExpenses = function(userName, query, callback) {
            var createdUrl = '/api/' + userName;
            createdUrl+= '/expenses?pageNumber=' +query.page;
            createdUrl+= '&perPage=' +query.limit;
            createdUrl+='&direction='+(query.order.charAt(0) == '-'? 'desc' : 'asc');
                        createdUrl+='&sortField='+(query.order.charAt(0) == '-'? query.order.substring(1,query.order.length) : query.order);
            return $http.get(createdUrl).then(function(data) {
                        callback(data.data);
            });
         }

        var registerExpense = function(userName, registerData) {
            var registerUrl = "/api/"+userName+"/register/expense";

            return $http({
                method: 'POST',
                url: registerUrl,
                data: {
                    expenseName: registerData.expenseName,
                    amount: registerData.amount,
                    category: registerData.category
                }
            });
        }

         return{
            getExpenses: getExpenses,
            registerExpense: registerExpense
         }
    });

})();