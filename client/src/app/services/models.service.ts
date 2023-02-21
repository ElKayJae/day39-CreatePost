import { Injectable } from '@angular/core';
import { Post } from '../models';

@Injectable({
  providedIn: 'root'
})
export class ModelsService {

  constructor() { }

  postid! : string
  imageurl! : string
  title! : string
  text! : string
  post! : Post

}

