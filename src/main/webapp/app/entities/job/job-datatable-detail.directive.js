(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .directive('jobDetail', jobDetail);

    function jobDetail() {
        var directive = {
            restrict: 'E',
            scope   : {
                job        : '=ngModel',
            },
            templateUrl: 'job-datatable-detail.directive.html',
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
