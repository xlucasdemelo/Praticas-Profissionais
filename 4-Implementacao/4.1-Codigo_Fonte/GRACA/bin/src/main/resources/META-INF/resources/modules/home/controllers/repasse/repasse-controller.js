(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('RepasseController', function( $scope, $state, $importService, $mdToast, $mdDialog, $mdExpansionPanel ) {
		
		   /**
		     * Serviços importados do DWR
		     */
			$importService("produtoService");
			/**
			 * 
			 */
			$mdExpansionPanel().waitFor('paperEncaminhamento').then(function (instance) {
				  instance.expand();
				});			
			
		    /*-------------------------------------------------------------------
		     * 		 				 	ATTRIBUTES
		     *-------------------------------------------------------------------*/
			 //----STATES
			/**
			 * Representa o estado de listagem de registros.
			 */
			$scope.REPASSE_LIST_STATE = "repasse.list";
			/**
			 * Representa o estado para a criação de registros.
			 */
			$scope.REPASSE_ADD_STATE = "repasse.add";
			/**
			 * Representa o estado para a edição de registros.
			 */
			$scope.REPASSE_EDIT_STATE = "repasse.edit";
			/**
			 * Representa o estado de detalhe de um registro.
			 */
			$scope.REPASSE_DETAIL_STATE = "repasse.detail";
			
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
				
				repasse: {
					form: null,
					entity: new Repasse(),
					
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
					
				},
				
				produtoRepassado: {
					form: null,
					entity: new ProdutoRepassado(),
					
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
					case $scope.REPASSE_LIST_STATE: {
						$scope.changeToList( false );
						break;
					}
					case $scope.REPASSE_DETAIL_STATE: {
						$scope.changeToDetail( $state.params.id );
						break;
					}
			        case $scope.REPASSE_ADD_STATE: {
			        	$scope.changeToAdd();
			        	break;
			        }
			        case $scope.REPASSE_EDIT_STATE: {
			        	$scope.changeToEdit( $state.params.id );
			        	break;
			        }
			        default : {
			        	if ( $state.current.name == $scope.REPASSE_LIST_STATE )
			        		$state.go( $scope.REPASSE_LIST_STATE );
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
				
				$scope.model.repasse.entity = new Repasse();//Limpa o formulário
				
				
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
		        
		        produtoService.findRepasseById( id, {
		            callback : function(result) {	   
		            	$scope.model.repasse.entity = result;
		            	$scope.listProdutosRepassadosByRepasse(id);
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
		        
		        if ( paginate ) $scope.model.repasse.page.pageable.page++;
		        
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
		
		        produtoService.findById( id, {
		            callback : function(result) {
		            	$scope.model.repasse.entity = result;
		            	$state.current.breadCrumbs.push({name: $scope.model.repasse.entity.razaoSocial});
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
		
		            produtoService.removeRepasse( $scope.model.repasse.entity.id, {
			            callback : function(result) {	   
			            	
			            	$state.go($scope.REPASSE_LIST_STATE);
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
			
			$scope.listByFilters = function(){
				
				produtoService.listRepassesByFilters(  $scope.model.repasse.filters.terms.toString(), $scope.model.repasse.page.pageable, {
	                callback : function(result) {
	                	$scope.totalPagesRepasse = result.totalPages;
	                	$scope.model.repasse.page = {//PageImpl
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
			}
	        
			/**
			 * 
			 */
			$scope.disableFornecedor = function(){
				
				produtoService.disableFornecedor( id, {
		            callback : function(result) {
		            	$state.go($scope.REPASSE_LIST_STATE);
		            	$scope.$apply();
		            },
		            errorHandler : function(message, exception) {
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
		                $scope.$apply();
		            }
		        });
				
			}
			
			/**
			 * 
			 */
			$scope.enableFornecedor = function(){
				
				var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja ativar este registro?')
	            .content('')
	            .ok('Sim')
	            .cancel('Cancelar');

		        $mdDialog.show(confirm).then(function (result) {
		            console.log(result);
		
		            produtoService.enableFornecedor( $scope.model.repasse.entity.id  , {
		                callback : function(result) {
		                    $scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi ativado com sucesso!" );
		                    $state.go($scope.REPASSE_LIST_STATE);
		                    
		                    $scope.listByFilters();
		                    $scope.$apply();
		                },
		                errorHandler : function(message, exception) {
		                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
		                    $scope.$apply();
		                }
		            });
		        });
			}
			
			/**
			 * 
			 */
			$scope.openSelecionarCasaLar = function( algo )
			{
		    	$mdDialog.show({
					controller: "SelecionarCasaLarControllerPopup",
					templateUrl: './modules/home/views/casalar/popup/selecionar-casa-lar-modal.html',
					parent: angular.element(document.body),
					clickOutsideToClose:true,
					fullscreen: true,
					scope: $scope.$new()
			    })
			    .then(function(casaLar) {
			    	
		    		$scope.model.repasse.entity.casaLar = casaLar;
			    	
			    }, function() {
			    	
			    });
				
			};
			
			/**
			 * 
			 */
			$scope.openSelecionarProduto = function( tipoConta )
			{
		    	$mdDialog.show({
					controller: "SelecionarProdutoRepassadoControllerPopup",
					templateUrl: './modules/home/views/repasse/popup/selecionar-produto-repassado-modal.html',
					parent: angular.element(document.body),
					clickOutsideToClose:true,
					fullscreen: true,
					scope: $scope.$new()
			    })
			    .then(function(produtoAdquirido) {
			    	
			    	$scope.listProdutosRepassadosByRepasse( $scope.model.repasse.entity.id );
			    	
			    }, function() {
			    	
			    });
				
			};
			
			/**
			 * 
			 */
			$scope.insertRepasse= function()
			{
				$scope.model.repasse.form.$submitted = true;
				if ($scope.model.repasse.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				produtoService.insertRepasse( $scope.model.repasse.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.repasse.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
	                	$state.go( $scope.REPASSE_EDIT_STATE, {id: result.id}, {reload: true } )
	                	$scope.$apply();
	                	
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
			}
			
			/**
			 * 
			 */
			$scope.insertProdutoRepassado = function(produtoRepassado)
			{
				
				produtoService.insertProdutoRepassado( produtoRepassado, {
	                callback : function(result) {
	                	return result;
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
				
			}
			
			/**
			 * 
			 */
			$scope.removeProdutorepassado = function (id) {
				
	            produtoService.removeProdutoRepassado( id, {
		            callback : function(result) {	   
		            	
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  "Registro excluído com sucesso" );
		            	$scope.listProdutosRepassadosByRepasse($scope.model.repasse.entity.id);
		            	$scope.$apply();
		            },
		            errorHandler : function(message, exception) {
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
		                $scope.$apply();
		            }
		        });
		    }
			
			$scope.changeToAprovado = function()
			{
				var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja aprovar este registro?')
	            .content('Não será mais possível a edição deste registro .')
	            .ok('Sim')
	            .cancel('Cancelar');
				
				$mdDialog.show(confirm).then(function (result) {
					
				produtoService.changeToAprovado( $scope.model.repasse.entity.id, {
		            callback : function(result) {	   
		            	$scope.model.repasse.entity = result;
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  "Registro a com sucesso" );
		            	$scope.$apply();
		            },
		            errorHandler : function(message, exception) {
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
		                $scope.$apply();
		            }
		        });
				
				});
			}
			
			/**
			 * 
			 */
			$scope.listProdutosRepassadosByRepasse = function( repasseId )
			{
				
				produtoService.listProdutosRepassadosByRepasse( repasseId,  {
	                callback : function(result) {
	                	
	                	$scope.totalPagesProdutoRepassado = result.totalPages;
	                	$scope.model.produtoRepassado.page = {//PageImpl
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
				
			}
			
			/**
			 * 
			 */
			$scope.updateFornecedor = function()
			{
				$scope.model.repasse.form.$submitted = true;
				if ($scope.model.repasse.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				produtoService.updateFornecedor(  $scope.model.repasse.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.repasse.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi alterado com sucesso!" );
	                	$state.go(REPASSE_EDIT_STATE)
	                	$scope.$apply();
	                	
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
			}
			
			/**
			 * 
			 */
			$scope.onFornecedorPaginationChange = function(paginate) {
	        	if (paginate) {
	        		$scope.model.repasse.page.pageable.page++;
	        	} else {
	        		$scope.model.repasse.page.pageable.page--;
	        	}
	        		
	        	$scope.listByFilters();
	        };
			
	        
			/**
			 * 
			 */
			$scope.listAllUserRoles= function(){
				accountService.listAllUserRoles(   {
	                callback : function(result) {
	                	
	                	$scope.allUserRoles = result;
	                	
	                	$scope.$apply();
	                	
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
			}
			
			
			/**
			 * 
			 */
			$scope.openMaisOpcoes = function(){
				$scope.moreFilters = !$scope.moreFilters;
			};
			
			/**
			 *  Dialog para confirmação de exclusão
			 */
			$scope.confirmDelete = function(ev){
				var dialog = {
	    				title: 'Tem certeza que deseja desativar este registro?',
	    				content: 'O registro será desativado e ficará indisponível para associação.',
	    				ok: 'Sim',
	    				cancel: 'Não',
	    				level: $scope.CRITICAL_LEVEL
		    	}
		    	
		    	$scope.model.dialog = dialog;
		    	$scope.openDefaultConfirmDialog( $scope, $scope.excluirFornecedorHandler, null );
			};
			
			
			/*-------------------------------------------------------------------
		     * 		 				 	POST CONSTRUCT
		     *-------------------------------------------------------------------*/
});
}(window.angular));