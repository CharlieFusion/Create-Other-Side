package net.obscurite.create_other_side.datagen;

import com.simibubi.create.AllItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.obscurite.create_other_side.register.ModBlocks;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.ROSE_QUARTZ_BLOCK.get())
                .pattern("QQQ")
                .pattern("QQQ")
                .pattern("QQQ")
                .define('Q', AllItems.ROSE_QUARTZ.get())
                .unlockedBy(getHasName(AllItems.ROSE_QUARTZ.get()), has(AllItems.ROSE_QUARTZ.get()))
                .save(consumer);
    }
}
