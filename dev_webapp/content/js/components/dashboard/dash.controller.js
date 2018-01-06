(function () {
    'use strict';

    angular.module('app')
        .controller('DashController', DashController);

    DashController.$inject = [
        'RequestService',
        'DashService'
    ];

    function DashController(_request, _dash) {
        let vm = this;

        let FEED_BATCH_SIZE = 20;
        let STORE_FEED_URL = "/feeds";

        let feedBatchNumber = 0;

        let autoPull = null;

        vm.$onInit = function () {
            vm.getData();
            vm.continuousPullFeeds();
        };

        vm.storeFeed = function () {
            if (vm.feedToStore) {
                let params = {
                    feed_url: vm.feedToStore
                };
                _request.post(STORE_FEED_URL, params).then(function (response) {
                        vm.getData();
                        if (!response.data.error) {
                            alert("Success!");
                        } else {
                            alert("Error!");
                        }
                    },
                    function () {
                        alert("Error!");
                    });
            }
        };

        vm.getFeedEntries = function () {
            return _dash.getFeedEntries(FEED_BATCH_SIZE * (feedBatchNumber + 1), 0).then(function (response) {
                vm.feedEntries = response.data.response;
            })
        };

        vm.getFeeds = function () {
            return _dash.getFeeds().then(function (response) {
                vm.feeds = response.data.response;
            })
        };

        vm.loadMoreFeedEntries = function () {
            feedBatchNumber++;

            return _dash.getFeedEntries(FEED_BATCH_SIZE, feedBatchNumber).then(function (response) {
                vm.feedEntries = vm.feedEntries.concat(response.data.response);
            })
        };

        vm.removeFeed = function (feedLink) {
            return _dash.removeFeed(feedLink).then(function (response) {
                    vm.getData();
                    if (!response.data.error) {
                        alert("Success!");
                    } else {
                        alert("Error!");
                    }
                },
                function () {
                    alert("Error!");
                });
        };

        vm.getData = function () {
            vm.getFeeds();
            vm.getFeedEntries();
        };

        vm.continuousPullFeeds = function () {
            autoPull = setInterval(vm.getData, 10000);
        };
    }
})();