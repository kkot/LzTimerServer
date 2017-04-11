import { User } from '../../shared';
export class UserSettings {
    constructor(
        public id?: number,
        public minIdleTime?: number,
        public updatedAt?: any,
        public user?: User,
    ) {
    }
}
