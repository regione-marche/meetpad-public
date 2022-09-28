/* tslint:disable:no-inferrable-types */
import { ComboBox, BaseFile, FileType } from '@common';
import { SignatureStatus } from '@app/core';
import { environment } from '@env/environment';
import { Owner } from './owner.model';

export class SignatureFile extends BaseFile {
    type = FileType.SIGNATURE;
    subType?;
    visibility: ComboBox[] = [];
    signers: ComboBox[] = [];
    signed: boolean = true;
    name: string;
    status: string;
    owner: Owner;
    remoteSignature: boolean;
    calamaioRemoteUsername: string;
    calamaioRemotePassword: string;
    calamaioRemoteOTP: string;
    calamaioRemoteDomain: string;
    calamaioRemoteDocumentId: string;
    padesCades : boolean;
    calamaioSignature: boolean;
    
    category: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.documentation.additionalCategory
    );

    constructor(file?: Partial<SignatureFile>) {
        super(file);
        
        if (file) {
            if (file.type && file.type !== this.type) {
                this.subType = file.type;
            }
            this.category = file.category;
            this.visibility = file.visibility;
            this.signers = file.visibility;
            this.status = file.status;
            this.owner = file.owner;
            this.remoteSignature = file.remoteSignature;
            this.calamaioRemoteUsername = file.calamaioRemoteUsername;
            this.calamaioRemotePassword = file.calamaioRemotePassword;
            this.calamaioRemoteOTP = file.calamaioRemoteOTP;
            this.calamaioRemoteDomain = file.calamaioRemoteDomain;
            this.calamaioRemoteDocumentId = file.calamaioRemoteDocumentId;
            this.padesCades = file.padesCades;
        } 
    }

    setCategory(_category: ComboBox) {
        this.category = _category;
    }

    setVisibility(_visibility: Array<ComboBox>) {
        this.visibility = _visibility;
    }

    setSigners(_signers: Array<ComboBox>) {
        this.signers = _signers;
    }

    setRemoteSignature(_remoteSignature: boolean) {
        this.remoteSignature = _remoteSignature;
    }

    setCalamaioRemoteUsername(_calamaioRemoteUsername: string) {
        this.calamaioRemoteUsername = _calamaioRemoteUsername;
    }
    setCalamaioRemotePassword(_calamaioRemotePassword: string) {
        this.calamaioRemotePassword = _calamaioRemotePassword;
    }
    setCalamaioRemoteOTP(_calamaioRemoteOTP: string) {
        this.calamaioRemoteOTP = _calamaioRemoteOTP;
    }
    setCalamaioRemoteDomain(_calamaioRemoteDomain: string) {
        this.calamaioRemoteDomain = _calamaioRemoteDomain;
    }
    setCalamaioRemoteDocumentId(_calamaioRemoteDocumentId: string) {
        this.calamaioRemoteDocumentId = _calamaioRemoteDocumentId;
    }

    
    
}
