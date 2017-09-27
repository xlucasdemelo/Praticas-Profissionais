(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('MovimentacaoController', function( $scope, $state, $importService, $mdToast, $mdDialog, $mdExpansionPanel ) {
		
		   /**
		     * Serviços importados do DWR
		     */
			$importService("caixaService");
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
			$scope.MOVIMENTACAO_LIST_STATE = "movimentacao.list";
			/**
			 * Representa o estado para a criação de registros.
			 */
			$scope.MOVIMENTACAO_ADD_STATE = "movimentacao.add";
			/**
			 * Representa o estado para a edição de registros.
			 */
			$scope.MOVIMENTACAO_EDIT_STATE = "movimentacao.edit";
			/**
			 * Representa o estado de detalhe de um registro.
			 */
			$scope.MOVIMENTACAO_DETAIL_STATE = "movimentacao.detail";
			
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
				
				movimentacao: {
					form: null,
					entity: new Movimentacao(),
					
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
					case $scope.MOVIMENTACAO_LIST_STATE: {
						$scope.changeToList( false );
						break;
					}
					case $scope.MOVIMENTACAO_DETAIL_STATE: {
						$scope.changeToDetail( $state.params.id );
						break;
					}
			        case $scope.MOVIMENTACAO_ADD_STATE: {
			        	$scope.changeToAdd();
			        	break;
			        }
			        case $scope.MOVIMENTACAO_EDIT_STATE: {
			        	$scope.changeToEdit( $state.params.id );
			        	break;
			        }
			        default : {
			        	if ( $state.current.name == $scope.MOVIMENTACAO_LIST_STATE )
			        		$state.go( $scope.MOVIMENTACAO_LIST_STATE );
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
				
				$scope.model.movimentacao.entity = new Movimentacao();//Limpa o formulário
				$scope.model.movimentacao.entity.naturezaGastos = new NaturezaGastos();
				
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
		        
		        caixaService.findMovimentacaoById( id, {
		            callback : function(result) {	   
		            	$scope.model.movimentacao.entity = result;
		            	
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
		        
		        if ( paginate ) $scope.model.movimentacao.page.pageable.page++;
		        
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
		
		        fornecedorService.findById( id, {
		            callback : function(result) {
		            	$scope.model.movimentacao.entity = result;
		            	$state.current.breadCrumbs.push({name: $scope.model.movimentacao.entity.razaoSocial});
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
		
		            fornecedorService.disableFornecedor( $scope.model.movimentacao.entity.id, {
			            callback : function(result) {	   
			            	
			            	$state.go($scope.MOVIMENTACAO_LIST_STATE);
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
			
		    /**
		     * 
		     */
		    $scope.changeToEmAberto = function()
		    {
		    	
		    	var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja enviar a movimentação para em aberto?')
	            .content('Não será possível excluir este registro.')
	            .ok('Sim')
	            .cancel('Cancelar');
	
		        $mdDialog.show(confirm).then(function (result) {
		            console.log(result);
		
		            caixaService.changeToAberto( $scope.model.movimentacao.entity.id, {
			            callback : function(result) {
			            	$scope.model.movimentacao.entity = result;
			            	$scope.$apply();
			            },
			            errorHandler : function(message, exception) {
			            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
			                $scope.$apply();
			            }
			        });
		        });
		    	
		    }
		    
		    $scope.changeToConcluido = function()
		    {
		    	
		    	var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja concluir a movimentação?')
	            .content('')
	            .ok('Sim')
	            .cancel('Cancelar');
	
		        $mdDialog.show(confirm).then(function (result) {
		            console.log(result);
		
		            caixaService.changeToConcluido( $scope.model.movimentacao.entity.id, {
			            callback : function(result) {
			            	$scope.model.movimentacao.entity = result;
			            	$scope.$apply();
			            },
			            errorHandler : function(message, exception) {
			            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
			                $scope.$apply();
			            }
			        });
		        });
		    	
		    }
		    
		    /*-------------------------------------------------------------------
		     * 		 				PRIVATE BEHAVIORS
		     *-------------------------------------------------------------------*/
			
			$scope.listByFilters = function(){
				
				caixaService.listMovimentacoesByFilters(  $scope.model.movimentacao.filters.terms.toString(), $scope.model.movimentacao.page.pageable, {
	                callback : function(result) {
	                	$scope.totalPagesMovimentacao = result.totalPages;
	                	$scope.model.movimentacao.page = {//PageImpl
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
				
				fornecedorService.disableFornecedor( id, {
		            callback : function(result) {
		            	$state.go($scope.MOVIMENTACAO_LIST_STATE);
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
		
		            fornecedorService.enableFornecedor( $scope.model.movimentacao.entity.id  , {
		                callback : function(result) {
		                    $scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi ativado com sucesso!" );
		                    $state.go($scope.MOVIMENTACAO_LIST_STATE);
		                    
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
			$scope.insertMovimentacao = function()
			{
				$scope.model.movimentacao.form.$submitted = true;
				
				if ($scope.model.movimentacao.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				
//				var naturezaGastos = new NaturezaGastos();
//				naturezaGastos = $scope.model.movimentacao.entity.naturezaGastos.nome;
//				
//				$scope.model.movimentacao.entity.naturezaGastos = naturezaGastos;
				
				caixaService.insertMovimentacao( $scope.model.movimentacao.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.movimentacao.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
	                	$state.go( $scope.MOVIMENTACAO_EDIT_STATE, {id: result.id}, {reload: true } )
	                	$scope.$apply();
	                	
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
			}
			
			$scope.updateMovimentacao = function()
			{
				$scope.model.movimentacao.form.$submitted = true;
				if ($scope.model.movimentacao.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				caixaService.updateMovimentacao(  $scope.model.movimentacao.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.movimentacao.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi alterado com sucesso!" );
	                	$state.go(MOVIMENTACAO_EDIT_STATE)
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
			$scope.insertConta = function()
			{
				caixaService.insertMovimentacao( $scope.model.movimentacao.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.movimentacao.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
	                	$state.go( $scope.MOVIMENTACAO_EDIT_STATE, {id: result.id}, {reload: true } )
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
			$scope.onMovimentacaoPaginationChange = function(paginate) {
	        	if (paginate) {
	        		$scope.model.movimentacao.page.pageable.page++;
	        	} else {
	        		$scope.model.movimentacao.page.pageable.page--;
	        	}
	        		
	        	$scope.listByFilters();
	        };
			
	        
			/**
			 * 
			 */
			$scope.listAllTiposMovimentacao= function() {
				caixaService.listAllTiposMovimentacao(   {
	                callback : function(result) {
	                	
	                	$scope.allTiposMovimentacao = result;
	                	
	                	$scope.$apply();
	                	
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
			}
			
			/**
			 * Parametro tipoConta é um boolean onde true é conta origem e false conta destino
			 */
			$scope.openSelecionarConta = function( tipoConta )
			{
		    	$mdDialog.show({
					controller: "SelecionarContaControllerPopup",
					templateUrl: './modules/home/views/conta/popup/selecionar-conta-modal.html',
					parent: angular.element(document.body),
					clickOutsideToClose:true,
					fullscreen: true,
					scope: $scope.$new()
			    })
			    .then(function(conta) {
			    	
//			    	var contaResult = new Conta();
//			    	contaResult.id = conta.id;
//			    	contaResult.nome = conta.nome;
//			    	
			    	if (tipoConta)
			    		$scope.model.movimentacao.entity.contaOrigem = conta;
			    	else
			    		$scope.model.movimentacao.entity.contaDestino = conta;
			    	
			    }, function() {
			    	
			    });
				
			};
			
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
			$scope.listAllTiposMovimentacao();
});
}(window.angular));