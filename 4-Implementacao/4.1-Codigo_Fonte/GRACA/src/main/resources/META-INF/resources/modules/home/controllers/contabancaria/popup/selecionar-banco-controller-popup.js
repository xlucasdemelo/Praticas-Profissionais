(function ( angular ) {
    'use strict';
    
/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('SelecionarBancoControllerPopup', function( $rootScope, $scope, $state, $importService, $mdDialog, $mdToast, $translate ) {

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
			
		banco: {
			entity: new Banco(),
			
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
     * Método de inserção
     */
    $scope.insertBanco = function() {
    	if ($scope.model.banco.filters.terms == null || $scope.model.banco.filters.terms == ""){
    		$scope.showMessage( $scope.ERROR_MESSAGE,  "Nome da banco não pode ser vazia" );
    		return;
    	}
    	
    	$scope.model.banco.entity = new Banco();
    	$scope.model.banco.entity.nome = $scope.model.banco.filters.terms;
    	
    	caixaService.insertBanco( $scope.model.banco.entity, {
            callback : function(result) {
            	$scope.listBancosByFilters( true );
            	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Banco inserida com sucesso!" );
            },
            errorHandler : function(message, exception) {
            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                $scope.$apply();
            }
        });
    };
    
    /**
     * Ao trocar de pagina
     */
    $scope.bancoPageChange = function( pageCount ) {
    	if( $scope.model.banco.page.pageable.first && pageCount < 0 ) return;
    	if( $scope.model.banco.page.pageable.last && pageCount > 0 ) return;
    	
    	$scope.model.banco.page.pageable.page += pageCount;
    	
    	//impede passar da ultima página
    	if( $scope.model.banco.page.pageable.totalPages -1 < $scope.model.banco.page.pageable.page )
    		$scope.model.banco.page.pageable.page = $scope.model.banco.page.pageable.totalPages -1;
    	
    	//impede passar da primeira página
    	if( $scope.model.banco.page.pageable.page < 0 )
    		$scope.model.banco.page.pageable.page = 0;
    	
    	$scope.listBancosByFilters();
    };
    
    /**
	 * Realiza a consulta para os filtros do campo único
	 */
	$scope.listBancosByFilters = function( restartPagination ){
		$scope.loading = true;
		
		if( restartPagination ) $scope.model.banco.page.pageable.page = 0;
		
		var terms = ($scope.model.banco.filters.terms != '') ? $scope.model.banco.filters.terms.toString() : null;
		
		caixaService.listBancosByFilters( terms, true, $scope.model.banco.page.pageable, {
			callback : function( result ) {
				$scope.totalPagesBanco= result.totalPages;
				
				$scope.model.banco.page = {//PageImpl
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
	
	/*-------------------------------------------------------------------
	 * 		 				  	POST CONSTRUCT
	 *-------------------------------------------------------------------*/
    
    $scope.listBancosByFilters();
    
    /**
    $scope.openSelecionarModeloPopup = function() {
    	$mdDialog.show({
			controller: "SelecionarModeloControllerPopup",
			templateUrl: './modules/home/views/banco/popup/selecionar-modelo-controller-popup.html',
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