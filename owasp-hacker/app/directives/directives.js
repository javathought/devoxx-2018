/**
 * prevent the {{}} syntax bindings AngularJS is displayed briefly
 * at page load
 *
 * Angular has a native implementation with attribute ngCloak
 * but not active at application startup
 *
 * From :
 * http://blogs.infinitesquare.com/b/seb/archives/pourquoi-il-ne-faut-pas-utiliser-ngcloak
 * http://sebastienollivier.fr/blog/angularjs/pourquoi-il-ne-faut-pas-utiliser-ngcloak
 *
 */
angular.module('devoxxApp.directives').directive("deferredCloak", function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            attrs.$set("deferredCloak", undefined);
            element.removeClass("deferred-cloak");
        }
    };
});
