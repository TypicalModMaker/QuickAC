package dev.isnow.quick.processor;

import dev.isnow.quick.data.PlayerData;
import io.github.retrooper.packetevents.packetwrappers.play.in.entityaction.WrappedPacketInEntityAction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionProcessor {
    public PlayerData data;

    public boolean sprinting, sneaking;

    public ActionProcessor(final PlayerData data) {
        this.data = data;

    }

    public void handleAction(WrappedPacketInEntityAction wrapped) {
        switch(wrapped.getAction()) {
            case START_SPRINTING:
                sprinting = true;
                break;
            case STOP_SPRINTING:
                sprinting = false;
                break;
            case START_SNEAKING:
                sneaking = true;
                break;
            case STOP_SNEAKING:
                sneaking = false;
        }

    }
}
