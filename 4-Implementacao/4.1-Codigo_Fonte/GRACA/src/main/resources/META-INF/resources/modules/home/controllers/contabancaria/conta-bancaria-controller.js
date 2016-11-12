(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('ContaBancariaController', function( $scope, $state, $importService, $mdToast, $mdDialog, $mdExpansionPanel ) {
		
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
			$scope.CONTA_BANCARIA_LIST_STATE = "conta-bancaria.list";
			/**
			 * Representa o estado para a criação de registros.
			 */
			$scope.CONTA_BANCARIA_ADD_STATE = "conta-bancaria.add";
			/**
			 * Representa o estado para a edição de registros.
			 */
			$scope.CONTA_BANCARIA_EDIT_STATE = "conta-bancaria.edit";
			/**
			 * Representa o estado de detalhe de um registro.
			 */
			$scope.CONTA_BANCARIA_DETAIL_STATE = "conta-bancaria.detail";
			
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
				
				contaBancaria: {
					form: null,
					entity: new ContaBancaria(),
					
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
			$scope.totalPagesContaBancaria = null;
			
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
					case $scope.CONTA_BANCARIA_LIST_STATE: {
						$scope.changeToList( false );
						break;
					}
					case $scope.CONTA_BANCARIA_DETAIL_STATE: {
						$scope.changeToDetail( $state.params.id );
						break;
					}
			        case $scope.CONTA_BANCARIA_ADD_STATE: {
			        	$scope.changeToAdd();
			        	break;
			        }
			        case $scope.CONTA_BANCARIA_EDIT_STATE: {
			        	$scope.changeToEdit( $state.params.id );
			        	break;
			        }
			        default : {
			        		$state.go( $scope.CONTA_BANCARIA_LIST_STATE );
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
				
				$scope.model.contaBancaria.entity = new contaBancaria();//Limpa o formulário
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
		        
		        caixaService.findContaBancariaById( id, {
		            callback : function(result) {	   
		            	$scope.model.contaBancaria.entity = result;
		            	
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
		        
		        if ( paginate ) $scope.model.contaBancaria.page.pageable.page++;
		        
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
		
		        caixaService.findContaBancariaById( id, {
		            callback : function(result) {
		            	$scope.model.contaBancaria.entity = result;
		            	$state.current.breadCrumbs.push({name: $scope.model.contaBancaria.entity.razaoSocial});
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
		
		            caixaService.disableContaBancaria( $scope.model.contaBancaria.entity.id, {
			            callback : function(result) {	   
			            	
			            	$state.go($scope.CONTA_BANCARIA_LIST_STATE);
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
				
				caixaService.listContaBancariaByFilters(  $scope.model.contaBancaria.filters.terms.toString(), true, $scope.model.contaBancaria.page.pageable, {
	                callback : function(result) {
	                	$scope.totalPagesContaBancaria = result.totalPages;
	                	$scope.model.contaBancaria.page = {//PageImpl
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
			$scope.disableContaBancaria = function(){
				
				caixaService.disableContaBancaria( id, {
		            callback : function(result) {
		            	$state.go($scope.CONTA_BANCARIA_LIST_STATE);
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
			$scope.enableContaBancaria = function(){
				
				var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja ativar este registro?')
	            .content('')
	            .ok('Sim')
	            .cancel('Cancelar');

		        $mdDialog.show(confirm).then(function (result) {
		            console.log(result);
		
		            caixaService.enableContaBancaria( $scope.model.contaBancaria.entity.id  , {
		                callback : function(result) {
		                    $scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi ativado com sucesso!" );
		                    $state.go($scope.CONTA_BANCARIA_LIST_STATE);
		                    
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
			$scope.insertContaBancaria = function()
			{
				$scope.model.contaBancaria.form.$submitted = true;
				if ($scope.model.contaBancaria.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				
				$scope.model.contaBancaria.entity.responsavel = new Responsavel();
				$scope.model.contaBancaria.entity.responsavel.id = 1;
				
				caixaService.insertContaBancaria( $scope.model.contaBancaria.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.contaBancaria.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
	                	$state.go($scope.CONTA_BANCARIA_LIST_STATE)
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
			$scope.updateContaBancaria= function()
			{
				$scope.model.contaBancaria.form.$submitted = true;
				if ($scope.model.contaBancaria.form.$invalid ){
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					return;
				}
				
				caixaService.updateContaBancaria(  $scope.model.contaBancaria.entity, {
	                callback : function(result) {
	                	
	                	$scope.model.contaBancaria.entity = result;
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi alterado com sucesso!" );
	                	$state.go(CONTA_BANCARIA_EDIT_STATE)
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
			$scope.openSelecionarBancoPopup = function() {
		    	$mdDialog.show({
					controller: "SelecionarBancoControllerPopup",
					templateUrl: './modules/home/views/conta-bancaria/popup/selecionar-banco-popup.html',
					parent: angular.element(document.body),
					clickOutsideToClose:true,
					fullscreen: true,
					scope: $scope.$new()
			    })
			    .then(function(banco) {
			    	$scope.model.contaBancaria.entity.banco = banco;
			    }, function() {
			    	
			    });
		    };
			
			/**
			 * 
			 */
			$scope.onContaBancariaPaginationChange = function(paginate) {
	        	if (paginate) {
	        		$scope.model.contaBancaria.page.pageable.page++;
	        	} else {
	        		$scope.model.contaBancaria.page.pageable.page--;
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
		    	$scope.openDefaultConfirmDialog( $scope, $scope.excluirContaBancariaHandler, null );
			};
			
			
			/*-------------------------------------------------------------------
		     * 		 				 	POST CONSTRUCT
		     *-------------------------------------------------------------------*/
});
}(window.angular));