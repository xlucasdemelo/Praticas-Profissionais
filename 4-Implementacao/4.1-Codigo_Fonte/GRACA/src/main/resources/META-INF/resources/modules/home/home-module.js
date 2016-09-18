(function(window, angular, undefined) {
	'use strict';

	//Start the AngularJS
	var module = angular.module('home', ['ngMessages', 'ngSanitize', 'ngMaterial', 'ui.router', 'eits-md', 'eits-ng', 
	                                     'md.data.table', 'ngMask', 'maskMoney', 'ngCpfCnpj', 'material.components.expansionPanels' ]);

	/**
	 * 
	 */
	module.config( function( $stateProvider, $urlRouterProvider, $importServiceProvider, $translateProvider, $mdThemingProvider, $mdDateLocaleProvider ) {
		
		    $mdDateLocaleProvider.formatDate = function(date) {
		    	if (date == null)
		    		return null;
		       return moment(date).format('DD/MM/YYYY');
		    };
		    
		
		$mdThemingProvider.theme('default')
	    .primaryPalette('indigo', {
	      'default': '800', // by default use shade 400 from the pink palette for primary intentions
	      'hue-1': '50', // use shade 100 for the <code>md-hue-1</code> class
	    })
	    // If you specify less than all of the keys, it will inherit from the
	    // default shades
	    .accentPalette('amber', {
	      'default': '700' // use shade 200 for default, and keep all other shades the same
	    });

		
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
	     
	     //QUESTIONARIO
	     $stateProvider.state('questionario',{
	    	 abstract: true,
	    	 url : "/questionario",
	    	 template: '<div layout="column" flex ui-view=""/>',
	    	 controller : 'QuestionarioController as questionarioController'
	     })
	     .state('questionario.list',{
	    	 url:'/listar',
	    	 templateUrl: './modules/home/views/questionario/questionario-list.html'
	     })
	     .state('questionario.add',{
	    	 url:'/inserir',
	    	 templateUrl: './modules/home/views/questionario/questionario-form.html'
	     })
	     .state('questionario.edit',{
	    	 url:'/editar/{id:[0-9]{1,10}}',
	    	 templateUrl: './modules/home/views/questionario/questionario-form.html'
	     })
	     
	     //AVALIAÇÃO INDIVIDUAL
	     $stateProvider.state('avaliacao-individual',{
	    	 abstract: true,
	    	 url : "/avaliacao-individual",
	    	 template: '<div layout="column" flex ui-view=""/>',
	    	 controller : 'AvaliacaoIndividualController as avaliacaoIndividualController'
	     })
	     .state('avaliacao-individual.list',{
	    	 url:'/listar',
	    	 templateUrl: './modules/home/views/avaliacaoindividual/avaliacao-individual-list.html'
	     })
	     .state('avaliacao-individual.add',{
	    	 url:'/inserir',
	    	 templateUrl: './modules/home/views/avaliacaoindividual/avaliacao-individual-form.html'
	     })
	     .state('avaliacao-individual.edit',{
	    	 url:'/editar/{id:[0-9]{1,10}}',
	    	 templateUrl: './modules/home/views/avaliacaoindividual/avaliacao-individual-form.html'
	     })
	     
	     //Plano de atendimento familiar
	     $stateProvider.state('plano-atendimento-familiar',{
        	abstract: true,
        	url : "/plano-atendimento-familiar",
        	template: '<div layout="column" flex ui-view=""/>',
        	controller : 'PlanoAtendimentoFamiliarController as planoAtendimentoFamiliarController'
        })
        .state('plano-atendimento-familiar.list',{
            url:'/listar',
            templateUrl: './modules/home/views/planoatendimentofamiliar/plano-atendimento-familiar-list.html'
          })
        .state('plano-atendimento-familiar.add',{
            url:'/inserir',
            templateUrl: './modules/home/views/planoatendimentofamiliar/plano-atendimento-familiar-form.html'
          })
	    .state('plano-atendimento-familiar.edit',{
	        url:'/editar/{id:[0-9]{1,10}}',
	        templateUrl: './modules/home/views/planoatendimentofamiliar/plano-atendimento-familiar-form.html'
	     })
	     
	      //Casa lar
	     $stateProvider.state('casa-lar',{
        	abstract: true,
        	url : "/casa-lar",
        	template: '<div layout="column" flex ui-view=""/>',
        	controller : 'CasaLarController as casaLarcontroller'
        })
        .state('casa-lar.list',{
            url:'/listar',
            templateUrl: './modules/home/views/casalar/casa-lar-list.html'
          })
        .state('casa-lar.add',{
            url:'/inserir',
            templateUrl: './modules/home/views/casalar/casa-lar-form.html'
          })
	    .state('casa-lar.edit',{
	        url:'/editar/{id:[0-9]{1,10}}',
	        templateUrl: './modules/home/views/casalar/casa-lar-form.html'
	     })
	     
	     //Casa lar
	     $stateProvider.state('rede-apoio',{
        	abstract: true,
        	url : "/rede-apoio",
        	template: '<div layout="column" flex ui-view=""/>',
        	controller : 'RedeApoioController as redeApoioController'
        })
        .state('rede-apoio.list',{
            url:'/listar',
            templateUrl: './modules/home/views/redeapoio/rede-apoio-list.html'
          })
        .state('rede-apoio.add',{
            url:'/inserir',
            templateUrl: './modules/home/views/redeapoio/rede-apoio-form.html'
          })
	    .state('rede-apoio.edit',{
	        url:'/editar/{id:[0-9]{1,10}}',
	        templateUrl: './modules/home/views/redeapoio/rede-apoio-form.html'
	     })
	     
	     //Usuário
	     $stateProvider.state('user',{
        	abstract: true,
        	url : "/user",
        	template: '<div layout="column" flex ui-view=""/>',
        	controller : 'UserController as userController'
        })
        .state('user.list',{
            url:'/listar',
            templateUrl: './modules/home/views/user/user-list.html'
          })
        .state('user.add',{
            url:'/inserir',
            templateUrl: './modules/home/views/user/user-form.html'
          })
	    .state('user.edit',{
	        url:'/editar/{id:[0-9]{1,10}}',
	        templateUrl: './modules/home/views/user/user-form.html'
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