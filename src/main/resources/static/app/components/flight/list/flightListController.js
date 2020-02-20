angular.module('app')
    .controller('FlightListController', function (FlightService) {
        const vm = this;
        vm.flights = FlightService.getAll();

        vm.search = (keyword, category) => {
            vm.flights = FlightService.getAll({keyword, category});
        };
    });