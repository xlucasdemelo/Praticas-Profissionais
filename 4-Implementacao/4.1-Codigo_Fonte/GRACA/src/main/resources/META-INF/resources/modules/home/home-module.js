(function(window, angular, undefined) {
	'use strict';

	//Start the AngularJS
	var module = angular.module('home', ['ngMessages', 'ngSanitize', 'ngMaterial', 'ui.router', 'eits-md', 'eits-ng', 'md.data.table' ]);

	/**
	 * 
	 */
	module.config( function( $stateProvider, $urlRouterProvider, $importServiceProvider, $translateProvider, $mdThemingProvider ) {
		
	
		
		//-------
		//Broker configuration
		//-------
		$importServiceProvider.setBrokerURL("./broker/interface");
		//-------
		//Translate configuration
		//-------
		$translateProvider.useURL('./bundles');

		//-------
		//URL Router
		//-------
        $urlRouterProvider.otherwise("/");

        //HOME
        $stateProvider.state('home',{
        	url : "/",
        	templateUrl: './modules/home/views/home/home-index.html',
        });
        
        //FAMILIA
        $stateProvider.state('familia',{
        	abstract: true,
        	url : "/familia",
        	template: '<div layout="column" flex ui-view=""/>',
        	controller : 'FamiliaController as familiaController'
        })
        .state('familia.list',{
            url:'/listar',
            templateUrl: './modules/home/views/familia/familia-list.html'
          });
	});

	/**
	 * 
	 */
	module.run( function( $rootScope, $window, $state, $stateParams ) {
		//$rootScope.$usuario 	= $window.usuario;
		$rootScope.$state 		= $state;
		$rootScope.$stateParams = $stateParams;
	});

	/**
	 * 
	 */
	angular.element(document).ready( function() {
		angular.bootstrap( document, ['home']);
	});

})(window, window.angular);