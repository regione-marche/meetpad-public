import { Component, OnInit } from '@angular/core';
import { LoaderService, SectionLoading } from '@common';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit {
    cards: { title: string; link: string | string[] }[] = [];

    constructor(private loaderService: LoaderService) {}

    ngOnInit() {
        this.cards = [
            {
                title: 'HOME.AUTHORITIES.TITLE',
                link: 'authorities'
            },
            {
                title: 'HOME.CONFERENCE.TITLE',
                link: ['conference']
            },
            {
                title: 'HOME.ADMINISTRATION.TITLE',
                link: ['authorities', 'proceeding']
            },
            {
                title: 'HOME.USERS.TITLE',
                link: 'users'
            },
            {
                title: 'HOME.PRELOADING.TITLE',
                link: 'preloading'
            },
            {
                title: 'HOME.CHATBOT.TITLE',
                link: 'chatbot'
            },
            {
                title: 'HOME.PROTOCOLS.TITLE',
                link: ['protocols']
            }
        ];
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT, 500);
    }
}
