(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('ContaController', function( $scope, $state, $importService, $mdToast, $mdDialog, $mdExpansionPanel ) {
		
		   /**
		     * Serviços importados do DWR
		     */
			$importService("caixaService");
			
		    /*-------------------------------------------------------------------
		     * 		 				 	ATTRIBUTES
		     *-------------------------------------------------------------------*/
			 //----STATES
			/**
			 * Representa o estado de listagem de registros.
			 */
			$scope.CONTA_LIST_STATE = "conta.list";
			/**
			 * Representa o estado para a criação de registros.
			 */
			$scope.CONTA_ADD_STATE = "conta.add";
			/**
			 * Representa o estado para a edição de registros.
			 */
			$scope.CONTA_EDIT_STATE = "conta.edit";
			/**
			 * Representa o estado de detalhe de um registro.
			 */
			$scope.CONTA_DETAIL_STATE = "conta.detail";
			
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
				
				conta: {
					form: null,
					entity: new Conta(),
					
					filters: {
					    terms: "",
					    ativo: true,
					    inativo: false
					},
					
				    page: {//PageImpl 
				    		content: [],
				    		pageable :{ size: 9,
							    		page: 0,
							    		total:0,
							        	sort:null
				        	}
				    },
				    sort: [{//Sort
		        		direction: 'ASC', properties: 'id', nullHandlingHint:null
		        	}],
					
				}
			};
			
			/**
			 * 
			 */
			$scope.totalPagesConta = null;
			
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
					case $scope.CONTA_LIST_STATE: {
						$scope.changeToList( false );
						break;
					}
					case $scope.CONTA_DETAIL_STATE: {
						$scope.changeToDetail( $state.params.id );
						break;
					}
			        case $scope.CONTA_ADD_STATE: {
			        	$scope.changeToAdd();
			        	break;
			        }
			        case $scope.CONTA_EDIT_STATE: {
			        	$scope.changeToEdit( $state.params.id );
			        	break;
			        }
			        default : {
			        		$state.go( $scope.CONTA_LIST_STATE );
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
				
				$scope.model.conta.entity = new Conta();//Limpa o formulário
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
		        
		        caixaService.findContaById( id, {
		            callback : function(result) {	   
		            	$scope.model.conta.entity = result;
		            	
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
		        
		        if ( paginate ) $scope.model.conta.page.pageable.page++;
		        
		        $scope.listByFilters();
		    };
		    
		    /**
		     * Realiza os procedimentos iniciais (prepara o estado)
		     * para a tela de detalhe e após isso, muda o estado para detail.
		     * 
		     * Para mudar para este estado, deve-se primeiro obter via id
		     * o registro atualizado pelo serviço de consulta e só então mudar o estado da tela.
		     * 
		     * @see $scope.DETAIL_STATE
		     */
		    $scope.changeToDetail = function( id ) {
		        console.debug("changeToDetail", id);
		
		        caixaService.findContaById( id, {
		            callback : function(result) {
		            	$scope.model.conta.entity = result;
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
		     * para a tela de exclusão.
		     * Antes de excluir, o usuário notificado para confirmação e só então o registro é excluido.
		     */
		    $scope.changeToRemove = function (entity) {
		        var confirm = $mdDialog.confirm()
		            .title('Tem certeza que deseja excluir este registro?')
		            .content('Não será possível recuperar este registro se for excluído.')
		            .ok('Sim')
		            .cancel('Cancelar');
		
		        $mdDialog.show(confirm).then(function (result) {
		            console.log(result);
		
		            caixaService.disableConta( $scope.model.conta.entity.id, {
			            callback : function(result) {	   
			            	
			            	$state.go($scope.CONTA_LIST_STATE);
			            	$scope.showMessage( $scope.ERROR_MESSAGE,  "Registro excluído com sucesso" );
			            	$scope.$apply();
			            },
			            errorHandler : function(message, exception) {
			            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
			                $scope.$apply();
			            }
			        });
		        });
		    };
			
		    /*-------------------------------------------------------------------
		     * 		 				PRIVATE BEHAVIORS
		     *-------------------------------------------------------------------*/
			
		    /**
		     * 
		     */
			$scope.listByFilters = function(){
				caixaService.listContaByFilters(  $scope.model.conta.filters.terms.toString(), true, $scope.model.conta.page.pageable, {
	                callback : function(result) {
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
			$scope.disableConta = function(){
				caixaService.disableConta( id, {
		            callback : function(result) {
		            	$state.go($scope.CONTA_LIST_STATE);
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
			$scope.enableConta = function(){
				var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja ativar este registro?')
	            .content('')
	            .ok('Sim')
	            .cancel('Cancelar');

		        $mdDialog.show(confirm).then(function (result) {
		            console.log(result);
		
		            caixaService.enableConta( $scope.model.conta.entity.id  , {
		                callback : function(result) {
		                    $scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi ativado com sucesso!" );
		                    $state.go($scope.CONTA_LIST_STATE);
		                    
		                    $scope.listByFilters();
		                    $scope.$apply();
		                },
		                errorHandler : function(message, exception) {
		                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
		                    $scope.$apply();
		                }
		            });
		        });
			};
			
			/**
			 * 
			 */
			$scope.listByMoreFilters = function(){
				
			};
			
			/**
			 * 
			 */
			$scope.insertConta = function(){
				$scope.model.conta.form.$setSubmitted();
				
				if ($scope.model.conta.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				caixaService.insertConta( $scope.model.conta.entity, {
	                callback : function(result) {
	                	$scope.model.conta.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
	                	$state.go($scope.CONTA_LIST_STATE);
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
			$scope.updateConta= function(){
				$scope.model.conta.form.$setSubmitted();
				
				if ($scope.model.conta.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				caixaService.updateConta(  $scope.model.conta.entity, {
	                callback : function(result) {
	                	$scope.model.conta.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi alterado com sucesso!" );
	                	$state.go(CONTA_EDIT_STATE);
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
			$scope.onContaPaginationChange = function(paginate) {
	        	if (paginate) {
	        		$scope.model.conta.page.pageable.page++;
	        	} else {
	        		$scope.model.conta.page.pageable.page--;
	        	}
	        		
	        	$scope.listByFilters();
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