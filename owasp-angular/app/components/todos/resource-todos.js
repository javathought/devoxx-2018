angular.module('devoxxApp.services').factory('Todos', function ($resource, Common) {

  var API_URI = Common.root_api + '/todos/:id';

  return $resource(API_URI , {id: '@id'}, {
    update: {method: 'PUT'},
      public: {method: 'GET', url: Common.root_api +'/todos/public', isArray: true}
  });

});
