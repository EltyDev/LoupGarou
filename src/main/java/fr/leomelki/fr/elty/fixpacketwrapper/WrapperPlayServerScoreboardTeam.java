package fr.leomelki.fr.elty.fixpacketwrapper;

import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WrapperPlayServerScoreboardTeam {

    @Setter @Getter private String name = "";
    @Setter @Getter private Method method = Method.CREATE_TEAM;
    @Setter @Getter private String displayName = "";
    @Setter @Getter private ChatColor color = ChatColor.WHITE;
    @Setter @Getter private String prefix = "";
    @Setter @Getter private String suffix = "";
    @Setter @Getter private List<String> players = new ArrayList<>();
    @Setter @Getter private int flags = FriendlyFlags.NONE.ordinal();
    @Setter @Getter private NameTagVisibility visibility = NameTagVisibility.ALWAYS;
    @Setter @Getter private CollisionRule rule = CollisionRule.ALWAYS;

    public enum FriendlyFlags {
        NONE,
        FRIENDLY_FIRE,
        INVISIBLE
    }

    public enum Method {
        CREATE_TEAM,
        REMOVE_TEAM,
        UPDATE_TEAM_INFO,
        ADD_ENTITIES_TEAM,
        REMOVE_ENTITIES_FROM_TEAM
    }

    public enum NameTagVisibility {

        ALWAYS("always"),
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"),
        HIDE_FOR_OWN_TEAM("hideForOwnTeam"),
        NEVER("never");

        private final String name;

        NameTagVisibility(String name) {
            this.name = name;
        }

        public String getString() {
            return name;
        }

        public static NameTagVisibility fromString(String name) {
            for (NameTagVisibility visibility : NameTagVisibility.values()) {
                if (visibility.name.equals(name))
                    return visibility;
            }
            return ALWAYS;
        }
    }

    public enum CollisionRule {

        ALWAYS("always"),
        PUSH_OTHER_TEAMS("pushOtherTeams"),
        PUSH_OWN_TEAM("pushOwnTeam"),
        NEVER("never");

        private final String name;

        CollisionRule(String name) {
            this.name = name;
        }

        public String getString() {
            return name;
        }

        public static CollisionRule fromString(String name) {
            for (CollisionRule rule : CollisionRule.values()) {
                if (rule.getString().equals(name))
                    return rule;
            }
            return ALWAYS;
        }
    }

    public WrapperPlayServerScoreboardTeam() {}

    public void setMethod(int method) {
        this.method = Method.values()[method];
    }

    public void sendPacket(Player player) {
        PacketDataSerializer packetData = new PacketDataSerializer(Unpooled.buffer());
        packetData.a(name);
        packetData.k(method.ordinal());
        switch (method) {
            case CREATE_TEAM:
            case UPDATE_TEAM_INFO:
                packetData.a(IChatBaseComponent.c(displayName));
                packetData.k(flags);
                packetData.a(visibility.getString());
                packetData.a(rule.getString());
                packetData.c(color.ordinal());
                packetData.a(IChatBaseComponent.c(prefix));
                packetData.a(IChatBaseComponent.c(suffix));
                if (method == Method.UPDATE_TEAM_INFO)
                    break;
            case ADD_ENTITIES_TEAM:
            case REMOVE_ENTITIES_FROM_TEAM:
                packetData.c(players.size());
                for (String entity : players)
                    packetData.a(entity);
        }
        ((CraftPlayer) player).getHandle().c.a(new PacketPlayOutScoreboardTeam(packetData));
    }
}
