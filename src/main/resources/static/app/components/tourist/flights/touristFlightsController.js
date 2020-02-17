angular.module('app')
    .controller('TouristFlightsController', function($routeParams, TouristService) {
        const vm = this;
        const touristId = $routeParams.touristId;
        vm.tourist = TouristService.get(touristId);
        vm.flights = TouristService.getFlights(touristId);
    });