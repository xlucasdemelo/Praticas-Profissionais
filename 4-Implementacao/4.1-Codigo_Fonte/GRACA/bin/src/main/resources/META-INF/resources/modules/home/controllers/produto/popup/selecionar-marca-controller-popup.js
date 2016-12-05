(function ( angular ) {
    'use strict';
    
/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('SelecionarMarcaControllerPopup', function( $rootScope, $scope, $state, $importService, $mdDialog, $mdToast, $translate ) {

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
		marca: {
			entity: new Marca(),
			
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
    $scope.insertMarca = function() {
    	if ($scope.model.marca.filters.terms == null || $scope.model.marca.filters.terms == ""){
    		$scope.showMessage( $scope.ERROR_MESSAGE,  "Nome da marca não pode ser vazia" );
    		return;
    	}
    	
    	$scope.model.marca.entity = new Marca();
    	$scope.model.marca.entity.nome = $scope.model.marca.filters.terms;
    	
    	produtoService.insertMarca( $scope.model.marca.entity, {
            callback : function(result) {
            	$scope.listMarcasByFilters( true );
            	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Marca inserida com sucesso!" );
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
    $scope.marcaPageChange = function( pageCount ) {
    	if( $scope.model.marca.page.pageable.first && pageCount < 0 ) return;
    	if( $scope.model.marca.page.pageable.last && pageCount > 0 ) return;
    	
    	$scope.model.marca.page.pageable.page += pageCount;
    	
    	//impede passar da ultima página
    	if( $scope.model.marca.page.pageable.totalPages -1 < $scope.model.marca.page.pageable.page )
    		$scope.model.marca.page.pageable.page = $scope.model.marca.page.pageable.totalPages -1;
    	
    	//impede passar da primeira página
    	if( $scope.model.marca.page.pageable.page < 0 )
    		$scope.model.marca.page.pageable.page = 0;
    	
    	$scope.listMarcasByFilters();
    };
    
    /**
	 * Realiza a consulta para os filtros do campo único
	 */
	$scope.listMarcasByFilters = function( restartPagination ){
		$scope.loading = true;
		
		if( restartPagination ) $scope.model.marca.page.pageable.page = 0;
		
		var terms = ($scope.model.marca.filters.terms != '') ? $scope.model.marca.filters.terms.toString() : null;
		
		produtoService.listMarcasByFilter( terms, $scope.model.marca.page.pageable, {
			callback : function( result ) {
				$scope.totalPagesMarca = result.totalPages;
				
				$scope.model.marca.page = {//PageImpl
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
    
    $scope.listMarcasByFilters();
    
    /**
    $scope.openSelecionarMarcaPopup = function() {
    	$mdDialog.show({
			controller: "SelecionarMarcaControllerPopup",
			templateUrl: './modules/home/views/marca/popup/selecionar-marca-controller-popup.html',
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