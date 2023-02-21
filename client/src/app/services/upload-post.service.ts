import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadPostService {

  constructor(private httpClient : HttpClient) { }

  
  upload(formData: FormData){
    return firstValueFrom(
      this.httpClient.post('api/upload', formData)
    )
  }

  get(postId : String){
    return lastValueFrom(
      this.httpClient.get(`api/post/${postId}` )
    )
  }

  vote(postId : string, vote: string){
    return lastValueFrom(
      this.httpClient.get(`api/post/${postId}/${vote}` )
    )
  }
}
