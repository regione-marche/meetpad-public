import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoaderService, SectionLoading } from '@common';
import { QueryApplicant } from './models/query-applicant.model';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-applicant-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class ApplicantMainComponent implements OnInit {
    searchQuery: QueryApplicant;
    back = 'admin/preloading';
    constructor(
        private router: Router,
        private loaderService: LoaderService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {}

    startResearch(query: QueryApplicant) {
        this.searchQuery = query;
    }

    navigateToCreate() {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.router.navigate(['./', 'new'], {
            relativeTo: this.activatedRoute
        });
    }
}
