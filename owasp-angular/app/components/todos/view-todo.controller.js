angular.module('devoxxApp.controllers')
.controller('ViewTodoController', ['$scope', '$http', '$log', '$sce', '$stateParams', '$state', 'Todos',
      function ($scope, $http, $log, $sce, $stateParams ,$state, Todos) {


        $scope.trusted = function(text) {
               return $sce.trustAsHtml(text);
             };
        // $scope.todos = Todo.query();

         $scope.state = "new";

            // Check if we have a ressource
         	if ($stateParams.id) {
         		$scope.state = "update";
         		$scope.todo = Todos.get({id: $stateParams.id});
         	} else {
            $scope.todo = {userId: $scope.globals.currentUser.id}
          }
         	$scope.getBtnLabel = function() {
         		return ($scope.state == "new") ? "Créer" : "Modifier";
         	};
         	$scope.saveTodo = function() {
         		if ($scope.state === "new") {
                    Todos.save($scope.todo,
                      function(success) {
                         $scope.alerts.push({type: 'success', msg: 'Tâche ' + success.summary + ' enregistré'});
                         $state.go('todos');
                      },
                      function(error) {
                          $scope.alerts.push({type: 'danger', msg: 'Impossible d\'enregistrer la tâche ' + '  : ' + error.status + "-" + error.statusText, details: error.data});
                       });
         		} else {
         	        Todos.update($scope.todo,
                      function(success) {
                         $scope.alerts.push({type: 'success', msg: 'Tâche ' + success.summary + ' modifié'});
                         $state.go('todos');
                      },
                      function(error) {
                         $scope.alerts.push({type: 'danger', msg: 'Impossible d\'enregistrer les modifications de la tâche '  +' : ' + error.status + "-" + error.statusText, details: error.data});
                      });
         		}

         	}

    }]);
