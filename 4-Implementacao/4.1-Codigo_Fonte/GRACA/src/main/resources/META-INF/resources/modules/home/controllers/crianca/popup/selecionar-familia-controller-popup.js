(function ( angular ) {
    'use strict';

/**
 *	
 * 
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('SelecionarFamiliaPopup', function( $scope, $state, $importService, $mdToast, $mdDialog, $injector, $timeout ) {
		
	    /**
	     * Serviços importados do DWR
	     */
		$importService("integranteFamiliarService");
		
	    /*-------------------------------------------------------------------
	     * 		 				 	ATTRIBUTES
	     *-------------------------------------------------------------------*/
		//----FORM MODEL
		
		
		$scope.allSexos = [];
		$scope.allTiposDocumentos = [];
		
		/**
	     *
		 */
		$scope.model = {

			familia: {
				form: null,
				entity: new IntegranteFamiliar(),
				
				filters: {
				    terms: "",
				   
				},
				
			    page: {//PageImpl 
			    		size: 9,
			    		page: 0,
			        	sort:null
			    },
			    sort: [{//Sort
	        		direction: 'ASC', properties: 'id', nullHandlingHint:null
	        	}],
			},
			
			estado: {
				itens: [],
				noCache: true,
				selectedItem: null,
				searchText: null
			},
			cidade: {
				itens: [],
				noCache: true,
				selectedItem: null,
				searchText: null
			},
			pais: {
				itens: [],
				noCache: true,
				selectedItem: null,
				searchText: null
			}
		};
		
		/*-------------------------------------------------------------------
	     * 		 				 	  HANDLERS
	     *-------------------------------------------------------------------*/
		
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
	    $scope.answer = function () {
	    	
	    	$mdDialog.hide();
	    };
	    
	    /*-------------------------------------------------------------------
	     * 		 				 PRIVATE BEHAVIORS
	     *-------------------------------------------------------------------*/
	    
	    /**
	     * 
	     */
	    $scope.listPaisesByFiltes = function(filter){
			enderecoService.listPaisesByFilters( filter, null, {
                callback : function(result) {
                	
                	$scope.model.pais.itens = result.content;
                	
                	$scope.$apply();
                	
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            });
		}
	    
	    /**
		 * 
		 */
		$scope.listEstadosByFiltes = function(filter){
			enderecoService.listEstadosByFIlters( filter, $scope.model.pais.selectedItem.id, null, {
                callback : function(result) {
                	
                	$scope.model.estado.itens = result.content;
                	
                	$scope.$apply();
                	
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            });
		}
		
		/**
		 * 
		 */
		$scope.listCidadesByFiltes = function(filter){
			enderecoService.listCidadesByFIlters( filter, $scope.model.estado.selectedItem.id, null, {
                callback : function(result) {
                	
                	$scope.model.cidade.itens = result.content;
                	
                	$scope.$apply();
                	
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            });
		}
		
		/**
		 * 
		 */
		$scope.listAllSexos = function(){
			integranteFamiliarService.listAllSexos( {
                callback : function(result) {
                	$scope.allSexos = result;
                	
                	$scope.$apply();
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            });
		}
		
		/**
		 * 
		 */
		$scope.selectFamilia = function( familia ){
			$mdDialog.hide(familia);
		}
		
		/**
		 * 
		 */
		$scope.listFamiliasByFilters = function(){
			
			familiaService.listFamiliasByFilters(  $scope.model.familia.filters.terms.toString(), $scope.model.familia.page.pageable, {
                callback : function(result) {
                	
                	$scope.model.familia.page.content = result.content; 
                	$scope.$apply();
                	
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            });
		}
		
		/**
		 * 
		 */
		$scope.init = function(){
			$scope.listFamiliasByFilters();
		}
	    /*-------------------------------------------------------------------
	     * 		 				  POST CONSTRUCT
	     *-------------------------------------------------------------------*/
		$scope.init();
		
});
}(window.angular));