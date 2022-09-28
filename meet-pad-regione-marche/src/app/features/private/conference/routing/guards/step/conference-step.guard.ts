import { Injectable } from '@angular/core';
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot
} from '@angular/router';

import { Observable } from 'rxjs';

import { LoaderService, SectionLoading } from '@common';
import { StepName } from '@app/core';
import { ConferenceStoreService } from '../../../core';

@Injectable()
export class ConferenceStepGuard implements CanActivate {
    constructor(
        private loaderService: LoaderService,
        public conferenceStoreService: ConferenceStoreService
    ) {}

    canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);

        const step = next.routeConfig.path.toUpperCase();

        if (
            this.conferenceStoreService.conference.isCompiling() &&
            this.conferenceStoreService.conference.step <
                StepName[step.toString()] - 1
        ) {
            this.conferenceStoreService.selectCorrectRoute();
            return false;
        }
        if (
            this.conferenceStoreService.conference.isDraft() &&
            !this.canActivateStep(next.routeConfig.path)
        ) {
            this.conferenceStoreService.selectCorrectRoute();
            return false;
        }

        this.setActiveStep(step);
        return true;
    }
    canActivateStep(step: string): boolean {
        if (StepName[step.toUpperCase().toString()] < 5) {
            return true;
        }
        return false;
    }

    setActiveStep(step: string): void {
        this.conferenceStoreService.setActiveStep(
            StepName[step.toUpperCase().toString()]
        );
    }
}
