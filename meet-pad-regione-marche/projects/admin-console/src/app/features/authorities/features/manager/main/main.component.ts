import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoaderService, SectionLoading } from '@common';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-manager-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainManagerComponent implements OnInit {
    searchQuery: { value: string; prosecutingAdministration: string };

    constructor(private router: Router, private loaderService: LoaderService) {}

    ngOnInit() {}

    startResearch(query: { value: string; prosecutingAdministration: string }) {
        this.searchQuery = query;
    }
    navigateToCreate(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.router.navigate([
            'admin',
            'authorities',
            'proceeding',
            'manager',
            'new'
        ]);
    }
}
