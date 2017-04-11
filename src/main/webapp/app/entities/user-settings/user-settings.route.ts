import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { UserSettingsComponent } from './user-settings.component';
import { UserSettingsDetailComponent } from './user-settings-detail.component';
import { UserSettingsPopupComponent } from './user-settings-dialog.component';
import { UserSettingsDeletePopupComponent } from './user-settings-delete-dialog.component';

import { Principal } from '../../shared';


export const userSettingsRoute: Routes = [
  {
    path: 'user-settings',
    component: UserSettingsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'lztimerApp.userSettings.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'user-settings/:id',
    component: UserSettingsDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'lztimerApp.userSettings.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userSettingsPopupRoute: Routes = [
  {
    path: 'user-settings-new',
    component: UserSettingsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'lztimerApp.userSettings.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'user-settings/:id/edit',
    component: UserSettingsPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'lztimerApp.userSettings.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'user-settings/:id/delete',
    component: UserSettingsDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'lztimerApp.userSettings.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
