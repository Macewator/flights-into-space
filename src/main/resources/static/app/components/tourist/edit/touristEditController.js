angular.module('app')
    .controller('TouristEditController', function ($routeParams, $location, $timeout, TouristService, FlightService) {
        const vm = this;
        const touristId = $routeParams.touristId;

        if (touristId) {
            vm.tourist = TouristService.get(touristId);
            vm.touristsFlights = TouristService.getFlights(touristId);
            vm.availableflights = FlightService.getAvailableFlights(touristId);
        }

        const errorCallback = err => {
            vm.msg = `Error: ${err.data.message}`;
        };

        const updateCallback = response => {
            $location.path(`/tourist-edit/${vm.tourist.id}`);
            vm.msg = 'Flight added';
        };

        vm.addFlight = flight => {
            vm.tourist.flights.push(flight)
            TouristService.update(vm.tourist)
                .then(updateCallback)
                .catch(errorCallback);
        };

        vm.removeFlight = flight => {
            vm.tourist.flights.push(flight)
            TouristService.update(vm.tourist)
                .then(updateCallback)
                .catch(errorCallback);
        };
    });