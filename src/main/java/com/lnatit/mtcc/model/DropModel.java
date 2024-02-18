package com.lnatit.mtcc.model;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.IModelBuilder;

public class DropModel implements IModelBuilder<DropModel>
{
    @Override
    public DropModel addCulledFace(Direction facing, BakedQuad quad)
    {
        return null;
    }

    @Override
    public DropModel addUnculledFace(BakedQuad quad)
    {
        return null;
    }

    @Override
    public BakedModel build()
    {
        return null;
    }
}
