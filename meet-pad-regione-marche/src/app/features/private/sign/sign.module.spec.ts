import { SignModule } from './sign.module';

describe('SignModule', () => {
  let signModule: SignModule;

  beforeEach(() => {
    signModule = new SignModule();
  });

  it('should create an instance', () => {
    expect(signModule).toBeTruthy();
  });
});
