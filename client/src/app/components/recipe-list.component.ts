import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RecipeSummary } from '../models';
import { RecipeService } from '../service/recipe.service';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css'],
})
export class RecipeListComponent implements OnInit {
  summary: RecipeSummary[] = [];

  constructor(private recipeSvc: RecipeService, private router: Router) {}

  ngOnInit(): void {
    this.recipeSvc.getAllRecipes().then((r) => (this.summary = r));
  }

  reload() {
    this.router.navigate(['/']);
  }
}
