angular.module('app')
    .config(function ($routeProvider) {
        $routeProvider
            .when('/tourists', {
                templateUrl: 'app/components/tourist/list/touristList.html',
                controller: 'TouristListController',
                controllerAs: 'ctrl'
            })
            .when('/tourist-add', {
                templateUrl: 'app/components/tourist/add/touristAdd.html',
                controller: 'TouristAddController',
                controllerAs: 'ctrl'
            })
            .when('/tourist-add-flights/:touristId', {
                templateUrl: 'app/components/tourist/edit/touristAddFlights.html',
                controller: 'TouristAddFlightsController',
                controllerAs: 'ctrl'
            })
            .when('/tourist-flights/:touristId', {
                templateUrl: 'app/components/tourist/flights/touristFlights.html',
                controller: 'TouristFlightsController',
                controllerAs: 'ctrl'
            })
            .when('/tourist-delete/:touristId', {
                templateUrl: 'app/components/tourist/delete/touristDelete.html',
                controller: 'TouristDeleteController',
                controllerAs: 'ctrl'
            })
            .when('/flights', {
                templateUrl: 'app/components/flight/list/flightList.html',
                controller: 'FlightListController',
                controllerAs: 'ctrl'
            })
            .when('/flight-add', {
                templateUrl: 'app/components/flight/add/flightAdd.html',
                controller: 'FlightAddController',
                controllerAs: 'ctrl'
            })
            .when('/flight-add-tourists/:flightId', {
                templateUrl: 'app/components/flight/edit/flightAddTourists.html',
                controller: 'FlightAddTouristsController',
                controllerAs: 'ctrl'
            })
            .when('/flight-tourists/:flightId', {
                templateUrl: 'app/components/flight/tourists/flightTourists.html',
                controller: 'FlightTouristsController',
                controllerAs: 'ctrl'
            })
            .when('/flight-delete/:flightId', {
                templateUrl: 'app/components/flight/delete/flightDelete.html',
                controller: 'FlightDeleteController',
                controllerAs: 'ctrl'
            })
            .otherwise({
                redirectTo: '/tourists'
            });
    });