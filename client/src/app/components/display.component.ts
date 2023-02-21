import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from '../models';
import { ModelsService } from '../services/models.service';
import { UploadPostService } from '../services/upload-post.service';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.css']
})
export class DisplayComponent implements OnInit{


  post!: Post
  // post = this.modelService.post

  id!: string

  constructor(private activatedRoute: ActivatedRoute, private modelService: ModelsService, private uploadService: UploadPostService){
    
  }
  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params['id']
    this.uploadService.get(this.id).then(result => this.post = result as Post)
    .catch(error => console.error(error))
  }

  like(){
    this.uploadService.vote(this.id,"like").then(result => this.post = result as Post)
    .catch(error => console.error(error))
  }

  dislike(){
    this.uploadService.vote(this.id,"dislike").then(result => this.post = result as Post)
    .catch(error => console.error(error))
  }




}
