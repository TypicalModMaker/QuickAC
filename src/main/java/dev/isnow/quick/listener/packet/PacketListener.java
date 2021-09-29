package dev.isnow.quick.listener.packet;

import dev.isnow.quick.AntiCheat;
import dev.isnow.quick.data.PlayerData;
import dev.isnow.quick.packet.processor.ReceivingPacketProcessor;
import dev.isnow.quick.packet.util.Packet;
import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlaySendEvent;
import io.github.retrooper.packetevents.event.priority.PacketEventPriority;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * In this class we listen to all of the packets being transmitted while we are playing the game.
 * We don't listen to login etc packets to save a bit of performance as thread switching every packet is not the way to go lol.
 *
 * When we receive a packet the onPacketPlayReceive event is called and we can deal with the packet there.
 * And when we send a packet we can listen to it on onPacketPlaySend.
 */
public final class PacketListener extends PacketListenerAbstract {

    private final ExecutorService packetExecutor = Executors.newSingleThreadExecutor();

    public PacketListener() {
        super(PacketEventPriority.MONITOR);
    }

    @Override
    public void onPacketPlayReceive(final PacketPlayReceiveEvent event) {
        final PlayerData data = AntiCheat.INSTANCE.getPlayerDataManager().get(event.getPlayer());

        if (data != null) {
            packetExecutor.execute(() -> ReceivingPacketProcessor.process(
                    data, new Packet(Packet.Direction.RECEIVE, event.getNMSPacket(), event.getPacketId(), event.getTimestamp()))
            );
        }
    }

    @Override
    public void onPacketPlaySend(final PacketPlaySendEvent event) {
        final PlayerData data = AntiCheat.INSTANCE.getPlayerDataManager().get(event.getPlayer());

        if (data != null) {
            packetExecutor.execute(() -> ReceivingPacketProcessor.process(
                    data, new Packet(Packet.Direction.SEND, event.getNMSPacket(), event.getPacketId(), event.getTimestamp()))
            );
        }
    }

}
