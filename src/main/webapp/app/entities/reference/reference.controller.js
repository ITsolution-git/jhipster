(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .controller('ReferenceController', ReferenceController);

    ReferenceController.$inject = ['$scope', '$state', 'Reference'];

    function ReferenceController ($scope, $state, Reference) {
        var vm = this;

        vm.references = [];

        loadAll();

        function loadAll() {
            Reference.query(function(result) {
                vm.references = result;
                vm.searchQuery = null;
            });
        }
    }
})();
