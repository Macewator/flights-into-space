angular.module('app')
    .constant('TOURIST_ENDPOINT', '/api/tourists/:id')
    .constant('TOURIST_FLIGHTS_ENDPOINT', '/api/tourists/:id/flights')
    .constant('TOURIST_MANAGE_FLIGHTS_ENDPOINT', '/api/tourists/:id/flights/:fl?action=:option')
    .constant('AVAILABLE_TOURISTS_FLIGHTS_ENDPOINT', '/api/tourists/flights/:id')
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
                    params: {id: '@id'},
                    isArray: true
                },
                getAvailableTourists: {
                    method: 'GET',
                    url: AVAILABLE_TOURISTS_FLIGHTS_ENDPOINT,
                    params: {id: '@id'},
                    isArray: true
                }
            });
    })
    .service('TouristService', function (Tourist) {
        this.getAll = params => Tourist.query(params);
        this.getAvailableTourists = index => Tourist.getAvailableTourists({id: index});
        this.get = index => Tourist.get({id: index});
        this.getFlights = index => Tourist.getFlights({id: index});
        this.save = tourist => tourist.$save();
        this.update = (tourist, flight, option) => tourist.$update({id: tourist.id, fl: flight.id, option: option});
        this.delete = tourist => tourist.$delete({id: tourist.id});
    });