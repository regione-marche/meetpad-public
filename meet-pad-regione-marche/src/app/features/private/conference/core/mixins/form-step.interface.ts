import { AutoUnsubscribe, FormButton } from '@common';

export abstract class FormStep extends AutoUnsubscribe {
    abstract setFooterButtons(buttons: Map<string, FormButton>): void;
    abstract isReadonly(): boolean;
    setStepAsReadOnly?(): void {}
}
