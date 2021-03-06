package net.insomniakitten.bamboo.client;

import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface BlockModelMapper {

    @SideOnly(Side.CLIENT)
    IStateMapper getModelMapper();

}
