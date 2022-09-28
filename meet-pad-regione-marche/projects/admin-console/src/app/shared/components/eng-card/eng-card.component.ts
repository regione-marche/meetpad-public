import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { I18nService } from '@eng-ds/ng-core';
import { LoaderService, SectionLoading } from '@common';

@Component({
    selector: 'eng-card',
    templateUrl: './eng-card.component.html',
    styleUrls: ['./eng-card.component.scss']
})
export class EngCardComponent implements OnInit {
    @Input('title') title: string;
    @Input('link') link: string[];
    @Input('image') image: string;
    @Input('relativePath') relativePath: boolean;
    @Input('textClass') textClass: string;
    titleTranslated: string;

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private i18nService: I18nService,
        private loaderService: LoaderService
    ) {}

    ngOnInit() {
        this.titleTranslated = this.i18nService.translate(this.title);
    }

    navigate(): void {
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        if (this.relativePath) {
            this.router.navigate(['./'].concat(this.link), {
                relativeTo: this.activatedRoute
            });
        } else {
            this.router.navigate([this.link]);
        }
    }
}
