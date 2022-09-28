import { ConferenceState } from '@app/core';
import { ConferenceDetailGuard } from '@app/features/private/conference';

export function conferenceDetailCommon(
    confDetailGuard: ConferenceDetailGuard,
    {
        conferenceState
    }: {
        conferenceState: ConferenceState;
    }
): boolean {
    return confDetailGuard.canView(conferenceState);
}
