var myApp = angular.module('myApp', [
    'ngRoute',
    'ui.bootstrap',    
    'angularUtils.directives.dirPagination'
]);

myApp.controller('myController', ['$scope', '$http', function($scope, $http) {
    $scope.url = 'index.php';
    
    $http.get($scope.url + '?api=legislators').then(function(response) {
        $scope.legislators = response.data.results;
        $scope.stateNames = [];
        angular.forEach($scope.legislators, function(value, key) {
            if ($scope.stateNames.indexOf(value.state_name) === -1) {
                $scope.stateNames.push(value.state_name);
            }    
        })
        $scope.stateNames.sort();
    });
    
    $http.get($scope.url + '?api=bills&history_active=true').then(function(response) {
        $scope.activeBills = response.data.results;
    });
    
    $http.get($scope.url + '?api=bills&history_active=false').then(function(response) {
        $scope.newBills = response.data.results;
    });
    
    $http.get($scope.url + '?api=committees').then(function(response) {
        $scope.committees = response.data.results;
    });
    
    $scope.getLegislatorDetails = function(bioguideID) {        
        $http.get($scope.url + '?api=legislators&bioguide_id=' + bioguideID).then(function(response) {
            $scope.details_legislator = response.data.results;
            $scope.startDate = new Date($scope.details_legislator[0].term_start);
            $scope.endDate = new Date($scope.details_legislator[0].term_end);
            $scope.currDate = new Date();
            $scope.progressPercent = Math.round(($scope.currDate.getTime() - $scope.startDate.getTime()) / ($scope.endDate.getTime() - $scope.startDate.getTime()) * 100);
        });
        $http.get($scope.url + '?api=bills&bioguide_id=' + bioguideID).then(function(response) {
            $scope.topFivebills = response.data.results;
        });
        $http.get($scope.url + '?api=committees&bioguide_id=' + bioguideID).then(function(response) {
            $scope.topFivecommittees = response.data.results;
        });        
    };
    
    $scope.getBillDetails = function(bill_id) {
        $http.get($scope.url + '?api=bills&bill_id=' + bill_id).then(function(response) {
            $scope.details_bill = response.data.results;
            $scope.pdfurl = $scope.details_bill[0].last_version.urls.pdf;
        });        
    };
    
    $scope.goForward = function(carousel_id) {
        $(carousel_id).carousel('next').carousel('pause');
    };
    
    $scope.goBack = function(carousel_id) {
        $(carousel_id).carousel('prev').carousel('pause');
    };
    
    $scope.goToPageNum = function(carousel_id, number) {
        $(carousel_id).carousel(number).carousel('pause');
    };
    
    $scope.goToFirst = function(carousel_id) {
        $(carousel_id).carousel(0).carousel('pause');
    };    
    
    $scope.toggleMenu = function() {
        $('#navbar').toggleClass("hidden");
        $('#content').toggleClass("col-xs-10").toggleClass("col-sm-10").toggleClass("col-xs-12").toggleClass("col-sm-12");
    };
    
    $scope.stateFilter = function(stateName) {
        return (stateName === null) ? {} : {state_name:stateName};
    };
    
    $scope.favoriteLegislators = localStorage.getItem('legislators') === null ? [] : angular.fromJson(localStorage.getItem('legislators'));
    
    $scope.favoriteLegislators_id = localStorage.getItem('legislators_id') === null ? [] : localStorage.getItem('legislators_id').split(',');   
    
    $scope.toggleFavoriteLegislators = function(obj) {
        if (!$scope.isInFavorite('legislators', obj)) {
            $scope.addToFavoriteLegislators(obj);
        } else {
            $scope.removeFromFavoriteLegislators(obj);
        }        
    }    
    
    $scope.addToFavoriteLegislators = function(obj) {
        $scope.favoriteLegislators.push(obj);
        $scope.favoriteLegislators_id.push(obj.bioguide_id);
        localStorage.setItem('legislators', angular.toJson($scope.favoriteLegislators));
        localStorage.setItem('legislators_id', $scope.favoriteLegislators_id.toString());
    };
    
    $scope.removeFromFavoriteLegislators = function(obj) {
        $scope.favoriteLegislators.splice($scope.favoriteLegislators.indexOf(obj), 1);
        $scope.favoriteLegislators_id.splice($scope.favoriteLegislators_id.indexOf(obj.bioguide_id), 1);
        localStorage.setItem('legislators', angular.toJson($scope.favoriteLegislators));
        localStorage.setItem('legislators_id', $scope.favoriteLegislators_id.toString());
    };
    
    $scope.favoriteBills = localStorage.getItem('bills') === null ? [] : angular.fromJson(localStorage.getItem('bills'));
    
    $scope.favoriteBills_id = localStorage.getItem('bills_id') === null ? [] : localStorage.getItem('bills_id').split(',');
    
    $scope.toggleFavoriteBills = function(obj) {
        if (!$scope.isInFavorite('bills', obj)) {
            $scope.addToFavoriteBills(obj);
        } else {
            $scope.removeFromFavoriteBills(obj);
        }        
    }        
    
    $scope.addToFavoriteBills = function(obj) {
        $scope.favoriteBills.push(obj);
        $scope.favoriteBills_id.push(obj.bill_id);
        localStorage.setItem('bills', angular.toJson($scope.favoriteBills));
        localStorage.setItem('bills_id', $scope.favoriteBills_id.toString());
    };
    
    $scope.removeFromFavoriteBills = function(obj) {
        $scope.favoriteBills.splice($scope.favoriteBills.indexOf(obj), 1);
        $scope.favoriteBills_id.splice($scope.favoriteBills_id.indexOf(obj.bill_id), 1);
        localStorage.setItem('bills', angular.toJson($scope.favoriteBills));
        localStorage.setItem('bills_id', $scope.favoriteBills_id.toString());
    };
    
    $scope.favoriteCommittees = localStorage.getItem('committees') === null ? [] : angular.fromJson(localStorage.getItem('committees'));
    
    $scope.favoriteCommittees_id = localStorage.getItem('committees_id') === null ? [] : localStorage.getItem('committees_id').split(',');
    
    $scope.toggleFavoriteCommittees = function(obj) {
        if (!$scope.isInFavorite('committees', obj)) {
            $scope.addToFavoriteCommittees(obj);
        } else {
            $scope.removeFromFavoriteCommittees(obj);
        }        
    }        
    
    $scope.addToFavoriteCommittees = function(obj) {
        $scope.favoriteCommittees.push(obj);
        $scope.favoriteCommittees_id.push(obj.committee_id);
        localStorage.setItem('committees', angular.toJson($scope.favoriteCommittees));
        localStorage.setItem('committees_id', $scope.favoriteCommittees_id.toString());
    };
    
    $scope.removeFromFavoriteCommittees = function(obj) {
        $scope.favoriteCommittees.splice($scope.favoriteCommittees.indexOf(obj), 1);
        $scope.favoriteCommittees_id.splice($scope.favoriteCommittees_id.indexOf(obj.committee_id), 1);
        localStorage.setItem('committees', angular.toJson($scope.favoriteCommittees));
        localStorage.setItem('committees_id', $scope.favoriteCommittees_id.toString());
    };
    
    $scope.isInFavorite = function(str, obj) {
        if (obj !== undefined) {
            if (str === 'legislators') {
                return $scope.favoriteLegislators_id.indexOf(obj.bioguide_id) !== -1;
            } else if (str === 'bills') {
                return $scope.favoriteBills_id.indexOf(obj.bill_id) !== -1;
            } else if (str === 'committees') {
                return $scope.favoriteCommittees_id.indexOf(obj.committee_id) !== -1;
            }            
        }
        return false;
    };
    
    $scope.isNull = function(str) {
        return str == null;
    }
}]);

