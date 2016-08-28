(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('PlanoAtendimentoFamiliarController', function( $scope, $state, $importService, $mdToast, $mdDialog, $mdExpansionPanel ) {
		
		   /**
		     * Serviços importados do DWR
		     */
			$importService("planoAtendimentoService");
			$importService("integranteFamiliarService");
			$importService("enderecoService");
			
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
			$scope.PLANO_ATENDIMENTO_FAMILIAR_LIST_STATE = "plano-atendimento-familiar.list";
			/**
			 * Representa o estado para a criação de registros.
			 */
			$scope.PLANO_ATENDIMENTO_FAMILIAR_ADD_STATE = "plano-atendimento-familiar.add";
			/**
			 * Representa o estado para a edição de registros.
			 */
			$scope.PLANO_ATENDIMENTO_FAMILIAR_EDIT_STATE = "plano-atendimento-familiar.edit";
			/**
			 * Representa o estado de detalhe de um registro.
			 */
			$scope.PLANO_ATENDIMENTO_FAMILIAR_DETAIL_STATE = "plano-atendimento-familiar.detail";
			
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
				
				planoAtendimentoFamiliar: {
					form: null,
					entity: new PlanoAtendimentoFamiliar(),
					
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
				
				integranteFamiliar: {
					form: null,
					entity: new IntegranteFamiliar(),
					
					filters: {
					    terms: "",
					   
					},
					
				    page: {//PageImpl 
				    		content:[],
				    		pageable : {size: 9,
							    		page: 0,
							        	sort:null
				        	}
				    },
				    sort: [{//Sort
		        		direction: 'ASC', properties: 'id', nullHandlingHint:null
		        	}],
				},
				
				endereco: {
					form: null,
					entity: new Endereco(),
					
					filters: {
					    terms: "",
					   
					},
					
					page: {//PageImpl 
				    	content: [],
				    	pageable: {//PageRequest
				    		size: 9,
				    		page: 0,
				        	sort: {//Sort
				        		direction: 'ASC', properties: ['id']
				        	},
				    	}
				    },
				},
				
				estado: {
					itens: [],
					noCache: true,
					selectedItem: null,
					searchText: null
				},
				cidade: {
					itens: [],
					noCache: true,
					selectedItem: null,
					searchText: null
				},
				pais: {
					itens: [],
					noCache: true,
					selectedItem: null,
					searchText: null
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
					case $scope.PLANO_ATENDIMENTO_FAMILIAR_LIST_STATE: {
						$scope.changeToList( false );
						break;
					}
					case $scope.PLANO_ATENDIMENTO_FAMILIAR_DETAIL_STATE: {
						$scope.changeToDetail( $state.params.id );
						break;
					}
			        case $scope.PLANO_ATENDIMENTO_FAMILIAR_ADD_STATE: {
			        	$scope.changeToAdd();
			        	break;
			        }
			        case $scope.PLANO_ATENDIMENTO_FAMILIAR_EDIT_STATE: {
			        	$scope.changeToEdit( $state.params.id );
			        	break;
			        }
			        default : {
			        	if ( $state.current.name == $scope.PLANO_ATENDIMENTO_FAMILIAR_LIST_STATE )
			        		$state.go( $scope.PLANO_ATENDIMENTO_FAMILIARLIST_STATE );
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
				
				$scope.model.planoAtendimentoFamiliar.entity = new PlanoAtendimentoFamiliar();//Limpa o formulário
				
				$scope.model.pais.selectedItem = null;
				$scope.model.pais.searchText = null;
	        	
				$scope.model.estado.selectedItem = null;
				$scope.model.estado.searchText = null;
				
	        	$scope.model.cidade.selectedItem = null;
	        	$scope.model.cidade.searchText = null;
				
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
		        
		        planoAtendimentoService.findPlanoAtendimentoFamiliarById( id, {
		            callback : function(result) {	   
		            	$scope.model.planoAtendimentoFamiliar.entity = result;
		            	
		            	$scope.$apply();
		            },
		            errorHandler : function(message, exception) {
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
		                $scope.$apply();
		            }
		        });
		        
		        $scope.listIntegrantesFamiliaresByFamilia(id);
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
		        
		        if ( paginate ) $scope.model.planoAtendimentoFamiliar.page.pageable.page++;
		        
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
		
		        contratadaService.findFornecedorById( id, {
		            callback : function(result) {
		            	$scope.model.fornecedor.entity = result;
		            	$state.current.breadCrumbs.push({name: $scope.model.fornecedor.entity.razaoSocial});
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
		
		            familiaService.disableFamilia( $scope.model.familia.entity  , {
		                callback : function(result) {
		                    $scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi excluído com sucesso!" );
		                    $state.go($scope.PLANO_ATENDIMENTO_FAMILIARLIST_STATE);
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
			$scope.enableFamilia = function(){
				
				var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja ativar este registro?')
	            .content('')
	            .ok('Sim')
	            .cancel('Cancelar');

		        $mdDialog.show(confirm).then(function (result) {
		            console.log(result);
		
		            familiaService.enableFamilia( $scope.model.familia.entity  , {
		                callback : function(result) {
		                    $scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi ativado com sucesso!" );
		                    $state.go($scope.PLANO_ATENDIMENTO_FAMILIARLIST_STATE);
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
		    
		    /*-------------------------------------------------------------------
		     * 		 				PRIVATE BEHAVIORS
		     *-------------------------------------------------------------------*/
			
			$scope.listByFilters = function(){
				
				planoAtendimentoService.listPlanoAtendimentoFamiliarByFilters(  $scope.model.planoAtendimentoFamiliar.filters.terms.toString(), true, $scope.model.planoAtendimentoFamiliar.page.pageable, {
	                callback : function(result) {
	                	$scope.totalPagesPlanoAtendimentoFamiliar = result.totalPages;
	                	$scope.model.planoAtendimentoFamiliar.page = {//PageImpl
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
	        
			$scope.listByMoreFilters = function(){
				
				planoAtendimentoService.listPlanoAtendimentoFamiliarByFilters(  $scope.model.planoAtendimentoFamiliar.filters.terms.toString(), $scope.model.planoAtendimentoFamiliar.filters.ativo, $scope.model.planoAtendimentoFamiliar.page.pageable, {
	                callback : function(result) {
	                	
	                	$scope.model.planoAtendimentoFamiliar.page.content = result.content; 
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
			$scope.insertFamilia = function()
			{
				$scope.model.familia.form.$submitted = true;
				if ($scope.model.familia.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
//				$scope.model.familia.entity.endereco = $scope.model.endereco.entity; 
				
				if ( !$scope.model.pais.selectedItem )
					return $scope.showMessage( $scope.ERROR_MESSAGE,  "Selecione um país" );
				if ( !$scope.model.estado.selectedItem )
					return $scope.showMessage( $scope.ERROR_MESSAGE,  "Selecione um estado" );
				if ( !$scope.model.cidade.selectedItem )
					return $scope.showMessage( $scope.ERROR_MESSAGE,  "Selecione uma cidade" );
					
				$scope.model.familia.entity.endereco.cidade = $scope.model.cidade.selectedItem ;
				$scope.model.familia.entity.endereco.cidade.estado = $scope.model.estado.selectedItem ;
				$scope.model.familia.entity.endereco.cidade.estado.pais = $scope.model.pais.selectedItem ;
				
				familiaService.insertFamilia(  $scope.model.familia.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.familia.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
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
			$scope.onFamiliaPaginationChange = function(paginate) {
	        	if (paginate) {
	        		$scope.model.familia.page.pageable.page++;
	        	} else {
	        		$scope.model.familia.page.pageable.page--;
	        	}
	        		
	        	$scope.listByFilters();
	        };
			
	        $scope.onIntegrantePaginationChange = function(paginate) {
	        	if (paginate) {
	        		$scope.model.integranteFamiliar.page.pageable.page++;
	        	} else {
	        		$scope.model.integranteFamiliar.page.pageable.page--;
	        	}
	        		
	        	$scope.listIntegrantesFamiliaresByFamilia($scope.model.familia.entity.id);
	        };
	        
			/**
			 * 
			 */
			$scope.listAllTiposImovel = function(){
				familiaService.listAllTiposImovel(   {
	                callback : function(result) {
	                	
	                	$scope.allTiposImovel = result;
	                	
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
			$scope.listAllTiposMoradia = function(){
				familiaService.listAllTiposMoradia(   {
	                callback : function(result) {
	                	
	                	$scope.allTiposMoradia = result;
	                	
	                	$scope.$apply();
	                	
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
			}
			
			$scope.selectPais = function(pais)
			{
				$scope.pais = pais;
			}
			
			/**
			 * 
			 */
			$scope.changetToEmExecucao = function()
			{
				planoAtendimentoService.changetToEmExecucao( $scope.model.planoAtendimentoFamiliar.entity.id,   {
	                callback : function(result) {
	                	
	                	$scope.model.planoAtendimentoFamiliar.entity = result;
	                	
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  "Plano de atendimento enviado para execução com sucesso" );
	                	
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
			$scope.openSelectFamilia = function()
			{
				
				$mdDialog.show({
				      controller: "SelecionarFamiliaPopup",
				      templateUrl: './modules/home/views/crianca/popup/selecionar-familia-popup.html',			      
				      scope: $scope.$new(),
					})
				    .then(function(result) {
				    	$scope.model.planoAtendimentoFamiliar.entity.familia = result;
				    	$scope.associateFamilia();
				 });
				
			}
			
			/**
			 * 
			 */
			$scope.associateFamilia = function(  ){
				
				planoAtendimentoService.associateFamiliaToPlano( $scope.model.planoAtendimentoFamiliar.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.planoAtendimentoFamiliar.entity = result;
	                	
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
			$scope.listPaisesByFiltes = function(filter){
				enderecoService.listPaisesByFilters( filter, null, {
	                callback : function(result) {
	                	
	                	$scope.model.pais.itens = result.content;
	                	
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
			$scope.listEstadosByFiltes = function(filter){
				enderecoService.listEstadosByFIlters( filter, $scope.model.pais.selectedItem.id, null, {
	                callback : function(result) {
	                	
	                	$scope.model.estado.itens = result.content;
	                	
	                	$scope.$apply();
	                	
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
			}
			
			$scope.listCidadesByFiltes = function(filter){
				enderecoService.listCidadesByFIlters( filter, $scope.model.estado.selectedItem.id, null, {
	                callback : function(result) {
	                	
	                	$scope.model.cidade.itens = result.content;
	                	
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
		     * 		 			HANDLERS INTEGRANTE FAMILIAR
		     *-------------------------------------------------------------------*/
			
			$scope.listIntegrantesFamiliaresByFamilia = function( id ){
				
				integranteFamiliarService.listIntegrantesByfamilia( id, $scope.model.integranteFamiliar.page.pageable, {
	                callback : function(result) {
	                	
	                	$scope.totalPagesIntegrante = result.totalPages;
	                	
	                	$scope.model.integranteFamiliar.page = {//PageImpl
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
			
			$scope.openAdicionarIntegranteFamiliarHandler = function( integranteFamiliar ) {

				$mdDialog.show({
				      controller: "AdicionarIntegranteFamiliarControllerPopup",
				      templateUrl: './modules/home/views/familia/popup/adicionar-integrante-familiar-popup.html',			      
				      scope: $scope.$new(),
				      resolve: {
				    	  integrante: function() {
				    		  return angular.copy(integranteFamiliar);
				    	  }
				      }
					})
				    .then(function(result) {
				    	 $scope.listIntegrantesFamiliaresByFamilia($scope.model.familia.entity.id);
				 });
			};
			
			/**
			 * 
			 */
			$scope.listAllTipoEncaminhamento= function()
			{
				planoAtendimentoService.listAllTIposEncaminhamento(   {
	                callback : function(result) {
	                	
	                	$scope.allTiposEncaminhamento = result;
	                	
	                	$scope.$apply();
	                	
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
			}
			
			/*-------------------------------------------------------------------
		     * 		 				 	POST CONSTRUCT
		     *-------------------------------------------------------------------*/
			$scope.listAllTipoEncaminhamento();
});
}(window.angular));