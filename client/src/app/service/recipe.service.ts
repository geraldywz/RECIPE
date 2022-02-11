import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { RecipeSummary } from '../models';

const URL_API_RECIPES = '/api/recipes';

@Injectable()
export class RecipeService {
  constructor(private http: HttpClient) {}

  getAllRecipes(): Promise<RecipeSummary[]> {
    const recipeSumm = lastValueFrom(
      this.http.get<RecipeSummary[]>(URL_API_RECIPES)
    );
    return recipeSumm;
  }
}
