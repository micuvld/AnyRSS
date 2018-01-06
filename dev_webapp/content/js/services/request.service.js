(function () {
    'use strict';

    angular.module('app')
        .service('RequestService', RequestService);

    RequestService.$inject = ['$http'];

    function RequestService($http) {
        return {
            get: get,
            post: post
        };

        function get(url, params) {
            return $http({
                method: 'GET',
                params: params,
                url: url
            })
        }

        function post(url, params, data) {
            return $http({
                method: 'POST',
                params: params,
                url: url,
                data: data
            })
        }
    }
})();