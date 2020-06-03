import { Injectable } from '@angular/core';
import { HttpParams, HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AvailableTagsDataService {

  host = environment.apiUrl;
  uri = "/listAvailableTags";
  response;

  constructor(
    private http: HttpClient
  ) { }

  executeListAvailableTags(id) {
    let params = new HttpParams().set("id", id);
    this.http.get(this.host + this.uri, {params: params}).subscribe(
      response => this.response = response
    );
    return this.response;
  }
}
