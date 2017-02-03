(function() {
    'use strict';
    angular
        .module('isoftnetApp')
        .factory('JobRating', JobRating);

    JobRating.$inject = ['$resource', 'DateUtils'];

    function JobRating ($resource, DateUtils) {
        var resourceUrl =  'api/job-ratings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdOn = DateUtils.convertDateTimeFromServer(data.createdOn);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
