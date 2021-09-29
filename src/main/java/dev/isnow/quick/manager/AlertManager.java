package dev.isnow.quick.manager;

import dev.isnow.quick.AntiCheat;
import dev.isnow.quick.check.Check;
import dev.isnow.quick.data.PlayerData;
import dev.isnow.quick.util.ColorUtil;
import io.github.retrooper.packetevents.PacketEvents;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashSet;
import java.util.Set;

@Getter
public final class AlertManager {

    private static final Set<PlayerData> alerts = new HashSet<>();

    public static ToggleAlertType toggleAlerts(final PlayerData data) {
        if (alerts.contains(data)) {
            alerts.remove(data);
            return ToggleAlertType.REMOVE;
        } else {
            alerts.add(data);
            return ToggleAlertType.ADD;
        }
    }

    public static void handleAlert(final Check check, final PlayerData data, final String info) {

        if(check.getCheckInfo() != null) {

            final TextComponent alertMessage = new TextComponent(ColorUtil.translate("&8[")
                    .replaceAll("%player%", data.getPlayer().getName())
                    .replaceAll("%check%", check.getCheckInfo().name())
                    .replaceAll("%dev%", check.getCheckInfo().experimental() ? ColorUtil.translate("&7*") : "")
                    .replaceAll("%vl%", Integer.toString(check.getVl()))
                    .replaceAll("%type%", check.getCheckInfo().type()));

            alertMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + data.getPlayer().getName()));
            alertMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ColorUtil.translate(
                    "&cDescription: &f" + check.getCheckInfo().description() +
                            "\n&cInfo: &b" + info +
                            "\n&cPing: &b" + PacketEvents.get().getPlayerUtils().getPing(data.getPlayer()) +
                            "\n&cTPS: &f" + String.format("%.2f", Math.min(20, PacketEvents.get().getServerUtils().getTPS())) +
                            "\n&cClick to teleport.")).create()));

            alerts.forEach(player -> player.getPlayer().spigot().sendMessage(alertMessage));
        }
    }

    public static void handleAlertLag(final Check check, final PlayerData data, final String info) {
        if(check.getCheckInfo() != null) {
            alerts.forEach(player -> player.getPlayer().sendMessage(ColorUtil.translate("" + player.getPlayer().getName() + " would flag for " + check.getCheckInfo().name()) + ", but server skipped " + AntiCheat.INSTANCE.getTickManager().getA() + "MS/" + (AntiCheat.INSTANCE.getTickManager().getA() / 50) + " Ticks."));
        }
    }

    public enum ToggleAlertType {
        ADD, REMOVE
    }
}

