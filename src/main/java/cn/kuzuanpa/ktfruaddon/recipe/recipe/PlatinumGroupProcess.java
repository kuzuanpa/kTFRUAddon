/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.fluid.flList;
import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.material.matList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.RM;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class PlatinumGroupProcess {
    public static void init(){
    //Alkaline Ion Exchange Membrane
        recipeMaps.   HeatMixer          .addRecipe0(F,212,120,FL.array(flList.Styrene.make(1000),flList.Dichloromethane.make(1000)),FL.array(flList.VinylbenzylChloride.make(500)),ZL_IS);
        recipeMaps.   HeatMixer          .addRecipe0(F,212,320,FL.array(flList.VinylbenzylChloride.make(1000), flList.Dimethylamine.make(1000)),ZL_FS,matList.DimethylaminomethylStyrene.getDust(4));
        recipeMaps.   HeatMixer          .addRecipe1(F, 82,120,matList.DimethylaminomethylStyrene.getDust(1), FL.array(FL.Oxygen.make(1000)),ZL_FS,ItemList.AlkalineIonExchangeMembrane.get(1));

    //DinhexylSulfide
        recipeMaps.HeatMixer.addRecipe1(F,350,16 , OP.dust.mat(MT.S, 2), FL.array(FL.Butane.make(2000), FL.Ethylene.make(1000)),FL.array(flList.DinhexylSulfide.make(1000)), ZL_IS);

    //Dimethylglycine
        recipeMaps.HeatMixer.addRecipe1(F,180,90 , OP.dust.mat(MT.Al2O3, 0),FL.array(flList.Methanol.make(2000), MT.NH3.gas(U, true)),FL.array(flList.Methylamine.make(400), flList.Dimethylamine.make(300), flList.Trimethylamine.make(300)), ZL_IS);
        recipeMaps.HeatMixer.addRecipe0(F,180,90 ,FL.array(FL.BioEthanol.make(1000), flList.GlacialAceticAcid.make(1000), MT.Cl.gas(U, true), MT.H2SO4.liquid(0, false)),FL.array(flList.EthylChloroacetate.make(1000)), ZL_IS);
        recipeMaps.HeatMixer.addRecipe0(F,180,90 ,FL.array(flList.EthylChloroacetate.make(1000), flList.Dimethylamine.make(1000)),FL.array(flList.Dimethylglycine.make(1000)), ZL_IS);

    //main
        RM.BurnMixer.addRecipeX(F,180,90 , ST.array(OP.dust.mat(MT.C,1),OP.dust.mat(MT.Al,3),OP.dust.mat(MT.PlatinumGroupSludge,1)), FL.array(MT.HCl.gas(U, true), MT.Cl.gas(2*U, true)),FL.array(flList.RoughPlatinumGroupSolution.make(1500)), OP.dustSmall.mat(MT.Al2O3, 2), OP.dust.mat(matList.SilverChloride.mat, 1));
        RM.    Electrolyzer.addRecipe1(F,180,90 , OP.dust.mat(matList.SilverChloride.mat, 1), ZL_FS,FL.array(MT.Cl.gas(U,false)), OP.dust.mat(MT.Ag, 2));
        recipeMaps.HeatMixer.addRecipe0(F,180,90 , FL.array(flList.RoughPlatinumGroupSolution.make(1000), MT.SO2.gas(U, true)),FL.array(flList.SulphurPlatinumGroupSolution.make(1500)), OP.dustSmall.mat(MT.Au, 2));
        RM.    Mixer     .addRecipe0(F,180,16 , FL.array(flList.SulphurPlatinumGroupSolution.make(2000), flList.Dimethylglycine.make(1000)),FL.array(flList.ExtractedPlatinumGroupSolutionUp.make(1500),flList.ExtractedPlatinumGroupSolutionDown.make(1500)), ZL_IS);

        //Down
        RM.    Distillery.addRecipe1(F,180,128 , ST.tag(0), FL.array(flList.ExtractedPlatinumGroupSolutionDown.make(1500)),FL.array(flList.DistilledPlatinumGroupSolutionDown.make(500)), OP.dust.mat(matList.OsmiumTetraoxide.mat, 2));
        RM.    Mixer     .addRecipe0(F,16,220 , FL.array(flList.DistilledPlatinumGroupSolutionDown.make(1000), flList.Trimethylamine.make(500), MT.HNO3.liquid(3 * U, true)),FL.array(flList.NitricPlatinumGroupSolutionUp.make(2000), flList.NitricPlatinumGroupSolutionDown.make(2000)), ZL_IS);
        RM.    Electrolyzer.addRecipe1(F,180,16, ItemList.AlkalineIonExchangeMembrane.get(1), FL.array(flList.NitricPlatinumGroupSolutionDown.make(1000)),FL.array(flList.ElectrolyzedNitricPlatinumGroupSolution.make(1000)), ItemList.IrAlkalineIonExchangeMembrane.get(1));
        recipeMaps.HeatMixer.addRecipe2(F,180,16 , OP.dust.mat(matList.AmmoniumChloride.mat, 1), OP.dust.mat(MT.NaCl, 1), FL.array(flList.ElectrolyzedNitricPlatinumGroupSolution.make(1000)),FL.array(), OP.dust.mat(MT.NaNO3, 2), OP.dust.mat(matList.AmmoniumHexachlororhodate.mat, 5));
        RM.    Mixer.addRecipe1(F,16,220 , ItemList.IrAlkalineIonExchangeMembrane.get(1), FL.array(MT.SO2.gas(2*U, true), MT.H2SO4.liquid(U2, true)),FL.array(flList.IridiumPlatinumGroupSolution.make(500)), ItemList.AlkalineIonExchangeMembrane.get(1));
        recipeMaps.HeatMixer.addRecipe1(F,180,16 , OP.dust.mat(matList.AmmoniumChloride.mat, 1), FL.array(flList.IridiumPlatinumGroupSolution.make(1000), MT.Cl.gas(2 * U, true)),FL.array(MT.H2SO4.liquid(U, true)), OP.dust.mat(matList.AmmoniumHexachloroiridate.mat, 7));
        recipeMaps.HeatMixer.addRecipe1(F,180,16 , OP.dust.mat(MT.NaOH, 3), FL.array(flList.NitricPlatinumGroupSolutionUp.make(1000)),FL.array(flList.DeprecatedNitricPlatinumGroupSolutionUp.make(1000)), OP.dust.mat(MT.Rh, 3));//todo: Rh -> RhOH
        recipeMaps.HeatMixer.addRecipe0(F,180,16 , FL.array(flList.DeprecatedNitricPlatinumGroupSolutionUp.make(2000), MT.HCl.gas(U, true)),FL.array(flList.Trimethylamine.make(500)), OP.dust.mat(MT.NaCl, 1));

        //Up
        recipeMaps.HeatMixer.addRecipe0(F,180,16 , FL.array(flList.ExtractedPlatinumGroupSolutionUp.make(1500), MT.HCl.gas(U, true)),FL.array(flList.Dimethylglycine.make(1000), flList.AcidPlatinumGroupSolutionUp.make(500)), ZL_IS);
        recipeMaps.HeatMixer.addRecipe0(F,180,16 , FL.array(flList.DinhexylSulfide.make(1000), flList.AcidPlatinumGroupSolutionUp.make(500)),FL.array(flList.DinhexylSulfidePlatinumGroupSolution.make(1000)), OP.dust.mat(MT.Pt, 2));//todo: Pt -> PtCl4
        recipeMaps.HeatMixer.addRecipe0(F,180,16 , FL.array(flList.DinhexylSulfidePlatinumGroupSolution.make(1000), MT.NH3.gas(U, true), MT.HCl.gas(U, true)),FL.array(flList.DinhexylSulfide.make(1000)), OP.dust.mat(matList.AmmoniumHexachloropalladate.mat, 4));
    }
}
 