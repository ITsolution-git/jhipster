(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('job-status', {
            parent: 'entity',
            url: '/job-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JobStatuses'
            },
            params: {
                openJobId: -1
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-status/job-statuses.html',
                    controller: 'JobStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('job-status-detail', {
            parent: 'entity',
            url: '/job-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JobStatus'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-status/job-status-detail.html',
                    controller: 'JobStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'JobStatus', function($stateParams, JobStatus) {
                    return JobStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'job-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('job-status-detail.edit', {
            parent: 'job-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-status/job-status-dialog.html',
                    controller: 'JobStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JobStatus', function(JobStatus) {
                            return JobStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job-status.new', {
            parent: 'job-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-status/job-status-dialog.html',
                    controller: 'JobStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: ['Principal', function (Principal) {
                            

                            return Principal.identity().then(function(account) {
                                return {
                                    comment: null,
                                    createdOn: null,
                                    updatedOn: null,
                                    jobId: $stateParams.openJobId,
                                    userId: account.id,
                                    id: null    
                                }
                            });
                        }]

                    }
                }).result.then(function() {
                    $state.go('job-status', null, { reload: 'job-status' });
                }, function() {
                    $state.go('job-status');
                });
            }]
        })
        .state('job-status.edit', {
            parent: 'job-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-status/job-status-dialog.html',
                    controller: 'JobStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JobStatus', function(JobStatus) {
                            return JobStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-status', null, { reload: 'job-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job-status.delete', {
            parent: 'job-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-status/job-status-delete-dialog.html',
                    controller: 'JobStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['JobStatus', function(JobStatus) {
                            return JobStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-status', null, { reload: 'job-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
