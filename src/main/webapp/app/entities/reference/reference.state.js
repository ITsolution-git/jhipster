(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reference', {
            parent: 'entity',
            url: '/reference',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'References'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reference/references.html',
                    controller: 'ReferenceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('reference-detail', {
            parent: 'entity',
            url: '/reference/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Reference'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reference/reference-detail.html',
                    controller: 'ReferenceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Reference', function($stateParams, Reference) {
                    return Reference.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'reference',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reference-detail.edit', {
            parent: 'reference-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference/reference-dialog.html',
                    controller: 'ReferenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Reference', function(Reference) {
                            return Reference.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reference.new', {
            parent: 'reference',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference/reference-dialog.html',
                    controller: 'ReferenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userId: null,
                                fullName: null,
                                phoneNumber: null,
                                email: null,
                                referenceType: null,
                                company: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reference', null, { reload: 'reference' });
                }, function() {
                    $state.go('reference');
                });
            }]
        })
        .state('reference.edit', {
            parent: 'reference',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference/reference-dialog.html',
                    controller: 'ReferenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Reference', function(Reference) {
                            return Reference.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reference', null, { reload: 'reference' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reference.delete', {
            parent: 'reference',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference/reference-delete-dialog.html',
                    controller: 'ReferenceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Reference', function(Reference) {
                            return Reference.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reference', null, { reload: 'reference' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
