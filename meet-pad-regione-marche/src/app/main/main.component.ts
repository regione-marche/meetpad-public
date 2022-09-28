import { Component } from '@angular/core';
import { LoaderService, SectionLoading } from '@common';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'main-root',
    template: '<router-outlet></router-outlet>'
})
export class MainComponent {
    constructor(private loaderService: LoaderService) {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
    }
}


