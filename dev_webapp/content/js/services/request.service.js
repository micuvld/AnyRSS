(function () {
    'use strict';

    angular.module('app')
        .service('RequestService', RequestService);

    RequestService.$inject = ['$http'];

    function RequestService($http) {
        return {
            post: post
        };

        function post(url, params, data) {
            $http({
                method: 'POST',
                params: params,
                url: url,
                data: data
            }).then(function successCallback(response) {
                alert(response);
            }, function errorCallback(response) {
                alert(response);
            });
        }
    }
})();