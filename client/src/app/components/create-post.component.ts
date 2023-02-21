import { Component, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { Post } from '../models';
import { ModelsService } from '../services/models.service';
import { UploadPostService } from '../services/upload-post.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit{
  
  imageBlob! : Blob
  url : any
  id! : string

  @Output()
  onUpload = new Subject<Post>

  constructor(private fb: FormBuilder, private uploadService: UploadPostService, 
    private router: Router, private modelService: ModelsService){}
  
  
  ngOnInit(): void {
    this.form = this.createForm()
  }

  form!: FormGroup

  createForm(){
    return this.fb.group({
      file : this.fb.control(''),
      title : this.fb.control(''),
      text : this.fb.control(''),
    })
  }
  
  // takes in event object when image is selected.
  selectImage(event: any){
    console.log(event)
    const reader = new FileReader()
    //event.target.files is an attribute of event object
    this.imageBlob = event.target.files[0]
    reader.readAsDataURL(this.imageBlob)
    console.log(this.imageBlob)
    console.log(this.form.value['file'])
    reader.onload = () => {
      this.url = reader.result
    }
  }
  
  uploadImage(){
    const formData = new FormData()
    formData.set('image', this.imageBlob)
    formData.set('title', this.form.value['title'])
    formData.set('text', this.form.value['text'])
    console.info(this.imageBlob)
    console.info(this.form.value['title'])
    console.info(this.form.value['text'])
    this.uploadService.upload(formData).then(
      v => {
        console.info(v)
        this.modelService.post = v as Post
        this.onUpload.next(this.modelService.post);
        this.router.navigate(['/post', this.modelService.post.postid])
        
      }
    ).catch (error => console.error(error))

  }
}
