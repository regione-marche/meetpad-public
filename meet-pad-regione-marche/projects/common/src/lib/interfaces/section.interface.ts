import { ContentSection } from './content-section.interface';

export interface Section {
    content: Map<string, ContentSection>;
    title?: string;
    initialOpen?: boolean;
    accordion?: boolean;
    panel?: boolean;
}
