import { OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';
import { ApplicationRole } from '../enums/application-role.enum';
import { AppSections } from '../enums/app-sections.enum';
import { ComboBox } from '../interfaces/combo-box.interface';

// import { FormField } from '@app/features/private/shared';
type FormField = any;

export class BaseComponent implements OnInit {
    protected roles = ApplicationRole;
    protected appSections = AppSections;

    constructor() {}

    ngOnInit() {}

    protected _initFieldFormAutocomplete(
        field: FormField,
        fn: (
            field: FormField
        ) => { options$: Observable<ComboBox[]>; typeahead$: Subject<string> }
    ): void {
        ({ options$: field.options, typeahead$: field.typeahead } = fn(field));
    }
}
