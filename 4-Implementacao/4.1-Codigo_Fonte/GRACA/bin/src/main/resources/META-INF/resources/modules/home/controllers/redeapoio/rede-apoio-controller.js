(function ( angular ) {
    'use strict';

/**s
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('RedeApoioController', function( $scope, $state, $importService, $mdToast, $mdDialog, $mdExpansionPanel ) {
		
		   /**
		     * Serviços importados do DWR
		     */
			$importService("redeApoioService");
			$importService("enderecoService");
			/**
			 * 
			 */
			$mdExpansionPanel().waitFor('paperEncaminhamento').then(function (instance) {
				  instance.expand();
				});			
			
			$scope.tipoEncaminhamento = null;
		    /*-------------------------------------------------------------------
		     * 		 				 	ATTRIBUTES
		     *-------------------------------------------------------------------*/
			 //----STATES
			/**
			 * Representa o estado de listagem de registros.
			 */
			$scope.REDE_APOIO_LIST_STATE = "rede-apoio.list";
			/**
			 * Representa o estado para a criação de registros.
			 */
			$scope.REDE_APOIO_ADD_STATE = "rede-apoio.add";
			/**
			 * Representa o estado para a edição de registros.
			 */
			$scope.REDE_APOIO_EDIT_STATE = "rede-apoio.edit";
			/**
			 * Representa o estado de detalhe de um registro.
			 */
			$scope.REDE_APOIO_DETAIL_STATE = "rede-apoio.detail";
			
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
				
				redeApoio: {
					form: null,
					entity: new RedeApoio(),
					
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
				
				user: {
					form: null,
					entity: new User(),
					
					filters: {
					    terms: "",
					   
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
				
				encaminhamento: {
					form: null,
					entity: new Encaminhamento(),
					
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
				
				parecer: {
					form: null,
					entity: new Parecer(),
					
					filters: {
					    terms: "",
					    ativo: true,
					    inativo: false
					},
					list:[],
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
					case $scope.REDE_APOIO_LIST_STATE: {
						$scope.changeToList( false );
						break;
					}
					case $scope.REDE_APOIO_DETAIL_STATE: {
						$scope.changeToDetail( $state.params.id );
						break;
					}
			        case $scope.REDE_APOIO_ADD_STATE: {
			        	$scope.changeToAdd();
			        	break;
			        }
			        case $scope.REDE_APOIO_EDIT_STATE: {
			        	$scope.changeToEdit( $state.params.id );
			        	break;
			        }
			        default : {
			        	if ( $state.current.name == $scope.REDE_APOIO_LIST_STATE )
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
				
				$scope.model.redeApoio.entity = new RedeApoio();//Limpa o formulário
				
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
		        
		        redeApoioService.findById( id, {
		            callback : function(result) {	   
		            	$scope.model.redeApoio.entity = result;
		            	$scope.listUsersByRedeApoio();
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
		        
		        if ( paginate ) $scope.model.redeApoio.page.pageable.page++;
		        
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
		
		            redeApoioService.disableRedeApoio( $scope.model.redeApoio.entity.id, {
			            callback : function(result) {	   
			            	
			            	$state.go($scope.REDE_APOIO_LIST_STATE);
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
			$scope.enableUser = function(){
				
				var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja ativar este registro?')
	            .content('')
	            .ok('Sim')
	            .cancel('Cancelar');

		        $mdDialog.show(confirm).then(function (result) {
		            console.log(result);
		
		            accountService.enableUser( $scope.model.redeApoio.entity.id  , {
		                callback : function(result) {
		                    $scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi ativado com sucesso!" );
		                    $state.go($scope.REDE_APOIO_LIST_STATE);
		                    
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
				
				redeApoioService.listByFilters(  $scope.model.redeApoio.filters.terms.toString(), $scope.model.redeApoio.filters.ativo, $scope.model.redeApoio.page.pageable, {
	                callback : function(result) {
	                	$scope.totalPagesRedeApoio = result.totalPages;
	                	$scope.model.redeApoio.page = {//PageImpl
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
				
				planoAtendimentoService.listPlanoAtendimentoFamiliarByFilters(  $scope.model.redeApoio.filters.terms.toString(), $scope.model.redeApoio.filters.ativo, $scope.model.redeApoio.page.pageable, {
	                callback : function(result) {
	                	
	                	$scope.model.redeApoio.page.content = result.content; 
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
			$scope.insertRedeApoio = function()
			{
				$scope.model.redeApoio.form.$submitted = true;
				if ($scope.model.redeApoio.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				redeApoioService.insertRedeApoio(  $scope.model.redeApoio.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.redeApoio.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
	                	$state.go($scope.REDE_APOIO_EDIT_STATE, {id: result.id}, {reload: true });
	                	$scope.$apply();
	                	
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
				
				
			}
			
			$scope.updateRedeApoio = function()
			{
				$scope.model.redeApoio.form.$submitted = true;
				if ($scope.model.redeApoio.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				redeApoioService.updateRedeApoio(  $scope.model.redeApoio.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.redeApoio.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
	                	$state.go(REDE_APOIO_EDIT_STATE)
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
			$scope.onUserPaginationChange = function(paginate) {
	        	if (paginate) {
	        		$scope.model.redeApoio.page.pageable.page++;
	        	} else {
	        		$scope.model.redeApoio.page.pageable.page--;
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
			
			$scope.selectPais = function(pais)
			{
				$scope.pais = pais;
			}
			
			
			/**
			 * 
			 */
			
			$scope.openChangePasswordHandler = function( id )
			{
				$mdDialog.show({
				      controller: $scope.changePasswordController(id),
				      templateUrl: './modules/home/views/user/popup/change-password-modal.html',			      
				      scope: $scope.$new(),
					})
				    .then(function(result) {
				 });
			}
			
			$scope.changePasswordController = function(id){
		    	
		    	$scope.form = null;
		    	$scope.modal = {
		    			newPassword:null,
		    			confirmNewPassword: null
		    	}
		    	  
		    	$scope.hide = function (result) {
		    		$mdDialog.hide(result);
		  	    };
		  	    
		  	    $scope.cancel = function () {
			        $mdDialog.cancel();
			    };
		  	    
		  	    $scope.changePassword = function()
		  	    {
		  	    	
		  	    	if ( !$scope.modal.newPassword || !$scope.modal.confirmNewPassword )
		  	    	{
		  	    		$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Preencha os campos obrigatórios" );
		  	    		return;
		  	    	}
		  	    	
		  	    	if ( $scope.modal.newPassword != $scope.modal.confirmNewPassword )
		  	    	{
		  	    		$scope.showMessage( $scope.SUCCESS_MESSAGE,  "A senha e confirmação precisam ser iguais" );
		  	    		return;
		  	    	}
		  	    	
		  	    	accountService.changePassword(  id, $scope.modal.newPassword, $scope.modal.confirmNewPassword, {
		                callback : function(result) {
		                	
		                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "A senha foi alterada com sucesso!" );
		                	$scope.hide();
		                	$scope.$apply();
		                	
		                },
		                errorHandler : function(message, exception) {
		                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
		                    $scope.$apply();
		                }
		            });
		  	    	
		  	    }
		  	    
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
			
			/**
			 * 
			 */
			$scope.disableUser = function(){
				
				accountService.disableUser( id, {
		            callback : function(result) {	   
		            	
		            	$state.go($scope.REDE_APOIO_LIST_STATE);
		            	
		            	$scope.$apply();
		            },
		            errorHandler : function(message, exception) {
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
		                $scope.$apply();
		            }
		        });
				
			}
			
			$scope.listUsersByRedeApoio = function()
			{
				redeApoioService.listUsersByRedeApoio( $scope.model.redeApoio.entity.id, $scope.model.user.page.pageable, {
		            callback : function(result) {	   
		            	
		            	$scope.totalPagesUser = result.totalPages;
	                	$scope.model.user.page = {//PageImpl
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
			
			$scope.listAllRedeApoio = function()
			{
				var redeApoio = new RedeApoio();
				redeApoio.nome = "CREA";
				$scope.allRedeApoio = [redeApoio];
			}
			
			/*-------------------------------------------------------------------
		     * 		 				 	POST CONSTRUCT
		     *-------------------------------------------------------------------*/
});
}(window.angular));