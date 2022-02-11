import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RecipeAddComponent } from './components/recipe-add.component';
import { RecipeDetailComponent } from './components/recipe-detail.component';
import { RecipeListComponent } from './components/recipe-list.component';
import { RecipeService } from './service/recipe.service';

@NgModule({
  declarations: [
    AppComponent,
    RecipeAddComponent,
    RecipeDetailComponent,
    RecipeListComponent,
  ],
  imports: [BrowserModule, AppRoutingModule],
  providers: [RecipeService],
  bootstrap: [AppComponent],
})
export class AppModule {}
