(function () {
    'use strict';

    angular.module('app')
        .component('feedCard', {
            bindings: {
                feedEntry: '='
            },
            replace: true,
            templateUrl: (TEMPLATES) => {
                return TEMPLATES.FEED_CARD;
            },
            controller: 'FeedController',
            controllerAs: 'vm'
        });
})();