package net.insomniakitten.bamboo;

import net.insomniakitten.bamboo.event.BambooRenderEvents;
import net.insomniakitten.bamboo.event.HopperRenderEvents;
import net.insomniakitten.bamboo.event.SlabInteractionEvents;
import net.insomniakitten.bamboo.world.GeneratorBamboo;
import net.insomniakitten.bamboo.world.GeneratorSaltOre;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Bamboozled.ID, name = Bamboozled.NAME, version = Bamboozled.VERSION)
public final class Bamboozled {

    public static final String ID = "bamboozled";
    public static final String NAME = "Bamboozled";
    public static final String VERSION = "%VERSION%";

    public static final Logger LOGGER = LogManager.getLogger(ID);

    @GameRegistry.ItemStackHolder(ID + ":bamboo")
    public static final ItemStack ICON = ItemStack.EMPTY;

    public static final CreativeTabs TAB = new CreativeTabs(ID) {
        @Override
        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel() {
            return "tab." + ID + ".label";
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return ICON;
        }
    };

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        if (BamboozledConfig.WORLD.generateBamboo) {
            MinecraftForge.EVENT_BUS.register(new GeneratorBamboo());
        }
        if (BamboozledConfig.WORLD.generateSaltOre) {
            MinecraftForge.EVENT_BUS.register(new GeneratorSaltOre());
        }
        if (BamboozledConfig.GENERAL.advancedSlabInteraction) {
            MinecraftForge.EVENT_BUS.register(new SlabInteractionEvents());
        }
        if (BamboozledConfig.GENERAL.fancyBamboo) {
            MinecraftForge.EVENT_BUS.register(new BambooRenderEvents());
        }
        if (BamboozledConfig.GENERAL.fancyHopper) {
            MinecraftForge.EVENT_BUS.register(new HopperRenderEvents());
        }
    }

}
