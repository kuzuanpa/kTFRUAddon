package cn.kuzuanpa.ktfruaddon.nei;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.bioxx.tfc.api.HeatIndex;
import com.google.common.collect.Lists;
import gregapi.data.FL;
import gregapi.data.IL;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;
import gregapi.util.OM;
import gregapi.util.UT;
import gregtech.tileentity.tools.MultiTileEntityMold;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static com.bioxx.tfc.Core.TFC_Core.l10n;

public class CrucibleNEIHandler extends TemplateRecipeHandler {
        private static Collection<OreDictMaterial> materials;
        private static final List<CachedCrucibleRecipe> cachedRecipes = new ArrayList<>();

        public String getRecipeName() {
            return "gtCrucible";
        }

        public String getGuiTexture() {
            return "ktfruaddon:textures/nei/main.png";
        }

        public String getOverlayIdentifier() {
            return "gtCrucible";
        }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    public void loadTransferRects() {
            this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(61, 9, 18, 18), "gtCrucible"));
        }

        private void addRecipe(Predicate<? super CachedCrucibleRecipe> condition){
            cachedRecipes.stream().filter(condition).forEach(recipe->this.arecipes.add(recipe));
        }
        public void loadCraftingRecipes(String outputId, Object... results) {
            if (outputId.equals("gtCrucible")) addRecipe(r->true);
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

            if (cachedRecipes.isEmpty()) materials.forEach(mat->cachedRecipes.add(new CachedCrucibleRecipe(mat)));
            return super.newInstance();
        }

        public class CachedCrucibleRecipe extends TemplateRecipeHandler.CachedRecipe {
            final List<PositionedStack> result = new ArrayList<>();
            final OreDictMaterial material;

            public CachedCrucibleRecipe(OreDictMaterial material) {
                this.material = material;
                int x = 0, y=16;
                ArrayList<OreDictPrefix> addedPrefixes = new ArrayList<>();
                Collection<OreDictPrefix> prefixes = new ArrayList<>(MultiTileEntityMold.MOLD_RECIPES.values());
                prefixes.add(OP.blockSolid);
                for (OreDictPrefix op : prefixes) {
                    if (op.mat(material, 1) != null && !addedPrefixes.contains(op)) {
                        this.result.add(new PositionedStack(op.mat(material, 1), x, y+16));
                        this.result.add(new PositionedStack(getRawClayMold(op, 1), x, y));
                        addedPrefixes.add(op);
                        x+=16;
                        if(x > 144){
                            x = 0;
                            y +=32;
                        }
                    }
                }
                FluidStack fl = material.liquid(1,false);   if(fl != null && !FL.Error.is(fl))this.result.add(new PositionedStack(FL.display(fl.getFluid()), 0,0));
              }

            public PositionedStack getResult() {
                return null;
            }

            public List<PositionedStack> getOtherStacks() {
                return result;
            }
        }
        
        public static ItemStack getRawClayMold(OreDictPrefix prefix, int amount){
             if (prefix.equals(OP.ingot)) return IL.Ceramic_Ingot_Mold_Raw.get(amount);
             if (prefix.equals(OP.billet)) return IL.Ceramic_Billet_Mold_Raw.get(amount);
             if (prefix.equals(OP.chunkGt)) return IL.Ceramic_Chunk_Mold_Raw.get(amount);
             if (prefix.equals(OP.plate)) return IL.Ceramic_Plate_Mold_Raw.get(amount);
             if (prefix.equals(OP.plateTiny)) return IL.Ceramic_Tiny_Plate_Mold_Raw.get(amount);
             if (prefix.equals(OP.bolt)) return IL.Ceramic_Bolt_Mold_Raw.get(amount);
             if (prefix.equals(OP.stick)) return IL.Ceramic_Rod_Mold_Raw.get(amount);
             if (prefix.equals(OP.stickLong)) return IL.Ceramic_Long_Rod_Mold_Raw.get(amount);
             if (prefix.equals(OP.casingSmall)) return IL.Ceramic_Item_Casing_Mold_Raw.get(amount);
             if (prefix.equals(OP.ring)) return IL.Ceramic_Ring_Mold_Raw.get(amount);
             if (prefix.equals(OP.gearGt)) return IL.Ceramic_Gear_Mold_Raw.get(amount);
             if (prefix.equals(OP.gearGtSmall)) return IL.Ceramic_Small_Gear_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawSword)) return IL.Ceramic_Sword_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawPickaxe)) return IL.Ceramic_Pickaxe_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawSpade)) return IL.Ceramic_Spade_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawShovel)) return IL.Ceramic_Shovel_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawUniversalSpade)) return IL.Ceramic_Universal_Spade_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawAxe)) return IL.Ceramic_Axe_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawAxeDouble)) return IL.Ceramic_Double_Axe_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawSaw)) return IL.Ceramic_Saw_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadHammer)) return IL.Ceramic_Hammer_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadFile)) return IL.Ceramic_File_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadScrewdriver)) return IL.Ceramic_Screwdriver_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawChisel)) return IL.Ceramic_Chisel_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawArrow)) return IL.Ceramic_Arrow_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawHoe)) return IL.Ceramic_Hoe_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawSense)) return IL.Ceramic_Sense_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadRawPlow)) return IL.Ceramic_Plow_Mold_Raw.get(amount);
             if (prefix.equals(OP.toolHeadBuilderwand)) return IL.Ceramic_Builderwand_Mold_Raw.get(amount);
             if (prefix.equals(OP.nugget)) return IL.Ceramic_Nugget_Mold_Raw.get(amount);
             if (prefix.equals(OP.blockSolid)) return IL.Ceramic_Basin_Raw.get(amount);
            return IL.Ceramic_Mold_Raw.get(amount);
            
        }

}
