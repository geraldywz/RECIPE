import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Recipe, RecipeSummary } from '../models';

const URL_API_RECIPES = '/api/recipes';

@Injectable()
export class RecipeService {
  constructor(private http: HttpClient) {}

  getRecipe(id: string): Promise<Recipe> {
    const recipe = lastValueFrom(
      this.http.get<Recipe>(`/api/recipe/${id}`)
    );
    console.log('Recipe >>>>' + recipe);

    return recipe;
  }

  getAllRecipes(): Promise<RecipeSummary[]> {
    const recipeSumm = lastValueFrom(
      this.http.get<RecipeSummary[]>(URL_API_RECIPES)
    );
    return recipeSumm;
  }
}
