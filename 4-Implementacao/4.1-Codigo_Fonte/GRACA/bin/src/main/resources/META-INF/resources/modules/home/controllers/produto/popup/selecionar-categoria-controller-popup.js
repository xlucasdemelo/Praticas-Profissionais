(function ( angular ) {
    'use strict';
    
/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('SelecionarCategoriaControllerPopup', function( $rootScope, $scope, $state, $importService, $mdDialog, $mdToast, $translate ) {

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
		categoria: {
			entity: new Categoria(),
			
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
    $scope.insertCategoria = function() {
    	if ($scope.model.categoria.filters.terms == null || $scope.model.categoria.filters.terms == ""){
    		$scope.showMessage( $scope.ERROR_MESSAGE,  "Nome da categoria não pode ser vazia" );
    		return;
    	}
    	
    	$scope.model.categoria.entity = new Categoria();
    	$scope.model.categoria.entity.nome = $scope.model.categoria.filters.terms;
    	
    	produtoService.insertCategoria( $scope.model.categoria.entity, {
            callback : function(result) {
            	$scope.listCategoriasByFilters( true );
            	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Categoria inserida com sucesso!" );
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
    $scope.categoriaPageChange = function( pageCount ) {
    	if( $scope.model.categoria.page.pageable.first && pageCount < 0 ) return;
    	if( $scope.model.categoria.page.pageable.last && pageCount > 0 ) return;
    	
    	$scope.model.categoria.page.pageable.page += pageCount;
    	
    	//impede passar da ultima página
    	if( $scope.model.categoria.page.pageable.totalPages -1 < $scope.model.categoria.page.pageable.page )
    		$scope.model.categoria.page.pageable.page = $scope.model.categoria.page.pageable.totalPages -1;
    	
    	//impede passar da primeira página
    	if( $scope.model.categoria.page.pageable.page < 0 )
    		$scope.model.categoria.page.pageable.page = 0;
    	
    	$scope.listCategoriasByFilters();
    };
    
    /**
	 * Realiza a consulta para os filtros do campo único
	 */
	$scope.listCategoriasByFilters = function( restartPagination ){
		$scope.loading = true;
		
		if( restartPagination ) $scope.model.categoria.page.pageable.page = 0;
		
		var terms = ($scope.model.categoria.filters.terms != '') ? $scope.model.categoria.filters.terms.toString() : null;
		
		produtoService.listCategoriasByFilter( terms, $scope.model.categoria.page.pageable, {
			callback : function( result ) {
				$scope.totalPagesCategoria = result.totalPages;
				
				$scope.model.categoria.page = {//PageImpl
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
    
    $scope.listCategoriasByFilters();
    
    /**
    $scope.openSelecionarCategoriaPopup = function() {
    	$mdDialog.show({
			controller: "SelecionarCategoriaControllerPopup",
			templateUrl: './modules/home/views/produto/popup/selecionar-categoria-controller-popup.html',
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