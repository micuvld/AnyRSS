(function () {
    'use strict';

    angular.module('app')
        .controller('FeedController', FeedController);

    FeedController.$inject = [
        'RequestService',
        'DashService'
    ];

    function FeedController() {
        let vm = this;

        vm.formatDate = function (millis) {
            return (new Date(millis)).toLocaleString();
        }
    }
})();