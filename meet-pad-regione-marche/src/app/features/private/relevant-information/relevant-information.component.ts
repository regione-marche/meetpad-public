import { Component, OnInit } from '@angular/core';
import {
    LoaderService,
    SectionLoading,
    AutoUnsubscribe,
    Section,
    ContentSection
} from '@common';
import { I18nService } from '@eng-ds/ng-core';

@Component({
    selector: 'app-relevant-information',
    templateUrl: './relevant-information.component.html',
    styleUrls: ['./relevant-information.component.scss']
})
export class RelevantInformationComponent extends AutoUnsubscribe
    implements OnInit {
    page: Section[] = [];

    constructor(
        private loaderService: LoaderService,
        private i18nService: I18nService
    ) {
        super();
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }

    ngOnInit() {
        this._createContent();
    }

    get paragraph1(): string {
        return `La <strong> conferenza di servizi </strong> è una forma di
        cooperazione tra amministrazioni pubbliche introdotta dalla
        L.241/90, e recentemente oggetto di una serie di modifiche operate
        dal D.Lgs. n. 127/2016, al fine di snellire l’azione amministrativa
        ed evitare che nei procedimenti particolarmente complessi, le
        amministrazioni partecipanti debbano pronunciarsi in luoghi e tempi
        diversi.`;
    }

    get paragraph2(): string {
        return `Il D.Lgs. n. 127/2016 ha introdotto in particolare due distinte
        modalità di svolgimento consistenti nella
        <strong>conferenza semplificata</strong> (art. 14-bis) e nella
        <strong>conferenza simultanea</strong> (art. 14-ter). Inoltre, a
        seconda della fase procedimentale in cui viene utilizzata e, dello
        scopo per cui deve essere convocata, avremo i seguenti tipi di
        conferenza di servizi: I) istruttoria; II) decisoria; III)
        preliminare.`;
    }

    get paragraph3(): string {
        return `<strong>La Conferenza decisoria</strong> è sempre indetta
            dall’Amministrazione Procedente (di seguito AP) e può svolgersi
            secondo due modalità: `;
    }

    get paragraph5(): string {
        return `<strong>La Conferenza istruttoria</strong> può essere indetta
    dall’Amministrazione Procedente, anche su richiesta di altra
    amministrazione coinvolta nel procedimento o del privato
    interessato, quando lo ritenga opportuno per effettuare un esame
    contestuale degli interessi pubblici coinvolti. Si svolge in forma
    semplificata o, in alternativa, secondo le modalità
    definite dall’Amministrazione Procedente sempre nel rispetto dei
    principi di semplificazione e secondo i termini indicati nel
    diagramma soprariportato.`;
    }

    private _createContent(): void {
        this.page.push({
            title: 'INFORMATION.CONFERENCE.TITLE',
            accordion: true,
            initialOpen: true,
            panel: false,
            content: new Map<string, ContentSection>()
                .set('paragraph1', {
                    type: 'p',
                    value: this.paragraph1,
                    class: 'text-margin'
                })
                .set('paragraph2', {
                    type: 'p',
                    value: this.paragraph2,
                    class: 'text-margin'
                })
                .set('paragraph3', {
                    type: 'p',
                    value: this.paragraph3,
                    class: 'text-margin'
                })
                .set('list', {
                    type: 'ul',
                    list: new Map<string, string>()
                        .set('point1', 'forma semplificata e asincrona')
                        .set(
                            'point2',
                            `forma simultanea e sincrona (in base alla complessità della
                        determinazione o su richiesta di altre amministrazioni)`
                        )
                })
                .set('paragraph4', {
                    type: 'p',
                    value: `Per facilità di consultazione, il processo relativo a tale
                    procedimento amministrativo può essere riassunto nel seguente
                    diagramma:`,
                    class: 'text-margin'
                })
                .set('image', {
                    type: 'img',
                    value: 'assets/img/informazioni_utili.svg',
                    class:
                        'col-md-12 col-sm-12 col-xs-12 marginBottomTrue noDragGhost'
                })
                .set('paragraph5', {
                    type: 'p',
                    value: this.paragraph5,
                    class: 'text-margin'
                })
        });
    }
}
