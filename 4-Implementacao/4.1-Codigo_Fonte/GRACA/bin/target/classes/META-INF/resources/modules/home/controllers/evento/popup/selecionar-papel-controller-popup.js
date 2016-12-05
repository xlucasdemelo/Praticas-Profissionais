(function ( angular ) {
    'use strict';

/**
 *	
 * 
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('SelecionarPapelPopup', function( $scope, $state, $importService, $mdToast, $mdDialog, $injector, $timeout ) {
		
	    /**
	     * Serviços importados do DWR
	     */
		$importService("accountService");
		$importService("eventoService");
		
	    /*-------------------------------------------------------------------
	     * 		 				 	ATTRIBUTES
	     *-------------------------------------------------------------------*/
		
		/**
		 * 
		 */
		$scope.USUARIOS_LIST_PAGE = 0;
		$scope.PAPEL_PAGE = 1;
		$scope.currentModalPage = $scope.USUARIOS_LIST_PAGE;
		
		//----FORM MODEL
		/**
	     *
		 */
		$scope.model = {
			evento: {
				entity: $scope.model.evento.entity,
			},
			
			usuario: {
				form: null,
				entity: new User(),
				
				filters: {
				    terms: "",
				},
				
				page: {//PageImpl 
		    		content: [],
		    		pageable :{ size: 9,
					    		page: 0,
					        	sort:null
		        	}
				},
			    sort: [{//Sort
	        		direction: 'ASC', properties: 'id', nullHandlingHint:null
	        	}],
			},
			
			papel: {
				form: null,
				entity: new Papel(),
			},
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
	    
	    /**
	     * 
	     */
	    $scope.changeModalPageToUsuariosListPage = function() {
	    	$scope.currentModalPage = $scope.USUARIOS_LIST_PAGE;
	    };
	    
	    /**
	     * 
	     */
	    $scope.changeModalPageToPapelPage = function() {
	    	$scope.currentModalPage = $scope.PAPEL_PAGE;
	    };
	    
	    /*-------------------------------------------------------------------
	     * 		 				 PRIVATE BEHAVIORS
	     *-------------------------------------------------------------------*/
		
		/**
		 * 
		 */
		$scope.selecionarUsuario = function( usuario ){
			$scope.model.usuario.entity = usuario;
			$scope.changeModalPageToPapelPage();
		};
		
		/**
		 * 
		 */
		$scope.insertPapel = function() {
			$scope.model.papel.form.$setSubmitted();
			
			if ($scope.model.papel.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
			
			$scope.model.papel.entity.evento = new Evento();
			$scope.model.papel.entity.evento.id = $scope.model.evento.entity.id;
			
			$scope.model.papel.entity.usuario = new User();
			$scope.model.papel.entity.usuario.id = $scope.model.usuario.entity.id;
			
			eventoService.insertPapel(  $scope.model.papel.entity, {
                callback : function(result) {
                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
                	$scope.hide( result );
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
		$scope.onUsuarioPaginationChange = function(paginate) {
        	if (paginate) {
        		$scope.model.usuario.page.pageable.page++;
        	} else {
        		$scope.model.usuario.page.pageable.page--;
        	}
        		
        	$scope.listUsuariosByFilters();
        };
		
		/**
		 * 
		 */
		$scope.listUsuariosByFilters = function(){
			accountService.listByFilters(  $scope.model.usuario.filters.terms.toString(), true, $scope.model.usuario.page.pageable, {
                callback : function(result) {
                	$scope.totalPagesUsuario = result.totalPages;
                	$scope.model.usuario.page = {//PageImpl
    						content : result.content,
							pageable : {//PageRequest
								page : result.number,
								size : result.size,
								sort:result.sort,
								total   : result.totalElements
							},
    				};
                	
                	$scope.$apply();
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            });
		};
		
	    /*-------------------------------------------------------------------
	     * 		 				  POST CONSTRUCT
	     *-------------------------------------------------------------------*/
		$scope.listUsuariosByFilters();
		
});
}(window.angular));