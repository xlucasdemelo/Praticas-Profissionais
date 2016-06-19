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
		   

	//-----
	/**
	 * 
	 */
	$scope.menuSideNavId = "menuSideNav";

	/*-------------------------------------------------------------------
     * 		 				  	POST CONSTRUCT
     *-------------------------------------------------------------------*/
	
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
    
});

}(window.angular));