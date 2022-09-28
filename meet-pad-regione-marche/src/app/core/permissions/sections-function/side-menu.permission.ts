import { combineLatest, Subscription } from 'rxjs';
import { distinctUntilChanged } from 'rxjs/operators';

import { MenuItem } from '@eng-ds/ng-toolkit';
import { MenuService } from '@app/core/services';

let subscription: Subscription;

export function sideMenuCreatorAndManager(instance: any): void {
    if (!(subscription && subscription instanceof Subscription)) {
        subscribeToMenuVisibilities(instance);
    }
}

export function sideMenuCommon(instance: MenuService): void {
    let links = [...instance.privateBaseMenuItems];
    if(instance.isSignatoryUser()){
        links.push(...instance.privateSignMenuItem);
    }
    instance.menuLoader.setMenu(links);
}

function subscribeToMenuVisibilities(instance: MenuService): void {
    subscription = combineLatest([
        instance.headerService.showMenu$,
        instance.menuGuard.show$
    ])
        .pipe(
            distinctUntilChanged(
                (prev: boolean[], curr: boolean[]) =>
                    prev[0] === curr[0] && prev[1] === curr[1]
            )
        )
        .subscribe(([headerShowMenu, routeShowMenu]: [boolean, boolean]) => {
            if (routeShowMenu) {
                let links = [...instance.privateBaseMenuItems];
                instance.menuLoader.setMenu(links);

                if (headerShowMenu) {
                    links.push(...instance.privateRoleMenuItems);
                } else {
                    links = links.filter((i: MenuItem) =>
                        instance.privateRoleMenuItems.some(
                            (itemMenu: MenuItem) =>
                                itemMenu.routerLink === i.routerLink
                        )
                    );
                    instance.menuLoader.setMenu(links);
                }
                if(instance.isSignatoryUser()){
                    links.push(...instance.privateSignMenuItem);
                 }
            } else {
                instance.menuLoader.setMenu([]);
            }
        });
}
