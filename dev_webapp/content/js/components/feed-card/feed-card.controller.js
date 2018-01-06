(function () {
    'use strict';

    angular.module('app')
        .controller('FeedController', FeedController);

    FeedController.$inject = [
        'RequestService',
        'DashService'
    ];

    function FeedController($mdSidenav, _request, _dash) {
        let vm = this;

    }
})();