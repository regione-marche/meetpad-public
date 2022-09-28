import { Component, OnInit, Input } from '@angular/core';
import { ContentSection } from '../../interfaces/content-section.interface';

@Component({
    selector: 'app-section',
    templateUrl: './section.component.html',
    styleUrls: ['./section.component.scss']
})
export class SectionComponent implements OnInit {
    @Input('section') section: Map<string, ContentSection>;

    constructor() {}

    ngOnInit() {}

    isParagraph(content: ContentSection): boolean {
        return content.type === 'p';
    }

    isImage(content: ContentSection): boolean {
        return content.type === 'img';
    }

    isList(content: ContentSection): boolean {
        return content.type === 'ul';
    }
}
