(function () {
    'use strict';

    angular.module('app')
        .component('dashComponent', {
            replace: true,
            templateUrl: (TEMPLATES) => {
                return TEMPLATES.DASHBOARD;
            },
            controller: 'DashController',
            controllerAs: 'vm'
        });
})();