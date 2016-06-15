(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('CriancaController', function( $scope, $state, $importService, $mdToast, $mdDialog ) {
		
	    /**
	     * Serviços importados do DWR
	     */
		$importService("familiaService");
		$importService("criancaService");
		$importService("integranteFamiliarService");
		$importService("enderecoService");
		
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
		$scope.LIST_STATE = "crianca.list";
		/**
		 * Representa o estado para a criação de registros.
		 */
		$scope.ADD_STATE = "crianca.add";
		/**
		 * Representa o estado para a edição de registros.
		 */
		$scope.EDIT_STATE = "crianca.edit";
		/**
		 * Representa o estado de detalhe de um registro.
		 */
		$scope.DETAIL_STATE = "crianca.detail";
		
		/**
		 * 
		 */
		$scope.allTiposImovel = [];
		
		/**
		 * 
		 */
		$scope.allSexos = [];
		
		/**
		 * 
		 */
		$scope.allTiposDocumentos = [];
		
		/**
		 * 
		 */
		$scope.allGrausEscolaridade = [];
		
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
			
			crianca: {
				form: null,
				entity: new Crianca(),
				
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
			
			documentoCrianca: {
				form: null,
				entity: new DocumentoCrianca(),
				
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
			
			$scope.changeToEdit();
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
	        
	        id = 1;
	        
	        criancaService.findCriancaById( id, {
	            callback : function(result) {	   
	            	$scope.model.crianca.entity = result;
	            	
	            	$scope.listDocumentosByCrianca();
	            	
	            	$scope.model.pais.selectedItem = result.endereco.cidade.estado.pais;
	            	$scope.model.estado.selectedItem = result.endereco.cidade.estado;
	            	$scope.model.cidade.selectedItem = result.endereco.cidade;
	            	
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
	
	            familiaService.disableFamilia( $scope.model.familia.entity  , {
	                callback : function(result) {
	                    $scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi excluído com sucesso!" );
	                    $state.go($scope.LIST_STATE);
	                    $scope.listFamiliasByFilters();
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
	                    $state.go($scope.LIST_STATE);
	                    $scope.listFamiliasByFilters();
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
		
        /**
         * 
         */
		$scope.listFamiliasByFilters = function(){
			
			familiaService.listFamiliasByFilters(  $scope.model.familia.filters.terms.toString(), $scope.model.familia.page.pageable, {
                callback : function(result) {
                	
                	$scope.model.familia.page.content = result.content; 
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
		$scope.insertCrianca = function()
		{
			$scope.model.crianca.form.$submitted = true;
			if ($scope.model.crianca.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
			
			$scope.model.crianca.entity.endereco.cidade = $scope.model.cidade.selectedItem ;
			$scope.model.crianca.entity.endereco.cidade.estado = $scope.model.estado.selectedItem ;
			$scope.model.crianca.entity.endereco.cidade.estado.pais = $scope.model.pais.selectedItem ;
			
			criancaService.insertCrianca(  $scope.model.crianca.entity, {
                callback : function(result) {
                	
                	$scope.model.crianca.entity = result;
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
		$scope.insertDocumentoCrianca = function() {
	    	
			$scope.model.documentoCrianca.entity.crianca = $scope.model.crianca.entity;
			
			criancaService.insertDocumentoCrianca( $scope.model.documentoCrianca.entity, {
                callback : function(result) {
                	
                	$scope.listDocumentosByCrianca();
                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Documento inserido com sucesso" );
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
		$scope.listDocumentosByCrianca = function(){
			criancaService.listDocumentosByCrianca( $scope.model.crianca.entity.id, $scope.model.documentoCrianca.page.pageable, {
                callback : function(result) {
                	
                	$scope.model.crianca.entity.documentosCrianca = result.content;
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
		$scope.listAllTiposDocumento = function(){
			integranteFamiliarService.listAllDocumentos( {
                callback : function(result) {
                	$scope.allTiposDocumentos = result;
                	
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
		$scope.listAllSexos = function(){
			integranteFamiliarService.listAllSexos( {
                callback : function(result) {
                	$scope.allSexos = result;
                	
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
		$scope.listIntegrantesFamiliaresByFamilia = function( id ){
			
			integranteFamiliarService.listIntegrantesByfamilia( id, $scope.model.integranteFamiliar.page.pageable, {
                callback : function(result) {
                	
                	$scope.model.integranteFamiliar.page.content = result.content;
                	
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
		$scope.listAllGrausEscolaridade = function(){
			integranteFamiliarService.listAllGrausEscolaridade( {
                callback : function(result) {
                	
                	$scope.allGrausEscolaridade = result;
                	
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
		$scope.listAllSexos();
		$scope.listAllGrausEscolaridade();
		$scope.listAllTiposDocumento();
		
});
}(window.angular));