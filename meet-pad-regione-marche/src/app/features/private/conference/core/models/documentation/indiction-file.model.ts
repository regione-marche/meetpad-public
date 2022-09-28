/* tslint:disable:no-inferrable-types */
import { DateModel } from '@eng-ds/ng-toolkit';

import * as moment from 'moment';
import { ComboBox, BaseFile, FileType } from '@common';

export class IndictionFile extends BaseFile {
    today: moment.Moment = moment(new Date());
    protocolNumber: string = '';
    protocolDate: DateModel | string = new DateModel(this.today);
    inventoryNumber?: string = '';
    type = FileType.INDICTION;
    visibility: ComboBox[] = [];
    multipleSign: boolean ; 
    statusDocument: string = '';
    constructor(file?: Partial<IndictionFile>) {
        super(file);
        if (file) {
            this.protocolNumber = file.protocolNumber;
            this.protocolDate = file.protocolDate;
            this.inventoryNumber = file.inventoryNumber;
            this.visibility = file.visibility;
            this.multipleSign = file.multipleSign; 
            this.statusDocument = file.statusDocument; 
        }
    }

    setProtocolNumber(_protocolNumber: string) {
        this.protocolNumber = _protocolNumber;
    }

    setInventoryNumber(_inventoryNumber: string) {
        this.inventoryNumber = _inventoryNumber;
    }

    setProtocolDate(_protocolDate: string) {
        this.protocolDate = _protocolDate;
    }

    setVisibility(_visibility: Array<ComboBox>) {
        this.visibility = _visibility;
    }

    setMultiSign(_multipleSign: boolean) {
        this.multipleSign = _multipleSign;
    }
}
