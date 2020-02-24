angular.module('app')
    .constant('TOURIST_ENDPOINT', '/api/tourists/:id')
    .constant('TOURIST_FLIGHTS_ENDPOINT', '/api/tourists/:id/flights?page=:page')
    .constant('TOURIST_MANAGE_FLIGHTS_ENDPOINT', '/api/tourists/:id/flights/:fl?action=:option')
    .constant('AVAILABLE_TOURISTS_FLIGHTS_ENDPOINT', '/api/tourists/flights/:id?page=:page')
    .factory('Tourist',
        function ($resource, TOURIST_ENDPOINT, TOURIST_FLIGHTS_ENDPOINT,
                  TOURIST_MANAGE_FLIGHTS_ENDPOINT, AVAILABLE_TOURISTS_FLIGHTS_ENDPOINT) {
            return $resource(
                TOURIST_ENDPOINT, {id: '@_id'}, {
                    update: {
                        method: 'PUT',
                        url: TOURIST_MANAGE_FLIGHTS_ENDPOINT,
                        params:
                            {id: '@id', fl: '@fl', option: '@option'}
                    },
                    getFlights: {
                        method: 'GET',
                        url: TOURIST_FLIGHTS_ENDPOINT,
                        params: {id: '@id', page: '@page'},
                        isArray: true
                    },
                    getAvailableTourists: {
                        method: 'GET',
                        url: AVAILABLE_TOURISTS_FLIGHTS_ENDPOINT,
                        params: {id: '@id', page: '@page'},
                        isArray: true
                    }
                });
        })
    .service('TouristService', function (Tourist) {
        this.getAll = params => Tourist.query(params);
        this.getAvailableTourists = (index, page) => Tourist.getAvailableTourists({id: index, page: page});
        this.get = index => Tourist.get({id: index});
        this.getFlights = (index, page) => Tourist.getFlights({id: index, page: page});
        this.save = tourist => tourist.$save();
        this.update = (tourist, flight, option) => tourist.$update({id: tourist.id, fl: flight.id, option: option});
        this.delete = tourist => tourist.$delete({id: tourist.id});
    });