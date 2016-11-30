(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('EventoController', function( $scope, $state, $importService, $mdToast, $mdDialog ) {
		
	    /**
	     * Serviços importados do DWR
	     */
		$importService("eventoService");
		
		/**
		 * 
		 */
		
	    /*-------------------------------------------------------------------
	     * 		 				 	ATTRIBUTES
	     *-------------------------------------------------------------------*/
		 //----STATES
		/**
		 * Representa o estado de listagem de registros.
		 */
		$scope.EVENTO_LIST_STATE = "evento.list";
		/**
		 * Representa o estado para a criação de registros.
		 */
		$scope.EVENTO_ADD_STATE = "evento.add";
		/**
		 * Representa o estado para a edição de registros.
		 */
		$scope.EVENTO_EDIT_STATE = "evento.edit";
		/**
		 * Representa o estado de detalhe de um registro.
		 */
		$scope.EVENTO_DETAIL_STATE = "evento.detail";
		
		
		//----FORM MODEL
		
		/**
		 * Contém o estados dos filtros da tela
		 * Contém estado da paginação inicial contendo sorting
	     *
		 */
		$scope.model = {
			query:{
				order : "",
			},
			
			evento: {
				form: null,
				entity: new Evento(),
				
				filters: {
				    terms: "",
				    ativo: true,
				    inativo: false
				},
				
			    page: {//PageImpl 
			    		content: [],
			    		pageable: { size: 9,
						    		page: 0,
						    		total:0,
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
				
				filters: {
					terms: "",
					ativo: true,
					inativo: false
				},
				
				page: {//PageImpl 
					content: [],
					pageable: { size: 9,
						page: 0,
						total:0,
						sort:null
					}
				},
				sort: [{//Sort
					direction: 'ASC', properties: 'id', nullHandlingHint:null
				}],
			},
		};
		
		/*-------------------------------------------------------------------
	     * 		 				  POST CONSTRUCT
	     *-------------------------------------------------------------------*/
		
		/**
		 * Handler que escuta as mudanças de URLs pertecentes ao estado da tela.
		 * Ex.: list, add, detail, edit
		 *
		 * Toda vez que ocorre uma mudança de URL se via botão, troca de URL manual, ou ainda
		 * ao vançar e voltar do browser, este evento é chamado.
		 *
		 */
	    $scope.$on('$stateChangeSuccess', function( event, toState, toParams, fromState, fromParams ) {
	    	
	    	switch ( $state.current.name ) {
				case $scope.EVENTO_LIST_STATE: {
					$scope.changeToList( false );
					break;
				}
				case $scope.EVENTO_DETAIL_STATE: {
					$scope.changeToDetail( $state.params.id );
					break;
				}
		        case $scope.EVENTO_ADD_STATE: {
		        	$scope.changeToAdd();
		        	break;
		        }
		        case $scope.EVENTO_EDIT_STATE: {
		        	$scope.changeToEdit( $state.params.id );
		        	break;
		        }
		        default : {
		        	if ( $state.current.name == $scope.EVENTO_LIST_STATE )
		        		$state.go( $scope.EVENTO_LIST_STATE );
		        }
			}
	    });
		
	    /*-------------------------------------------------------------------
	     * 		 				HANDLERS FAMÍLIA
	     *-------------------------------------------------------------------*/
	    
	    /**
	     * Handler para que realiza os procedimentos iniciais (prepara o estado)
	     * para a tela de inserção e após isso, muda o estado para $scope.ADD_STATE.
	     *  
	     * @see $scope.ADD_STATE
	     */
		$scope.changeToAdd = function() {
			console.debug("changeToAdd");
			$scope.model.evento.entity = new Evento();//Limpa o formulário
		};
		
		/**
	     * Realiza os procedimentos iniciais (prepara o estado)
	     * para a tela de edição e após isso, muda o estado para update.
	     *
	     * Para mudar para este estado, deve-se primeiro obter via id
	     * o registro pelo serviço de consulta e só então mudar o estado da tela.
	     * 
	     * @see $scope.EDIT_STATE
	     */
	    $scope.changeToEdit = function( id ) {
	        console.debug("changeToEdit", id);
	        
	        eventoService.findEventoById( id, {
	            callback : function(result) {	   
	            	$scope.model.evento.entity = result;
	            	$scope.$apply();
	            },
	            errorHandler : function(message, exception) {
	            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                $scope.$apply();
	            }
	        });
	    };
		
		/**
	     * Realiza os procedimentos iniciais (prepara o estado)
	     * para a tela de consulta e após isso, muda o estado para list.
	     * Para mudar para este estado, deve-se primeiro carregar os dados da consulta.
	     * 
	     * @see $scope.LIST_STATE
	     * 
	     * @param paginate boolean para saber se precisa paginar
	     */
	    $scope.changeToList = function( paginate ) {
	        console.debug("changeToList");
	        
	        if ( paginate ) $scope.model.evento.page.pageable.page++;
	        
	        $scope.listEventosByFilters();
	    };
	    
	    /*-------------------------------------------------------------------
	     * 		 				 PRIVATE BEHAVIORS
	     *-------------------------------------------------------------------*/
	    
	    /**
		 * 
		 */
		$scope.openSelecionarPapelHandler = function() {
			$mdDialog.show({
			      controller: "SelecionarPapelPopup",
			      templateUrl: './modules/home/views/evento/popup/selecionar-papel-popup.html',			      
			      scope: $scope.$new(),
				})
			    .then(function( result ) {
			    	$scope.listPapeisByEvento();
			 });
		};
		
		
	    /**
	     * 
	     */
		$scope.listEventosByFilters = function(){
			eventoService.listEventosByFilters(  $scope.model.evento.filters.terms.toString(), $scope.model.evento.page.pageable, {
                callback : function(result) {
                	$scope.totalPagesEvento = result.totalPages;
                	$scope.model.evento.page = {//PageImpl
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
		
		/**
		 * 
		 */
		$scope.listPapeisByEvento = function(){
			eventoService.listPapeisByEvento( $scope.model.evento.entity.id, $scope.model.papel.page.pageable, {
				callback : function(result) {
					$scope.totalPagesPapel = result.totalPages;
					$scope.model.papel.page = {//PageImpl
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
		
		/**
		 * 
		 */
		$scope.save = function() {
			$scope.model.evento.entity.id ? $scope.updateEvento() : $scope.insertEvento();
		};
        
		/**
		 * 
		 */
		$scope.insertEvento = function() {
			$scope.model.evento.form.$setSubmitted();
			
			if ($scope.model.evento.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
			
			eventoService.insertEvento(  $scope.model.evento.entity, {
                callback : function(result) {
                	$state.go( $scope.EVENTO_EDIT_STATE, {id: result.id}, {reload: true } )
                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
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
		$scope.updateEvento = function() {
			$scope.model.evento.form.$setSubmitted();
			
			if ($scope.model.evento.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
			
			eventoService.updateEvento(  $scope.model.evento.entity, {
				callback : function(result) {
					$scope.model.evento.entity = result;
					$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
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
		$scope.onEventoPaginationChange = function( paginate ) {
        	if (paginate) {
        		$scope.model.evento.page.pageable.page++;
        	} else {
        		$scope.model.evento.page.pageable.page--;
        	}
        		
        	$scope.listEventosByFilters();
        };
        
        /**
         * 
         */
        $scope.onPapelPaginationChange = function( paginate ) {
        	if (paginate) {
        		$scope.model.papel.page.pageable.page++;
        	} else {
        		$scope.model.papel.page.pageable.page--;
        	}
        	
        	$scope.listPapeisByEvento();
        };
		
		
		/**
		 * 
		 */
		$scope.openMaisOpcoes = function(){
			$scope.moreFilters = !$scope.moreFilters;
		};
		
		/*-------------------------------------------------------------------
	     * 		 				 	POST CONSTRUCT
	     *-------------------------------------------------------------------*/
		
});
}(window.angular));