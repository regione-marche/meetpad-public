/* tslint:disable:no-inferrable-types */
import { ComboBox, BaseFile, FileType } from '@common';
import { DateModel } from '@eng-ds/ng-toolkit';

export class ConsoleFile extends BaseFile {
    type: FileType = null;
    subType?;
    visibility: ComboBox[] = [];
    category: ComboBox = null;
    meetingDate?: DateModel;

    constructor(file?: Partial<ConsoleFile>) {
        super(file);
        if (file) {
            if (file.meetingDate) {
                this.meetingDate = file.meetingDate;
            }
            this.type = file.type;
            this.category = file.category;
            this.visibility = file.visibility;
        }
    }

    setCategory(_category: ComboBox) {
        this.category = _category;
    }

    setVisibility(_visibility: Array<ComboBox>) {
        this.visibility = _visibility;
    }
}
