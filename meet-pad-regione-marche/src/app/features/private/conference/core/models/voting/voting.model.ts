/* tslint:disable:no-inferrable-types */
import { DateModel, BaseModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';
import { Vote } from './vote.model';
import { environment } from '@env/environment';
import { debug } from 'util';
import { VotingState } from '@app/core/enums/voting-state.enum';

export class Voting extends BaseModel {
    id: any;

    subject: string = '';

    votingType: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.voting.votingType
    );

    endVotingDate: DateModel | string = new DateModel('');
    votingDate: DateModel | string = new DateModel('');

    votingRule: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.voting.votingRule
    );
    votingState: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.voting.votingState
    );
    votingResult: ComboBox = Object.assign(
        {},
        environment.defaultComboBox.conference.voting.votingResult
    );

    visibility: ComboBox[] = [];

    permissions: {
        edit: boolean;
        start: boolean;
        close: boolean;
        vote: boolean;
        delete: boolean;
        evaluate: boolean;
    } = { edit: true, start: true, close: true, vote: true, delete: true, evaluate: true };

    vote: Vote[] = [];

    constructor(voting?: Partial<Voting>) {
        super();
        if (voting) {
            
            this.id = voting.id;
            this.subject = voting.subject;

            this.endVotingDate = new DateModel(voting.endVotingDate as string);
            this.votingDate = new DateModel(voting.votingDate as string);
            this.votingType = voting.votingType;
            this.votingState = voting.votingState;
            this.votingRule = voting.votingRule;
            this.votingResult = voting.votingResult;
            this.visibility = voting.visibility;
            this.permissions = voting.permissions;

            if (!voting.votingType) {
                this.votingType = Object.assign(
                    {},
                    environment.defaultComboBox.conference.voting.votingType
                );
            }

            if (!voting.votingRule) {
                this.votingRule = Object.assign(
                    {},
                    environment.defaultComboBox.conference.voting.votingRule
                );
            }

            if (!voting.votingState) {
                this.votingState = Object.assign(
                    {},
                    environment.defaultComboBox.conference.voting.votingState
                );
            }

            if (!voting.votingResult) {
                this.votingResult = Object.assign(
                    {},
                    environment.defaultComboBox.conference.voting.votingResult
                );
            }

            if (voting.vote) {
                voting.vote.forEach((value: Vote) => {
                    this.vote.push(Vote.fromDto(value));
                });
            }
        }
    }

    static fromDto(data: any) {
        return new Voting({
            id: data.id,
            subject: data.subject,
            votingResult: data.votingResult,
            votingState: data.votingState,
            votingType: data.votingType,
            votingRule: data.votingRule,
            visibility: data.visibility,
            endVotingDate: data.endVotingDate,
            votingDate: data.votingDate,
            vote: data.vote,
            permissions: {
                edit: data.canEdit,
                start: data.canStart,
                delete: data.canDelete,
                close: data.canClose,
                vote: data.canVote,
                evaluate: data.canEvaluate
            }
        });
    }

    startVoting(): void {
        this.votingState = Object.assign(this.votingState, { key: VotingState.IN_CORSO });
    }

    closeVoting(): void {
        this.votingState = Object.assign(this.votingState, { key: VotingState.TERMINATA });
    }
}
