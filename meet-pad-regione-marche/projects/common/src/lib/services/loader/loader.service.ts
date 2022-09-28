import { Injectable } from '@angular/core';

import { BehaviorSubject } from 'rxjs';

import { SectionLoading } from '../../enums/section-loading.enum';

@Injectable()
export class LoaderService {
    private _map: Map<SectionLoading, BehaviorSubject<boolean>>;

    constructor() {
        this._map = new Map<SectionLoading, BehaviorSubject<boolean>>();
    }

    private _check(section: SectionLoading): void {
        if (!this._map.has(section)) {
            this._map.set(section, new BehaviorSubject(false));
        }
    }

    getLoading$(section: SectionLoading): BehaviorSubject<boolean> {
        this._check(section);
        return this._map.get(section);
    }

    showLoading(section: SectionLoading): void {
        this._check(section);
        this._loading(section, true);
    }

    hideLoading(section?: SectionLoading, delay: number = 0): void {
        if (!section) {
            this._hideAll(delay);
        } else {
            this._check(section);
            this._loading(section, false, delay);
        }
    }

    private _hideAll(delay: number): void {
        this._map.forEach(
            (subject: BehaviorSubject<boolean>, section: SectionLoading) => {
                this._loading(section, false, delay);
            }
        );
    }

    private _loading(
        section: SectionLoading,
        state: boolean,
        delay: number = 0
    ) {
        if (this._map.has(section)) {
            setTimeout(_ => this._map.get(section).next(state), delay);
        }
    }
}
