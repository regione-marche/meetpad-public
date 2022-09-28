import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoaderService, SectionLoading } from '@common';
import { QueryPreaccreditation } from './models/query-preaccreditation.model';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-preaccreditation-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class PreaccreditationMainComponent implements OnInit {
    searchQuery: QueryPreaccreditation;
    back = 'admin/preloading';
    constructor(
        private router: Router,
        private loaderService: LoaderService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {}

    startResearch(query: QueryPreaccreditation) {
        this.searchQuery = query;
    }

    navigateToCreate() {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.router.navigate(['./', 'new'], {
            relativeTo: this.activatedRoute
        });
    }
}
