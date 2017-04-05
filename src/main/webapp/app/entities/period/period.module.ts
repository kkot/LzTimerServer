import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LztimerSharedModule } from '../../shared';
import { LztimerAdminModule } from '../../admin/admin.module';

import {
    PeriodService,
    PeriodPopupService,
    PeriodComponent,
    PeriodDetailComponent,
    PeriodDialogComponent,
    PeriodPopupComponent,
    PeriodDeletePopupComponent,
    PeriodDeleteDialogComponent,
    periodRoute,
    periodPopupRoute,
} from './';

let ENTITY_STATES = [
    ...periodRoute,
    ...periodPopupRoute,
];

@NgModule({
    imports: [
        LztimerSharedModule,
        LztimerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PeriodComponent,
        PeriodDetailComponent,
        PeriodDialogComponent,
        PeriodDeleteDialogComponent,
        PeriodPopupComponent,
        PeriodDeletePopupComponent,
    ],
    entryComponents: [
        PeriodComponent,
        PeriodDialogComponent,
        PeriodPopupComponent,
        PeriodDeleteDialogComponent,
        PeriodDeletePopupComponent,
    ],
    providers: [
        PeriodService,
        PeriodPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LztimerPeriodModule {}
