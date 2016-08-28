(function ( angular ) {
    'use strict';

/**
 *	
 * 
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('AdicionarEncaminhamentoControllerPopup', function( $scope, $state, $importService, $mdToast, $mdDialog, $injector, $timeout ) {
		
	    /**
	     * Serviços importados do DWR
	     */
		$importService("planoAtendimentoService");
		
	    /*-------------------------------------------------------------------
	     * 		 				 	ATTRIBUTES
	     *-------------------------------------------------------------------*/
		//----FORM MODEL
		
		$scope.allSexos = [];
		$scope.allTiposDocumentos = [];
//		$scope.planoAtendimento = planoAtendimento;
		/**
	     *
		 */
		$scope.model = {
			encaminhamento: $scope.model.familia.entity,

			encaminhamento: {
				form: null,
				entity: new Encaminhamento(),
				
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
			
			responsavel: {
				form: null,
				entity: new Responsavel(),
				
				filters: {
				    terms: "",
				   
				},
				selected:null,
				list:[],
			    page: {//PageImpl 
			    		size: 9,
			    		page: 0,
			        	sort:null
			    },
			    sort: [{//Sort
	        		direction: 'ASC', properties: 'id', nullHandlingHint:null
	        	}],
			},
			
		};
		
		/*-------------------------------------------------------------------
	     * 		 				 	  HANDLERS
	     *-------------------------------------------------------------------*/
		
		/**
		 * 
		 */
		$scope.changeToAdd = function(){
			$scope.model.encaminhamento.entity = new Encaminhamento();
		}
		
		$scope.teste = function(){
			alert("123");
		}
		
		/**
		 * 
		 */
		$scope.insertResponsavel = function()
		{
			
			planoAtendimentoService.isnertResposnavel( $scope.model.responsavel.entity, {
	            callback : function(result) {
	            	
	            	$scope.model.responsavel = result;
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
		$scope.listResponsaveisByFilters = function(paia)
		{
			planoAtendimentoService.listResponsaveisByFilters( paia, {
	            callback : function(result) {
	            	
	            	$scope.model.responsavel.list = result;
	            	
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
		$scope.changeToEdit = function(){
			$scope.model.integranteFamiliar.entity = $scope.integranteFamiliar;
			$scope.listDocumentosByIntegrantefamiliar();
			
			$scope.model.pais.selectedItem = $scope.model.integranteFamiliar.entity.endereco.cidade.estado.pais;
        	$scope.model.estado.selectedItem = $scope.model.integranteFamiliar.entity.endereco.cidade.estado;
        	$scope.model.cidade.selectedItem = $scope.model.integranteFamiliar.entity.endereco.cidade
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
	    $scope.insertEncaminhamento = function() {
	    	
	    	$scope.model.encaminhamento.form.$submitted = true;
	    	
			if ($scope.model.encaminhamento.form.$invalid ){
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Preencha os campos obrigatórios" );
				return;
			}
	    	
			$scope.model.encaminhamento.entity.responsavel = $scope.model.responsavel.selected;
			$scope.model.encaminhamento.entity.tipo = $scope.$parent.model.encaminhamento.tipo;
			
			planoAtendimentoService.insertEncaminhamento( $scope.model.encaminhamento.entity, $scope.$parent.model.planoAtendimentoFamiliar.entity.id, {
                callback : function(result) {
                	
                	$scope.showMessage( $scope.SUCCESS_MESSAGE,  "Encaminhamento inserido com sucesso" );
                	$scope.hide();
                	
                },
                errorHandler : function(message, exception) {
                	$scope.showMessage( $scope.ERROR_MESSAGE,  message );
                    $scope.$apply();
                }
            });
	    }
	    
	    
	    /*-------------------------------------------------------------------
	     * 		 				  POST CONSTRUCT
	     *-------------------------------------------------------------------*/
//		$scope.init();
		
});
}(window.angular));