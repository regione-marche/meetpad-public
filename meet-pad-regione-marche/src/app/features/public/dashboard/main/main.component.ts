import { Component, OnInit } from '@angular/core';

import {
    BaseComponent,
    Section,
    ContentSection,
    LoaderService,
    SectionLoading
} from '@common';

@Component({
    selector: 'app-dashboard-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainComponent extends BaseComponent implements OnInit {
    page: Section[] = [];

    constructor(private loaderService: LoaderService) {
        super();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }

    get paragraph1(): string {
        return `Il processo di ricostruzione richiede la messa in campo di un
            sistema di governo, di un’organizzazione e di strumenti che
            dovranno gestire una mole di lavoro impressionante: 275.000
            sopralluoghi, 120.000 pratiche da gestire, oltre 90.000
            interventi di ricostruzione stimati per edilizia abitativa e
            produttiva; un processo che vede coinvolti 85 comuni (su 229)
            nel cratere sismico, per una popolazione di circa 350.000
            residenti, e comunque 195 comuni marchigiani nei quali almeno
            un’abitazione privata o un’opera pubblica hanno subito
            danneggiamenti a causa del terremoto.`;
    }

    get paragraph2(): string {
        return `La Regione Marche, per dare ulteriori risposte in termini di
            efficacia alle esigenze del territorio (a cittadini, imprese ed
            enti locali), anche e soprattutto al fine di governare
            efficacemente il processo di ricostruzione post sisma, ha deciso
            di intraprendere un percorso che porta alla standardizzazione
            dei procedimenti e allo snellimento dei processi tramite
            l’adozione di una serie di strumenti di interazione online,
            gestionali e di condivisione documentale in grado di
            razionalizzare, rendere più fluidi ed efficienti e semplificare
            il dialogo da remoto, la collaborazione digitale e la
            condivisione di contenuti tra i soggetti del territorio. In
            altre parole, si intende sviluppare una piattaforma abilitante
            di collaborazione multicanale, utilizzabile in diversi contesti,
            tra cui la conferenza di servizi telematica, che costituisce il
            primo caso d’uso.`;
    }

    get paragraph3(): string {
        return `Tale progetto prende il nome di MeetPAd (a rappresentare,
            appunto, un’infrastruttura applicativa utile alla pianificazione
            e conduzione di incontri – meeting – e dei relativi processi di
            lavoro collaborativo, tra soggetti della PA, in forma
            interamente digitale), e viene finanziato per 1.700.000 di euro
            con risorse del Programma Operativo Regionale FESR 2014-2020,
            nell’Obiettivo Tematico 2 “Migliorare l’accesso alle tecnologie
            dell’informazione”. Il progetto, oltre ad essere conforme alla
            strategia dell’Agenda Digitale Marche, approvata con DGR n.
            1686/2013, ed alle modalità attuative del POR FESR, approvate
            con DGR n. 1313/2017, verrà inserito nell’Accordo Territoriale,
            in fase di realizzazione, tra Regione Marche ed AgID, ai sensi
            del più generale ”Accordo Quadro per la crescita e la
            cittadinanza digitale verso gli obiettivi EU2020” tra AgID e
            Conferenza Regioni e Province Autonome.`;
    }

    get paragraph4(): string {
        return `Per ricevere informazioni e supporto sull'utilizzo della
    piattaforma o per inviare segnalazioni è attiva la casella mail
    <a href="mailto:meetpad@regione.marche.it"
        >meetpad@regione.marche.it</a
    >.`;
    }

    ngOnInit() {
        this._createContent();
    }

    _createContent(): void {
        this.page.push({
            accordion: false,
            panel: false,
            content: new Map<string, ContentSection>()
                .set('paragraph1', {
                    type: 'p',
                    value: this.paragraph1,
                    class: 'col-xs-12 reconstructionParagraph text-margin'
                })
                .set('paragraph2', {
                    type: 'p',
                    value: this.paragraph2,
                    class: 'col-xs-12 reconstructionParagraph text-margin'
                })

                .set('image', {
                    type: 'img',
                    value: 'assets/img/img_collaborativa.png',
                    class:
                        'img-responsive col-xs-12 col-sm-12 col-md-offset-1 col-md-10 col-lg-offset-2 col-lg-8 mb-20 marginBottomTrue noDragGhost'
                })
                .set('paragraph3', {
                    type: 'p',
                    value: this.paragraph3,
                    class: 'col-xs-12 reconstructionParagraph text-margin'
                })
                .set('paragraph4', {
                    type: 'p',
                    value: this.paragraph4,
                    class: 'col-xs-12 reconstructionParagraph text-margin'
                })
        });
    }
}
