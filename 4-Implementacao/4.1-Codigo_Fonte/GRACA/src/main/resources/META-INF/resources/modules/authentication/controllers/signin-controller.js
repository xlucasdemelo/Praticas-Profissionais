(function ( angular ) {
    'use strict';

/**
 *
 * @param $scope
 * @param $state
 */
angular.module('authentication')
	   .controller('SigninController', function( $scope, $http, $mdToast, $window, $translate ) {

    /*-------------------------------------------------------------------
     * 		 				 	ATTRIBUTES
     *-------------------------------------------------------------------*/
     /**
      * 
      */
     $scope.model = {
		 entity: null,
	 };

    /*-------------------------------------------------------------------
     * 		 				 	  HANDLERS
     *-------------------------------------------------------------------*/
   /**
    * 
    */
	$scope.signinHandler = function() {

    	if ( $scope.form.$invalid ) {
    		$mdToast.showSimple( $translate("invalidForm") );

    	} else {

			var config = {
				headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
			};
			
			$http.post( "./authenticate", $.param($scope.model.entity), config)
			.then(function successCallback( data, status, headers, config ) {
				
				$window.location.href = "./";
				
			  }, function errorCallback(response) {
			    
			    $mdToast.showSimple( (data && data.message) ? data.message : data );
			    	
			  });
    	}
    }
});

}(window.angular));