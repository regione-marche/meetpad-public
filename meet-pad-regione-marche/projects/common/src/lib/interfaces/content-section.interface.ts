export interface ContentSection {
    value?: string;
    type: 'p' | 'img' | 'ul';
    list?: Map<string, string>;
    class?: string;
}
