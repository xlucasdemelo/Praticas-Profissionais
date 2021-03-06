(function ( angular ) {
    'use strict';

/**
 *	
 * 
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('AdicionarIntegranteFamiliarControllerPopup', function( $scope, $state, $importService, $mdToast, $mdDialog, $injector, $timeout, integrante ) {
		
	    /**
	     * Serviços importados do DWR
	     */
		$importService("integranteFamiliarService");
		
	    /*-------------------------------------------------------------------
	     * 		 				 	ATTRIBUTES
	     *-------------------------------------------------------------------*/
		//----FORM MODEL
		
		$scope.integranteFamiliar = integrante;
		
		$scope.maxDate = new Date();
		$scope.allSexos = [];
		$scope.allTiposDocumentos = [];
		
		/**
	     *
		 */
		$scope.model = {
			familia: $scope.model.familia.entity,

			integranteFamiliar: {
				form: null,
				entity: new IntegranteFamiliar(),
				
				filters: {
				    terms: "",
				   
				},
				
			    page: {//PageImpl 
			    		size: 9,
			    		page: 0,
			        	sort:null
			    },
			    sort: [{//Sort
	        		direction: 'ASC', properties: 'id', nullHandlingHint:null
	        	}],
			},
			
			documentoIntegranteFamiliar: {
				form: null,
				entity: new DocumentoIntegranteFamiliar(),
				
				filters: {
				    terms: "",
				   
				},
				
			    page: {//PageImpl 
			    		size: 9,
			    		page: 0,
			        	sort:null
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
	     * 		 				 	  HANDLERS
	     *-------------------------------------------------------------------*/
		
		/**
		 * 
		 */
		$scope.changeToAdd = function(){
			$scope.model.integranteFamiliar.entity = new IntegranteFamiliar();
			$scope.model.integranteFamiliar.entity.familia = $scope.model.familia;
			
			if ($scope.model.familia.endereco.complemento) {
				$scope.model.integranteFamiliar.entity.endereco = angular.copy($scope.model.familia.endereco);
				
				$scope.model.pais.selectedItem = $scope.model.integranteFamiliar.entity.endereco.cidade.estado.pais;
	        	$scope.model.estado.selectedItem = $scope.model.integranteFamiliar.entity.endereco.cidade.estado;
	        	$scope.model.cidade.selectedItem = $scope.model.integranteFamiliar.entity.endereco.cidade;
			}
		}
		
		$scope.teste = function(){
			alert("123");
		}
		
		/**
		 * 
		 */
		$scope.changeToEdit = function(){
			$scope.model.integranteFamiliar.entity = $scope.integranteFamiliar;
			$scope.listDocumentosByIntegrantefamiliar();
			
//			if ($scope.model.integranteFamiliar.entity.endereco)
//			{
//				$scope.model.cidade.selectedItem = $scope.model.integranteFamiliar.entity.endereco.cidade ? $scope.model.integranteFamiliar.entity.endereco.cidade : null;
//	        	$scope.model.estado.selectedItem = $scope.model.integranteFamiliar.entity.endereco.cidade.estado ? $scope.model.integranteFamiliar.entity.endereco.cidade.estado : null;
//	        	$scope.model.pais.selectedItem = $scope.model.integranteFamiliar.entity.endereco.cidade.estado.pais != null ? $scope.model.integranteFamiliar.entity.endereco.cidade.estado.pais : null;
//			}
			
		}
		
		/**
		 * 
		 */
	    $scope.hide = function (result) {
	        $mdDialog.hide(result);
	    };
	    
	    /**
	     * 
	     */
	    $scope.cancel = function () {
	        $mdDialog.cancel();
	    };
	    
	    /**
	     * 
	     */
	    $scope.answer = function () {
	    	
	    	$mdDialog.hide();
	    };
	    
	    /*-------------------------------------------------------------------
	     * 		 				 PRIVATE BEHAVIORS
	     *-------------------------------------------------------------------*/
	    /**
	     * 
	     */
	    $scope.insertIntegranteFamiliar = function() {
	    	
	    	$scope.model.integranteFamiliar.form.$submitted = true;
	    	
			if ($scope.model.integranteFamiliar.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
	    	
	    	integranteFamiliarService.insertIntegranteFamiliar( $scope.model.integranteFamiliar.entity, {
                callback : function(result) {
                	
                	$scope.model.integranteFamiliar.entity = result;
                	$scope.listIntegrantesFamiliaresByFamilia(result.familia.id);
                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Integrante inserido com sucesso" );
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
	    $scope.removeIntegranteFamiliar = function(){
			
	    	$scope.model.integranteFamiliar.entity.rendaMensal = 0;
	    	
			integranteFamiliarService.disableIntegranteFamiliar( $scope.model.integranteFamiliar.entity, {
                callback : function(result) {
                	
                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Integrante removido com sucesso" );
                	$mdDialog.hide();
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
	    $scope.updateIntegranteFamiliar = function() {
	    	
	    	$scope.model.integranteFamiliar.form.$submitted = true;
	    	
			if ($scope.model.integranteFamiliar.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
	    	
			if ( $scope.model.integranteFamiliar.entity.endereco )
			{
				var end = new Endereco();
				end.rua = $scope.model.integranteFamiliar.entity.endereco.rua;
				end.bairro = $scope.model.integranteFamiliar.entity.bairro;
				$scope.model.integranteFamiliar.entity.endereco = end;
			}
			
	    	integranteFamiliarService.updateIntegranteFamiliar( $scope.model.integranteFamiliar.entity, {
                callback : function(result) {
                	
                	$scope.model.integranteFamiliar.entity = result;
                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Integrante editado com sucesso" );
                	$mdDialog.hide();
                	$scope.$apply();
                	
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            });
	    }
	    
	    $scope.listDocumentosByIntegrantefamiliar = function(){
	    	integranteFamiliarService.listDocumentosByIntegranteFamiliar( $scope.model.integranteFamiliar.entity.id, {
                callback : function(result) {
                	$scope.model.integranteFamiliar.entity.documentos = result.content;
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
		
		$scope.updateDocumento = function(e, item) {
			
			var saveDocumento = function(item) {
				integranteFamiliarService.updateDocumentoIntegranteFamiliar( item, {
					callback : function( result ) {
						$scope.showMessage( $scope.ERROR_MESSAGE,  "Documento alterado com sucesso" );
						$scope.$apply();
					},
					errorHandler : function(message, exception) {
						$scope.showMessage( $scope.ERROR_MESSAGE,  message );
		                $scope.$apply();
		            }
				});
			}
			
			if( e.type == "blur" || ( e.type == "keypress" && e.keyCode == 13 ) ) {
				if ($scope.model.integranteFamiliar.form.$invalid ){
					
					var message = "Preencha os campos obrigatórios";
					
					if ($scope.model.integranteFamiliar.form.$error.cpf)
					{
						message = "CPF inválido";
					}
					
					if ($scope.model.integranteFamiliar.form.$error.mask)
					{
						message = "Valor inválido";
					}
					
					$scope.showMessage( $scope.ERROR_MESSAGE,  message );
					return;
				}
				saveDocumento(item);
			}
		};
		
		/**
		 * 
		 */
		$scope.insertDocumentoIntegranteFamiliar = function() {
	    	
			$scope.model.integranteFamiliar.form.$submitted = true;
			
			if ( !$scope.model.documentoIntegranteFamiliar.entity.tipoDocumento ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Selecione um tipo de documento" );
				return;
			}
			
			if ( $scope.model.integranteFamiliar.form.numeroCpf && $scope.model.integranteFamiliar.form.numeroCpf.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
			
			if ( $scope.model.integranteFamiliar.form.numeroRg && $scope.model.integranteFamiliar.form.numeroRg.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
			
			$scope.model.documentoIntegranteFamiliar.entity.integranteFamiliar = new IntegranteFamiliar();
			$scope.model.documentoIntegranteFamiliar.entity.integranteFamiliar.id = $scope.model.integranteFamiliar.entity.id;
			
	    	integranteFamiliarService.insertDocumentoIntegranteFamiliar( $scope.model.documentoIntegranteFamiliar.entity, {
                callback : function(result) {
                	
                	$scope.listDocumentosByIntegrantefamiliar();
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
		$scope.removeDocumentoIntegranteFamiliar = function( item ) {
	    	
	    	integranteFamiliarService.removeDocumentoIntegranteFamiliar( item, {
                callback : function(result) {
                	
                	$scope.listDocumentosByIntegrantefamiliar();
                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Documento removido com sucesso" );
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
		$scope.init = function(){
			$scope.listAllSexos();
			$scope.listAllTiposDocumento();
			
			if ($scope.integranteFamiliar)
				$scope.changeToEdit();
			else
				$scope.changeToAdd();
		}
	    /*-------------------------------------------------------------------
	     * 		 				  POST CONSTRUCT
	     *-------------------------------------------------------------------*/
		$scope.init();
		
});
}(window.angular));