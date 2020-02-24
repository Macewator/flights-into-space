angular.module('app')
    .controller('TouristAddFlightsController', function ($routeParams, $location, TouristService, FlightService) {
        const vm = this;
        vm.page = 1;
        const touristId = $routeParams.touristId;

        vm.tourist = TouristService.get(touristId);
        vm.availableFlights = FlightService.getAvailableFlights(touristId, vm.page);

        const errorCallback = err => {
            vm.msg = `Error: ${err.data.message}`;
        };

        const updateCallback = () => {
            vm.availableFlights = FlightService.getAvailableFlights(touristId, vm.page);
            vm.msg = 'Flight added';
        };

        vm.addFlight = flight => {
            TouristService.update(vm.tourist, flight, 'add')
                .then(updateCallback)
                .catch(errorCallback);
        };

        vm.getPrevPage = () => {
            vm.page = vm.page - 1;
            vm.availableFlights = FlightService.getAvailableFlights(touristId, vm.page);
        };

        vm.getNextPage = () => {
            vm.page = vm.page + 1;
            vm.availableFlights = FlightService.getAvailableFlights(touristId, vm.page);
        };
    });
