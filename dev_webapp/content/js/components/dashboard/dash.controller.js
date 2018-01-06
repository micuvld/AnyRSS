(function () {
    'use strict';

    angular.module('app')
        .controller('DashController', DashController);

    DashController.$inject = [
        '$mdSidenav',

        'RequestService',
        'DashService'
    ];

    function DashController($mdSidenav, _request, _dash) {
        let vm = this;

        let STORE_FEED_URL = "/feeds";

        vm.$onInit = function () {
            _dash.getFeeds().then(function (response) {
                vm.feeds = response.data.response;
                console.log(vm.feeds);
            })
        };

        vm.storeFeed = function () {
            if (vm.feedToStore) {
                let params = {
                    feed_url: vm.feedToStore
                };
                _request.post(STORE_FEED_URL, params)
                    .then(function () {
                        alert("Success!");
                    });
            }
        };
    }
})();