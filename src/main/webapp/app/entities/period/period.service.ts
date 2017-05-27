import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { Period } from './period.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PeriodService {

    private resourceUrl = 'api/periods';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(period: Period): Observable<Period> {
        const copy = this.convert(period);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(period: Period): Observable<Period> {
        const copy = this.convert(period);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Period> {
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
        entity.beginTime = this.dateUtils
            .convertDateTimeFromServer(entity.beginTime);
        entity.endTime = this.dateUtils
            .convertDateTimeFromServer(entity.endTime);
    }

    private convert(period: Period): Period {
        const copy: Period = Object.assign({}, period);

        copy.beginTime = this.dateUtils.toDate(period.beginTime);

        copy.endTime = this.dateUtils.toDate(period.endTime);
        return copy;
    }
}
