export class ConferencePermissions {
    conferenceEditable: boolean;
    stepList: number[];
    enabled: boolean;

    constructor(
        conferenceEditable: boolean,
        stepList: number[],
        enabled: boolean
    ) {
        this.conferenceEditable = conferenceEditable;
        this.stepList = stepList;
        this.enabled = enabled;
    }
}
