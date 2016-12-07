(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('QuestionarioRespostaController', function( $scope, $state, $importService, $mdToast, $mdDialog ) {
		
	    /**
	     * Serviços importados do DWR
	     */
		$importService("questionarioService");
		
		/**
		 * 
		 */
		
	    /*-------------------------------------------------------------------
	     * 		 				 	ATTRIBUTES
	     *-------------------------------------------------------------------*/
		$scope.toFinalizado = false;
		
		 //----STATES
		/**
		 * Representa o estado de listagem de registros.
		 */
		$scope.QUESTIONARIO_RESPOSTA_LIST_STATE = "questionario-resposta.list";
		/**
		 * Representa o estado para a criação de registros.
		 */
		$scope.QUESTIONARIO_RESPOSTA_ADD_STATE = "questionario-resposta.add";
		/**
		 * Representa o estado para a edição de registros.
		 */
		$scope.QUESTIONARIO_RESPOSTA_EDIT_STATE = "questionario-resposta.edit";
		/**
		 * Representa o estado de detalhe de um registro.
		 */
		$scope.QUESTIONARIO_RESPOSTA_DETAIL_STATE = "questionario-resposta.detail";
		
		
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
			
			questionarioResposta: {
				form: null,
				entity: new QuestionarioResposta(),
				
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
			
			questionario: {
				form: null,
				entity: new Questionario(),
				
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
				case $scope.QUESTIONARIO_RESPOSTA_LIST_STATE: {
					$scope.changeToList( false );
					break;
				}
				case $scope.QUESTIONARIO_RESPOSTA_DETAIL_STATE: {
					$scope.changeToDetail( $state.params.id );
					break;
				}
		        case $scope.QUESTIONARIO_RESPOSTA_ADD_STATE: {
		        	$scope.changeToAdd();
		        	break;
		        }
		        case $scope.QUESTIONARIO_RESPOSTA_EDIT_STATE: {
		        	$scope.changeToEdit( $state.params.id );
		        	break;
		        }
		        default : {
		        	if ( $state.current.name == $scope.QUESTIONARIO_RESPOSTA_LIST_STATE )
		        		$state.go( $scope.QUESTIONARIO_RESPOSTA_LIST_STATE );
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
			$scope.model.resposta.page.content = [];
			$scope.model.questionarioResposta.entity = new QuestionarioResposta();//Limpa o formulário
			$scope.model.versao.entity = new VersaoQuestionario();
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
	        
	        questionarioService.findQuestionarioRespostaById( id, {
	            callback : function(result) {	   
	            	$scope.model.questionarioResposta.entity = result;
	            	$scope.listRespostas();
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
	        
	        if ( paginate ) $scope.model.questionarioResposta.page.pageable.page++;
	        
	        $scope.listQuestionariosByFilters();
	    };
	    
	    /*-------------------------------------------------------------------
	     * 		 				 PRIVATE BEHAVIORS
	     *-------------------------------------------------------------------*/
		
	    /**
	     * 
	     */
		$scope.listQuestionariosByFilters = function(){
			
			questionarioService.listQuestionariosRespostaByUserAndFilters(  $scope.model.questionarioResposta.filters.terms.toString(), $scope.model.questionarioResposta.page.pageable, {
                callback : function(result) {
                	$scope.totalPagesQuestionarioResposta = result.totalPages;
                	$scope.model.questionarioResposta.page = {//PageImpl
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
		$scope.listRespostas = function(){
			questionarioService.listRespostasByQuestionarioResposta( $scope.model.questionarioResposta.entity.id, $scope.model.questionarioResposta.page.pageable, {
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
			$scope.model.questionarioResposta.entity.id ? $scope.updateQuestionario() : $scope.insertQuestionario();
		};
        
		/**
		 * 
		 */
		$scope.insertQuestionarioResposta = function() {
			$scope.model.questionarioResposta.form.$submitted = true;
			
//			if ($scope.model.questionarioResposta.form.$invalid ){
//				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
//				return;
//			}

			questionarioService.insertQuestionarioResposta(  $scope.model.questionarioResposta.entity, {
                callback : function(result) {
                	$state.go( $scope.QUESTIONARIO_RESPOSTA_EDIT_STATE, {id: result.id}, {reload: true } )
                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            });
		};
		
		$scope.updateQuestionario = function() {
			$scope.model.questionarioResposta.form.$submitted = true;
			
			if ($scope.model.questionarioResposta.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
			
			questionarioService.updateQuestionario(  $scope.model.questionarioResposta.entity, {
				callback : function(result) {
					$scope.model.questionarioResposta.entity = result;
					$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi cadastrado com sucesso!" );
					$scope.$apply();
					
				},
				errorHandler : function(message, exception) {
					$scope.showMessage( $scope.ERROR_MESSAGE,  message );
					$scope.$apply();
				}
			});
		};
		
		$scope.removeQuestionario = function()
		{
			var confirm = $mdDialog.confirm()
            .title('Tem certeza que deseja enviar este questionário para aprovação ?')
            .content('Não será possível mais altera-lo')
            .ok('Sim')
            .cancel('Cancelar');

        $mdDialog.show(confirm).then(function (result) {
        	
        
			questionarioService.removeQuestionarioResposta(  $scope.model.questionarioResposta.entity.id, {
				callback : function(result) {
					$scope.showMessage( $scope.SUCCESS_MESSAGE,  "O registro foi excluído com sucesso!" );
					$state.go( $scope.QUESTIONARIO_RESPOSTA_LIST_STATE );
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
		$scope.enviarParaAprovacao = function (entity) {
			
	        var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja enviar este questionário para aprovação ?')
	            .content('Não será possível mais altera-lo')
	            .ok('Sim')
	            .cancel('Cancelar');
	
	        $mdDialog.show(confirm).then(function (result) {
	            console.log(result);
	
	            questionarioService.enviarVersaoParaAprovacao( $scope.model.questionarioResposta.entity.id, {
		            callback : function(result) {	   
		            	$scope.model.versao.entity = result;
		            	$scope.showMessage( $scope.ERROR_MESSAGE,  "Registro foi enviado para aprovação com sucesso" );
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
	    $scope.aprovarQuestionario = function() {
			
	        var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja aprovar este questionário ?')
	            .content(' Este questionário ficará disponível para ser utilizado ')
	            .ok('Sim')
	            .cancel('Cancelar');
	
	        $mdDialog.show(confirm).then(function (result) {
	            console.log(result);
	
	            questionarioService.aprovarVersao( $scope.model.questionarioResposta.entity.id, {
		            callback : function(result) {	   
		            	$scope.model.versao.entity = result;
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
	    $scope.recusarQuestionario = function() {
			
	        var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja recusar este questionário ?')
	            .content(' Este questionário ficará disponível para que seja reenviado para avaliação ')
	            .ok('Sim')
	            .cancel('Cancelar');
	
	        $mdDialog.show(confirm).then(function (result) {
	            console.log(result);
	
	            questionarioService.rejeitarVersao( $scope.model.questionarioResposta.entity.id, {
		            callback : function(result) {	   
		            	$scope.model.versao.entity = result;
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
	
	            questionarioService.aumentarVersao( $scope.model.questionarioResposta.entity.id, {
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
				  			$scope.model.questao.entity.versaoQuestionario.questionario.id = $scope.model.questionarioResposta.entity.id;
				  			
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
				  			$scope.model.questao.entity.versaoQuestionario.questionario.id = $scope.model.questionarioResposta.entity.id;
				  			
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
		
		$scope.openSelectQuestionario = function()
		{
			
			$mdDialog.show({
				controller: "SelecionarQuestionarioControllerPopup",
				templateUrl: './modules/home/views/questionario/popup/selecionar-questionario-modal.html',
				parent: angular.element(document.body),
				clickOutsideToClose:true,
				fullscreen: true,
				scope: $scope.$new()
		    })
		    .then(function(questionario) {
		    	
		    	$scope.findLastVersaoAprovadaByQuestionario(questionario.id);
		    	
		    }, function() {
		    	
		    });
			
		}
		
		$scope.responderQuestoes = function()
		{
			questionarioService.responderResposta( $scope.model.resposta.page.content,  {
                callback : function(result) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  "Questionário salvo com suecesso" );
//                	$scope.listRespostas();
                	$scope.$apply();
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            })
		}
		
		$scope.changeQuestionarioRespostaToFinalizado = function()
		{
			$scope.toFinalizado = true;
			
			 var confirm = $mdDialog.confirm()
	            .title('Tem certeza que deseja finalizar este questionário ?')
	            .content('Não será possível mais altera-lo')
	            .ok('Sim')
	            .cancel('Cancelar');
			
			$mdDialog.show(confirm).then(function (result) {
				 
			questionarioService.changeQuestionarioRespostaToFinalizado( $scope.model.questionarioResposta.entity.id, {
                callback : function(result) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  "Questionário finalizado com suecesso" );
                	$scope.model.questionarioResposta.entity = result;
                	$scope.$apply();
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            });
			
			});
		}
		
		$scope.findLastVersaoAprovadaByQuestionario = function(id)
		{
			
			questionarioService.findLastVersaoAprovadaByQuestionarrio( id,  {
                callback : function(result) {
                	$scope.model.questionarioResposta.entity.versao = result;
                	$scope.insertQuestionarioResposta();
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
		$scope.onQuestionarioPaginationChange = function( paginate ) {
        	if (paginate) {
        		$scope.model.questionarioResposta.page.pageable.page++;
        	} else {
        		$scope.model.questionarioResposta.page.pageable.page--;
        	}
        		
        	$scope.listQuestionariosByFilters();
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
		
		/*-------------------------------------------------------------------
	     * 		 				 	POST CONSTRUCT
	     *-------------------------------------------------------------------*/
		
});
}(window.angular));