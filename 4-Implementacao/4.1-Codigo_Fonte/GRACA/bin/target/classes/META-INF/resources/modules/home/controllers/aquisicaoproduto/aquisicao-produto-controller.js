(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('AquisicaoProdutoController', function( $scope, $state, $importService, $mdToast, $mdDialog, $mdExpansionPanel ) {
		
		   /**
		     * Serviços importados do DWR
		     */
			$importService("aquisicaoProdutoService");
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
			$scope.AQUISICAO_PRODUTO_LIST_STATE = "aquisicao-produto.list";
			/**
			 * Representa o estado para a criação de registros.
			 */
			$scope.AQUISICAO_PRODUTO_ADD_STATE = "aquisicao-produto.add";
			/**
			 * Representa o estado para a edição de registros.
			 */
			$scope.AQUISICAO_PRODUTO_EDIT_STATE = "aquisicao-produto.edit";
			/**
			 * Representa o estado de detalhe de um registro.
			 */
			$scope.AQUISICAO_PRODUTO_DETAIL_STATE = "aquisicao-produto.detail";
			
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
				
				aquisicaoProduto: {
					form: null,
					entity: new AquisicaoProduto(),
					
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
				
				produtoAdquirido: {
					form: null,
					entity: new ProdutoAdquirido(),
					
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
					case $scope.AQUISICAO_PRODUTO_LIST_STATE: {
						$scope.changeToList( false );
						break;
					}
					case $scope.AQUISICAO_PRODUTO_DETAIL_STATE: {
						$scope.changeToDetail( $state.params.id );
						break;
					}
			        case $scope.AQUISICAO_PRODUTO_ADD_STATE: {
			        	$scope.changeToAdd();
			        	break;
			        }
			        case $scope.AQUISICAO_PRODUTO_EDIT_STATE: {
			        	$scope.changeToEdit( $state.params.id );
			        	break;
			        }
			        default : {
			        	if ( $state.current.name == $scope.AQUISICAO_PRODUTO_LIST_STATE )
			        		$state.go( $scope.AQUISICAO_PRODUTO_LIST_STATE );
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
				
				$scope.model.aquisicaoProduto.entity = new AquisicaoProduto();//Limpa o formulário
				$scope.model.produtoAdquirido.page.content = [];
				
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
		        
		        
		        
		        aquisicaoProdutoService.findAquisicaoProdutoById( id, {
		            callback : function(result) {	   
		            	$scope.model.aquisicaoProduto.entity = result;
		            	$scope.listProdutosByAquisicao();
		            	
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
		        
		        if ( paginate ) $scope.model.aquisicaoProduto.page.pageable.page++;
		        
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
		
		        aquisicaoProdutoService.findById( id, {
		            callback : function(result) {
		            	$scope.model.aquisicaoProduto.entity = result;
		            	$state.current.breadCrumbs.push({name: $scope.model.aquisicaoProduto.entity.razaoSocial});
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
		
		            aquisicaoProdutoService.removeAquisicaoProduto( $scope.model.aquisicaoProduto.entity.id, {
			            callback : function(result) {	   
			            	
			            	$state.go($scope.AQUISICAO_PRODUTO_LIST_STATE);
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
				
				aquisicaoProdutoService.listAquisicoesByFilters(  $scope.model.aquisicaoProduto.filters.terms.toString(), $scope.model.aquisicaoProduto.page.pageable, {
	                callback : function(result) {
	                	$scope.totalPagesAquisicaoProduto = result.totalPages;
	                	$scope.model.aquisicaoProduto.page = {//PageImpl
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
			$scope.changeToConcluido = function()
			{
				var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja concluir a movimentação?')
	            .content('')
	            .ok('Sim')
	            .cancel('Cancelar');
	
		        $mdDialog.show(confirm).then(function (result) {
		        	aquisicaoProdutoService.changeToConcluido( $scope.model.aquisicaoProduto.entity.id , {
						callback : function( result ) {
							$scope.model.aquisicaoProduto.entity = result;
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
			$scope.openSelecionarFornecedor = function( tipoConta )
			{
		    	$mdDialog.show({
					controller: "SelecionarFornecedorControllerPopup",
					templateUrl: './modules/home/views/aquisicaoproduto/popup/selecionar-fornecedor-modal.html',
					parent: angular.element(document.body),
					clickOutsideToClose:true,
					fullscreen: true,
					scope: $scope.$new()
			    })
			    .then(function(fornecedor) {
			    	
		    		$scope.model.aquisicaoProduto.entity.fornecedor = fornecedor;
			    	
			    }, function() {
			    	
			    });
				
			};
			
			/**
			 * 
			 */
			$scope.insertProdutoAdquirido = function( produtoAdquirido )
			{
				aquisicaoProdutoService.insertProdutoAdquirido( produtoAdquirido , {
					callback : function( result ) {
						$scope.showMessage( $scope.ERROR_MESSAGE,  "O registro foi inserido com sucesso" );
						return result;
					},
					errorHandler : function(message, exception) {
						$scope.showMessage( $scope.ERROR_MESSAGE,  message );
		                $scope.$apply();
		            }
				});
			}
			
			$scope.removeProdutoAdquirido = function(id)
			{
				aquisicaoProdutoService.removeProdutoAdquirido( id , {
					callback : function( result ) {
						$scope.showMessage( $scope.ERROR_MESSAGE,  "O registro foi removido com sucesso" );
						$scope.listProdutosByAquisicao();
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
			$scope.listProdutosByAquisicao = function()
			{
				
				aquisicaoProdutoService.listProdutosByAquisicao(  $scope.model.aquisicaoProduto.entity.id, $scope.model.produtoAdquirido.page.pageable, {
	                callback : function(result) {
	                	$scope.totalPagesProdutoAdquirido = result.totalPages;
	                	$scope.model.produtoAdquirido.page = {//PageImpl
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
			$scope.openSelecionarProduto = function( tipoConta )
			{
		    	$mdDialog.show({
					controller: "SelecionarProdutoControllerPopup",
					templateUrl: './modules/home/views/produto/popup/selecionar-produto-modal.html',
					parent: angular.element(document.body),
					clickOutsideToClose:true,
					fullscreen: true,
					scope: $scope.$new()
			    })
			    .then(function(produtoAdquirido) {
			    	
		    		$scope.listProdutosByAquisicao();
			    	
			    }, function() {
			    	
			    });
				
			};
			
			
			/**
			 * 
			 */
			$scope.disableAquisicaoProduto = function(){
				
				aquisicaoProdutoService.disableAquisicaoProduto( id, {
		            callback : function(result) {
		            	$state.go($scope.AQUISICAO_PRODUTO_LIST_STATE);
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
			$scope.enableAquisicaoProduto = function(){
				
				var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja ativar este registro?')
	            .content('')
	            .ok('Sim')
	            .cancel('Cancelar');

		        $mdDialog.show(confirm).then(function (result) {
		            console.log(result);
		
		            aquisicaoProdutoService.enableAquisicaoProduto( $scope.model.aquisicaoProduto.entity.id  , {
		                callback : function(result) {
		                    $scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi ativado com sucesso!" );
		                    $state.go($scope.AQUISICAO_PRODUTO_LIST_STATE);
		                    
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
			
			$scope.listByMoreFilters = function(){
				
			}
			
			/**
			 * 
			 */
			$scope.insertAquisicaoProduto = function()
			{
				$scope.model.aquisicaoProduto.form.$submitted = true;
				if ($scope.model.aquisicaoProduto.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				
				aquisicaoProdutoService.insertAquisicaoProduto( $scope.model.aquisicaoProduto.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.aquisicaoProduto.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
	                	$scope.$apply();
	                	
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
			}
			
			$scope.updateAquisicaoProduto = function()
			{
				$scope.model.aquisicaoProduto.form.$submitted = true;
				if ($scope.model.aquisicaoProduto.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				aquisicaoProdutoService.updateAquisicaoProduto(  $scope.model.aquisicaoProduto.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.aquisicaoProduto.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi alterado com sucesso!" );
	                	$state.go(AQUISICAO_PRODUTO_EDIT_STATE)
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
			$scope.onAquisicaoProdutoPaginationChange = function(paginate) {
	        	if (paginate) {
	        		$scope.model.aquisicaoProduto.page.pageable.page++;
	        	} else {
	        		$scope.model.aquisicaoProduto.page.pageable.page--;
	        	}
	        		
	        	$scope.listByFilters();
	        };
			
	        
			/**
			 * 
			 */
			$scope.listAllFormasPagamento= function(){
				aquisicaoProdutoService.listAllFormasPagamento({
	                callback : function(result) {
	                	
	                	$scope.allFormasPagamento = result;
	                	
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
			$scope.listAllCondicoesPagamento= function(){
				aquisicaoProdutoService.listAllCondicoesPagamento(   {
	                callback : function(result) {
	                	
	                	$scope.allCondicoesPagamento = result;
	                	
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
		    	$scope.openDefaultConfirmDialog( $scope, $scope.excluirAquisicaoProdutoHandler, null );
			};
			
			
			/*-------------------------------------------------------------------
		     * 		 				 	POST CONSTRUCT
		     *-------------------------------------------------------------------*/
			
			$scope.listAllFormasPagamento();
			$scope.listAllCondicoesPagamento();
});	
}(window.angular));