(function ( angular ) {
    'use strict';
    
/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('SelecionarContaControllerPopup', function( $rootScope, $scope, $state, $importService, $mdDialog, $mdToast, $translate ) {

   /**
     * Serviços importados do DWR
     */
	$importService("caixaService");
	
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
		conta: {
			entity: new Conta(),
			
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
    $scope.contaPageChange = function( pageCount ) {
    	if( $scope.model.conta.page.pageable.first && pageCount < 0 ) return;
    	if( $scope.model.conta.page.pageable.last && pageCount > 0 ) return;
    	
    	$scope.model.conta.page.pageable.page += pageCount;
    	
    	//impede passar da ultima página
    	if( $scope.model.conta.page.pageable.totalPages -1 < $scope.model.conta.page.pageable.page )
    		$scope.model.conta.page.pageable.page = $scope.model.conta.page.pageable.totalPages -1;
    	
    	//impede passar da primeira página
    	if( $scope.model.conta.page.pageable.page < 0 )
    		$scope.model.conta.page.pageable.page = 0;
    	
    	$scope.listContasByFilters();
    };
    
    /**
	 * Realiza a consulta para os filtros do campo único
	 */
	$scope.listContasByFilters = function( restartPagination ){
		$scope.loading = true;
		
		if( restartPagination ) $scope.model.conta.page.pageable.page = 0;
		
		var terms = ($scope.model.conta.filters.terms != '') ? $scope.model.conta.filters.terms.toString() : null;
		
		caixaService.listContaByFilters( terms, true, $scope.model.conta.page.pageable, {
			callback : function( result ) {
				$scope.totalPagesConta = result.totalPages;
				
				$scope.model.conta.page = {//PageImpl
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
    
    $scope.listContasByFilters();
    
    /**
    $scope.openSelecionarContaPopup = function() {
    	$mdDialog.show({
			controller: "SelecionarContaControllerPopup",
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