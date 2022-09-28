import { Component, OnInit } from '@angular/core';
import { LoaderService, SectionLoading, BaseComponent } from '@common';
import { ResultTableMode } from '@app/core';

@Component({
    selector: 'app-desktop',
    templateUrl: './desktop.component.html',
    styleUrls: ['./desktop.component.scss']
})
export class DesktopComponent extends BaseComponent implements OnInit {
    resultTableMode: typeof ResultTableMode = ResultTableMode;
    loading: boolean = true;

    constructor(private loaderService: LoaderService) {
        super();
        this.startPageLoading();
    }

    public startPageLoading() {
        this.loading = true;
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
    }
}
