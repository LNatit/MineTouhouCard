package lnatit.mcardsth.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class FakeClone extends PlayerEvent.Clone
{
    public FakeClone(PlayerEntity _new, PlayerEntity oldPlayer, boolean wasDeath)
    {
        super(_new, oldPlayer, wasDeath);
    }
}
