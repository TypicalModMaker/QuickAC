package dev.isnow.quick.packet.processor;

import dev.isnow.quick.data.PlayerData;
import dev.isnow.quick.packet.util.Packet;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class ReceivingPacketProcessor {
    public void process(final PlayerData data, final Packet packet) {
        if (PacketType.Play.Client.Util.isInstanceOfFlying(packet.getPacketId())) {
            final WrappedPacketInFlying wrapper = new WrappedPacketInFlying(packet.getRawPacket());
            if (Math.abs(wrapper.getX()) > 1.0E+7
                    || Math.abs(wrapper.getY()) > 1.0E+7
                    || Math.abs(wrapper.getZ()) > 1.0E+7
                    || Math.abs(wrapper.getPitch()) > 1.0E+7
                    || Math.abs(wrapper.getYaw()) > 1.0E+7) {
                return;
            }
        }
        if(packet.isFlying()) {
            final WrappedPacketInFlying wrapper = new WrappedPacketInFlying(packet.getRawPacket());

            data.getPositionProcessor().handle(wrapper);
        }
        data.getChecks().forEach(check -> check.onPacketReceive(packet));
    }
}
