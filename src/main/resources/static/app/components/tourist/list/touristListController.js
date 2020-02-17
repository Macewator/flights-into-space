angular.module('app')
    .controller('TouristListController', function(TouristService) {
        const vm = this;
        vm.tourists = TouristService.getAll();

        vm.search = lastName => {
            vm.tourists = TouristService.getAll({lastName});
        };
    });