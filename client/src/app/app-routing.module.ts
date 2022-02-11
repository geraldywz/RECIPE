import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecipeAddComponent } from './components/recipe-add.component';
import { RecipeDetailComponent } from './components/recipe-detail.component';
import { RecipeListComponent } from './components/recipe-list.component';

const routes: Routes = [
  { path: '', component: RecipeDetailComponent },
  {
    path: 'add',
    component: RecipeAddComponent,
  },
  {
    path: 'recipe/:recipeId',
    component: RecipeListComponent,
  },
  { path: '**', redirectTo: '/', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
