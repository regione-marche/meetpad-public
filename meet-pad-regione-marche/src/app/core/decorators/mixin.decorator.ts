export function Mixin(baseCtors: Function[]): ClassDecorator {
    return function(derivedCtor: Function): void {
        baseCtors.forEach((baseCtor: Function) => {
            Object.getOwnPropertyNames(baseCtor.prototype).forEach(name => {
                derivedCtor.prototype[name] = baseCtor.prototype[name];
            });
        });
    };
}
