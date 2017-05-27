import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { UserSettings } from './user-settings.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UserSettingsService {

    private resourceUrl = 'api/user-settings';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(userSettings: UserSettings): Observable<UserSettings> {
        const copy = this.convert(userSettings);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(userSettings: UserSettings): Observable<UserSettings> {
        const copy = this.convert(userSettings);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<UserSettings> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convertItemFromServer(entity: any) {
        entity.updatedAt = this.dateUtils
            .convertDateTimeFromServer(entity.updatedAt);
    }

    private convert(userSettings: UserSettings): UserSettings {
        const copy: UserSettings = Object.assign({}, userSettings);

        copy.updatedAt = this.dateUtils.toDate(userSettings.updatedAt);
        return copy;
    }
}
