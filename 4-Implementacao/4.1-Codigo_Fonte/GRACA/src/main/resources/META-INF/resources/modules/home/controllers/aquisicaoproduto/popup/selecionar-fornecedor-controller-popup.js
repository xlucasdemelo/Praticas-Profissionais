(function ( angular ) {
    'use strict';
    
/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('SelecionarFornecedorControllerPopup', function( $rootScope, $scope, $state, $importService, $mdDialog, $mdToast, $translate ) {

   /**
     * Serviços importados do DWR
     */
	$importService("fornecedorService");
	
    /*-------------------------------------------------------------------
     * 		 				 	ATTRIBUTES
     *-------------------------------------------------------------------*/
	
	/**
	 * Armazena a entidade que foi selecionada na modal
	 */
	$scope.selectedEntity = null;
	
	/**
	 * 
	 */
	$scope.model = {
		fornecedor: {
			entity: new Fornecedor(),
			
			page: {//PageImpl 
	    		content: [],
	    		pageable: { 
	    			size: 9,
		    		page: 0,
		    		total:0,
		        	sort:null
	        	}
		    },
		    
		    sort: [{//Sort
	    		direction: 'ASC', properties: 'id', nullHandlingHint:null
	    	}],
			
			filters: {
				terms : []
			},
		}
	};

    /*-------------------------------------------------------------------
     * 		 				 	  HANDLERS
     *-------------------------------------------------------------------*/
	
	/**
     * Seleciona a entidade
     */
    $scope.setSelectedEntity = function( entity ) {
    	$scope.selectedEntity = entity;
    };
    
    /**
     * Associa a entidade selecionada
     */
    $scope.associateSelectedEntity = function( ) {
    	if ($scope.selectedEntity == null){
    		$scope.showMessage( $scope.ERROR_MESSAGE, $translate('NoRegisterSelected') );
    		return;
    	}
    	
    	$mdDialog.hide( $scope.selectedEntity );
    };
    
    /**
	 * 
	 */
	$scope.cancel = function() {
    	$mdDialog.cancel();
    };
    
    /**
     * Ao trocar de pagina
     */
    $scope.contaPageChange = function( pageCount )
    {
    	if( $scope.model.fornecedor.page.pageable.first && pageCount < 0 ) return;
    	if( $scope.model.fornecedor.page.pageable.last && pageCount > 0 ) return;
    	
    	$scope.model.fornecedor.page.pageable.page += pageCount;
    	
    	//impede passar da ultima página
    	if( $scope.model.fornecedor.page.pageable.totalPages -1 < $scope.model.fornecedor.page.pageable.page )
    		$scope.model.fornecedor.page.pageable.page = $scope.model.fornecedor.page.pageable.totalPages -1;
    	
    	//impede passar da primeira página
    	if( $scope.model.fornecedor.page.pageable.page < 0 )
    		$scope.model.fornecedor.page.pageable.page = 0;
    	
    	$scope.listFornecedorsByFilters();
    };
    
    /**
	 * Realiza a consulta para os filtros do campo único
	 */
	$scope.listFornecedoresByFilters = function( restartPagination ){
		$scope.loading = true;
		
		if( restartPagination ) $scope.model.fornecedor.page.pageable.page = 0;
		
		var terms = ($scope.model.fornecedor.filters.terms != '') ? $scope.model.fornecedor.filters.terms.toString() : null;
		
		fornecedorService.listFornecedoresByFilters( terms, true, $scope.model.fornecedor.page.pageable, {
			callback : function( result ) {
				$scope.totalPagesFornecedor = result.totalPages;
				
				$scope.model.fornecedor.page = {//PageImpl
						content : result.content,
						pageable : {//PageRequest
							page : result.number,
							size : result.size,
							sort:result.sort,
							total   : result.totalElements
						},
    				};
				
				$scope.loading = false;
				$scope.$apply();
			},
			errorHandler : function(message, exception) {
				$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                $scope.$apply();
            }
		});
	};
	
	/**
	 * 
	 */
	$scope.setSelectedEntity = function(conta)
	{
		$scope.selectedEntity = conta;
	}
	
	/*-------------------------------------------------------------------
	 * 		 				  	POST CONSTRUCT
	 *-------------------------------------------------------------------*/
    
    $scope.listFornecedoresByFilters();
    
    /**
    $scope.openSelecionarFornecedorPopup = function() {
    	$mdDialog.show({
			controller: "SelecionarFornecedorControllerPopup",
			templateUrl: './modules/home/views/conta/popup/selecionar-conta-controller-popup.html',
			parent: angular.element(document.body),
			clickOutsideToClose:true,
			fullscreen: true,
			scope: $scope.$new()
	    })
	    .then(function(answer) {
	    	alert(answer);
	    }, function() {
	    	
	    });
    };
    */
    
});
}(window.angular));