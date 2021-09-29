package dev.isnow.quick.check.impl.combat;

import dev.isnow.quick.check.Check;
import dev.isnow.quick.check.api.CheckInfo;
import dev.isnow.quick.data.PlayerData;
import dev.isnow.quick.packet.util.Packet;

@CheckInfo(name = "Aura", type = "A", description = "Detects keepsprint.")
public final class AuraA extends Check {


    public AuraA(final PlayerData data) {
        super(data);
    }

    @Override
    public void onPacketReceive(final Packet packet) {
        if(packet.isUseEntity()) {
            if(data.getActionProcessor().isSprinting()) {
                fail();
            }
        }
    }

    @Override
    public void onPacketSend(final Packet packet) {

    }
}
