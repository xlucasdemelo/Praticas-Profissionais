(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('ProdutoReportController', function( $scope, $state, $importService, $mdToast, $mdDialog ) {
		
	    /**
	     * Serviços importados do DWR
	     */
		$importService("produtoService");
		
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
		$scope.PRODUTO_REPORT_ADQUIRIDOS_STATE = "relatorio-produto.adquiridos";
		
		
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
			
			relatorio:{
				produtosAdquiridos:{
					filters: {
					    terms: "",
					    ativo: true,
					    inativo: false,
					    dataInicio: null,
					    dataFim: null,
					    fornecedores: [],
					    produtos: []
					}
				}
				
			},
			
			produto: {
				form: null,
				entity: new Produto(),
				
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
			
			fornecedor: {
				form: null,
				entity: new Fornecedor(),
				
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
				case $scope.PRODUTO_REPORT_ADQUIRIDOS_STATE: {
					$scope.changeToAdquiridos( false );
					break;
				}
				case $scope.PRODUTO_DETAIL_STATE: {
					$scope.changeToDetail( $state.params.id );
					break;
				}
		        case $scope.PRODUTO_ADD_STATE: {
		        	$scope.changeToAdd();
		        	break;
		        }
		        case $scope.PRODUTO_EDIT_STATE: {
		        	$scope.changeToEdit( $state.params.id );
		        	break;
		        }
		        default : {
		        	if ( $state.current.name == $scope.PRODUTO_LIST_STATE )
		        		$state.go( $scope.PRODUTO_LIST_STATE );
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
			$scope.model.produto.entity = new Produto();//Limpa o formulário
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
	        
	        produtoService.findProdutoById( id, {
	            callback : function(result) {	   
	            	$scope.model.produto.entity = result;
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
	    $scope.changeToAdquiridos = function( paginate ) {
	        console.debug("changeToList");
	        
	        $scope.model.relatorio.produtosAdquiridos.filters = {
			    terms: "",
			    ativo: true,
			    inativo: false,
			    dataInicio: null,
			    dataFim: null,
			    fornecedores: [],
			    produtos: []
			}
	        
	    };
	    
	    /*-------------------------------------------------------------------
	     * 		 				 PRIVATE BEHAVIORS
	     *-------------------------------------------------------------------*/
	    
	    /**
	     * 
	     */
	    $scope.exportarProdutosAdquiridos = function()
	    {
	    	produtoService.gerarRelatorioProdutosAdquiridos( $scope.model.relatorio.produtosAdquiridos.filters.fornecedores, $scope.model.relatorio.produtosAdquiridos.filters.produtos, 
	    			$scope.model.relatorio.produtosAdquiridos.filters.dataInicio, $scope.model.relatorio.produtosAdquiridos.filters.dataFim, 
	    		{
 	            callback : function(result) {
 	            	dwr.engine.openInDownload( result );
 	                $scope.$apply();
 	            },
 	            errorHandler : function(message, exception) {
 	            	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
 	                $scope.$apply();
 	            }
 	        });
	    }
	    
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
		    	
		    	$scope.model.relatorio.produtosAdquiridos.filters.fornecedores.push(fornecedor);
		    	
		    }, function() {
		    	
		    });
			
		};
		
		/**
		 * 
		 */
		$scope.openSelecionarProduto = function( tipoConta )
		{
	    	$mdDialog.show({
				controller: "SelecionarOnlyProdutoControllerPopup",
				templateUrl: './modules/home/views/produto/popup/selecionar-produto-relatorio-modal.html',
				parent: angular.element(document.body),
				clickOutsideToClose:true,
				fullscreen: true,
				scope: $scope.$new()
		    })
		    .then(function(produtoAdquirido) {
		    	
	    		$scope.model.relatorio.produtosAdquiridos.filters.produtos.push(produtoAdquirido);
		    	
		    }, function() {
		    	
		    });
			
		};
		
		/**
		 * 
		 */
		$scope.removeProduto = function( produto, index )
		{
			
			$scope.model.relatorio.produtosAdquiridos.filters.produtos.splice = $scope.model.relatorio.produtosAdquiridos.filters.produtos.splice(index,1) ;
			
		}
		
		/**
		 * 
		 */
		$scope.updateProduto = function() {
			$scope.model.produto.form.$setSubmitted();
			
			if ($scope.model.produto.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
			
			produtoService.updateProduto(  $scope.model.produto.entity, {
				callback : function(result) {
					$scope.model.produto.entity = result;
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
		$scope.save = function() {
			$scope.model.produto.entity.id ? $scope.updateProduto() : $scope.insertProduto();
		};
		
		/**
		 * 
		 */
		$scope.onProdutoPaginationChange = function( paginate ) {
        	if (paginate) {
        		$scope.model.produto.page.pageable.page++;
        	} else {
        		$scope.model.produto.page.pageable.page--;
        	}
        		
        	$scope.listProdutosByFilters();
        };
		
		
		/**
		 * 
		 */
		$scope.openMaisOpcoes = function(){
			$scope.moreFilters = !$scope.moreFilters;
		};
		
		/*-------------------------------------------------------------------
	     * 		 				 	POST CONSTRUCT
	     *-------------------------------------------------------------------*/
		
});
}(window.angular));