import { Component, OnInit } from '@angular/core';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-edit-proceeding-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainEditProceedingComponent implements OnInit {
    searchQuery: string = '';

    constructor() {}

    ngOnInit() {}

    startResearch(query: string) {
        this.searchQuery = query;
    }
}
