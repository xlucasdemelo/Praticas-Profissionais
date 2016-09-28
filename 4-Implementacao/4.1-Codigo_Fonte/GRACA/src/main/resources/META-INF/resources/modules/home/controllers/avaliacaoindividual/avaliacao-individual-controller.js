(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('AvaliacaoIndividualController', function( $scope, $state, $importService, $mdToast, $mdDialog ) {
		
	    /**
	     * Serviços importados do DWR
	     */
		$importService("avaliacaoService");
		$importService("questionarioService");
		
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
		$scope.AVALIACAO_INDIVIDUAL_LIST_STATE = "avaliacao-individual.list";
		/**
		 * Representa o estado para a criação de registros.
		 */
		$scope.AVALIACAO_INDIVIDUAL_ADD_STATE = "avaliacao-individual.add";
		/**
		 * Representa o estado para a edição de registros.
		 */
		$scope.AVALIACAO_INDIVIDUAL_EDIT_STATE = "avaliacao-individual.edit";
		/**
		 * Representa o estado de detalhe de um registro.
		 */
		$scope.AVALIACAO_INDIVIDUAL_DETAIL_STATE = "avaliacao-individual.detail";
		
		
		//----FORM MODEL
		
		$scope.toAprovacao = false;
		
		/**
		 * Contém o estados dos filtros da tela
		 * Contém estado da paginação inicial contendo sorting
	     *
		 */
		$scope.model = {
			query:{
				order : "",
			},
			
			avaliacaoIndividual: {
				form: null,
				entity: new AvaliacaoIndividual(),
				
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
			
			resposta: {
				entity: new Resposta(),
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
			
			/**
			 * 
			 */
			questao: {
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
			
			questionario: {
				entity: new Questionario(),
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
			
			configuracao: {
				entity: new ConfiguracaoAvaliacaoIndividual(),
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
			
			versao: {
				entity: new VersaoQuestionario(),
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
				case $scope.AVALIACAO_INDIVIDUAL_LIST_STATE: {
					$scope.changeToList( false );
					break;
				}
				case $scope.AVALIACAO_INDIVIDUAL_DETAIL_STATE: {
					$scope.changeToDetail( $state.params.id );
					break;
				}
		        case $scope.AVALIACAO_INDIVIDUAL_ADD_STATE: {
		        	$scope.changeToAdd();
		        	break;
		        }
		        case $scope.AVALIACAO_INDIVIDUAL_EDIT_STATE: {
		        	$scope.changeToEdit( $state.params.id );
		        	break;
		        }
		        default : {
		        	if ( $state.current.name == $scope.AVALIACAO_INDIVIDUAL_LIST_STATE )
		        		$state.go( $scope.AVALIACAO_INDIVIDUAL_LIST_STATE );
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
			$scope.model.avaliacaoIndividual.entity = new AvaliacaoIndividual();//Limpa o formulário
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
	        
	        avaliacaoService.findAvaliacaoById( id, {
	            callback : function(result) {	   
	            	$scope.model.avaliacaoIndividual.entity = result;
	            	$scope.listRespostasByAvaliacao();
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
	    $scope.findLastVersaoByQuestionario = function(id)
	    {
	    	questionarioService.findLastVersaoByQuestionario( id, {
	            callback : function(result) {	   
	            	$scope.model.versao.entity = result;
	            	$scope.$apply();
	            },
	            errorHandler : function(message, exception) {
	            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                $scope.$apply();
	            }
	        });
	    }
	    
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
	        
	        if ( paginate ) $scope.model.avaliacaoIndividual.page.pageable.page++;
	        
	        $scope.findConfiguracao();
	        
	        $scope.listAvaliacoesByFilters();
	    };
	    
	    /*-------------------------------------------------------------------
	     * 		 				 PRIVATE BEHAVIORS
	     *-------------------------------------------------------------------*/
		
	    /**
	     * 
	     */
		$scope.listAvaliacoesByFilters = function(){
			
			avaliacaoService.listAvaliacoesByFilters(  $scope.model.avaliacaoIndividual.filters.terms.toString(), $scope.model.avaliacaoIndividual.page.pageable, {
                callback : function(result) {
                	$scope.totalPagesAvaliacao = result.totalPages;
                	$scope.model.avaliacaoIndividual.page = {//PageImpl
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
		$scope.listQuestoesByVersao = function(){
			questionarioService.listQuestoesByVersao(  $scope.model.avaliacaoIndividual.entity.id, $scope.model.avaliacaoIndividual.page.pageable, {
				callback : function(result) {
					$scope.totalPagesAvaliacao = result.totalPages;
					$scope.model.questao.page = {//PageImpl
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
		$scope.listRespostasByAvaliacao = function(){
			avaliacaoService.listRespostasByAvaliacao( $scope.model.avaliacaoIndividual.entity.id, null, {
				callback : function(result) {
					$scope.model.resposta.page = {//PageImpl
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
			$scope.model.avaliacaoIndividual.entity.id ? $scope.updateQuestionario() : $scope.insertQuestionario();
		};
        
		$scope.salvarResposta = function()
		{
			$scope.toAprovacao = false;
			avaliacaoService.salvarRespostas( $scope.model.resposta.page.content, {
				callback : function(result) {
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
		$scope.insertAvaliacaoIndividual = function() {
			$scope.model.avaliacaoIndividual.form.$submitted = true;
			
			if ($scope.model.avaliacaoIndividual.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
			
			avaliacaoService.insertAvaliacaoIndividual(  $scope.model.avaliacaoIndividual.entity, {
                callback : function(result) {
                	$state.go( $scope.AVALIACAO_INDIVIDUAL_EDIT_STATE, {id: result.id}, {reload: true } )
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
		$scope.updateQuestionario = function() {
			$scope.model.avaliacaoIndividual.form.$submitted = true;
			
			if ($scope.model.avaliacaoIndividual.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
			
			questionarioService.updateQuestionario(  $scope.model.avaliacaoIndividual.entity, {
				callback : function(result) {
					$scope.model.avaliacaoIndividual.entity = result;
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
		$scope.enviarParaAprovacao = function (entity) {
			
			$scope.toAprovacao = true;
			
			var errado = false;
			
			angular.forEach($scope.model.resposta.page.content, function(resposta, key) {
				if (!resposta.resposta)
				{
					$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
					errado = true;
				}
			});
			
			if (errado)
				return;
			
//			if ($scope.model.avaliacaoIndividual.form.$invalid ){
//				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
//				return;
//			}
			
	        var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja enviar esta avaliação para aprovação ?')
	            .content('Não será possível mais altera-la')
	            .ok('Sim')
	            .cancel('Cancelar');
	        
	        $mdDialog.show(confirm).then(function (result) {
	            console.log(result);
	
	            avaliacaoService.enviarParaAvaliacao( $scope.model.avaliacaoIndividual.entity.id, {
		            callback : function(result) {	   
		            	$scope.model.avaliacaoIndividual.entity = result;
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  "Registro foi enviado para avaliação com sucesso" );
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
	    $scope.aprovarAvaliacao = function() {
			
	        var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja aprovar esta avaliação ?')
	            .ok('Sim')
	            .cancel('Cancelar');
	
	        $mdDialog.show(confirm).then(function (result) {
	            console.log(result);
	
	            avaliacaoService.aceitarAvaliacao( $scope.model.avaliacaoIndividual.entity.id, {
		            callback : function(result) {	   
		            	$scope.model.avaliacaoIndividual.entity = result;
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  "Registro foi aprovado com sucesso" );
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
	    $scope.recusarAvaliacao = function() {
			
	        var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja recusar esta avaliação ?')
	            .ok('Sim')
	            .cancel('Cancelar');
	
	        $mdDialog.show(confirm).then(function (result) {
	            console.log(result);
	
	            avaliacaoService.rejeitarAvaliacao( $scope.model.avaliacaoIndividual.entity.id, {
		            callback : function(result) {	   
		            	$scope.model.avaliacaoIndividual.entity = result;
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  "Registro foi recusado com sucesso" );
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
	    $scope.aumentarVersaoQuestionario = function() {
			
	        var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja aumentar a versão deste questionário ?')
	            .content('')
	            .ok('Sim')
	            .cancel('Cancelar');
	
	        $mdDialog.show(confirm).then(function (result) {
	            console.log(result);
	
	            questionarioService.aumentarVersao( $scope.model.avaliacaoIndividual.entity.id, {
		            callback : function(result) {	   
		            	$scope.model.versao.entity = result;
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  "A versão foi alterada com sucesso" );
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
		$scope.openInsertUpdateQuestaoHandler = function( questao ) {
			$mdDialog.show({
			      controller: function( $scope, questao ) {
			    	  	$scope.model.questao.entity = questao ? questao : new Questao();
			    	  	
						$scope.cancel = function() {
							$mdDialog.cancel();
						};
						
			    	    /**
				  		 * 
				  		 */
				  		$scope.insertQuestao = function() {
				  			$scope.model.questao.form.$submitted = true;
				  			
				  			if ($scope.model.questao.form.$invalid ){
				  				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				  				return;
				  			}
				  			
				  			$scope.model.questao.entity.versaoQuestionario = new VersaoQuestionario();
				  			$scope.model.questao.entity.versaoQuestionario.questionario = new Questionario();
				  			$scope.model.questao.entity.versaoQuestionario.questionario.id = $scope.model.avaliacaoIndividual.entity.id;
				  			
				  			questionarioService.insertQuestao(  $scope.model.questao.entity, {
				                  callback : function(result) {
				                  	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
				                  	$mdDialog.hide( result );
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
				  		$scope.updateQuestao = function() {
				  			$scope.model.questao.form.$submitted = true;
				  			
				  			if ($scope.model.questao.form.$invalid ){
				  				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				  				return;
				  			}
				  			
				  			$scope.model.questao.entity.versaoQuestionario = new VersaoQuestionario();
				  			$scope.model.questao.entity.versaoQuestionario.questionario = new Questionario();
				  			$scope.model.questao.entity.versaoQuestionario.questionario.id = $scope.model.avaliacaoIndividual.entity.id;
				  			
				  			questionarioService.updateQuestao(  $scope.model.questao.entity, {
				  				callback : function(result) {
				  					$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi alterado com sucesso!" );
				  					$mdDialog.hide( result );
				  				},
				  				errorHandler : function(message, exception) {
				  					$scope.showMessage( $scope.ERROR_MESSAGE,  message );
				  					$scope.$apply();
				  				}
				  			});
				  		};
				  		
				  		//POST CONSTRUCT
				  		
				  		$scope.listAllTiposQuestao();
			      },
			      templateUrl: './modules/home/views/questionario/popup/adicionar-questao-popup.html',			      
			      resolve: {
			    	  questao: function() {
			    		  return angular.copy(questao);
			    	  }
			      },
			      scope: $scope.$new(),
				})
			    .then(function( result ) {
			    	 $scope.listQuestoesByVersao();
			 });
		};
		
		/**
		 * 
		 */
		$scope.openConfiguracaoHandler = function()
		{
			$mdDialog.show({
			      controller: $scope.configuracaoController(),
			      templateUrl: './modules/home/views/avaliacaoindividual/popup/configuracao-modal.html',			      
			      scope: $scope.$new(),
				})
			    .then(function(result) {
			 });
		}
		
		/**
		 * 
		 */
		$scope.configuracaoController = function(){
	    	
			$scope.model.configuracao = {};
			$scope.model.configuracao.entity = new ConfiguracaoAvaliacaoIndividual();
			
	    	$scope.hide = function (result) {
	    		$mdDialog.hide(result);
	  	    };
	  	    
	  	    $scope.cancel = function () {
		        $mdDialog.cancel();
		    };
	  	    
	  	    $scope.insertConfiguracao = function()
	  	    {
	  	    	var a = $scope.model.configuracao.entity.questionario.id;
	  	    	$scope.model.configuracao.entity.questionario = new Questionario();
	  	    	$scope.model.configuracao.entity.questionario.id = a;
	  	    	
	  	    	avaliacaoService.insertConfiguracao(  $scope.model.configuracao.entity, {
	                callback : function(result) {
	                	
	                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Configurado com sucesso" );
	                	$scope.hide();
	                	$scope.$apply();
	                	
	                },
	                errorHandler : function(message, exception) {
	                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
	                    $scope.$apply();
	                }
	            });
	  	    	
	  	    }
	  	    
	  	    $scope.listQuestionariosByFilters = function()
	  	    {
	  	    	questionarioService.listQuestionariosAprovados(  {
	                callback : function(result) {
	                	$scope.totalPagesAvaliacao = result.totalPages;
	                	$scope.model.questionario.page = {//PageImpl
	    						content : result,
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
	  	    
	  	    $scope.listQuestionariosByFilters();
	  	    
	      }
		
  	    /**
  	     * 
  	     */
  	    $scope.openSelectCrianca = function()
		{
			
			$mdDialog.show({
			      controller: "SelecionarCriancaPopup",
			      templateUrl: './modules/home/views/avaliacaoindividual/popup/selecionar-crianca-popup.html',			      
			      scope: $scope.$new(),
				})
			    .then(function(result) {
			    	$scope.model.avaliacaoIndividual.entity.crianca = result;
			 });
			
		}
		
		/**
		 * 
		 */
		$scope.onQuestionarioPaginationChange = function( paginate ) {
        	if (paginate) {
        		$scope.model.avaliacaoIndividual.page.pageable.page++;
        	} else {
        		$scope.model.avaliacaoIndividual.page.pageable.page--;
        	}
        		
        	$scope.listAvaliacoesByFilters();
        };
		
		
		/**
		 * 
		 */
		$scope.openMaisOpcoes = function(){
			$scope.moreFilters = !$scope.moreFilters;
		};
		
		/**
		 * 
		 */
		$scope.listAllTiposQuestao = function(){
			questionarioService.listAllTiposQuestao(   {
                callback : function(result) {
                	$scope.allTiposQuestao = result;
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
		$scope.findConfiguracao = function(){
			
			avaliacaoService.findConfiguracao(   {
                callback : function(result) {
                	$scope.model.configuracao.entity = result;
                	$scope.$apply();
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            })
			
		}
		
		/*-------------------------------------------------------------------
	     * 		 				 	POST CONSTRUCT
	     *-------------------------------------------------------------------*/
		
});
}(window.angular));