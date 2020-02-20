angular.module('app')
    .controller('TouristFlightsController', function ($routeParams, $location, TouristService) {
        const vm = this;
        const touristId = $routeParams.touristId;

        vm.tourist = TouristService.get(touristId);
        vm.touristFlights = TouristService.getFlights(touristId);

        const errorCallback = err => {
            vm.msg = `Error: ${err.data.message}`;
        };

        const updateCallback = tourist => {
            vm.touristFlights = TouristService.getFlights(tourist.id);
            $location.path(`/tourist-flights/${vm.tourist.id}`);
            vm.msg = 'Flight removed';
        };

        vm.removeFlight = flight => {
            TouristService.update(vm.tourist, flight, 'remove')
                .then(updateCallback)
                .catch(errorCallback);
        };
    });