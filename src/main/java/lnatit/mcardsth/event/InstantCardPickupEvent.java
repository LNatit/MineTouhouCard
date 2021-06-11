package lnatit.mcardsth.event;

import lnatit.mcardsth.item.InstantCard;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Deprecated
@Cancelable
@Event.HasResult
public class InstantCardPickupEvent extends PlayerEvent
{
    private final InstantCard card;

    public InstantCardPickupEvent(PlayerEntity player, InstantCard card)
    {
        super(player);
        this.card = card;
    }

    public InstantCard getCard()
    {
        return card;
    }
}
