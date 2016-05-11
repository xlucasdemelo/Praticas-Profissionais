(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('FamiliaController', function( $scope, $state, $importService, $mdToast, $mdDialog ) {
		
	    /**
	     * Serviços importados do DWR
	     */
		$importService("familiaService");
		
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
		$scope.LIST_STATE = "familia.list";
		/**
		 * Representa o estado para a criação de registros.
		 */
		$scope.ADD_STATE = "familia.add";
		/**
		 * Representa o estado para a edição de registros.
		 */
		$scope.EDIT_STATE = "familia.edit";
		/**
		 * Representa o estado de detalhe de um registro.
		 */
		$scope.DETAIL_STATE = "familia.detail";
		
		/**
		 * 
		 */
		$scope.allTiposImovel = [];
		
		/**
		 * 
		 */
		$scope.allTiposMoradia = [];
		
		
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
			
			familia: {
				form: null,
				entity: new Familia(),
				
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
				case $scope.LIST_STATE: {
					$scope.changeToList( false );
					break;
				}
				case $scope.DETAIL_STATE: {
					$scope.changeToDetail( $state.params.id );
					break;
				}
		        case $scope.ADD_STATE: {
		        	$scope.changeToAdd();
		        	break;
		        }
		        case $scope.EDIT_STATE: {
		        	$scope.changeToEdit( $state.params.id );
		        	break;
		        }
		        default : {
		        	$state.go( $scope.LIST_STATE );
		        }
			}
	    });
		
	    /*-------------------------------------------------------------------
	     * 		 				 	  HANDLERS
	     *-------------------------------------------------------------------*/
	    
	    /**
	     * Handler para que realiza os procedimentos iniciais (prepara o estado)
	     * para a tela de inserção e após isso, muda o estado para $scope.ADD_STATE.
	     *  
	     * @see $scope.ADD_STATE
	     */
		$scope.changeToAdd = function() {
			console.debug("changeToAdd");
			
			$scope.model.familia.entity = new Familia();//Limpa o formulário
			
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
	        $scope.model.fornecedor.entity = null;
	        
	        fornecedorService.findFornecedorById( id, {
	            callback : function(result) {	   
	            	$scope.model.cidade.searchText = result.cidade.nome;
	            	$scope.model.estado.searchText = result.cidade.estado.nome;
	            	$scope.model.fornecedor.entity = result;
	            	
	            	$scope.listContratosByFornecedor();
	            	$scope.$apply();
	            },
	            errorHandler : function(message, exception) {
	            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	            	$state.go( $scope.LIST_STATE );
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
	        
	        if ( paginate ) $scope.model.familia.page.pageable.page++;
	        
	        $scope.listFamiliasByFilters();
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
	            	$state.go( $scope.LIST_STATE );
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
	
	            fornecedorService.removeFornecedor( ($.isArray(entity) ? entity : [entity] ) , {
	                callback : function(result) {
	                	$scope.moreFilters = false;
	                    $scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi excluído com sucesso!" );
	                    $scope.listFornecedoresByFiltersHandler();
	                    $state.go($scope.LIST_STATE);
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
         *  Ao Trocar de Página
         */
        $scope.onFornecedorPaginationChange = function(paginate) {
        	if (paginate) {
        		$scope.model.fornecedor.page.pageable.page++;
        	} else {
        		$scope.model.fornecedor.page.pageable.page--;
        	}
        		
        	$scope.listFornecedoresByFiltersHandler();
        };
	    
	    /*-------------------------------------------------------------------
	     * 		 				 PRIVATE BEHAVIORS
	     *-------------------------------------------------------------------*/
		
		$scope.listFamiliasByFilters = function(){
			familiaService.listFamiliasByFilters(  $scope.model.familia.filters.terms.toString(), null, {
                callback : function(result) {
                	
                	$scope.model.familia.page = {//PageImpl
    						content : result.content,
    						total   : result.total,
    							pageable : {//PageRequest
    								page : result.number,
    								size : result.totalPages,
    									sort : {//Sort
    									direction: 'ASC', properties: ['id']
    									},
    							}
    						};
                	
                	$scope.$apply();
                	
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            });
		}
        
		$scope.insertFamilia = function()
		{
			$scope.model.familia.form.$submitted = true;
			if ($scope.model.familia.form.$invalid ){
				alert("Preenche");
				return;
			}
			
			$scope.model.familia.entity.endereco = $scope.model.endereco.entity; 
			$scope.model.familia.entity.tipoImovel =  "CASA";
			
			familiaService.insertFamilia(  $scope.model.familia.entity, {
                callback : function(result) {
                	
                	$scope.model.familia.entity = result;
                	
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
		$scope.listAllTiposImovel();
		$scope.listAllTiposMoradia();
});
}(window.angular));