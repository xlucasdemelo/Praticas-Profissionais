(function ( angular ) {
    'use strict';

/**
 *	
 * 
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('DefaultConfirmDialogControllerPopup', function( $scope, $state, $importService, $mdToast, $mdDialog, $injector ) {
		
	    /*-------------------------------------------------------------------
	     * 		 				 	ATTRIBUTES
	     *-------------------------------------------------------------------*/
		
	     //----FORM MODEL
		
	    /**
	     * 
	     */
		$scope.model = {
				dialog : $scope.model.dialog,
		};
		
		/**
		 * 
		 */
	    $scope.hide = function (result) {
	        $mdDialog.hide(result);
	    };
	    
	    /**
	     * 
	     */
	    $scope.cancel = function () {
	        $mdDialog.cancel();
	    };
	    
	    /**
	     * 
	     */
	    $scope.answer = function (result) {
	    	$scope.hide(result);
	    };
});
}(window.angular));