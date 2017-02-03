(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('ReferenceDetailController', ReferenceDetailController);

    ReferenceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Reference'];

    function ReferenceDetailController($scope, $rootScope, $stateParams, previousState, entity, Reference) {
        var vm = this;

        vm.reference = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isoftnetApp:referenceUpdate', function(event, result) {
            vm.reference = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
