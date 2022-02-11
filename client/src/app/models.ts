export interface RecipeSummary {
  id: string;
  title: string;
}

export interface Recipe extends RecipeSummary {
  image: string;
  instruction: string;
  ingredients: string[];
}
