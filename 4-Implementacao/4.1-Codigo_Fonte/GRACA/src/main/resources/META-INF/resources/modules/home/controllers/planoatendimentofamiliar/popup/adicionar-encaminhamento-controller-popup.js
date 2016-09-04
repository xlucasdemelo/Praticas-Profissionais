(function ( angular ) {
    'use strict';

/**
 *	
 * 
 * @param $scope
 * @param $state
 */
angular.module('home')
	   .controller('AdicionarEncaminhamentoControllerPopup', function( $scope, $state, $importService, $mdToast, $mdDialog, $injector, $timeout, encaminhamento ) {
		
	    /**
	     * Serviços importados do DWR
	     */
		$importService("planoAtendimentoService");
		$scope.selectedResponsavel = null;
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
			encaminhamentoEdit: encaminhamento,

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
				searchText:null,
				
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
		$scope.concluirEncaminhamento = function()
		{
			if ( !$scope.model.encaminhamento.entity.observacao || $scope.model.encaminhamento.entity.observacao == "" )
			{
				$scope.model.encaminhamento.form.$submitted = true;
				$scope.showMessage( $scope.ERROR_MESSAGE,  "Para concluir um encaminhamento é necessário informar a obervação" );
				return;
			}
			
			planoAtendimentoService.concluirEncaminhamento( $scope.model.encaminhamento.entity.id, $scope.model.encaminhamento.entity.observacao,{
	            callback : function(result) {
	            	
	            	$scope.model.encaminhamento.list = result;
	            	
	            	$scope.hide();
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
		$scope.cancelarEncaminhamento = function()
		{
			planoAtendimentoService.cancelEncaminhamento( $scope.model.encaminhamento.entity.id, {
	            callback : function(result) {
	            	
	            	$scope.model.encaminhamento.list = result;
	            	
	            	$scope.hide();
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
		$scope.changeToEdit = function()
		{
			$scope.model.encaminhamento.entity = $scope.model.encaminhamentoEdit;
			$scope.model.responsavel.selected = $scope.model.encaminhamento.entity.responsavel;
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
	    	
			$scope.model.encaminhamento.entity.tipo = $scope.$parent.model.encaminhamento.tipo;
			
			if ($scope.model.encaminhamento.entity.integranteFamiliar){
				
				//gambi
				var a = JSON.parse($scope.model.encaminhamento.entity.integranteFamiliar);
				$scope.model.encaminhamento.entity.integranteFamiliar = new IntegranteFamiliar();
				$scope.model.encaminhamento.entity.integranteFamiliar.id = a.id;
			}
			
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
	    
	    $scope.init = function()
	    {
	    	if ($scope.model.encaminhamentoEdit)
	    	{
	    		$scope.changeToEdit();
	    	}
	    }
	    
	    /*-------------------------------------------------------------------
	     * 		 				  POST CONSTRUCT
	     *-------------------------------------------------------------------*/
		$scope.init();
		
});
}(window.angular));