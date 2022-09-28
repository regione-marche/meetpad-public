import {
    Input,
    ElementRef,
    ViewContainerRef,
    OnInit,
    OnDestroy
} from '@angular/core';
import { Subscription } from 'rxjs';

import { BasePermission, AppSections } from '@common';
import { UserPortalService } from '@app/core';

export abstract class BaseAuthDirective extends BasePermission
    implements OnInit, OnDestroy {
    // tslint:disable-next-line:no-input-rename
    @Input() protected _sectionName: AppSections;
    // tslint:disable-next-line:no-input-rename
    @Input('authParams') protected _params: any;

    protected _element: HTMLElement;
    protected _subscription: Subscription;

    constructor(
        private el: ElementRef,
        private _view: ViewContainerRef,
        userService: UserPortalService
    ) {
        super(userService);
        this._element = el.nativeElement;
    }

    abstract ngOnInit(): void;

    protected get _hostComponent(): any {
        // controllo se la direttiva è stata applicata ad un componente angular
        // in caso positivo viene passato come argomento alla callback
        // di controllo dei permessi in modo da avere pieno controllo
        // su tale elemento/component
        // in caso negativo viene passato semplicemente l'elemento html
        // per applicare comunque controlli al di fuori del contesto angular
        let hostComponent = this._element;

        try {
            hostComponent = this._view['_data'].componentView.component;
        } catch (e) {
            console.warn(
                'La direttiva appAuth non è applicata ad un componente Angular'
            );
        }
        return hostComponent;
    }

    protected _noPermission(): void {
        this._element.remove();
    }

    ngOnDestroy(): void {
        if (this._subscription) {
            this._subscription.unsubscribe();
        }
    }
}
