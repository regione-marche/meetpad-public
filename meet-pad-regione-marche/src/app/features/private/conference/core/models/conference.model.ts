import { DateModel } from '@eng-ds/ng-toolkit';

import * as moment from 'moment';

import { ComboBox } from '@common';

import {
    StepName,
    ConferenceState,
    EventType,
    ConferenceType
} from '@app/core';

import { environment } from '@env/environment';

import {
    Event,
    IntegrationRequest,
    IntegrationClosed,
    IntegrationOnlyOneRequest,
    GenericCommunication,
    IntegrationRegistered,
    IntegrationSend,
    ConferenceMemo,
    ConferenceMemoInternal,
    OpinionExpress,
    FinalResolution
} from './events';
import { Procedure, Applicant, Company } from './procedure';
import { Definition, SupportContact } from './definition';
import { Documentation } from './documentation';
import { Participant, User } from './participant';
import { Voting } from './voting/voting.model';
import { DateUpdate } from './definition/update-date.model';

export class Conference {
    id?: string;
    geomapGuid?: string;
    isImpersonatedAdmin?: boolean;
    clonedFromId?: string;
    foglioMappale?: string;
    enabled?: boolean;
    step?: StepName = StepName.PROCEDURE;
    state?: ComboBox = { key: ConferenceState.COMPILING };
    procedure?: Procedure;
    definition?: Definition;
    documentation?: Documentation;
    participants?: Participant[] = [];
    events?: Event[] = [];
    enableApplicantEdit?: boolean;
    // votings?: Voting[] = [];

    constructor(conference?: Partial<Conference>) {
        this.procedure = new Procedure(conference && conference.procedure);
        this.definition = new Definition(conference && conference.definition);

        if (conference) {
            this.id = conference.id;
            this.geomapGuid = conference.geomapGuid;
            this.isImpersonatedAdmin = conference.isImpersonatedAdmin;
            this.clonedFromId = conference.clonedFromId;
            this.foglioMappale = conference.foglioMappale;
            this.step = conference.step;
            this.state = conference.state;
            this.enabled = conference.enabled;
            this.enableApplicantEdit = conference.enableApplicantEdit;
        }

        if (!(this.definition.instance.creationDate as DateModel).date) {
            this.definition.instance.creationDate = this.procedure.applicant.startDate;
        }
    }

    toDto({
        procedure = true,
        definition = true,
        documentation = true,
        participants = false,
        events = false
    }): Conference {
        const _that = Object.assign({}, this);

        if (procedure) {
            _that.procedure = _that.procedure.toDto();
        } else {
            delete _that.procedure;
        }

        if (definition) {
            _that.definition = _that.definition.toDto();
        } else {
            delete _that.definition;
        }

        if (documentation) {
            // _that.documentation = _that.documentation.toDto();
        } else {
            delete _that.documentation;
        }

        if (participants) {
            // _that.participants = _that.participants.toDto();
        } else {
            delete _that.participants;
        }

        if (events) {
            // _that.participants = _that.participants.toDto();
        } else {
            delete _that.events;
        }

        return _that;
    }

    getParticipant(participantId: string): Participant {
        if (!participantId) {
            return;
        }
        return this.participants.find(
            (_p: Participant) => _p.id === participantId
        );
    }

    createApplicantParticipantFromApplicant(applicant: Applicant): Participant {
        const users = [
            new User({
                surname: applicant.surname,
                name: applicant.name,
                fiscalCode: applicant.fiscalCode,
                email: applicant.pec,
                profile:
                    environment.defaultComboBox.conference.defaultParticipant
                        .applicant.userRole
            })
        ];
        return new Participant({
            authority:
                environment.defaultComboBox.conference.defaultParticipant
                    .applicant.participantAuthority,
            role:
                environment.defaultComboBox.conference.defaultParticipant
                    .applicant.participantRole,
            pec: applicant.pec,
            fiscalCode: applicant.fiscalCode,
            description: '----',
            users,
            readonly: true
        });
    }

