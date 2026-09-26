/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.item.items.random;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemParticle extends MultiItemRandom {
    public itemParticle() {
        super(MOD_ID, "ktfru.item.particle");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Particles", this, (short) 1000));
    }

    @Override
    public void addItems() {

        //Copy From GT6U
        ItemList.Proton.set(addItem(1000, "Proton", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Anti_Proton.set(addItem(1001, "Anti Proton", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Electron.set(addItem(1002, "Electron", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Positron.set(addItem(1003, "Positron (Anti Electron)", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Neutron.set(addItem(1004, "Neutron", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Neutrino.set(addItem(1005, "Neutrino", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Anti_Neutrino.set(addItem(1006, "Anti Neutrino", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Alpha_Particle.set(addItem(1007, "Alpha Particle", "The nucleus of helium. "));
        ItemList.Higgs_Boson.set(addItem(1008, "Higgs-Boson", "A Standard Model particle. Origin of mass. "));
        ItemList.Kerr_Blackhole.set(addItem(1009, "Kerr Blackhole", "An extremely rare tiny blackhole that can be manually produced in particle collider "));

        ItemList.LaserTargetDT.set(addItem(1100, "Deuterium-Tritium Target Pellet", "Consumed by a laser fusion pulse."));
        ItemList.LaserTargetLi6.set(addItem(1101, "Lithium-6 Breeding Target", "Consumes a laser pulse to breed tritium."));
        ItemList.LaserTargetLead.set(addItem(1102, "Lead Isotope Target", "Industrial laser fusion target."));
        ItemList.LaserTargetTantalum.set(addItem(1103, "Tantalum Isotope Target", "Industrial laser fusion target."));
        ItemList.LaserTargetGraphite.set(addItem(1104, "Graphite Compression Target", "Industrial laser fusion target."));
        ItemList.LaserTargetBismuth.set(addItem(1105, "Bismuth Superheavy Target", "High-energy industrial laser fusion target."));

        // These are deliberately artificial nuclear materials. They have no ore
        // dictionary entries and are consumed only by the late-game fusion chain.
        ItemList.NeutronRichBismuth.set(addItem(1106, "Neutron-Rich Bismuth Isotope", "Artificial isotope from an industrial lead target."));
        ItemList.MetastableTantalum.set(addItem(1107, "Metastable Tantalum Isotope", "Artificial isotope for high-energy control components."));
        ItemList.DenseGraphenePrecursor.set(addItem(1108, "High-Density Graphene Precursor", "Laser-compressed carbon precursor; refine it before use."));
        ItemList.NuclearTargetSubstrate.set(addItem(1109, "Nuclear Target Substrate", "Neutron-conditioned substrate for superheavy target synthesis."));
        ItemList.QuantumControlElement.set(addItem(1110, "High-Energy Quantum Control Element", "A metastable-tantalum control component for later quantum machinery."));
        ItemList. SuperheavyNuclidePrecursor.set(addItem(1111, "Superheavy Nuclide Precursor", "Short-lived artificial nuclear matter requiring immediate refinement."));
        ItemList.NaquadriaPrecursor.set(addItem(1112, "Naquadria Precursor", "Fusion-made precursor for the late-game Naquadria material chain."));
        ItemList.QuantumObservationData.set(addItem(1113, "Quantum Observation Data", "A reproducible raw measurement record from the Quantum Observation Tower, ready for state analysis."));

    }
}
