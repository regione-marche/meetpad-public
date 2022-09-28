import { BaseModel } from '@eng-ds/ng-toolkit';

export class PaleoFile extends BaseModel {
    id: any;
    fileCode: string;
    fileDescription: string;

    constructor(data?: Partial<PaleoFile>) {
        super();
        if (data) {
            this.fileCode = data.fileCode;
            this.fileDescription = data.fileDescription;
        }
    }
}
