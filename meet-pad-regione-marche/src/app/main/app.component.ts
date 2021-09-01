import { Component, ViewChild, OnInit } from '@angular/core';
import { MenuLoader } from '@eng-ds/ng-toolkit';
import { Router, ActivatedRoute } from '@angular/router';
import { MenuItem, ActionItem } from '@eng-ds/ng-toolkit';
import { I18nService, ConfigService } from '@eng-ds/ng-core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  @ViewChild('sidebar1') sidebar1;
  appName = this.configService.get('appName');
  public show = false;

  constructor(
    private menuLoader: MenuLoader,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private i18nService: I18nService,
    private configService: ConfigService
  ) {}

  ngOnInit() {

    this.menuLoader.setMenu(this.menuConfigurationNowInsideComponent());
    this.menuLoader.setMenuAction([
      new ActionItem(
        this.i18nService.translate('MENU.OPEN_SITE'),
        (action, item) => {
          window.location.href = '#';
        },
        'book'
      )
    ]);

    this.menuLoader.setTitle(this.i18nService.translate('MENU.TITLE'));
  }

  // menuConfigurationNowInsideComponent() {
  // 	const links = [];

  // 	links.push(
  // 		new MenuItem('Playground', undefined, 'star', 'NORMAL', undefined, undefined, [
  // 			new MenuItem('Old Checkbox', () => {
  // 				this.navigateTo(['/playground', 'old-checkbox']);
  // 			}),
  // 			new MenuItem('Messages', () => {
  // 				this.navigateTo(['/playground', 'mex']);
  // 			}),
  // 			new MenuItem(
  // 				'Form',
  // 				() => {
  // 					this.navigateTo(['/playground', 'form']);
  // 				},
  // 				'',
  // 			),
  // 			new MenuItem(
  // 				'Search with new',
  // 				() => {
  // 					this.navigateTo(['/playground', 'utenti']);
  // 				},
  // 				'',
  // 			),
  // 			new MenuItem(
  // 				'Authorization',
  // 				() => {
  // 					this.navigateTo(['/playground', 'auth-demo']);
  // 				},
  // 				'lock',
  // 			),
  // 			new MenuItem('Numbers', () => {
  // 				this.navigateTo(['/playground', 'numbers']);
  // 			}),
  // 		]),
  // 	);

  // 	links.push(
  // 		new MenuItem(
  // 			'Playground Documentation Element',
  // 			undefined,
  // 			'book',
  // 			'NORMAL',
  // 			undefined,
  // 			undefined,
  // 			[
  // 				new MenuItem('Input text', () => {
  // 					this.navigateTo(['/playground', 'input-text']);
  // 				}),
  // 				new MenuItem('Message Service', () => {
  // 					this.navigateTo(['/playground', 'message-service']);
  // 				}),
  // 				new MenuItem('Input error', () => {
  // 					this.navigateTo(['/playground', 'input-error']);
  // 				}),
  // 				new MenuItem('Filter', () => {
  // 					this.navigateTo(['/playground', 'filter']);
  // 				}),
  // 				new MenuItem('Result Table', () => {
  // 					this.navigateTo(['/playground', 'table-result']);
  // 				}),
  // 				new MenuItem('Input Date', () => {
  // 					this.navigateTo(['/playground', 'input-date']);
  // 				}),
  // 				new MenuItem('Input Double Date', () => {
  // 					this.navigateTo(['/playground', 'input-double-date']);
  // 				}),
  // 				new MenuItem('Accordion testing page', () => {
  // 					this.navigateTo(['/playground', 'accordion']);
  // 				}),
  // 				new MenuItem('Tabs playground', () => {
  // 					this.navigateTo(['/playground', 'tabs']);
  // 				}),
  // 				new MenuItem('Wizard playground', () => {
  // 					this.navigateTo(['/playground', 'wizard']);
  // 				}),
  // 				new MenuItem('Button with loading', () => {
  // 					this.navigateTo(['/playground', 'callback-button']);
  // 				}),
  // 				new MenuItem('Checkbox', () => {
  // 					this.navigateTo(['/playground', 'checkbox']);
  // 				}),
  // 				new MenuItem('Single Checkbox', () => {
  // 					this.navigateTo(['/playground', 'single-checkbox']);
  // 				}),
  // 				new MenuItem('Numbers', () => {
  // 					this.navigateTo(['/playground', 'numbers-documentation']);
  // 				}),
  // 				new MenuItem('Select', () => {
  // 					this.navigateTo(['/playground', 'select-documentation']);
  // 				}),
  // 				new MenuItem('Feedback', () => {
  // 					this.navigateTo(['/playground', 'feedback']);
  // 				}),
  // 				new MenuItem('Modal', () => {
  // 					this.navigateTo(['/playground', 'modal']);
  // 				}),
  // 				new MenuItem('Form section documentation', () => {
  // 					this.navigateTo(['/playground', 'form-section-documentation']);
  // 				}),
  // 				new MenuItem('Radio button documentation', () => {
  // 					this.navigateTo(['/playground', 'radio-doc']);
  // 				}),
  // 				new MenuItem('Text-area playground', () => {
  // 					this.navigateTo(['/playground', 'text-area']);
  // 				}),
  // 				new MenuItem('Action playground', () => {
  // 					this.navigateTo(['/playground', 'action']);
  // 				}),
  // 				new MenuItem('Accordion playground', () => {
  // 					this.navigateTo(['/playground', 'accordion']);
  // 				}),
  // 				new MenuItem('button', () => {
  // 					this.navigateTo(['/playground', 'button']);
  // 				}),
  // 				new MenuItem('Confirmation', () => {
  // 					this.navigateTo(['/playground', 'confirmation']);
  // 				}),
  // 				new MenuItem('Tabs', () => {
  // 					this.navigateTo(['/playground', 'tabs']);
  // 				}),
  // 				new MenuItem('Input-detail', () => {
  // 					this.navigateTo(['/playground', 'input-detail']);
  // 				}),
  // 				new MenuItem('Tooltip', () => {
  // 					this.navigateTo(['/playground', 'tooltip']);
  // 				}),
  // 				new MenuItem('Content loading', () => {
  // 					this.navigateTo(['/playground', 'loader']);
  // 				}),
  // 			],
  // 		),
  // 	);
  // 	links.push(
  // 		new MenuItem('uno', undefined, 'star', 'NORMAL', undefined, undefined, [
  // 			new MenuItem('due', undefined, 'star', 'NORMAL', undefined, undefined, [
  // 				new MenuItem('yeah', undefined, 'star', 'NORMAL', undefined, undefined, []),
  // 				new MenuItem('tre', undefined, 'star', 'NORMAL', undefined, undefined, [
  // 					new MenuItem('are you crazy?', undefined, 'star', 'NORMAL', undefined, undefined, []),
  // 					new MenuItem('uno', undefined, 'star', 'NORMAL', undefined, undefined, []),
  // 				]),
  // 			]),
  // 		]),
  // 	);
  // 	return links;
  // }
  menuConfigurationNowInsideComponent() {
    const links = [];

    links.push(
      new MenuItem(this.i18nService.translate('MENU.LINKS.DESKTOP'), () => {
        this.navigateTo(['/playground', 'input-text']);
      })
    );
    return links;
  }

  navigateTo(path: Array<string>) {
    this.router.navigate(path, { relativeTo: this.activatedRoute });
  }

  showMenu() {
    this.show = true;
    console.log(this.show, ': show');
  }

  hideMenu() {
    this.show = false;
  }
}
