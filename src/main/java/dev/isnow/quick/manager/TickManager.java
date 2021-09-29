package dev.isnow.quick.manager;

import dev.isnow.quick.AntiCheat;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public final class TickManager implements Runnable {

    @Getter
    private int ticks;

    @Getter
    private int a = 0;

    private static BukkitTask task;

    public void start() {
        assert task == null : "TickProcessor has already been started!";

        final long[] lastTick = { System.currentTimeMillis() };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(AntiCheat.INSTANCE.getPlugin(), new Runnable() {
            @Override
            public void run() {
                a = (int)(System.currentTimeMillis() - lastTick[0]);
                lastTick[0] = System.currentTimeMillis();
            }
        }, 1L, 1L);
        task = Bukkit.getScheduler().runTaskTimer(AntiCheat.INSTANCE.getPlugin(), this, 0L, 1L);

//        Bukkit.getScheduler().scheduleSyncRepeatingTask(AntiCheat.INSTANCE.getPlugin(), new Runnable() {
//            @Override
//            public void run() {
//                for(Player p : Bukkit.getOnlinePlayers()) {
//                    PacketEvents.get().getPlayerUtils().sendPacket(p, new WrappedPacketOutTransaction(0, (short) ThreadLocalRandom.current().nextInt(32767), false));
//                }
//            }
//        }, 1L, 0L);
    }
    public void stop() {
        if (task == null) return;

        task.cancel();
        task = null;
    }


    @Override
    public void run() {
        ++ticks;
    }
}
