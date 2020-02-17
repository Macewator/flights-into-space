angular.module('app')
    .controller('TouristAddController', function ($routeParams, $location, $timeout, TouristService, Tourist) {
        const vm = this;
        vm.tourist = new Tourist();

        const saveCallback = () => {
            $location.path(`/tourist-edit/${vm.tourist.id}`);
        };
        const errorCallback = err => {
            vm.msg = `Write error: ${err.data.message}`;
        };

        vm.saveTourist = () => {
            TouristService.save(vm.tourist)
                .then(saveCallback)
                .catch(errorCallback);
        };
    });