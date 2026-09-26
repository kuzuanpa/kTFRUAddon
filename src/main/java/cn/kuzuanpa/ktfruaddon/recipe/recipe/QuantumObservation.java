/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 */
package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.material.prefix.prefixList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.api.tile.GTTileEntityRegistry;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.util.ST;

import static gregapi.data.CS.F;
import static gregapi.data.CS.ZL_FS;

/** Q3 recipes connecting raw quantum measurement to reusable research data. */
public final class QuantumObservation {
    private QuantumObservation() { }

    public static void init() {
        MultiTileEntityRegistry registry = GTTileEntityRegistry.ktfruaddon;

        // Q3-R01: direct measurement of a prepared Quantum Gold field.
        recipeMaps.QuantumObservation.addRecipeX(F, 16384, 4800,
                ST.array(OP.plate.mat(matList.QuantumGold.mat, 1),
                        OP.foil.mat(matList.MirrorGold.mat, 2),
                        prefixList.AbsolutelyPureDust.mat(matList.Ij.mat, 4),
                        prefixList.AbsolutelyPureDust.mat(MT.Nq_522, 4)),
                ZL_FS, ZL_FS, ItemList.QuantumObservationData.get(1));

        // Q3-R02: a lower-yield calibration pass using existing readout coils.
        recipeMaps.QuantumObservation.addRecipeX(F, 8192, 2400,
                ST.array(OP.foil.mat(matList.QuantumGold.mat, 1),
                        registry.getItem(31114, 2),
                        prefixList.AnalyticalPureDust.mat(MT.Pt, 4)),
                ZL_FS, ZL_FS, ItemList.QuantumObservationData.get(2));

    }
}
