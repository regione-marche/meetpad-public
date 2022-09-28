import { Component, OnInit } from '@angular/core';
import { I18nService } from '@eng-ds/ng-core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-company-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class CompanyMainComponent implements OnInit {
    searchQuery: string = '';
    back = 'admin/preloading';

    constructor(
        private i18nService: I18nService,
        private router: Router,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {}

    navigateToCreate() {
        this.router.navigate(['./', 'new'], {
            relativeTo: this.activatedRoute
        });
    }

    startResearch(query: string) {
        this.searchQuery = query;
    }
}
