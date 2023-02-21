import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';


import { AppComponent } from './app.component';
import { CreatePostComponent } from './components/create-post.component';
import { DisplayComponent } from './components/display.component';
import { PostRepository } from './posts.repository';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
  { path: 'create', component: CreatePostComponent},
  { path: 'post/:id', component: DisplayComponent},
  { path: '**', redirectTo: 'create', pathMatch: 'full'}
]

@NgModule({
  declarations: [
    AppComponent,
    CreatePostComponent,
    DisplayComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, {useHash: true}),
  ],
  providers: [PostRepository],
  bootstrap: [AppComponent]
})
export class AppModule { }
