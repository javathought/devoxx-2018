var controllers = angular.module('devoxxApp.controllers', []);

controllers.controller('MainController', ['$scope', '$http', '$interval', 'alert', '$q', '$window',
    function ($scope, $http, $interval, alert, $q, $window) {
        $scope.loading = true;
        $scope.alerts = alert.alerts;
        $scope.url = '#';
        $scope.closeAlert = function(index) {
          $scope.alerts.splice(index, 1);
        };

        $scope.resolveUrl = function() {
            async().then(function(resolvedUrl){
              $window.open(resolvedUrl, 'myTab');
            });
            $window.open('#', 'myTab');
        };

        function async() {
            var deffered = $q.defer();
            setTimeout(function() {
              /*deffered.resolve('http://www.google.com');*/
              deffered.resolve($scope.url);
            }, 1);

            return deffered.promise;
        }
    }]);
