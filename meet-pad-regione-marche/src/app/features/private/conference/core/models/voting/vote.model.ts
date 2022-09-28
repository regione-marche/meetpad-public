import { BaseModel, DateModel } from '@eng-ds/ng-toolkit';
import { ComboBox } from '@common';

export class Vote extends BaseModel {
    id: any;
    participant: ComboBox;
    vote: boolean;
    voteDate: DateModel | string = new DateModel('');
    detail: string = '';

    constructor(vote?: Partial<Vote>) {
        super();
        if (vote) {
            this.id = vote.id;
            this.participant = vote.participant;
            this.vote = vote.vote;
            this.voteDate = new DateModel(vote.voteDate as string);
            this.detail = vote.detail;
        }
    }

    static fromDto(data: any) {
        return new Vote({
            id: data.id,
            participant: data.participant,
            vote: data.vote,
            detail: data.detail,
            voteDate: data.date
        });
    }
}
