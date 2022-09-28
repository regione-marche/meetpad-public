import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoaderService, SectionLoading } from '@common';
import { QueryDelegate } from './models/query-delegate.model';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-delegate-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class DelegateMainComponent implements OnInit {
    searchQuery: QueryDelegate;
    back = 'admin/preloading';
    constructor(
        private router: Router,
        private loaderService: LoaderService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {}

    startResearch(query: QueryDelegate) {
        this.searchQuery = query;
    }

    navigateToCreate() {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.router.navigate(['./', 'new'], {
            relativeTo: this.activatedRoute
        });
    }
}
