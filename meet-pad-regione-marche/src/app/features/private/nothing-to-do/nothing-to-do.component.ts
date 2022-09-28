import { Component } from '@angular/core';
import { LoaderService, SectionLoading } from '@common';

@Component({
    template: `
        <div class="text-center">
            <h2>{{ "PAGE_NOTHING_TO_DO.TEXT" | translate }}</h2>
        </div>
    `
})
export class NothingToDoComponent {
    constructor(private loaderService: LoaderService) {
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }
}
