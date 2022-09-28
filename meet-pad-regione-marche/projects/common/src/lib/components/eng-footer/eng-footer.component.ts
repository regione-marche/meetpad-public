
import { Component, OnInit } from '@angular/core';
declare var $: any;

declare const version: string;

@Component({
    selector: 'eng-footer',
    templateUrl: './eng-footer.component.html',
    styleUrls: ['./eng-footer.component.scss']
})

export class EngFooterComponent implements OnInit {

    constructor() {
    }

    get version(): string {
        return version;
    }

    ngOnInit() {


        function showError(response) {
            alert('Inserire gestione errore');
        }


         $(document).ready(function () {
             const host = document.location.href;
             console.log(host)
             var apiURL;

             if(host.startsWith("http://localhost:4200/"))
             { apiURL = 'http://as.meetpad-dev.eng:8090/cdst_be_marche_svil/api/customer/question';}

             else if(host.startsWith("https://wso2.meetpad-dev.eng.it"))
             { apiURL = 'http://as.meetpad-dev.eng:8090/cdst_be_marche_svil/api/customer/question';}

             else if(host.startsWith("https://meetpad-test.regione.marche.it"))
             { apiURL = 'https://meetpad-test.regione.marche.it/chatbot/question';}
             else
             { apiURL = 'http://as.meetpad-dev.eng:8090/cdst_be_marche_svil/api/customer/question';}
             console.log(apiURL)



            var $chatbox = $('.chatbox'),
                $chatboxTitle = $('.chatbox__title'),
                $chatboxTitleClose = $('.chatbox__title__close'),
                $chatboxCredentials = $('.chatbox__credentials'),
                $invia = $('.invia');

             //var URL = this.getApiUrl()
            $('#invia').hide();

            $chatboxTitle.on('click', function () {
                $chatbox.toggleClass('chatbox--tray');
            });
            $chatboxTitleClose.on('click', function (e) {
                e.stopPropagation();
                $chatbox.addClass('chatbox--closed');
            });
            $chatbox.on('transitionend', function () {
                if ($chatbox.hasClass('chatbox--closed')) $chatbox.remove();
            });
            $chatboxCredentials.on('submit', function (e) {
                $('#invia').show();
                e.preventDefault();
                $chatbox.removeClass('chatbox--empty');
                var name = $("#inputName").val();
                var testo = "Ciao " + name + " sono il tuo assistente virtuale, come posso esserti utile? Digita la tua domanda...";
                $('#chatbox__body').append('<div class="chatbox__body__message chatbox__body__message--left"><img src="assets/img/chatbot-support.png" alt="Customer"><p>' + testo + '</p></div>');
            });


            $invia.on('click', function (e) {
                var domanda = $("#domanda").val();
                if (domanda.length > 0) {
                    event.preventDefault();
                    var risposta = "";

                    var message = {
                        risposta: undefined,
                        domanda: undefined
                    };
                    message.domanda = domanda;
                    message.risposta = risposta;

                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: apiURL,
                        data: JSON.stringify(message),
                        dataType: 'json',
                        success: function (response) {
                            if (response.status === "Success") {
                                var element = document.getElementById("chatbox__body");
                                $('#chatbox__body').append('<div class="chatbox__body__message chatbox__body__message--right"><img src="assets/img/user.png" alt="Picture"><p>' + domanda + '</p></div>');
                                $("#domanda").val("");
                                $('#invia').attr("disabled", true);
                                setTimeout(
                                    function () {
                                        $('#chatbox__body').append('<div class="chatbox__body__message chatbox__body__message--left"><img src="assets/img/chatbot-support.png" alt="Customer"><p>' + response.data.risposta + '</p></div>');
                                        $('#invia').attr("disabled", false);
                                        element.scrollTop = element.scrollHeight;
                                    }, 1000);
                                element.scrollTop = element.scrollHeight;
                            } else {
                                showError(response);
                            }
                        },
                    });
                }
            });
        });}


    getApiUrl()
    {
        const host = document.location.href;
        console.log(host)
        var apiURL;

        if(host.startsWith("http://localhost:4200/"))
        { apiURL = 'http://as.meetpad-dev.eng:8090/cdst_be_marche_svil/api/customer/question';}

        else if(host.startsWith("https://wso2.meetpad-dev.eng.it"))
        { apiURL = 'http://as.meetpad-dev.eng:8090/cdst_be_marche_svil/api/customer/question';}

        else if(host.startsWith("https://meetpad-test.regione.marche.it"))
        { apiURL = 'https://meetpad-test.regione.marche.it/chatbot/question';}
        else
        { apiURL = 'http://as.meetpad-dev.eng:8090/cdst_be_marche_svil/api/customer/question';}
        console.log(apiURL)
        return apiURL;

    }
}



