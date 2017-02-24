(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('job', {
            parent: 'entity',
            url: '/job',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Jobs',
            },
            params: {
                openJobId: 9
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job/jobs.html',
                    controller: 'JobController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('job-detail', {
            parent: 'entity',
            url: '/job/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Job'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job/job-detail.html',
                    controller: 'JobDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Job', function($stateParams, Job) {
                    return Job.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'job',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('job-detail.edit', {
            parent: 'job-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job/job-dialog.html',
                    controller: 'JobDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Job', function(Job) {
                            return Job.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job.new', {
            parent: 'job',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job/job-dialog.html',
                    controller: 'JobDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                profession: null,
                                duration: null,
                                term: null,
                                referralFee: null,
                                shortDescription: null,
                                longDescription: null,
                                type: null,
                                status: null,
                                workPermit: null,
                                skill: null,
                                createdOn: null,
                                updatedOn: null,
                                jobGroup: null,
                                renewable: null,
                                salary: null,
                                jobURL: null,
                                industry: null,
                                createdBy: null,
                                companyId: null,
                                attachement: null,
                                logo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('job', null, { reload: 'job' });
                }, function() {
                    $state.go('job');
                });
            }]
        })
        .state('job.edit', {
            parent: 'job',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job/job-dialog.html',
                    controller: 'JobDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Job', function(Job) {
                            return Job.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('job', null, { reload: 'job' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job.delete', {
            parent: 'job',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job/job-delete-dialog.html',
                    controller: 'JobDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Job', function(Job) {
                            return Job.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('job', null, { reload: 'job' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job.addstatus', {
            parent: 'job',
            url: '/addstatus',
            data: {
                authorities: ['ROLE_USER'],
            },
            params: {
                selectIDs: [],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job/job-addstatus-dialog.html',
                    controller: 'cccc',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                    }
                }).result.then(function() {
                    $state.go('job', null, { reload: 'job' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job.closejobs', {
            parent: 'job',
            url: '/closejobs',
            data: {
                authorities: ['ROLE_USER'],
            },
            params: {
                selectIDs: [],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job/job-closejob-dialog.html',
                    controller: 'JobCloseJobsController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                    }
                }).result.then(function() {
                    $state.go('job', null, { reload: 'job' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job.deletejobs', {
            parent: 'job',
            url: '/deletejobs',
            data: {
                authorities: ['ROLE_USER'],
            },
            params: {
                selectIDs: [],
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job/job-deletejob-dialog.html',
                    controller: 'JobDeleteJobsController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                    }
                }).result.then(function() {
                    $state.go('job', null, { reload: 'job' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
