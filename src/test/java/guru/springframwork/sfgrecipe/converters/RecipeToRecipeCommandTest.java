package guru.springframwork.sfgrecipe.converters;


import guru.springframwork.sfgrecipe.commands.RecipeCommand;
import guru.springframwork.sfgrecipe.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {
    public static final Long ID_VALUE = new Long(1L);
    public static final Integer COOK_TIME = Integer.valueOf(5);
    public static final Integer PREP_TIME = Integer.valueOf(7);
    public static final String DESCRIPTION = "Description";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf(3);
    public static final String SOURCE = "Source";
    public static final String URL = "Url";
    public static final Long CAT_ID_1 = new Long(1L);
    public static final Long CAT_ID_2 = new Long(2L);
    public static final Long INGRED_ID_1 = new Long(3L);
    public static final Long INGRED_ID_2 = new Long(4L);
    public static final Long NOTES_ID = new Long(9L);

    RecipeToRecipeCommand converter;

    @Before
    public void setUp() throws Exception {

        converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(), new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()), new NotesToNotesCommand());
    }

    @Test
    public void testNullObject() throws Exception{
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(converter.convert(new Recipe()));
    }
    @Test
    public void convert() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(ID_VALUE);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        recipe.setNotes(notes);

        Category category1 = new Category();
        category1.setId(CAT_ID_1);

        Category category2 = new Category();
        category2.setId(CAT_ID_2);

        recipe.getCategories().add(category1);
        recipe.getCategories().add(category2);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGRED_ID_1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        //when
        RecipeCommand recipeCommand = converter.convert(recipe);

        //then
        assertNotNull(recipeCommand);
        assertEquals(ID_VALUE, recipeCommand.getId());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(NOTES_ID, recipeCommand.getNotes().getId());
        assertEquals(2, recipeCommand.getCategories().size());
        assertEquals(2, recipeCommand.getIngredients().size());
    }
}