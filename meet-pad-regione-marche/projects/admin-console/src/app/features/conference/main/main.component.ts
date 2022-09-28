import { Component, OnInit } from '@angular/core';
import { ActionItem } from '@eng-ds/ng-toolkit';
import { SearchConference } from '../../authorities/core/models/searchConference.model';
import { SectionLoading, LoaderService } from '@common';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-conference-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainConferenceComponent implements OnInit {
    searchQuery: string;
    actions: ActionItem[] = [];
    actionMail = new ActionItem(
        'BUTTON.MAIL',

        (a, item: SearchConference) => {
            this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
            this.router.navigate(['./', item.id, 'sendMail'], {
                relativeTo: this.activatedRoute
            });
        },
        'at'
    );
    actionUpload = new ActionItem(
        'BUTTON.UPLOAD',

        (a, item: SearchConference) => {
            this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
            this.router.navigate(['./', item.id, 'upload'], {
                relativeTo: this.activatedRoute
            });
        },
        'upload'
    );
    
    actionChangeState = new ActionItem(
        'BUTTON.CHANGE',

        (a, item: SearchConference) => {
            this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
            this.router.navigate(['./', item.id, 'changeState'], {
                relativeTo: this.activatedRoute
            });
        },
        'refresh'
    );

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private loaderService: LoaderService
    ) {}

    ngOnInit() {
        this.actions.push(this.actionMail, this.actionUpload, this.actionChangeState);
    }

    startResearch(query: string) {
        this.searchQuery = query;
    }
}