    getApplicantParticipantFromApplicant(applicant: Applicant): Participant {
        const authorityApplicantParticipant =
            environment.defaultComboBox.conference.defaultParticipant.applicant
                .participantAuthority;
        const roleApplicantParticipant =
            environment.defaultComboBox.conference.defaultParticipant.applicant
                .participantRole;
        const profileUserApplicantParticipant =
            environment.defaultComboBox.conference.defaultParticipant.applicant
                .userRole;

        const applicantParticipantIndex = this.participants.findIndex(
            (p: Participant) =>
                p.authority.key === authorityApplicantParticipant.key &&
                p.role.key === roleApplicantParticipant.key
        );

        if (applicantParticipantIndex < 0) {
            return;
        }


        // si da per scontato che l'utente sia uno solo e soltanto
        // se nel futuro vorranno poter agiiungenre pià partecipanti richiedenti
        // andra modificata questa sezione
        const participant = this.createApplicantParticipantFromApplicant(
            applicant
        );
        participant.id = this.participants[applicantParticipantIndex].id;
        participant.users = [
            new User({
                name : applicant.name,
                surname : applicant.surname,
                profile : roleApplicantParticipant,
                fiscalCode : applicant.fiscalCode,
                email : applicant.pec
           })
        ]

        // this.participants[applicantParticipantIndex] = this.createApplicantParticipantFromApplicant(applicant);
        return participant;
    }

    editParticipant(participant: Participant): void {
        if (!participant.id) {
            return;
        }
        const p = this.participants.find(
            (_p: Participant) => _p.id === participant.id
        );

        if (!p) {
            return;
        }

        p.update(participant);
    }

    addParticipants(participants: Participant[]): void {
        this.participants = [];
        participants.forEach((participant: Participant) => {
            // tslint:disable-next-line:max-line-length
            participant.readonly =
                participant.role.key ===
                    environment.defaultComboBox.conference.defaultParticipant
                        .administrationProceding.participantRole.key ||
                participant.role.key ===
                    environment.defaultComboBox.conference.defaultParticipant
                        .applicant.participantRole.key;
            this.participants.push(new Participant(participant));
        });
    }

    addDocumentation(documents: Documentation): void {
        this.documentation = new Documentation(documents);
    }

    updateDocumentation(documents: Documentation): void {
        delete this.documentation;
        this.documentation = new Documentation(documents);
    }

    // updateVotings(votings: Voting[]): void {
    //     delete this.votings;
    //     votings.forEach((value: Voting) => {
    //         this.votings.push(new Voting(value));
    //     });
    // }

    addContacts(contacts: SupportContact[]): void {
        this.definition.supportContacts = [];
        contacts.forEach((contact: SupportContact) => {
            // tslint:disable-next-line:max-line-length
            this.definition.supportContacts.push(new SupportContact(contact));
        });
    }

    addEvents(events: Partial<Event>[]): void {
        this.events = [];
        events.forEach((event: Event) => {
            switch (event.type.key) {
                case EventType.INTEGRATION_REQUEST:
                    this.events.push(new IntegrationRequest(event));
                    break;
                case EventType.INTEGRATION_CLOSED:
                    this.events.push(new IntegrationClosed(event));
                    break;
                case EventType.INTEGRATION_ONLY_ONE_REQUEST:
                    this.events.push(new IntegrationOnlyOneRequest(event));
                    break;
                case EventType.INTEGRATION_SEND:
                    this.events.push(new IntegrationSend(event));
                    break;
                case EventType.CONFERENCE_MEMO:
                    this.events.push(new ConferenceMemo(event));
                    break;
                case EventType.CONFERENCE_MEMO_INTERNAL:
                    this.events.push(new ConferenceMemoInternal(event));
                    break;
                case EventType.OPINION_EXPRESS:
                    this.events.push(new OpinionExpress(event));
                    break;
                case EventType.FINAL_RESOLUTION:
                    this.events.push(new FinalResolution(event));
                    break;
                case EventType.GENERIC_COMUNICATION:
                    this.events.push(new GenericCommunication(event));
                    break;
                case EventType.INTEGRATION_REGISTERED:
                    this.events.push(new IntegrationRegistered(event));
                    break;
                default:
                    this.events.push(new Event(event));
                    break;
            }
        });
    }

