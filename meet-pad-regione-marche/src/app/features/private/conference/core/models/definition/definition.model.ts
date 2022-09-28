import { ConferenceType } from '@app/core';

import { Determination } from './determination.model';
import { Instance } from './instance.model';
import { SupportContact } from './support-contact.model';

export class Definition {
    determination: Determination;
    instance: Instance;
    supportContacts: SupportContact[] = [];

    constructor(definition: Partial<Definition>) {
        this.determination = new Determination(
            definition && definition.determination
        );
        this.instance = new Instance(definition && definition.instance);
        if (
            definition &&
            definition.supportContacts &&
            Array.isArray(definition.supportContacts)
        ) {
            for (let i = 0; i < definition.supportContacts.length; i++) {
                this.supportContacts.push(
                    new SupportContact(definition.supportContacts[i])
                );
            }
        }
    }

    update(definition: Partial<Definition>) {
        this.determination.update(definition.determination);
        this.instance.update(definition.instance);
        this.updateSupportContacts(definition.supportContacts);
    }

    updateSupportContacts(supportContacts: SupportContact[]): void {
        if (supportContacts && Array.isArray(supportContacts)) {
            for (let i = 0; i < supportContacts.length; i++) {
                this.editSupportContact(supportContacts[i]);
            }
        }
    }

    editSupportContact(supportContact: SupportContact): void {
        if (!supportContact.id) {
            return;
        }
        const sc = this.supportContacts.find(
            (_sc: SupportContact) => _sc.id === supportContact.id
        );

        if (!sc) {
            return;
        }

        sc.update(supportContact);
    }

    deleteSupportContact(supportContact: SupportContact): void {
        if (!supportContact.id) {
            return;
        }
        const isc = this.supportContacts.findIndex(
            (_sc: SupportContact) => _sc.id === supportContact.id
        );

        if (isc < 0) {
            return;
        }

        this.supportContacts.splice(isc, 1);
    }

    toDto(): Definition {
        const _that = Object.assign({}, this);
        _that.instance = _that.instance.toDto();
        return _that;
    }

    setType(type: ConferenceType): void {
        this.instance.setType(type);
    }
}
