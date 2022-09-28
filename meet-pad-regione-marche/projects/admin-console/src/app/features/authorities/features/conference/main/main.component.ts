import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActionItem } from '@eng-ds/ng-toolkit';
import { SearchConference } from '../../../core/models/searchConference.model';
import { SectionLoading, LoaderService } from '@common';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin--authorities-conference-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainAuthoritiesConferenceComponent implements OnInit {
    searchQuery: string;
    actions: ActionItem[] = [];
    actionEdit = new ActionItem(
        'BUTTON.EDIT',

        (a, item: SearchConference) => {
            this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
            this.router.navigate(['./', item.id], {
                relativeTo: this.activatedRoute
            });
        },
        'edit'
    );

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private loaderService: LoaderService
    ) {}

    ngOnInit() {
        this.actions.push(this.actionEdit);
    }

    startResearch(query: string) {
        this.searchQuery = query;
    }
}
