import { Component, ViewChild } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';

import { takeUntil, filter } from 'rxjs/operators';

import { MenuItem } from '@eng-ds/ng-toolkit';

import { LoaderService } from '../../services/loader/loader.service';
import { MenuGuard } from '../../services/menu-guard/menu.guard';
import { SectionLoading } from '../../enums/section-loading.enum';
import { ConfigService, I18nService } from '@eng-ds/ng-core';
import { AuthService, LoginType } from '../../services/auth/auth.service';
import { AutoUnsubscribe } from '../auto-unsubscribe/auto-unsubscribe.class';

@Component({
    selector: 'eng-template',
    templateUrl: './eng-template.component.html',
    styleUrls: ['./eng-template.component.scss']
})
export class EngTemplateComponent extends AutoUnsubscribe {
    @ViewChild('sidebar1') sidebar1;
    appName = this.configService.get('appName');
    public show = false;
    allContentLoading$ = this.loaderService.getLoading$(
        SectionLoading.ALL_CONTENT
    );
    links: MenuItem[] = [];

    constructor(
        private configService: ConfigService,
        private loaderService: LoaderService,
        private authService: AuthService,
        private router: Router,
        private i18nService: I18nService, // workaround per non avere il blink delle label da capire sulla libreria @eng-ds/ng-core
        public menuGuard: MenuGuard
    ) {
        super();
        this.routerEventListener();
    }

    showMenu(): void {
        this.show = true;
    }

    hideMenu(): void {
        this.show = false;
    }

    login(type: LoginType): void {
        this.authService.login(type);
    }

    logout(): void {
        this.authService.logout();
    }

    routerEventListener(): void {
        this.router.events
            .pipe(
                takeUntil(this.destroy$),
                filter(event => event instanceof NavigationStart)
            )
            .subscribe(() => {
                this.hideMenu();
            });
    }
}
