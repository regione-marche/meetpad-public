import { Component, Input } from '@angular/core';

import { AbstractTableField } from '@eng-ds/ng-toolkit';

@Component({
    selector: 'app-form-list-table',
    templateUrl: './list-table.component.html',
    styleUrls: ['./list-table.component.scss']
})
export class ListTableComponent {
    @Input() list = [];
    @Input() title: string;
    @Input() emptyText: string;
    @Input() structure: AbstractTableField[] = [];
    @Input() searchCriteria = false;
    @Input() detailLookup = null;
    constructor() {}
}
