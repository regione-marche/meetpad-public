import { DateModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common/common';
import { environment } from '@env/environment';

export class DateUpdate {
    
    code?: string;
    description?: string;
    date?: DateModel | string = new DateModel('');
    dateNew?: DateModel | string = new DateModel('');
    editDate?: DateModel | string = new DateModel('');
    type?: ComboBox =  Object.assign(
        {},
        environment.defaultComboBox.conference.events.editDate.dateType
    )

    constructor(ud?: Partial<DateUpdate>) {
        if (ud) {
            this.code = ud.code;
            this.description = ud.description;
            this.date = new DateModel(
                ud.date as string
            );
            this.dateNew = new DateModel(
                ud.dateNew as string
            );
            this.editDate = new DateModel(
                ud.editDate as string
            );

            this.type = Object.assign(
                { key : ud.code, value: ud.description}
            );
        }
    }

    update(ud: DateUpdate): void {
        if (ud) {
            this.code = ud.code;
            this.description = ud.description;
            this.date = new DateModel(
                ud.date as string
            );
            this.dateNew = new DateModel(
                ud.dateNew as string
            );
            this.editDate = new DateModel(
                ud.editDate as string
            );

            this.type = Object.assign(
                { key : ud.code, value: ud.description}
            );
        }
    }
    
}
