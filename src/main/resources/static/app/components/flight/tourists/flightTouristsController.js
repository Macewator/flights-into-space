angular.module('app')
    .controller('FlightTouristsController', function ($routeParams, $location, FlightService) {
        const vm = this;
        const flightId = $routeParams.flightId;

        vm.flight = FlightService.get(flightId);
        vm.flightTourists = FlightService.getTourists(flightId);

        const errorCallback = err => {
            vm.msg = `Error: ${err.data.message}`;
        };

        const updateCallback = flight => {
            vm.flightTourists = FlightService.getTourists(flight.id);
            $location.path(`/flight-tourists/${vm.flight.id}`);
            vm.msg = 'Tourist removed';
        };

        vm.removeTourist = tourist => {
            FlightService.update(vm.flight, tourist, 'remove')
                .then(updateCallback)
                .catch(errorCallback);
        };
    });
