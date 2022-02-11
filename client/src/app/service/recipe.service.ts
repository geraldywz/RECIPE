import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Recipe, RecipeSummary } from '../models';

const URL_API_RECIPES = '/api/recipes';
const URL_API_RECIPE = '/api/recipe';

@Injectable()
export class RecipeService {
  constructor(private http: HttpClient) {}

  async getRecipe(id: string): Promise<Recipe> {
    const recipe = lastValueFrom(
      this.http.get<Recipe>(URL_API_RECIPE.concat('/' + id))
    );
    return recipe;
  }

  async getAllRecipes(): Promise<RecipeSummary[]> {
    const recipeSumm = lastValueFrom(
      this.http.get<RecipeSummary[]>(URL_API_RECIPES)
    );
    return recipeSumm;
  }

  async addRecipe(recipe: Partial<Recipe>) {
    return await lastValueFrom(this.http.post<any>(URL_API_RECIPE, recipe));
  }
}
