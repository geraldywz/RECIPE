import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { BehaviorSubject, Subscription } from 'rxjs';
import { Recipe } from '../models';

@Component({
  selector: 'app-recipe-add',
  templateUrl: './recipe-add.component.html',
  styleUrls: ['./recipe-add.component.css'],
})
export class RecipeAddComponent implements OnInit, OnDestroy {
  recipe!: Recipe;
  form!: FormGroup;
  sub$!: Subscription;
  valid = new BehaviorSubject<boolean>(false);

  constructor(private fb: FormBuilder, private router: Router) {}

  ngOnInit(): void {
    this.resetForm(this.recipe);
  }

  ngOnDestroy() {
    this.sub$.unsubscribe();
  }

  resetForm(r: Partial<Recipe> = {}) {
    this.sub$?.unsubscribe();

    this.form = this.fb.group({
      title: this.fb.control(r.title || '', [
        Validators.required,
        Validators.minLength(3),
      ]),
      instruction: this.fb.control(r.instruction || '', [
        Validators.required,
        Validators.minLength(3),
      ]),
      image: this.fb.control(r.image || '', [Validators.required]),
      ingredients: this.IngredientsList(),
    });
    this.sub$ = this.form.statusChanges.subscribe((s) => {
      console.info('>>> s: ', s);
      this.valid.next(s.toLowerCase() == 'valid');
    });
  }

  Ingredient(ingredient: string) {
    return this.fb.group({
      name: this.fb.control(ingredient || '', Validators.required),
    });
  }

  IngredientsList(ingredients: string[] = []) {
    const list = this.fb.array([], Validators.required);
    for (let i of ingredients) {
      list.push(this.Ingredient(i));
    }
    return list;
  }

  onAddIngredient() {
    (<FormArray>this.form.get('ingredients')).push(
      new FormGroup({
        name: new FormControl('', Validators.required),
      })
    );
  }

  get controls() {
    return (<FormArray>this.form.get('ingredients')).controls;
  }

  onDeleteIngredient(index: number) {
    (<FormArray>this.form.get('ingredients')).removeAt(index);
  }

  clearAndGoBack() {
    this.resetForm();
    this.back();
  }

  back() {
    this.router.navigate(['/']);
  }
}
