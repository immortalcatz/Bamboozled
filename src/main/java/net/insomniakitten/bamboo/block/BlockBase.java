package net.insomniakitten.bamboo.block;

import net.insomniakitten.bamboo.Bamboozled;
import net.insomniakitten.bamboo.util.RayTraceHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class BlockBase extends Block {

    private boolean fullBlock = true;
    private boolean opaqueBlock = true;
    private String name;

    public BlockBase(Material material, MapColor mapColor, SoundType sound, float hardness, float resistance) {
        super(material, mapColor);
        setHardness(hardness);
        setResistance(resistance);
        setSoundType(sound);
        setCreativeTab(Bamboozled.TAB);
    }

    public BlockBase(Material material, SoundType sound, float hardness, float resistance) {
        this(material, material.getMaterialMapColor(), sound, hardness, resistance);
    }

    public final void setFullBlock(boolean fullBlock) {
        this.fullBlock = fullBlock;
    }

    public void setOpaqueBlock(boolean opaqueBlock) {
        this.opaqueBlock = opaqueBlock;
    }

    public void getCollisionBoxes(IBlockState state, IBlockAccess world, BlockPos pos, List<AxisAlignedBB> boxes) {
        boxes.add(FULL_BLOCK_AABB);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return fullBlock;
    }

    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        List<AxisAlignedBB> boxes = new ArrayList<>();
        getCollisionBoxes(state, world, pos, boxes);
        return !boxes.isEmpty() ? boxes.get(0) : FULL_BLOCK_AABB;
    }

    @Override
    @Deprecated
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return fullBlock ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    @Deprecated
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean isActualState) {
        if (!isActualState) state = state.getActualState(world, pos);
        List<AxisAlignedBB> boxes = new ArrayList<>();
        getCollisionBoxes(state, world, pos, boxes);
        for (AxisAlignedBB box : boxes) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return opaqueBlock;
    }

    @Override
    @Deprecated
    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
        List<AxisAlignedBB> boxes = new ArrayList<>();
        getCollisionBoxes(state, world, pos, boxes);

        if (boxes.size() <= 1) {
            AxisAlignedBB box = !boxes.isEmpty() ? boxes.get(0) : Block.FULL_BLOCK_AABB;
            return rayTrace(pos, start, end, box);
        }

        return RayTraceHelper.rayTraceMultiAABB(boxes, pos, start, end);
    }

    @Override
    public final Block setUnlocalizedName(String name) {
        return super.setUnlocalizedName(Bamboozled.ID + "." + name);
    }

    @Override
    public final String getUnlocalizedName() {
        if (name == null) {
            name = super.getUnlocalizedName()
                    .replace("tile.", "block.");
        }
        return name;
    }

    @Override
    @Deprecated
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return fullBlock ? EnumPushReaction.NORMAL : EnumPushReaction.DESTROY;
    }

}
