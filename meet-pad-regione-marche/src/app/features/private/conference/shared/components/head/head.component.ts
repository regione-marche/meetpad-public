import { Component, Input } from '@angular/core';

import { Conference } from '@features/private/conference/core';
import { TooltipModel } from '@eng-ds/ng-toolkit';
import { ConferenceState } from '@app/core';

@Component({
    selector: 'app-conference-head',
    templateUrl: './head.component.html',
    styleUrls: ['./head.component.scss']
})
export class HeadComponent {
    @Input('conference') conference: Conference;

    constructor() {}

    tooltipState(): TooltipModel {
        let content = null;

        switch (this.conference.state.key) {
            case ConferenceState.COMPILING:
                content = 'CONFERENCE.TOOLTIP_STATES.COMPILING';
                break;
            case ConferenceState.DRAFT:
                content = 'CONFERENCE.TOOLTIP_STATES.DRAFT';
                break;
            case ConferenceState.JUDGMENT:
                content = 'CONFERENCE.TOOLTIP_STATES.JUDGMENT';
                break;
            case ConferenceState.CLOSED:
                content = 'CONFERENCE.TOOLTIP_STATES.CLOSED';
                break;
            case ConferenceState.ARCHIVIED:
                content = 'CONFERENCE.TOOLTIP_STATES.ARCHIVIED';
                break;
        }

        return new TooltipModel(content);
    }

    get conferenceState(): string {
        return this.conference.state && this.conference.state.value;
    }

    showAddressSkypeLink(): boolean {
        let s = '';
        if (this.conference.definition.instance.address) {
            if (this.conference.definition.instance.address.address) {
                s = this.conference.definition.instance.address.address;
            }
        }
        return s.indexOf('http') > -1 || s.indexOf('https') > -1;
    }

    openAddressSkypeLink(): void {
        window
            .open(this.conference.definition.instance.address.address, '_blank')
            .focus();
    }
}
