package fr.leomelki.loupgarou.scoreboard;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.comphenix.protocol.wrappers.WrappedChatComponent;

import fr.leomelki.com.comphenix.packetwrapper.PacketWrapper;
import fr.leomelki.com.comphenix.packetwrapper.wrappers.play.clientbound.WrapperPlayServerScoreboardDisplayObjective;
import fr.leomelki.com.comphenix.packetwrapper.wrappers.play.clientbound.WrapperPlayServerScoreboardObjective;
import fr.leomelki.loupgarou.classes.LGPlayer;
import fr.leomelki.loupgarou.utils.RandomString;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.world.scores.ScoreboardObjective;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class CustomScoreboard {
	@Getter private final String name = RandomString.generate(16);
	@Getter private final String displayName;
	private final List<CustomScoreboardEntry> entries = Arrays.asList(new CustomScoreboardEntry(15, this), new CustomScoreboardEntry(14, this), new CustomScoreboardEntry(13, this),
																			  new CustomScoreboardEntry(12, this), new CustomScoreboardEntry(11, this), new CustomScoreboardEntry(10, this),
																			  new CustomScoreboardEntry(9, this), new CustomScoreboardEntry(8, this), new CustomScoreboardEntry(7, this),
																			  new CustomScoreboardEntry(6, this), new CustomScoreboardEntry(5, this), new CustomScoreboardEntry(4, this),
																			  new CustomScoreboardEntry(3, this), new CustomScoreboardEntry(2, this), new CustomScoreboardEntry(1, this),
																			  new CustomScoreboardEntry(0, this));
	@Getter private final LGPlayer player;
	@Getter private boolean shown;

	@Getter private final Scoreboard board;
	@Getter private final Objective objective;

	public CustomScoreboard(String displayName, LGPlayer player) {
		this.player = player;
		this.displayName = displayName;
		this.board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
		this.objective = board.registerNewObjective(name, Criteria.DUMMY, displayName);
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.objective.setDisplayName(displayName);
	}

	public CustomScoreboardEntry getLine(int index) {
		return entries.get(index);
	}

	public void show() {

		shown = true;
		player.getPlayer().setScoreboard(board);
		for(CustomScoreboardEntry entry : entries)
			entry.show();
	}

	public void hide() {
		player.getPlayer().setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());

		for(CustomScoreboardEntry entry : entries)
			entry.hide();

		shown = false;
	}
}
