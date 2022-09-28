/* tslint:disable:no-inferrable-types */
import { BaseFile, FileType, ComboBox } from '@common';

export class SharedFile extends BaseFile {
    type = FileType.SHARED;
    subType?;
    visibility: ComboBox[] = [];
    category: ComboBox;
    model: ComboBox;

    constructor(file?: Partial<SharedFile>) {
        super(file);
        if (file) {
            if (file.type && file.type !== this.type) {
                this.subType = file.type;
            }
            this.category = file.category;
            this.visibility = file.visibility;
            this.setName(file.name);
            this.model = file.model;
        }
    }

    setCategory(_category: ComboBox) {
        this.category = _category;
    }

    setVisibility(_visibility: Array<ComboBox>) {
        this.visibility = _visibility;
    }
}
