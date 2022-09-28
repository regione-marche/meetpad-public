import { Component, Input, EventEmitter, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { Observable } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { FormFieldGroup } from '../../interfaces';
import { AutoUnsubscribe } from '../../../auto-unsubscribe/auto-unsubscribe.class';

declare var $: any;

@Component({
    selector: 'app-one-to-many-form',
    templateUrl: './one-to-many-form.component.html',
    styleUrls: ['./one-to-many-form.component.scss']
})
export class OneToManyFormComponent extends AutoUnsubscribe {
    @Input('group') group: FormFieldGroup;
    @Input('groupName') groupName: string;
    @Input('form') form: FormGroup;
    @Input('formName') formName: string;
    @Input('errorLabels') errorLabels: any;
    @Input('detailLookup') detailLookup: any;

    @Output('openControl') openControl: EventEmitter<string> = new EventEmitter();
    @Output('removeControl') removeControl: EventEmitter<string> = new EventEmitter();

    @Input() accordion: number | boolean = false;
    @Input() addRowFn: (groupName: string) => Observable<void>;
    @Input() submitRowFn: (groupName: string) => Observable<any>;

    open = false;
    insertActive = false;
    loading = false;

    constructor() {
        super();
    }

    isPanel(): boolean {
        return this.group.panel;
    }

    isPanelBodyOpen(): boolean {
        return this.open;
    }

    openPanel(delay: number = 0): void {
        setTimeout(() => {
            $(`#collapse${this.accordion}`).collapse('show');
        }, delay);
    }

    closePanel(): void {
        $(`#collapse${this.accordion}`).collapse('hide');
    }

    toogleForm() {
        if (this.form) {
            // il form cè
            if (!this._isOpen()) {
                // il pannello è chiuso
                this._addRow(); // va aperto
            } else {
                this.removeControl.emit(this.groupName);
                if (!this.group.listMany.length) {
                    // il pannello va chiuso se non ci sono utenti in tabella
                    this.closePanel();
                }
            }
        } else {
            this.openControl.emit(this.groupName);
            this._addRow();
        }
    }

    private _isOpen(): boolean {
        return $(`#collapse${this.accordion}.panel-collapse.in`).length;
    }

    _addRow(): void {
        this.insertActive = true;
        this.addRowFn(this.groupName)
            .pipe(takeUntil(this.destroy$))
            .subscribe(() => {
                this.openPanel();
            });
    }

    onSubmit(object): void {
        this.loading = true;
        this.submitRowFn(this.groupName)
            .pipe(takeUntil(this.destroy$))
            .subscribe(() => {
                this.insertActive = false;
                this.loading = false;
                object.onComplete();
                if (!this.group.listMany.length) {
                    this.closePanel();
                }
            });
    }

    resetForm(): void {
        this.form.reset();
    }

    haveHead(): boolean {
        return this.isPanel() && (this.group.panelHead as boolean);
    }

    getKeys(map): {}[] {
        return Array.from(map.keys());
    }
}
