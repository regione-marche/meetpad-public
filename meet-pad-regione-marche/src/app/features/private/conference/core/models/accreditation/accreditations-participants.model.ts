/* tslint:disable:no-inferrable-types */
import { ComboBox, BaseFile, FileType } from '@common';
import {SupportContact, User} from "@features/private/conference/core";

export class AccreditationPartecipants extends BaseFile {

    participant : ComboBox;
    accreditations: ComboBox<string>[] = [];



    constructor(participant?: Partial<AccreditationPartecipants>) {
        super();
        if (participant) {

            this.id = participant.id;
            this.participant = participant.participant;

            if (participant.accreditations)
            {
                for (let i = 0; i < participant.accreditations.length; i++)
                {
                    this.accreditations.push(participant.accreditations[i]);
                }
            }
        }
    }
}
