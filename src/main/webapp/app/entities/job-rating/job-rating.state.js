(function() {
    'use strict';

    angular
        .module('isoftnetApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('job-rating', {
            parent: 'entity',
            url: '/job-rating',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JobRatings'
            },
            params:{
                openJobId: -1
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-rating/job-ratings.html',
                    controller: 'JobRatingController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('job-rating-detail', {
            parent: 'entity',
            url: '/job-rating/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'JobRating'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/job-rating/job-rating-detail.html',
                    controller: 'JobRatingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'JobRating', function($stateParams, JobRating) {
                    return JobRating.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'job-rating',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('job-rating-detail.edit', {
            parent: 'job-rating-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-rating/job-rating-dialog.html',
                    controller: 'JobRatingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JobRating', function(JobRating) {
                            return JobRating.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job-rating.new', {
            parent: 'job-rating',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-rating/job-rating-dialog.html',
                    controller: 'JobRatingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                userId: null,
                                jobId: null,
                                comment: null,
                                responsive: null,
                                truthful: null,
                                reliable: null,
                                professional: null,
                                efficient: null,
                                effective: null,
                                overall: null,
                                createdOn: null,
                                informative: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('job-rating', null, { reload: 'job-rating' });
                }, function() {
                    $state.go('job-rating');
                });
            }]
        })
        .state('job-rating.edit', {
            parent: 'job-rating',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-rating/job-rating-dialog.html',
                    controller: 'JobRatingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['JobRating', function(JobRating) {
                            return JobRating.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-rating', null, { reload: 'job-rating' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('job-rating.delete', {
            parent: 'job-rating',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/job-rating/job-rating-delete-dialog.html',
                    controller: 'JobRatingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['JobRating', function(JobRating) {
                            return JobRating.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('job-rating', null, { reload: 'job-rating' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
