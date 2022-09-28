/* tslint:disable:no-inferrable-types */

import { BaseModel, DateModel } from '@eng-ds/ng-toolkit';

import { FileType } from '../enums/file-type.enum';

// TODO: togliere le proprietà specifiche e metterle nelle specializzazioni
// (IndictionFile, AdditionalFile ..etc)
// spostare questo modello nel core
export class BaseFile extends BaseModel {
    id: any;
    name: string = '';
    url: string = '';
    cityReference: string = '';
    type: FileType;
    selected: boolean = false;
    fileComplient: boolean = false;
    fileSignedFromUser: boolean = false;

    constructor(file?: Partial<BaseFile>) {
        super();
        if (file) {
            this.id = file.id;
            this.name = file.name;
            this.cityReference = file.cityReference;
            this.url = file.url;
            this.fileComplient = file.fileComplient;
            this.fileSignedFromUser = file.fileSignedFromUser;
        }
    }

    setName(_name: string) {
        this.name = _name;
    }

    setUrl(_url: string) {
        this.url = _url;
    }

    setId(_id: string) {
        this.id = _id;
    }

    toggleSelection(){
        this.selected = !this.selected
    }

    isSelected(){
        return this.selected
    }

    isFileComplient(){
        return this.fileComplient
    }

    isFileSignedFromUser() {
        return this.fileSignedFromUser;
    }
}
