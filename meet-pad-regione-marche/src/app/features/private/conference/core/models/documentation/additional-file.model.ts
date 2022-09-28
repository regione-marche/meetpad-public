/* tslint:disable:no-inferrable-types */
import { ComboBox, BaseFile, FileType } from '@common';

import { environment } from '@env/environment';

export class AdditionalFile extends BaseFile {
    type = FileType.ADDITIONAL;
    subType?;
    visibility: ComboBox[] = [];
    category: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.documentation.additionalCategory
    );

    constructor(file?: Partial<AdditionalFile>) {
        super(file);
        if (file) {
            if (file.type && file.type !== this.type) {
                this.subType = file.type;
            }
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
