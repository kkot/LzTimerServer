import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LztimerSharedModule } from '../../shared';
import { LztimerAdminModule } from '../../admin/admin.module';
import {
    UserSettingsService,
    UserSettingsPopupService,
    UserSettingsComponent,
    UserSettingsDetailComponent,
    UserSettingsDialogComponent,
    UserSettingsPopupComponent,
    UserSettingsDeletePopupComponent,
    UserSettingsDeleteDialogComponent,
    userSettingsRoute,
    userSettingsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userSettingsRoute,
    ...userSettingsPopupRoute,
];

@NgModule({
    imports: [
        LztimerSharedModule,
        LztimerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserSettingsComponent,
        UserSettingsDetailComponent,
        UserSettingsDialogComponent,
        UserSettingsDeleteDialogComponent,
        UserSettingsPopupComponent,
        UserSettingsDeletePopupComponent,
    ],
    entryComponents: [
        UserSettingsComponent,
        UserSettingsDialogComponent,
        UserSettingsPopupComponent,
        UserSettingsDeleteDialogComponent,
        UserSettingsDeletePopupComponent,
    ],
    providers: [
        UserSettingsService,
        UserSettingsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LztimerUserSettingsModule {}
