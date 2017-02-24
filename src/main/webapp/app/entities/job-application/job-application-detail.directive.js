(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .directive('jobApplicationDetail', jobApplicationDetail);

    function jobApplicationDetail() {
        var directive = {
            restrict: 'E',
            scope   : false,
            templateUrl: 'job-application-detail.directive.html',
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs, parentCtrl) {

            // scope.$on('tick', function(){
            //     el.append('-');
            // });

            // scope.$on('$destroy', function(){
            //     alert('Put unbind handlers for timers etc. here')
            // })
        }
    }
})();
