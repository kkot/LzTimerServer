import { User } from '../../shared';
export class Period {
    constructor(
        public id?: number,
        public startTime?: any,
        public stopTime?: any,
        public active?: boolean,
        public owner?: User,
    ) {
        this.active = false;
    }
}
