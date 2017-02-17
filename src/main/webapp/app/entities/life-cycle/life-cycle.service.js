(function() {
    'use strict';
    angular
        .module('isoftnetApp')
        .factory('LifeCycle', LifeCycle);

    LifeCycle.$inject = ['$resource', 'DateUtils'];

    function LifeCycle ($resource, DateUtils) {
        var resourceUrl =  'api/life-cycles/:id';

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
