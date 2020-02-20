angular.module('app')
    .controller('TouristAddFlightsController', function ($routeParams, $location, TouristService, FlightService) {
        const vm = this;
        const touristId = $routeParams.touristId;

        vm.tourist = TouristService.get(touristId);
        vm.availableFlights = FlightService.getAvailableFlights(touristId);

        const errorCallback = err => {
            vm.msg = `Error: ${err.data.message}`;
        };

        const updateCallback = () => {
            vm.availableFlights = FlightService.getAvailableFlights(touristId);
            vm.msg = 'Flight added';
        };

        vm.addFlight = flight => {
            TouristService.update(vm.tourist, flight, 'add')
                .then(errorCallback)
                .catch(updateCallback);
        };
    });
