package dev.isnow.quick.packet.processor;

import dev.isnow.quick.data.PlayerData;
import dev.isnow.quick.packet.util.Packet;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class SendingPacketProcessor {
    public void process(final Packet packet, final PlayerData data) {
        data.getChecks().forEach(check -> check.onPacketSend(packet));
    }
}
