import { Step } from '@eng-ds/ng-toolkit';

export class ConferenceStep extends Step {
    stepPath: string;
    constructor(step: Step, stepPath) {
        super(step.id, step.title, step.hasError, step.disable, step.visited);
        this.stepPath = stepPath;
    }
}
