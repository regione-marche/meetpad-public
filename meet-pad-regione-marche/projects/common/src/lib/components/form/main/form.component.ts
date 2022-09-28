import {
    Component,
    Input,
    OnChanges,
    SimpleChanges,
    OnDestroy,
    Output,
    EventEmitter,
    ComponentFactoryResolver,
    ComponentRef,
    ViewChildren,
    QueryList,
    AfterViewInit,
    ChangeDetectionStrategy,
    ChangeDetectorRef
} from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Observable, Subject, Subscription } from 'rxjs';
import { tap, takeUntil } from 'rxjs/operators';

import {
    FormBasePageComponent,
    ErrorMessage,
    MessageService
} from '@eng-ds/ng-toolkit';

import { LoggerService } from '@eng-ds/ng-core';

import { FormFieldGroup, FormField, FormButton } from '../interfaces';
import { FormStoreService } from '../services/form-store.service';
import { AlertDirective } from '../directives/alert-host/alert.directive';
import { FooterButtons } from '../../../enums/footer-buttons.enum';
import { LoaderService } from '../../../services/loader/loader.service';
import { SectionLoading } from '../../../enums/section-loading.enum';

declare var $: any;

@Component({
    selector: 'app-form',
    templateUrl: './form.component.html',
    styleUrls: ['./form.component.scss'],
    changeDetection: ChangeDetectionStrategy.Default
})
export class FormComponent extends FormBasePageComponent<any>
    implements OnChanges, OnDestroy, AfterViewInit {
    @ViewChildren(AlertDirective) alertsHost: QueryList<AlertDirective>;
    componentsRef: ComponentRef<any>[] = [];

    @Input('groupFields') groupFields: Map<string, FormFieldGroup>;
    @Input('groupButtons') groupButtons: Map<FooterButtons, FormButton>;
    @Input('formGrey') formGrey = true;
    @Input('footerButtons') footerButtons = true;
    @Input('showCancelBtn') showCancelBtn = true;
    @Input('menu') menu = false;
    @Input('footerTextSubmitButton') footerTextSubmitButton = 'BUTTON.NEXT';
    @Input('formClass') formClass: string[] = [];

    @Input('showFooterLoading') showFooterLoading: boolean = false;
    @Input('saveFn') saveFn: (data: any) => Observable<any>;
    @Input('extractDataToSubmitFn') extractDataToSubmitFn: (
        form: FormGroup
    ) => any;
    @Input('saveComplete') saveComplete: (model: any) => void;
    @Input('saveError') saveError: (error: ErrorMessage) => void;
    @Output('reset') reset: EventEmitter<void> = new EventEmitter();
    @Output('openControl') openControlEvent: EventEmitter<string> = new EventEmitter();
    @Output('removeControl') removeControlEvent: EventEmitter<string> = new EventEmitter();

    addRow: (groupName: string) => Observable<void>;
    submitRow: (groupName: string) => Observable<any>;
    destroy$: Subject<boolean> = new Subject<boolean>();
    formName = `form-${Math.round(Math.random() * 1000)}`;
    openPanel: string | number;
    private _valueChangeSubscibe: Subscription[] = [];

    constructor(
        private resolver: ComponentFactoryResolver,
        public fb: FormBuilder,
        public activatedRoute: ActivatedRoute,
        public router: Router,
        public messageService: MessageService,
        public formStore: FormStoreService,
        private loggerService: LoggerService,
        private loaderService: LoaderService,
        private changeDetectorRef: ChangeDetectorRef
    ) {
        super(fb, activatedRoute, router, messageService);
        this.addRow = this._addRow.bind(this);
        this.submitRow = this._submitRow.bind(this);
    }

    get loading(): Observable<boolean> {
        return this.loaderService.getLoading$(
            SectionLoading.CONFERENCE_FOOTER_BUTTON
        );
    }

    get menuItems(): string[] {
        const items = [];
        if (this.groupFields.size) {
            // cicla i gruppi
            this.groupFields.forEach(
                (group: FormFieldGroup, groupName: string) => {
                    if (group.accordion && group.panelHead) {
                        items.push(group.panelHead);
                    }
                }
            );
        }
        return items;
    }

    get hasAccordion(): boolean {
        for (const group of this.groupFields.values()) {
            if (group.accordion) {
                return true;
            }
        }

        return false;
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.loggerService.log('ngOnChanges', changes);
        if (
            changes.groupFields &&
            !changes.groupFields.firstChange &&
            changes.groupFields.currentValue.size
        ) {
            this._valueChangeUnsubscibe();
            this._formGroup(true);
            this._refreshErrorLabels();
            this.createAlertComponent();
        }
    }

    ngAfterViewInit(): void {
        this.createAlertComponent();
        this._setAccordionListener();
    }

    ngOnInitForChildren(): void {}

    /**
     * Crea il component alert con link
     * L'idea sarebbe di generalizzare questa logica per componenti di alert diversi
     * ovviamente la generalizzazione andrebbe ad avvolgere anche le altri parti coinvolte
     */
    createAlertComponent() {
        if (this.alertsHost) {
            this.alertsHost.forEach((alertHost: AlertDirective) => {
                if (alertHost.alert) {
                    setTimeout(() => {
                        const componentFactory = this.resolver.resolveComponentFactory(
                            alertHost.alert.component
                        );
                        const viewContainerRef = alertHost.viewContainerRef;
                        viewContainerRef.clear();
                        const componentRef: ComponentRef<
                            any
                        > = viewContainerRef.createComponent(componentFactory);

                        this.componentsRef.push(componentRef);
                        componentRef.instance.linkClick =
                            alertHost.alert.linkClick;
                        componentRef.instance.textAfterLink =
                            alertHost.alert.textAfterLink;
                        componentRef.instance.textBeforeLink =
                            alertHost.alert.textBeforeLink;
                        componentRef.instance.textLink =
                            alertHost.alert.textLink;
                        componentRef.instance.type = alertHost.alert.type;
                    });
                }
            });
        }
    }

    oneToMany(group: FormFieldGroup): boolean {
        return group.oneToMany;
    }

    isPanel(group: FormFieldGroup): boolean {
        return group.panel;
    }

    isAccordion(group: FormFieldGroup): boolean {
        return group.accordion;
    }

    // haveMenu(group: FormFieldGroup): boolean;
    haveMenu(group: FormFieldGroup): boolean {
        if (group) {
            return group.menu;
        }

        return true;
    }

    haveHead(group: FormFieldGroup): boolean {
        return this.isPanel(group) && !!group.panelHead;
    }

    haveAlert(group: FormFieldGroup): boolean {
        return !!group.alert && !!group.alert.component && !!group.alert.type;
    }

    removeControl(groupName: string): void {
        this.form.removeControl(groupName);
        this.removeControlEvent.emit(groupName);
    }

    openControl(groupName: string): void {
        this.openControlEvent.emit(groupName);
    }

    createForm(): FormGroup {
        const f = this.fb.group(this._formGroup());
        return f;
    }

    fillForm(form: FormGroup, model: any): null {
        return null;
    }

    extractDataToSubmit(form: FormGroup): any {
        return this.extractDataToSubmitFn
            ? this.extractDataToSubmitFn(form)
            : form.value;
    }

    loadModel(id: any): Observable<any> {
        return new Observable(observer => {
            observer.next({ name: 'Mario', surname: 'Rossi' });
            observer.complete();
        });
    }

    getPathKeyForTakeId(): string {
        return undefined;
    }

    save(model: any): Observable<any> {
        return this.saveFn(model);
    }

    resetForm(): void {
        this.reset.emit();

        this.groupFields.forEach((value: FormFieldGroup, key: string) => {
            value.fields.forEach((field: FormField, _key: string) => {
                if (field.control && !field.control.touched) {
                    field.control.reset();
                }
            });
        });
    }

    update(model: any): Observable<any> {
        return new Observable(observer => {
            observer.next(model);
            observer.complete();
        });
    }

    submitComplete(model: any): void {
        this.saveComplete(model);
    }

    submitError(error: ErrorMessage): void {
        this.submitLoading = false;
        this.saveError(error);
    }

    pageName(): string {
        return 'Conference';
    }

    rememberToCreateAndUseErrorLabels(err: any): any {
        return this._getInputFormKey('errorLabels');
    }

    rememberToCreateAndUseTooltips(tooltip: any) {
        return this._getInputFormKey('tooltip');
    }

    getKeys(map: Map<any, any>) {
        return Array.from(map.keys());
    }

    ngOnDestroyForChildren() {}

    hideFooterButtons(): void {
        this.footerButtons = false;
    }

    ngOnDestroy() {
        this.destroy$.next(true);
        this.destroy$.unsubscribe();
        this._destroyAlertComponent();
    }

    private _destroyAlertComponent(): void {
        for (let i = 0; i < this.componentsRef.length; i++) {
            this.componentsRef[i].destroy();
        }
    }

    private _valueChangeUnsubscibe(): void {
        for (let k = 0; k < this._valueChangeSubscibe.length; k++) {
            this._valueChangeSubscibe[k].unsubscribe();
        }
        this._valueChangeSubscibe = [];
    }

    private _addRow(groupName: string): Observable<void> {
        const form = {};
        const group = this.groupFields.get(groupName);
        const model = new (group.model as any)();

        form[groupName] = {};

        // cicla i campi
        group.fields.forEach((field: FormField, fieldName: string) => {
            field.control = new FormControl(model[fieldName], field.validators);

            form[groupName][fieldName] = field.control;

            if (field.valueChange) {
                this._valueChangeSubscibe.push(
                    field.control.valueChanges
                        .pipe(takeUntil(this.destroy$))
                        .subscribe(e => field.valueChange(e))
                );
            }
        });

        this.form.setControl(groupName, this.fb.group(form[groupName]));

        return new Observable(observer => {
            observer.next();
            observer.complete();
        });
    }

    private _submitRow(groupName: string): Observable<any> {
        return this.groupFields
            .get(groupName)
            .saveFn(this.form.get(groupName).value)
            .pipe(
                tap(() => {
                    this.form.removeControl(groupName);
                })
            );
    }

    private _getInputFormKey(key: string) {
        const data = {};
        if (this.groupFields.size) {
            this.groupFields.forEach(
                (group: FormFieldGroup, groupName: string) => {
                    if (!group.oneToMany) {
                        data[groupName] = {};
                    }
                    group.fields.forEach(
                        (field: FormField, fieldName: string) => {
                            if (!group.oneToMany) {
                                data[groupName][fieldName] = field[key];
                            } else {
                                data[fieldName] = field[key];
                            }
                        }
                    );
                }
            );
        }
        return data;
    }

    private _formGroup(update = false) {
        const form = {};
        if (this.groupFields.size) {
            let groupIndex = 0;
            // cicla i gruppi
            this.groupFields.forEach(
                (group: FormFieldGroup, groupName: string) => {
                    if (group.accordion) {
                        group.accordionName = `collapse${
                            this.formName
                        }${groupIndex}`;
                        groupIndex += 1;
                        group.openAccordion = () => {
                            this._openAccordionPanel(group.accordionName);
                            this._setActiveAccordion(groupIndex);
                        };
                    }
                    if (!group.oneToMany) {
                        form[groupName] = {};

                        // cicla i campi
                        group.fields.forEach(
                            (field: FormField, fieldName: string) => {
                                if (field.type !== 'nop') {
                                    form[groupName][fieldName] = field.control;

                                    if (field.valueChange) {
                                        this._valueChangeSubscibe.push(
                                            field.control.valueChanges
                                                .pipe(takeUntil(this.destroy$))
                                                .subscribe(e =>
                                                    field.valueChange(e)
                                                )
                                        );
                                    }
                                }
                            }
                        );

                        if (update) {
                            this.form.setControl(
                                groupName,
                                this.fb.group(form[groupName])
                            );
                        } else {
                            form[groupName] = this.fb.group(form[groupName]);
                        }
                    }
                }
            );
        }
        return form;
    }

    private _refreshErrorLabels(): void {
        this.errorLabels = this._getInputFormKey('errorLabels');
    }

    private _openAccordionPanel(accordionName: string): void {
        $(`#accordion${this.formName}`)
            .find(`#${accordionName}`)
            .collapse('show');
    }

    private _setAccordionListener(): void {
        this._listenShowAccordion();
        this._listenShownAccordion();
        this._listenHiddenAccordion();
    }

    private _listenHiddenAccordion(): void {
        $(`#accordion${this.formName}`).on('hidden.bs.collapse', e => {
            if (!this._existOpenAccordion()) {
                this._setActiveAccordion(null);
                this._blurAllMenuItem();
            }
        });
    }

    private _blurAllMenuItem(): void {
        $('.formNav a').blur();
    }

    private _listenShownAccordion(): void {
        $(`#accordion${this.formName}`).on('shown.bs.collapse', e => {
            this._setActiveAccordion(e.target.id);
        });
    }

    private _listenShowAccordion(): void {
        $(`#accordion${this.formName}`).on('show.bs.collapse', e => {
            if (e.target.nodeName !== 'INPUT') {
                this._closeAllAccordion();
            }
        });
    }

    private _existOpenAccordion(): boolean {
        return $(`#accordion${this.formName}`).find('.panel-collapse.in')
            .length;
    }

    private _setActiveAccordion(id: string | number): void {
        this.openPanel = id;
        this.changeDetectorRef.detectChanges();
    }

    private _closeAllAccordion(): void {
        this.openPanel = null;
        $(`#accordion${this.formName}`)
            .find('.panel-collapse.in')
            .collapse('hide');
    }
}
