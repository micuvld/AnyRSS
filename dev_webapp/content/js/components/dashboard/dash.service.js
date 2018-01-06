(function () {
    'use strict';

    angular.module('app')
        .service('DashService', DashService);

    DashService.$inject = [
        'RequestService'
    ];

    function DashService(_request) {
        let GET_FEEDS_URL = "/feeds";

        return {
            getFeeds: getFeeds
        };

        function getFeeds() {
            return _request.get(GET_FEEDS_URL);
        }
    }
})();