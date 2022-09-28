import { execSync } from 'child_process';

export function exec(args: string[], opts: { cwd?: string }): void {
  execSync(args.join(' '), {
      stdio: 'inherit',
      ...opts,
  });
}
