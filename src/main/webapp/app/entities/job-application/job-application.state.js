(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('job-application', {
            parent: 'entity',
            url: '/job-application/view/:openJobId',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JobApplications'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-application/job-applications.html',
                    controller: 'JobApplicationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                job: ['$stateParams', 'Job', function($stateParams, Job) {
                    return Job.get({id : $stateParams.openJobId}).$promise;
                }],
            }
        })
        .state('job-application-detail', {
            parent: 'entity',
            url: '/job-application/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JobApplication'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-application/job-application-detail.html',
                    controller: 'JobApplicationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'JobApplication', function($stateParams, JobApplication) {
                    return JobApplication.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'job-application',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('job-application-sendmsg', {
            parent: 'job-application',
            url: '/job-application-sendmsg/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JobApplication'
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-application/job-application-sendmsg-dialog.html',
                    controller: 'JobApplicationSendmsgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })

        .state('job-application-detail.edit', {
            parent: 'job-application-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-application/job-application-dialog.html',
                    controller: 'JobApplicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JobApplication', function(JobApplication) {
                            return JobApplication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job-application.new', {
            parent: 'job-application',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-application/job-application-dialog.html',
                    controller: 'JobApplicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {

                        entity: ['Principal', '$stateParams', function (Principal, $stateParams) {

                            return Principal.identity().then(function(account) {
                                return {
                                    jobId: parseInt($stateParams.openJobId),
                                    coverNote: null,
                                    resumeName: null,
                                    status: null,
                                    userId: account.id,
                                    referredBy: null,
                                    createdOn: null,
                                    updatedOn: null,
                                    id: null
                                };
                            });
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-application', null, { reload: 'job-application' });
                }, function() {
                    $state.go('job-application');
                });
            }]
        })
        .state('job-application.edit', {
            parent: 'job-application',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-application/job-application-dialog.html',
                    controller: 'JobApplicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JobApplication', function(JobApplication) {
                            return JobApplication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-application', null, { reload: 'job-application' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job-application.delete', {
            parent: 'job-application',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-application/job-application-delete-dialog.html',
                    controller: 'JobApplicationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['JobApplication', function(JobApplication) {
                            return JobApplication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-application', null, { reload: 'job-application' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
