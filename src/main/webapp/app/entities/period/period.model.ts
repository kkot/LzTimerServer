export class Period {
    constructor(
        public id?: number,
        public startTime?: any,
        public stopTime?: any,
        public active?: boolean,
        public ownerId?: number,
    ) {
        this.active = false;
    }
}
