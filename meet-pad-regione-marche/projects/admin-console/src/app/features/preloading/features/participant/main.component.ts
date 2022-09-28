import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoaderService, SectionLoading } from '@common';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-participant-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class ParticipantMainComponent implements OnInit {
    searchQuery: string;
    back = 'admin/preloading';
    constructor(
        private router: Router,
        private loaderService: LoaderService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {}

    startResearch(query: string) {
        this.searchQuery = query;
    }

    navigateToCreate() {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.router.navigate(['../', 'new'], {
            relativeTo: this.activatedRoute
        });
    }
}
