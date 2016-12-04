(function ( angular ) {
    'use strict';
    
/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('SelecionarOnlyProdutoControllerPopup', function( $rootScope, $scope, $state, $importService, $mdDialog, $mdToast, $translate ) {

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
		produto: {
			entity: new Produto(),
			
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
		},
		
	};

    /*-------------------------------------------------------------------
     * 		 				 	  HANDLERS
     *-------------------------------------------------------------------*/
	
	/**
     * Seleciona a entidade
     */
    $scope.setSelectedEntity = function( entity ) 
    {
    	$scope.model.produto.entity = entity;
    };
    
    /**
     * Associa a entidade selecionada
     */
    $scope.associateSelectedEntity = function( ) {
    	
    	$mdDialog.hide( $scope.model.produto.entity );
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
    $scope.categoriaPageChange = function( pageCount ) {
    	if( $scope.model.produto.page.pageable.first && pageCount < 0 ) return;
    	if( $scope.model.produto.page.pageable.last && pageCount > 0 ) return;
    	
    	$scope.model.produto.page.pageable.page += pageCount;
    	
    	//impede passar da ultima página
    	if( $scope.model.produto.page.pageable.totalPages -1 < $scope.model.produto.page.pageable.page )
    		$scope.model.produto.page.pageable.page = $scope.model.produto.page.pageable.totalPages -1;
    	
    	//impede passar da primeira página
    	if( $scope.model.produto.page.pageable.page < 0 )
    		$scope.model.produto.page.pageable.page = 0;
    	
    	$scope.listProdutosByFilters();
    };
    
    /**
	 * Realiza a consulta para os filtros do campo único
	 */
	$scope.listProdutosByFilters = function( restartPagination ){
		$scope.loading = true;
		
		if( restartPagination ) $scope.model.produto.page.pageable.page = 0;
		
		var terms = ($scope.model.produto.filters.terms != '') ? $scope.model.produto.filters.terms.toString() : null;
		
		produtoService.listProdutosByFilters( terms, true, $scope.model.produto.page.pageable, {
			callback : function( result ) {
				$scope.totalPagesProduto = result.totalPages;
				
				$scope.model.produto.page = {//PageImpl
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
    
    $scope.listProdutosByFilters();
    
    /**
    $scope.openSelecionarProdutoPopup = function() {
    	$mdDialog.show({
			controller: "SelecionarProdutoControllerPopup",
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