(function(window, angular, undefined) {
	'use strict';

	//Start the AngularJS
	var module = angular.module('home', ['ngMessages', 'ngSanitize', 'ngMaterial', 'ui.router', 'eits-md', 'eits-ng', 'md.data.table', 'ngMask' ]);

	/**
	 * 
	 */
	module.config( function( $stateProvider, $urlRouterProvider, $importServiceProvider, $translateProvider, $mdThemingProvider, $mdDateLocaleProvider ) {
		
		$mdDateLocaleProvider.parseDate = function(dateString) {
	       var m = moment(dateString, 'DD/MM/YYYY', true);
	       return m.isValid() ? m.toDate() : new Date(NaN);
	    }
		
//		$mdThemingProvider.theme('default')
//	    .primaryPalette('blue', {
//	      'default': '500', // by default use shade 400 from the pink palette for primary intentions
//	      'hue-1': '100', // use shade 100 for the <code>md-hue-1</code> class
//	      'hue-2': '600', // use shade 600 for the <code>md-hue-2</code> class
//	      'hue-3': 'A100' // use shade A100 for the <code>md-hue-3</code> class
//	    })
//	    // If you specify less than all of the keys, it will inherit from the
//	    // default shades
//	    .accentPalette('red', {
//	      'default': '700' // use shade 200 for default, and keep all other shades the same
//	    });

		
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
          })
        .state('familia.add',{
            url:'/inserir',
            templateUrl: './modules/home/views/familia/familia-form.html'
          })
	    .state('familia.edit',{
	        url:'/editar/{id:[0-9]{1,10}}',
	        templateUrl: './modules/home/views/familia/familia-form.html'
	     })
	     
	     //CRIANÇA
        $stateProvider.state('crianca',{
        	abstract: true,
        	url : "/crianca",
        	template: '<div layout="column" flex ui-view=""/>',
        	controller : 'CriancaController as criancaController'
        })
        .state('crianca.list',{
            url:'/listar',
            templateUrl: './modules/home/views/crianca/crianca-list.html'
          })
        .state('crianca.add',{
            url:'/inserir',
            templateUrl: './modules/home/views/crianca/crianca-form.html'
          })
	    .state('crianca.edit',{
	        url:'/editar/{id:[0-9]{1,10}}',
	        templateUrl: './modules/home/views/crianca/crianca-form.html'
	     })
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