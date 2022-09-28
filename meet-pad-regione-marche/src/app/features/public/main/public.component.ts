import { Component, OnInit } from '@angular/core';

import { AutoUnsubscribe, HeaderService } from '@common';
import { MenuService } from '@app/core';

@Component({
    template: '<eng-template><router-outlet></router-outlet></eng-template>'
})
export class PublicComponent extends AutoUnsubscribe implements OnInit {
    constructor(
        private headerService: HeaderService,
        public menuService: MenuService
    ) {
        super();
    }

    ngOnInit() {
        this.headerService.showLogin();
        this.headerService.showMenu();
        this.menuService.loadPublicMenu();
    }
}
