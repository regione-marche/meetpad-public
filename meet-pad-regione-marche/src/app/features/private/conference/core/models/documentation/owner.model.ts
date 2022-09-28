/* tslint:disable:no-inferrable-types */
import { ComboBox, BaseFile, FileType } from '@common';
import { SignatureStatus } from '@app/core';
import { environment } from '@env/environment';

export class Owner {

    //id?: Number;
    name: String;
    surname: String;
    //fiscalCode: String;
    //email?: String;
    
    constructor(owner?: Partial<Owner>) {
        
            //this.id = owner.id;
            this.name = owner.name;
            this.surname = owner.surname;
            //this.fiscalCode = owner.fiscalCode;
            //this.email = owner.email;
    }

}