    deleteParticipant(participantId: string): void {
        if (!participantId) {
            return;
        }
        const ip = this.participants.findIndex(
            (_p: Participant) => _p.id === participantId
        );

        if (ip < 0) {
            return;
        }

        this.participants.splice(ip, 1);
    }

    getParticipantsComboBox(): ComboBox[] {
        return this.participants.map((p: Participant) => {
            return { key: p.id.toString(), value: p.authority.value };
        });
    }

    getDateUpdateList(): DateUpdate[] {
        return this.definition.instance.dateUpdateList;
    }

    getAccreditedForPartecipantsComboBox(): ComboBox[] {
        return this.participants.map((p: Participant) => {
            return { key: p.id.toString(), value: p.authority.value };
        });
    }

    getAuthorityComboBox(): ComboBox[] {
        return this.participants.map((p: Participant) => {
            return {
                key: p.authority.key.toString(),
                value: p.authority.value
            };
        });
    }

    getParticipantsAuthority(): string[] {
        return this.participants.map((p: Participant) => {
            return p.authority.key;
        });
    }

    getParticipantsRoles(): string[] {
        return this.participants.map((p: Participant) => {
            return p.role.key;
        });
    }

    setType(type: ConferenceType): void {
        this.definition.setType(type);
    }

    setApplicant(applicant: Partial<Applicant>): void {
        this.procedure.setApplicant(applicant);
    }

    setCompany(company: Partial<Company>): void {
        this.procedure.setCompany(company);
    }

    updateState(state: ComboBox): void {
        this.state = state;
    }

    isBroadband(): boolean {
        return (
            this.definition.instance.conferenceType.key ===
            ConferenceType.BROADBAND
        );
    }

    isOperationalMeeting(): boolean {
        return (
            this.definition.instance.conferenceType.key ===
            ConferenceType.OPERATIONAL_MEETING
        );
    }

    isPreliminary(): boolean {
        return (
            this.definition.instance.conferenceType.key ===
            ConferenceType.PRELIMINARY
        );
    }

    isIndictionState(): boolean {
        return this.state.key === ConferenceState.JUDGMENT;
    }

    isDraft(): boolean {
        return this.state.key === ConferenceState.DRAFT;
    }

    isClosed(): boolean {
        return this.state.key === ConferenceState.CLOSED;
    }

    isArchivied(): boolean {
        return this.state.key === ConferenceState.ARCHIVIED;
    }

    isCompiling(): boolean {
        return this.state.key === ConferenceState.COMPILING;
    }

    hasEvent(eventType: EventType): boolean {
        return (
            this.events.findIndex((e: Event) => e.type.key === eventType) > -1
        );
    }

    private _isDateSameOrExpired(date: moment.Moment): boolean {
        return date ? date.isSameOrBefore(new Date(), 'day') : false;
    }

    private _isDateExpired(date: moment.Moment): boolean {
        return date ? date.isBefore(new Date(), 'day') : false;
    }

    isFirstSimultaneousSessionExpired(): boolean {
        return this._isDateSameOrExpired(
            (this.definition.instance.firstSessionDate as DateModel).date
        );
    }

    isEndOpinionDateExpired(): boolean {
        return this._isDateExpired(
            (this.definition.instance.endOpinionDate as DateModel).date
        );
    }

    isEndIntegrationDateExpired(): boolean {
        return this._isDateExpired(
            (this.definition.instance.endIntegrationDate as DateModel).date
        );
    }

    getEnableApplicantEdit(): boolean {
        return this.enableApplicantEdit;
    }

    setEnableApplicantEdit(state: boolean): void {
        this.enableApplicantEdit = state;
    }
}
