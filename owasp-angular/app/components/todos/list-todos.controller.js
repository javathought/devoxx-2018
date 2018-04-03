'use strict';

angular.module('devoxxApp.controllers')
.controller('ListTodosController',
    ['$scope', '$rootScope', '$state', 'Todos', 'Users',
    function ($scope, $rootScope, $state, Todos, Users) {

      $scope.refresh = function () {
        // $scope.todos = $scope.globals.currentUser.$todos();
        $scope.todos = Users.todos({id: $scope.globals.currentUser.id});
      }
      $scope.refresh();

      $scope.deletetodo = function(todo) {
        todo.$delete(function(success) {
           $scope.alerts.push({type: 'success', msg: 'Tâche supprimée'});
           var index = $scope.todos.indexOf(todo);
           $scope.todos.splice(index, 1);

           // $state.go('todos');
        },
        function(error) {
           $scope.alerts.push({type: 'danger', msg: 'Impossible de supprimer la tâche '  +' : ' + error.status + "-" + error.statusText, details: error.data});
        });
      };


    }]);
