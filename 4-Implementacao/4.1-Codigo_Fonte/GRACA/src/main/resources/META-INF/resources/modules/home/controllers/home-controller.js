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
   
   $scope.user = user;	
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
   		 	.position('center')
   		 	.hideDelay(3000)
   		 	.theme( messageType )
   	 );
    };
    
    $scope.hasPermission = function(role) {
    	var roles = (typeof role == "string") ? [role] : role;
    	var authorities = $scope.user.principal.authorities;
    	
    	for(var role in roles) {
    		for(var i in authorities) {
    			switch(authorities[i].$name) {
    				case roles[role]:
    					return true;
    					break;
    			}
    		}
    	}
    	
    	return false;
    }
    
});

//angular.module('home').directive('cpfValido', function () {
//    return {
//        restrict: 'A',
//        require: 'ngModel',
//        link: function (scope, elem, attrs, ctrl) {
// 
//            scope.$watch(attrs.ngModel, function () {
// 
//                if (elem[0].value.length == 0)
//                    ctrl.$setValidity('cpfValido', true);
//                else if (elem[0].value.length < 11) {
//                    //aplicar o algoritmo de validação completo do CPF
//                    ctrl.$setValidity('cpfValido', false);
//                }
//                else ctrl.$setValidity('cpfValido', true);
//            });
//        }
//    };
//});

}(window.angular));