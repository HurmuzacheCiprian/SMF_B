/**
 * Created by cipriach on 04.12.2015.
 */
(function() {
    var app = angular.module('smf');

    function RegisterService($http) {
        this.$http = $http;
        return this;
    }

    RegisterService.prototype.registerUser = function(userName, firstName, lastName, password) {
        var _self = this;
        return _self.$http({
            method: 'POST',
            url: '/register',
            headers: {
                'Content-Type':'application/json'
            },
            data:  {
                userName: userName,
                password: password,
                firstName: firstName,
                lastName: lastName
            }
        });


    }

    app.service('RegisterService', RegisterService);

})();