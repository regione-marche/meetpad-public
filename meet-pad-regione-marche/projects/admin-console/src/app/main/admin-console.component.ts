import { Component, ViewChild, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { takeUntil, auditTime } from 'rxjs/operators';

import { ToastrService } from 'ngx-toastr';

import {
    MenuItem,
    MenuLoader,
    ModalComponent,
    ActionItem
} from '@eng-ds/ng-toolkit';

import { I18nService } from '@eng-ds/ng-core';

import {
    AuthService,
    MenuGuard,
    AutoUnsubscribe,
    UserService,
    FileService
} from '@common';
import { Subject } from 'rxjs';

@Component({
    templateUrl: './admin-console.component.html',
    styleUrls: ['./admin-console.component.scss']
})
export class AdminConsoleComponent extends AutoUnsubscribe implements OnInit {
    @ViewChild('sidebar1') sidebar1;
    @ViewChild('confirmLoginRedirectModal')
    confirmLoginRedirectModalconfirmModal: ModalComponent;
    currentUploadSize: number = -1;

    confirmLoginRedirectBtnModal: ActionItem[] = [
        new ActionItem(
            'ERROR.MODAL.CONFIRM_LOGIN_REDIRECT.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.confirmLoginRedirectModalconfirmModal.close();
                this.authService.redirectToPublicRoute();
            },
            'close'
        ),
        new ActionItem(
            'ERROR.MODAL.CONFIRM_LOGIN_REDIRECT.OK_BUTTON',
            async (action: ActionItem) => {
                await this.authService.storeRedirectAppUri(this.router.url);
                this.authService.login();
            },
            'check'
        )
    ];
    links: MenuItem[] = [];

    constructor(
        private menuLoader: MenuLoader,
        private toastr: ToastrService,
        private authService: AuthService,
        private router: Router,
        private i18nService: I18nService, // workaround per non avere il blink delle label da capire sulla libreria @eng-ds/ng-core
        public menuGuard: MenuGuard,
        public userService: UserService,
        private fileService: FileService
    ) {
        super();
        // this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this._menuBaseConfiguration();
        this.menuLoader.setMenu(this.links);
        this.menuLoader.setTitle(this.i18nService.translate('MENU.TITLE'));

        this._subscribeToUnauthenticated();
        this._subscribeToSystemMaintenance();
    }

    ngOnInit() {
        this._initFileServiceObserver();
    }

    private _subscribeToSystemMaintenance() {
        this.userService.systemMaintenance$
            .pipe(
                takeUntil(this.destroy$),
                auditTime(3000)
            )
            .subscribe(() => {
                this.toastr.warning(
                    this.i18nService.translate('TOASTR.MAINTENANCE.TEXT'),
                    this.i18nService.translate('TOASTR.MAINTENANCE.TITLE')
                );
                this.router.navigate(['/contact']);
            });
    }

    private _subscribeToUnauthenticated(): void {
        this.userService.unauthenticated$
            .pipe(
                takeUntil(this.destroy$),
                auditTime(500)
            )
            .subscribe(() => {
                this.toastr.error(
                    this.i18nService.translate('TOASTR.UNAUTHENTICATED.TEXT'),
                    this.i18nService.translate('TOASTR.UNAUTHENTICATED.TITLE')
                );
                this.confirmLoginRedirectModalconfirmModal.open();
            });
    }

    private _menuBaseConfiguration(): void {
        this.links.push(
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.AUTHORITIES.TITLE'),
                null,
                '/admin/authorities'
            )
        );
        this.links.push(
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.CONFERENCE.TITLE'),
                null,
                '/admin/conference'
            )
        );
        this.links.push(
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.PROCEEDING.TITLE'),
                null,
                null,
                null,
                'NORMAL',
                null,
                false,
                [
                    new MenuItem(
                        this.i18nService.translate(
                            'MENU.LINKS.PROCEEDING.EDIT'
                        ),
                        null,
                        '/admin/authorities/proceeding/edit'
                    ),
                    new MenuItem(
                        this.i18nService.translate(
                            'MENU.LINKS.PROCEEDING.MANAGERS'
                        ),
                        null,
                        '/admin/authorities/proceeding/manager'
                    ),
                    new MenuItem(
                        this.i18nService.translate(
                            'MENU.LINKS.PROCEEDING.CONFERENCE'
                        ),
                        null,
                        '/admin/authorities/proceeding/conference'
                    )
                ]
            )
        );
        this.links.push(
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.USERS'),
                null,
                '/admin/users'
            )
        );
        this.links.push(
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.CHATBOT'),
                null,
                '/admin/chatbot'
            )
        );
        this.links.push(
            new MenuItem(
                this.i18nService.translate('MENU.LINKS.CONFERENCE_TYPE'),
                null,
                '/admin/preloading'
            )
        );
    }

    login(): void {
        this.authService.login();
    }

    logout(): void {
        this.authService.logout();
    }

    private _initFileServiceObserver(): void {
        this.fileService.uploads$
            .pipe(takeUntil(this.destroy$))
            .subscribe(
                (
                    upload: Map<
                        string,
                        Subject<{ name: string; progress: number }>
                    >
                ) => {
                    if (
                        upload.size !== 0 &&
                        this.currentUploadSize !== upload.size
                    ) {
                        const _upload = Array.from(upload.values());
                        this.currentUploadSize = upload.size;
                        this.toastr
                            .info(
                                this.i18nService.translate(
                                    'CONFERENCE.UPLOAD.TOASTR.NEW_FILE_UPLOAD.TEXT'
                                ),
                                this.i18nService.translate(
                                    'CONFERENCE.UPLOAD.TOASTR.NEW_FILE_UPLOAD.TITLE'
                                )
                            )
                            .onTap.subscribe(value => {
                                this.router.navigate([
                                    '/admin',
                                    'upload-status'
                                ]);
                            });

                        _upload[_upload.length - 1].subscribe(
                            null,
                            error => {
                                this.currentUploadSize = upload.size - 1;
                                this.toastr.error(
                                    this.i18nService.translate(
                                        'CONFERENCE.UPLOAD.TOASTR.ERROR_FILE_UPLOAD.TEXT'
                                    ),
                                    this.i18nService.translate(
                                        'CONFERENCE.UPLOAD.TOASTR.ERROR_FILE_UPLOAD.TITLE'
                                    )
                                );
                            },
                            () => {
                                this.currentUploadSize = upload.size - 1;
                                this.toastr.info(
                                    this.i18nService.translate(
                                        'CONFERENCE.UPLOAD.TOASTR.FINISH_FILE_UPLOAD.TEXT'
                                    ),
                                    this.i18nService.translate(
                                        'CONFERENCE.UPLOAD.TOASTR.FINISH_FILE_UPLOAD.TITLE'
                                    )
                                );
                            }
                        );
                    }
                }
            );
    }
}
