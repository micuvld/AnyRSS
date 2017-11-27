(function () {
    'use strict';

    angular.module('app')
        .controller('DashController', DashController);

    DashController.$inject = ['RequestService'];

    function DashController(_request) {
        let vm = this;

        let STORE_FEED_URL = "/feeds";

        vm.storeFeed = function () {
            if (vm.feedToStore) {
                let params = {
                    feed_url: vm.feedToStore
                };
                _request.post(STORE_FEED_URL, params);
            }
        }
    }
})();