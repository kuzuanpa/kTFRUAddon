package cn.kuzuanpa.ktfruaddon.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import gregapi.data.FL;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;
import gregapi.util.OM;
import gregtech.tileentity.tools.MultiTileEntityMold;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static com.bioxx.tfc.Core.TFC_Core.l10n;

public class MaterialShapeNEIHandler extends TemplateRecipeHandler {
        private static Collection<OreDictMaterial> materials;
        private static final List<CachedMaterialShapeRecipe> cachedRecipes = new ArrayList<>();

        public String getRecipeName() {
            return "gtMaterialShape";
        }

        public String getGuiTexture() {
            return "ktfruaddon:textures/nei/main.png";
        }

        public String getOverlayIdentifier() {
            return "gtMaterialShape";
        }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    public void loadTransferRects() {
            this.transferRects.add(new RecipeTransferRect(new Rectangle(61, 9, 18, 18), "gtCrucible"));
        }

        private void addRecipe(Predicate<? super CachedMaterialShapeRecipe> condition){
            cachedRecipes.stream().filter(condition).forEach(recipe->this.arecipes.add(recipe));
        }
        public void loadCraftingRecipes(String outputId, Object... results) {
            if (outputId.equals("gtMaterialShape")) addRecipe(r->true);
            else super.loadCraftingRecipes(outputId, results);
        }

        public void loadCraftingRecipes(ItemStack result) {
            this.arecipes.clear();
            addRecipe(recipe-> OM.materialcontained(result, recipe.material));
        }

        public void loadUsageRecipes(ItemStack ingredient) {
            this.arecipes.clear();
            addRecipe(recipe -> OP.dust.contains(ingredient) && OM.materialcontained(ingredient, recipe.material));
        }

        public TemplateRecipeHandler newInstance() {
            if (materials == null) materials = OreDictMaterial.MATERIAL_MAP.values();


            if (cachedRecipes.isEmpty()) materials.forEach(mat->cachedRecipes.add(new CachedMaterialShapeRecipe(mat)));
            return super.newInstance();
        }

        public class CachedMaterialShapeRecipe extends CachedRecipe {
            final PositionedStack ingred;
            final List<PositionedStack> result = new ArrayList<>();
            final OreDictMaterial material;

            public CachedMaterialShapeRecipe(OreDictMaterial material) {
                this.material = material;
                this.ingred = null;
                int x = 0, y=16;
                for (OreDictPrefix op : OreDictPrefix.VALUES_SORTED) {
                    if(op.contains(TD.Prefix.STANDARD_ORE))continue;
                    if (op.mat(material, 1) != null) {
                        this.result.add(new PositionedStack(op.mat(material, 1), x, y));
                        x+=16;
                        if(x > 144){
                            x = 0;
                            y +=16;
                        }
                    }
                }
                FluidStack fl = material.liquid(1,false);   if(fl != null && !FL.Error.is(fl))this.result.add(new PositionedStack(FL.display(fl.getFluid()), 0,0));
                 fl = material.gas(1,false);                if(fl != null && !FL.Error.is(fl))this.result.add(new PositionedStack(FL.display(fl.getFluid()), 16,0));
                 fl = material.plasma(1,false);             if(fl != null && !FL.Error.is(fl))this.result.add(new PositionedStack(FL.display(fl.getFluid()), 32,0));
            }

            public PositionedStack getResult() {
                return null;
            }

            public PositionedStack getIngredient() {
                return this.ingred;
            }

            public List<PositionedStack> getOtherStacks() {
                return result;
            }
        }

}
