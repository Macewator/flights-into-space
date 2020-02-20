angular.module('app')
    .controller('TouristAddController', function ($location, TouristService, Tourist) {
        const vm = this;
        vm.tourist = new Tourist();

        const saveCallback = () => {
            $location.path(`/tourist-add-flights/${vm.tourist.id}`);
        };
        const errorCallback = err => {
            vm.msg = `Save error: ${err.data.message}`;
        };

        vm.saveTourist = () => {
            TouristService.save(vm.tourist)
                .then(saveCallback)
                .catch(errorCallback);
        };
    });