(function ( angular ) {
    'use strict';
    
/**
 * 
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('HomeController', function( $rootScope, $scope, $state, $importService, $mdToast, $mdDialog, $mdSidenav ) {

    /*-------------------------------------------------------------------
     * 		 				 	ATTRIBUTES
     *-------------------------------------------------------------------*/
    //----STATES
	
   $rootScope.$on('$stateChangeError', function (e, toState, toParams, fromState, fromParams, error) {
	    if (error && error.redirect) {
	        e.preventDefault()
	        return $state.go(error.redirect);
	    }

	    $exceptionHandler(error);
	});
   
   $rootScope.$on('$locationChangeStart', function (event, next, current) {
       $rootScope.$broadcast('$locationChangeSuccess', next, current);
   });
   
	//-----
	/**
	 * 
	 */
	$scope.menuSideNavId = "menuSideNav";

	/*-------------------------------------------------------------------
     * 		 				  	POST CONSTRUCT
     *-------------------------------------------------------------------*/
	
	/**
	 * Constantes para definição do tipo de mensagem que aparece no md toast
	 */
	$scope.ERROR_MESSAGE = 0;
	$scope.SUCCESS_MESSAGE = 1;
	$scope.ALERT_MESSAGE = 2;
	
	/**
	 * Constantes para definição do tipo da criticidade de ações (utilizada em modais de confirm dialog)
	 */
	$scope.SUCCESS_LEVEL = 4; //cinza escuro
	$scope.DELETE_LEVEL = 3; //cinza escuro
	$scope.CRITICAL_LEVEL = 2; //vermelho
	$scope.WARNING_LEVEL = 1;//amarelo
	$scope.NORMAL_LEVEL = 0;//cinza
	
    /*-------------------------------------------------------------------
     * 		 				 	  HANDLERS
     *-------------------------------------------------------------------*/
    /**
     * 
     */
    $scope.toggleMenuSideNavHandler = function(state) {
    	$mdSidenav($scope.menuSideNavId).toggle();
    	if (state)
    		$state.go(state);
    };
    
    $scope.openDefaultConfirmDialog = function( scope, onConfirmFunction, onCancelFunction, confirmArgument ) {
   	 $mdDialog.show({
    		controller: "DefaultConfirmDialogControllerPopup",
    		templateUrl: './modules/home/views/dialog/default-confirm-dialog-template-popup.html',
    		parent: angular.element(document.body),
    		scope: scope.$new(),
    		//targetEvent: ev,
    		clickOutsideToClose:true
	    }).then(function(answer) {
	    	if( onConfirmFunction != null ) {
	    		if (confirmArgument)
	    			onConfirmFunction(confirmArgument);
	    		else
	    			onConfirmFunction();
	    	}
	    }, function() {
	    	if( onCancelFunction != null ) {
	    		onCancelFunction();
	    	}
	    });
    };
    
    $scope.showMessage = function ( type, message ) {
   	 var messageType;
   	 
   	 if (type == $scope.ERROR_MESSAGE) { //ERROR
   		 messageType = 'error-message';
   	 }
   	 else if (type == $scope.SUCCESS_MESSAGE) {
   		 messageType = 'success-message';
   	 }
   	 
   	 $mdToast.show(
   		 $mdToast.simple()
   		 	.content( message )
   		 	.position('top left right')
   		 	.hideDelay(6000)
   		 	.theme( messageType )
   	 );
    };
    
    $scope.grauEscolaridadeToString = function( grau ){
    	
    	switch ( grau ){
			case "SEM_ESCOLARIDADE":{
				return "Sem escolaridade"
			}
			case "FUNDAMENTAL_COMPLETO":{
				return "Fundamental completo"
			}
			case "MEDIO_COMPLETO":{
				return "Medio completo"
			}
			case "MEDIO_INCOMPLETO":{
				return "Medio incompleto"
			}
			case "SUPERIOR_INCOMPLETO":{
				return "Superior incompleto"
			}
			case "MEDIO_COMPLETO":{
				return "Medio completo"
			}
		}
    	
    }
    
    /**
     * 
     */
    $scope.sexoToString = function( sexo ){
    	
    	switch ( sexo ){
    		case "MASCULINO":{
    			return "masculino"
    		}
    		case "FEMININO":{
    			return "feminino"
    		}
    		case "OUTRO":{
    			return "outro"
    		}
    	}
    	
    }
    
});

}(window.angular));