import { Component } from '@angular/core';
import { Post } from './models';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  post!: Post

  onUpload(post : Post){
    this.post = post
  }
}
