import { Component, OnInit, Input } from '@angular/core';

import { Observable } from 'rxjs';

import { I18nService } from '@eng-ds/ng-core';

import { Section } from '../../interfaces/section.interface';
import { LoaderService } from '../../services/loader/loader.service';
import { SectionLoading } from '../../enums/section-loading.enum';

@Component({
    selector: 'app-page',
    templateUrl: './page.component.html',
    styleUrls: ['./page.component.scss']
})
export class PageComponent implements OnInit {
    @Input('page') page: Section[] = [];

    constructor(
        private loaderService: LoaderService,
        private i18nService: I18nService
    ) {}

    ngOnInit() {}

    get loading(): Observable<boolean> {
        return this.loaderService.getLoading$(SectionLoading.ALL_CONTENT);
    }

    isAccordion(content: Section): boolean {
        return content.accordion;
    }

    isPanel(content: Section): boolean {
        return content.panel;
    }

    getTitle(section: Section): string {
        return this.i18nService.translate(section.title);
    }
}
