(function () {
    'use strict';

    angular.module('app')
        .directive('truncatedSpan', TruncatedSpan);

    TruncatedSpan.$inject = ['$compile'];

    function TruncatedSpan($compile) {
        let DEFAULT_LENGTH = 100;

        function shortenText(text, truncateLength) {
            if (text.length > truncateLength) {
                return text.substring(0, truncateLength) + "...";
            } else {
                return text;
            }
        }

        function refineText(text) {
            //remove any tags: https://stackoverflow.com/questions/822452/strip-html-from-text-javascript
            return text.replace(/<.*?>/g, ' ');
        }

        return {
            restrict: "E",
            scope: {
                text: '@',
                length: '@'
            },
            link: function (scope, element) {
                let truncateLength = scope.length ? scope.length : DEFAULT_LENGTH;

                let textToDisplay = shortenText(refineText(scope.text), truncateLength);

                let textSpan = angular.element("<span>" + textToDisplay + "</span>");

                element.append(textSpan);
                $compile(element);
            }
        }
    }
})();