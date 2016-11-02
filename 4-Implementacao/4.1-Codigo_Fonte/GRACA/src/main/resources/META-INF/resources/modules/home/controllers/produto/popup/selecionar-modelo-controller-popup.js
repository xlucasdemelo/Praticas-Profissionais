(function ( angular ) {
    'use strict';
    
/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('SelecionarModeloControllerPopup', function( $rootScope, $scope, $state, $importService, $mdDialog, $mdToast, $translate ) {

   /**
     * Serviços importados do DWR
     */
	$importService("produtoService");
	
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
		marca: $scope.model.produto.entity.marca,
			
		modelo: {
			entity: new Modelo(),
			
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
    $scope.insertModelo = function() {
    	if ($scope.model.modelo.filters.terms == null || $scope.model.modelo.filters.terms == ""){
    		$scope.showMessage( $scope.ERROR_MESSAGE,  "Nome da modelo não pode ser vazia" );
    		return;
    	}
    	
    	$scope.model.modelo.entity = new Modelo();
    	$scope.model.modelo.entity.marca = $scope.model.marca;
    	$scope.model.modelo.entity.nome = $scope.model.modelo.filters.terms;
    	
    	produtoService.insertModelo( $scope.model.modelo.entity, {
            callback : function(result) {
            	$scope.listModelosByFilters( true );
            	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Modelo inserida com sucesso!" );
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
    $scope.modeloPageChange = function( pageCount ) {
    	if( $scope.model.modelo.page.pageable.first && pageCount < 0 ) return;
    	if( $scope.model.modelo.page.pageable.last && pageCount > 0 ) return;
    	
    	$scope.model.modelo.page.pageable.page += pageCount;
    	
    	//impede passar da ultima página
    	if( $scope.model.modelo.page.pageable.totalPages -1 < $scope.model.modelo.page.pageable.page )
    		$scope.model.modelo.page.pageable.page = $scope.model.modelo.page.pageable.totalPages -1;
    	
    	//impede passar da primeira página
    	if( $scope.model.modelo.page.pageable.page < 0 )
    		$scope.model.modelo.page.pageable.page = 0;
    	
    	$scope.listModelosByFilters();
    };
    
    /**
	 * Realiza a consulta para os filtros do campo único
	 */
	$scope.listModelosByFilters = function( restartPagination ){
		$scope.loading = true;
		
		if( restartPagination ) $scope.model.modelo.page.pageable.page = 0;
		
		var terms = ($scope.model.modelo.filters.terms != '') ? $scope.model.modelo.filters.terms.toString() : null;
		
		produtoService.listModelosByMarcaAndFilters( $scope.model.marca.id, terms, $scope.model.modelo.page.pageable, {
			callback : function( result ) {
				$scope.totalPagesModelo = result.totalPages;
				
				$scope.model.modelo.page = {//PageImpl
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
    
    $scope.listModelosByFilters();
    
    /**
    $scope.openSelecionarModeloPopup = function() {
    	$mdDialog.show({
			controller: "SelecionarModeloControllerPopup",
			templateUrl: './modules/home/views/produto/popup/selecionar-modelo-controller-popup.html',
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