import { OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { BaseComponent } from '../../class/base.component';

export class AutoUnsubscribe extends BaseComponent implements OnDestroy {
    destroy$: Subject<boolean> = new Subject<boolean>();

    ngOnDestroy() {
        this.destroy$.next(true);
        this.destroy$.unsubscribe();
    }
}
