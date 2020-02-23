angular.module('app')
    .constant('FLIGHT_ENDPOINT', '/api/flights/:id')
    .constant('FLIGHT_TOURISTS_ENDPOINT', '/api/flights/:id/tourists')
    .constant('FLIGHT_MANAGE_TOURISTS_ENDPOINT', '/api/flights/:id/tourists/:tr?action=:option')
    .constant('AVAILABLE_FLIGHTS_TOURISTS_ENDPOINT', '/api/flights/tourists/:id')
    .factory('Flight',
        function ($resource, FLIGHT_ENDPOINT, FLIGHT_TOURISTS_ENDPOINT,
                  FLIGHT_MANAGE_TOURISTS_ENDPOINT, AVAILABLE_FLIGHTS_TOURISTS_ENDPOINT) {
        return $resource(
            FLIGHT_ENDPOINT, {id: '@_id'}, {
                update: {
                    method: 'PUT',
                    url: FLIGHT_MANAGE_TOURISTS_ENDPOINT,
                    params:
                        {id: '@id', tr: '@tr', option: '@option'}
                },
                getTourists: {
                    method: 'GET',
                    url: FLIGHT_TOURISTS_ENDPOINT,
                    params: {id: '@id'},
                    isArray: true
                },
                getAvailableFlights: {
                    method: 'GET',
                    url: AVAILABLE_FLIGHTS_TOURISTS_ENDPOINT,
                    params: {id: '@id'},
                    isArray: true
                }
            });
    })
    .service('FlightService', function (Flight) {
        this.getAll = (param1, param2, param3) => Flight.query(param1, param2, param3);
        this.getAvailableFlights = index => Flight.getAvailableFlights({id: index});
        this.get = index => Flight.get({id: index});
        this.getTourists = index => Flight.getTourists({id: index});
        this.save = flight => flight.$save();
        this.update = (flight, tourist, option) => flight.$update({id: flight.id, tr: tourist.id, option: option});
        this.delete = flight => flight.$delete({id: flight.id});
    });
