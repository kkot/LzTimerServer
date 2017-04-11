import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LztimerPeriodModule } from './period/period.module';
import { LztimerUserSettingsModule } from './user-settings/user-settings.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LztimerPeriodModule,
        LztimerUserSettingsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LztimerEntityModule {}
