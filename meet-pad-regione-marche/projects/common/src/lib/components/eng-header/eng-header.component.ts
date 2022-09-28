import {
    Component,
    ViewEncapsulation,
    EventEmitter,
    Output,
    ChangeDetectionStrategy
} from '@angular/core';

import { Router } from '@angular/router';

import { Observable, combineLatest } from 'rxjs';
import { shareReplay, take, takeUntil, map } from 'rxjs/operators';

import { MenuLoader, MenuItem } from '@eng-ds/ng-toolkit';
import { I18nService } from '@eng-ds/ng-core';

import { HeaderService } from '../../services/header/header.service';
import { MenuGuard } from '../../services/menu-guard/menu.guard';
import { UserService } from '../../services/user/user.service';
import { AutoUnsubscribe } from '../auto-unsubscribe/auto-unsubscribe.class';
import { FileService } from '../../services/file/file.service';
import { LoginType } from '../../services/auth/auth.service';

declare var $: any;

@Component({
    selector: 'eng-header',
    templateUrl: './eng-header.component.html',
    styleUrls: ['./eng-header.component.scss'],
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class EngHeaderComponent extends AutoUnsubscribe {
    @Output('logout') logout: EventEmitter<void> = new EventEmitter();
    @Output('login') login: EventEmitter<LoginType> = new EventEmitter();
    @Output('showMenu') showMenu: EventEmitter<void> = new EventEmitter();
    uploads: number = 0;

    loginType = LoginType;

    constructor(
        private headerService: HeaderService,
        private i18nService: I18nService,
        private menuLoader: MenuLoader,
        private router: Router,
        private userService: UserService,
        private menuGuard: MenuGuard,
        private fileService: FileService
    ) {
        super();
        this.fileService.uploads$
            .pipe(takeUntil(this.destroy$))
            .subscribe(value => {
                this.uploads = value.size;
            });

        this.fileService.getUploads();
    }

    get admin$(): Observable<boolean> {
        return this.userService.isAdmin().pipe(takeUntil(this.destroy$));
    }

    get user$(): Observable<string> {
        return this.headerService.user$.pipe(
            takeUntil(this.destroy$),
            shareReplay(1),
            take(1)
        );
    }

    get showFiscalCode$(): Observable<boolean> {
        return this.headerService.showFiscalCode$;
    }

    get showUser$(): Observable<boolean> {
        return this.headerService.showUser$;
    }

    get showMenu$(): Observable<boolean> {
        return combineLatest([
            this.headerService.showMenu$,
            this.menuGuard.show$
        ]).pipe(
            takeUntil(this.destroy$),
            map((res: [boolean, boolean]) =>
                res.reduce((acc, current) => acc && current, true)
            )
        );
    }

    get showLogin$(): Observable<boolean> {
        return this.headerService.showLogin$;
    }

    get emptyHeader$(): Observable<boolean> {
        return this.headerService.emptyHeader$;
    }

    get fiscalCode(): Observable<string> {
        return this.headerService.fiscalCode;
    }

    get menuItems(): MenuItem[] {
        return this.menuLoader.getMenu().filter(
            m =>
                // m.name !== this.i18nService.translate('LOGIN') &&
                m.name !== this.i18nService.translate('MENU.LINKS.DESKTOP')
        );
    }

    get inAdmin(): boolean {
        return this.router.url.includes('admin');
    }

    get isPublicRoute(): boolean {
        return this.router.url === '/public';
    }

    get isHomeLinkActive$(): Observable<boolean> {
        return this.admin$.pipe(
            map(
                (admin: boolean) =>
                    (this.router.url === '/admin' && admin) ||
                    this.router.url === '/'
            )
        );
    }

    manageClick(itemMenu: MenuItem): void {
        itemMenu.type === 'CALLBACK'
            ? itemMenu.actionMenu(itemMenu, this.router, null)
            : () => {};

        this.closeMobileMenu();
    }

    navigateHome() {
        this.closeMobileMenu();
        if (this.router.url.includes('admin')) {
            this.router.navigate(['/', 'admin']);
        } else {
            this.router.navigate(['/']);
        }
    }

    navigateToUploadStatus() {
        this.router.navigate(['/', 'admin', 'upload-status']);
    }

    navigateToAdmin() {
        this.router.navigate(['/', 'admin']);
    }

    navigateToPortal() {
        this.router.navigate(['/']);
    }

    closeMobileMenu() {
        if (document.body.clientWidth < 768) {
            $('#menuprincipale').collapse('hide');
        }
    }
}
