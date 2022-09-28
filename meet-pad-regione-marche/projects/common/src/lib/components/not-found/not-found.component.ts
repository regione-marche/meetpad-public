import { Component, ViewEncapsulation } from '@angular/core';
import { I18nService } from '@eng-ds/ng-core';
import { LoaderService } from '../../services/loader/loader.service';
import { SectionLoading } from '../../enums/section-loading.enum';

@Component({
    selector: 'app-not-found',
    templateUrl: './not-found.component.html',
    encapsulation: ViewEncapsulation.None
})
export class NotFoundComponent {
    text: string = '';

    constructor(
        private loaderService: LoaderService,
        private i18nService: I18nService
    ) {
        this.text = this.i18nService.translate('PAGE_NOT_FOUND.TEXT');
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }
}
