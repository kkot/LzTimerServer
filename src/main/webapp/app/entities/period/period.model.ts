import { User } from '../../shared';
export class Period {
    constructor(
        public id?: number,
        public beginTime?: any,
        public endTime?: any,
        public active?: boolean,
        public owner?: User,
    ) {
        this.active = false;
    }
}
