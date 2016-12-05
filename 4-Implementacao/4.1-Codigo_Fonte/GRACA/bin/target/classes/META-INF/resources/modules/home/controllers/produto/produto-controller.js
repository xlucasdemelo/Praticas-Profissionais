(function ( angular ) {
    'use strict';

/**
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('ProdutoController', function( $scope, $state, $importService, $mdToast, $mdDialog ) {
		
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
		$scope.PRODUTO_LIST_STATE = "produto.list";
		/**
		 * Representa o estado para a criação de registros.
		 */
		$scope.PRODUTO_ADD_STATE = "produto.add";
		/**
		 * Representa o estado para a edição de registros.
		 */
		$scope.PRODUTO_EDIT_STATE = "produto.edit";
		/**
		 * Representa o estado de detalhe de um registro.
		 */
		$scope.PRODUTO_DETAIL_STATE = "produto.detail";
		
		
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
			
			produto: {
				form: null,
				entity: new Produto(),
				
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
				case $scope.PRODUTO_LIST_STATE: {
					$scope.changeToList( false );
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
	    $scope.changeToList = function( paginate ) {
	        console.debug("changeToList");
	        
	        if ( paginate ) $scope.model.produto.page.pageable.page++;
	        
	        $scope.listProdutosByFilters();
	    };
	    
	    /*-------------------------------------------------------------------
	     * 		 				 PRIVATE BEHAVIORS
	     *-------------------------------------------------------------------*/
	    
	    /**
	     * 
	     */
	    $scope.openSelecionarMarcaPopup = function() {
	    	$mdDialog.show({
				controller: "SelecionarMarcaControllerPopup",
				templateUrl: './modules/home/views/produto/popup/selecionar-marca-popup.html',
				parent: angular.element(document.body),
				clickOutsideToClose:true,
				fullscreen: true,
				scope: $scope.$new()
		    })
		    .then(function(marca) {
		    	$scope.model.produto.entity.marca = marca;
		    }, function() {
		    	
		    });
	    };
	    
	    /**
	     * 
	     */
	    $scope.openSelecionarModeloPopup = function() {
	    	$mdDialog.show({
				controller: "SelecionarModeloControllerPopup",
				templateUrl: './modules/home/views/produto/popup/selecionar-modelo-popup.html',
				parent: angular.element(document.body),
				clickOutsideToClose:true,
				fullscreen: true,
				scope: $scope.$new()
		    })
		    .then(function(modelo) {
		    	$scope.model.produto.entity.modelo = modelo;
		    }, function() {
		    	
		    });
	    };
	    
	    /**
	     * 
	     */
	    $scope.openSelecionarCategoriaPopup = function() {
	    	$mdDialog.show({
	    		controller: "SelecionarCategoriaControllerPopup",
	    		templateUrl: './modules/home/views/produto/popup/selecionar-categoria-popup.html',
	    		parent: angular.element(document.body),
	    		clickOutsideToClose:true,
	    		fullscreen: true,
	    		scope: $scope.$new()
	    	})
	    	.then(function(categoria) {
	    		$scope.model.produto.entity.categoria = categoria;
	    	}, function() {
	    		
	    	});
	    };
		
	    /**
	     * 
	     */
		$scope.listProdutosByFilters = function(){
			produtoService.listProdutosByFilters(  $scope.model.produto.filters.terms.toString(), true, $scope.model.produto.page.pageable, {
                callback : function(result) {
                	$scope.totalPagesProduto = result.totalPages;
                	
                	$scope.model.produto.page = {//PageImpl
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
		$scope.insertProduto = function() {
			$scope.model.produto.form.$setSubmitted();
			
			if ($scope.model.produto.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
			
			produtoService.insertProduto(  $scope.model.produto.entity, {
                callback : function(result) {
                	$state.go( $scope.PRODUTO_EDIT_STATE, {id: result.id}, {reload: true } )
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