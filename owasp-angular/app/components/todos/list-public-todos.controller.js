'use strict';

angular.module('devoxxApp.controllers')
.controller('ListPublicTodosController',
    ['$scope', '$rootScope', '$state', '$sce', 'Todos', 'Users',
    function ($scope, $rootScope, $state, $sce, Todos, Users) {

        $scope.trusted = function(text) {
            return $sce.trustAsHtml(text);
        };

        $scope.refresh = function () {
        // $scope.todos = $scope.globals.currentUser.$todos();
        $scope.todos = Todos.public();
      }
      $scope.refresh();


    }]);
