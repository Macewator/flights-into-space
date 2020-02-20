angular.module('app')
    .controller('FlightAddTouristsController', function ($routeParams, $location, FlightService, TouristService) {
        const vm = this;
        const flightId = $routeParams.flightId;

        vm.flight = FlightService.get(flightId);
        vm.availableTourists = TouristService.getAvailableTourists(flightId);

        const errorCallback = err => {
            vm.msg = `Error: ${err.data.message}`;
        };

        const updateCallback = () => {
            vm.availableTourists = TouristService.getAvailableTourists(flightId);
            vm.msg = 'Tourists added';
        };

        vm.addTourist = tourist => {
            FlightService.update(vm.flight, tourist, 'add')
                .then(updateCallback)
                .catch(errorCallback);
        };

    });
