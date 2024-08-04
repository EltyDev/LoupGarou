package fr.leomelki.loupgarou.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import fr.leomelki.com.comphenix.packetwrapper.PacketWrapper;
import fr.leomelki.com.comphenix.packetwrapper.wrappers.play.clientbound.WrapperPlayServerInitializeBorder;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

public class VariousUtils {
	public static double distanceSquaredXZ(Location from, Location to) {
		return Math.pow(from.getX()-to.getX(), 2)+Math.pow(from.getZ()-to.getZ(), 2);
	}
	public static void setWarning(Player p, boolean warning) {
		WrapperPlayServerInitializeBorder border = new WrapperPlayServerInitializeBorder();
		WorldBorder wb = p.getWorld().getWorldBorder();
		border.setNewAbsoluteMaxSize(29999984);
		border.setNewCenterX(p.getLocation().getX());
		border.setNewCenterZ(p.getLocation().getZ());
		border.setOldSize(wb.getSize());
		border.setNewSize(wb.getSize());
		border.setWarningTime(warning ? (int) wb.getSize() : wb.getWarningDistance());
		border.setWarningBlocks(0);
		border.sendPacket(p);
    }
	private static char[] hex = "0123456789abcdef".toCharArray();
	public static char toHex(int i) {
		return hex[i];
	}

}