myApp.config(['$routeProvider', function($routeProvider) {
    $routeProvider
    .when('/legislators', {
        templateUrl: 'partials/legislators.html'
    })
    .when('/bills', {
        templateUrl: 'partials/bills.html'
    })
    .when('/committees', {
        templateUrl: 'partials/committees.html'
    })
    .when('/favorites', {
        templateUrl: 'partials/favorites.html'
    })
    .otherwise({
        redirectTo: '/legislators'
    });
}]);

myApp.filter('toPartyFullName', function() {
    return function(x) {
        if (x === 'R') {
            return "Republican";
        } else if (x === 'D') {
            return "Democrat";
        } else if (x === 'I') {
            return "Independent";
        }       
        return "";
    };
});

myApp.filter('toChamberImageName', function() {
    return function(x) {
        if (x.charAt(0) === 'h') {
            return "h.png";
        } else if (x.charAt(0) === 's' || x.charAt(0) === 'j') {
            return "s.svg";
        }
        return "";
    };
});

myApp.filter('toDistrictString', function() {
    return function(x) {
        if (x == null) {
            return "N.A";
        }
        return "District " + x;
    };
});

myApp.filter('officeFilter', function() {
    return function(x) {
        if (x == null) {
            return "N.A";
        }
        return x;
    };
});

myApp.filter('billStatusFilter', function() {
    return function(x) {
        if (x) {
            return "Active";
        }
        return "New";
    };
});
