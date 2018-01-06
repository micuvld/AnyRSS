(function () {
    'use strict';

    angular.module('app')
        .service('DashService', DashService);

    DashService.$inject = [
        'RequestService'
    ];

    function DashService(_request) {
        let FEED_ENTRIES_URL = "/feedEntries";
        let FEEDS_URL = "/feeds";

        return {
            getFeedEntries: getFeedEntries,
            getFeeds: getFeeds,
            removeFeed: removeFeed
        };

        function getFeedEntries(feedBatchSize, feedBatchNumber) {
            let params = {
                feedBatchSize: feedBatchSize,
                feedBatchNumber: feedBatchNumber
            };

            return _request.get(FEED_ENTRIES_URL, params);
        }

        function getFeeds() {
            return _request.get(FEEDS_URL);
        }

        function removeFeed(feedLink) {
            let params = {
                feed_link: feedLink
            };

            return _request.remove(FEEDS_URL, params);
        }
    }
})();