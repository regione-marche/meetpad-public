import { Component, OnInit } from '@angular/core';
import { LoaderService, SectionLoading } from '@common';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-preceeding-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainProceedingComponent implements OnInit {
    cards: { title: string; link: string }[] = [];

    constructor(private loaderService: LoaderService) {}

    ngOnInit() {
        this.cards = [
            {
                title: 'AUTHORITIES.PROCEDING.HOME.EDIT',
                link: 'edit'
            },
            {
                title: 'AUTHORITIES.PROCEDING.HOME.MANAGER',
                link: 'manager'
            },
            {
                title: 'AUTHORITIES.PROCEDING.HOME.CONFERENCE',
                link: 'conference'
            }
        ];
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }
}
