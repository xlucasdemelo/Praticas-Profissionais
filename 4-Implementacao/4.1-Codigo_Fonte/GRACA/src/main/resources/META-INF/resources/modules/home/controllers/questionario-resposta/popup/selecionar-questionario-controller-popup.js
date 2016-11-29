(function ( angular ) {
    'use strict';
    
/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('SelecionarQuestionarioControllerPopup', function( $rootScope, $scope, $state, $importService, $mdDialog, $mdToast, $translate ) {

   /**
     * Serviços importados do DWR
     */
	$importService("questionarioService");
	
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
		questionario: {
			entity: new Questionario(),
			
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
    	if( $scope.model.questionario.page.pageable.first && pageCount < 0 ) return;
    	if( $scope.model.questionario.page.pageable.last && pageCount > 0 ) return;
    	
    	$scope.model.questionario.page.pageable.page += pageCount;
    	
    	//impede passar da ultima página
    	if( $scope.model.questionario.page.pageable.totalPages -1 < $scope.model.questionario.page.pageable.page )
    		$scope.model.questionario.page.pageable.page = $scope.model.questionario.page.pageable.totalPages -1;
    	
    	//impede passar da primeira página
    	if( $scope.model.questionario.page.pageable.page < 0 )
    		$scope.model.questionario.page.pageable.page = 0;
    	
    	$scope.listQuestionariosByFilters();
    };
    
    /**
	 * Realiza a consulta para os filtros do campo único
	 */
	$scope.listQuestionariosByFilters = function( restartPagination ){
		$scope.loading = true;
		
		if( restartPagination ) $scope.model.questionario.page.pageable.page = 0;
		
		var terms = ($scope.model.questionario.filters.terms != '') ? $scope.model.questionario.filters.terms.toString() : null;
		
		questionarioService.listQuestionariosAprovados( terms, $scope.model.questionario.page.pageable, {
			callback : function( result ) {
				$scope.totalPagesQuestionario = result.totalPages;
				
				$scope.model.questionario.page = {//PageImpl
						content : result,
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
    
    $scope.listQuestionariosByFilters();
    
    /**
    $scope.openSelecionarQuestionarioPopup = function() {
    	$mdDialog.show({
			controller: "SelecionarQuestionarioControllerPopup",
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