import { Component, OnInit } from '@angular/core';

import {
    LoaderService,
    SectionLoading,
    AuthService,
    HeaderService
} from '@common';
import { MenuService } from '@app/core';

@Component({
    selector: 'app-contact',
    templateUrl: './contact.component.html',
    styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {
    constructor(
        private loaderService: LoaderService,
        private authService: AuthService,
        private headerService: HeaderService,
        public menuService: MenuService
    ) {
        this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
    }

    ngOnInit(): void {
        if (this.authService.hasValidToken()) {
            this.menuService.loadPrivateMenu();
        } else {
            this.headerService.showLogin();
            this.headerService.showMenu();
            this.menuService.loadPublicMenu();
        }
    }
}
