function showError(response) {
	alert('Inserire gestione errore');
				}
(function($) {
    $(document).ready(function() {
        var $chatbox = $('.chatbox'),
            $chatboxTitle = $('.chatbox__title'),
            $chatboxTitleClose = $('.chatbox__title__close'),
            $chatboxCredentials = $('.chatbox__credentials'),
        	$invia = $('.invia'),
        	$insert = $('.insert'),
            $getAllDomande = $('.getAllDomande'),
            $getAllRichieste = $('.getAllRichieste'),
            $deleteDomanda = $('.deleteDomanda'),
            $deleteRichiesta = $('.deleteRichiesta'),
            $update = $('.update');        
            
        	$('#invia').hide();
        	
        $chatboxTitle.on('click', function() {
            $chatbox.toggleClass('chatbox--tray');
        });
        $chatboxTitleClose.on('click', function(e) {
            e.stopPropagation();
            $chatbox.addClass('chatbox--closed');
        });
        $chatbox.on('transitionend', function() {
            if ($chatbox.hasClass('chatbox--closed')) $chatbox.remove();
        });
        $chatboxCredentials.on('submit', function(e) {
        	$('#invia').show();
            e.preventDefault();
            $chatbox.removeClass('chatbox--empty');
            var name = $("#inputName").val();
            var testo = "Ciao " + name + " sono il tuo assistente virtuale, come posso esserti utile ?, digita la tua domanda...";
            $('#chatbox__body').append('<div class="chatbox__body__message chatbox__body__message--left"><img src="../img/chatbot-support.png" alt="Customer"><p>' + testo + '</p></div>');
        });   
        
        $update.on('click', function(e) {        	
        	var question = $("#questionUpdate").val();
        	var answer = $("#answerUpdate").val();   
        	var id =  $("#idUpdate").val(); 
        	var message = {};
	    	message.domanda = question;
	    	message.risposta = answer;	    
	    	message.id = id;
    		$.ajax({
    			type : "POST",
    			contentType : "application/json",
    			url : "http://localhost:8080/api/customer/update",
    			data : JSON.stringify(message),
    			dataType : 'json',
    			success : function(response) {    				
    			},    			
    		});	    	
        }); 
        
        
        
        $insert.on('click', function(e) {        	
        	var question = $("#questionInsert").val();
        	var answer = $("#answerInsert").val();   
        	var message = {};
	    	message.domanda = question;
	    	message.risposta = answer;	    	
    		$.ajax({
    			type : "POST",
    			contentType : "application/json",
    			url : "http://localhost:8080/api/customer/insert",
    			data : JSON.stringify(message),
    			dataType : 'json',
    			success : function(response) {    				
    			},    			
    		});	    	
        }); 
        
        $getAllDomande.on('click', function(e) {        	    	
    		$.ajax({
    			type : "POST",
    			contentType : "application/json",
    			url : "http://localhost:8080/api/customer/getAllDomande",
    			dataType : 'json',
    			success : function(data) {    
    				for (var i = 0; i < data.length; i++) {    					
    					$("#selectedAllDomande").append(' <option value="' + data[i] + '">' + data[i] + '</option>');
					}
    			},    			
    		});	    	
        });
        
        $getAllRichieste.on('click', function(e) {        	    	
    		$.ajax({
    			type : "POST",
    			contentType : "application/json",
    			url : "http://localhost:8080/api/customer/getAllRichieste",
    			dataType : 'json',
    			success : function(data) {    
    				for (var i = 0; i < data.length; i++) {    					
    					$("#selectedAllRichieste").append(' <option value="' + data[i] + '">' + data[i] + '</option>');
					}
    			},    			
    		});	    	
        });
        
        $deleteRichiesta.on('click', function(e) {       
        	var id = $("#idRichiestaDelete").val();  
    		$.ajax({
    			type : "POST",
    			contentType : "application/json",
    			url : "http://localhost:8080/api/customer/deleteRichiesta",
    			data : JSON.stringify(id),
    			dataType : 'json',
    			success : function(data) {    
    				$("#idRichiestaDelete").val(""); 
    			},    			
    		});	    	
        });
        
        $deleteDomanda.on('click', function(e) {       
        	var id = $("#idDomandaDelete").val();  
    		$.ajax({
    			type : "POST",
    			contentType : "application/json",
    			url : "http://localhost:8080/api/customer/deleteDomanda",
    			data : JSON.stringify(id),
    			dataType : 'json',
    			success : function(data) {    
    				$("#idDomandaDelete").val(""); 
    			},    			
    		});	    	
        });
                        
        $invia.on('click', function(e) {
        	var domanda = $("#domanda").val();
        	if(domanda.length > 0){
        		
        		event.preventDefault();        		
        		var risposta   = "";
		    	
		    	var message = {};
		    	message.domanda = domanda;
		    	message.risposta = risposta;		    	
      		
        		$.ajax({
        			type : "POST",
        			contentType : "application/json",
        			url : "http://localhost:8080/api/customer/question",
        			data : JSON.stringify(message),
        			dataType : 'json',
        			success : function(response) {
        				if(response.status == "Success"){ 
        					var element = document.getElementById("chatbox__body");
        					$('#chatbox__body').append('<div class="chatbox__body__message chatbox__body__message--right"><img src="../img/user.png" alt="Picture"><p>' + domanda + '</p></div>');
        	        		$("#domanda").val("");  
        	        		$('#invia').attr("disabled", true);
        	        		setTimeout(
        	        				  function() 
        	        				  {
        	        					  $('#chatbox__body').append('<div class="chatbox__body__message chatbox__body__message--left"><img src="../img/chatbot-support.png" alt="Customer"><p>' + response.data.risposta + '</p></div>');         	        		  
        	          	        	      $('#invia').attr("disabled", false);        	          	        	     
        	        	        	      element.scrollTop = element.scrollHeight;
        	        				  }, 1000);
        	        	    element.scrollTop = element.scrollHeight;        	        		
        				}
        				else{
							showError(response);
						}
        			},        			
        		});
        	}
        });        
    });
})(jQuery);