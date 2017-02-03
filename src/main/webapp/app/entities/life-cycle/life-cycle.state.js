(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('life-cycle', {
            parent: 'entity',
            url: '/life-cycle',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LifeCycles'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/life-cycle/life-cycles.html',
                    controller: 'LifeCycleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('life-cycle-detail', {
            parent: 'entity',
            url: '/life-cycle/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LifeCycle'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/life-cycle/life-cycle-detail.html',
                    controller: 'LifeCycleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LifeCycle', function($stateParams, LifeCycle) {
                    return LifeCycle.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'life-cycle',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('life-cycle-detail.edit', {
            parent: 'life-cycle-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/life-cycle/life-cycle-dialog.html',
                    controller: 'LifeCycleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LifeCycle', function(LifeCycle) {
                            return LifeCycle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('life-cycle.new', {
            parent: 'life-cycle',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/life-cycle/life-cycle-dialog.html',
                    controller: 'LifeCycleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                event: null,
                                createdOn: null,
                                createdBy: null,
                                jobId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('life-cycle', null, { reload: 'life-cycle' });
                }, function() {
                    $state.go('life-cycle');
                });
            }]
        })
        .state('life-cycle.edit', {
            parent: 'life-cycle',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/life-cycle/life-cycle-dialog.html',
                    controller: 'LifeCycleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LifeCycle', function(LifeCycle) {
                            return LifeCycle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('life-cycle', null, { reload: 'life-cycle' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('life-cycle.delete', {
            parent: 'life-cycle',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/life-cycle/life-cycle-delete-dialog.html',
                    controller: 'LifeCycleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LifeCycle', function(LifeCycle) {
                            return LifeCycle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('life-cycle', null, { reload: 'life-cycle' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
