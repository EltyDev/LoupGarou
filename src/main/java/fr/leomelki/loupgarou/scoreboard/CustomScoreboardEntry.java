package fr.leomelki.loupgarou.scoreboard;

import java.util.Arrays;

import com.comphenix.protocol.wrappers.EnumWrappers.ScoreboardAction;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.leomelki.com.comphenix.packetwrapper.wrappers.play.clientbound.WrapperPlayServerScoreboardScore;
import fr.leomelki.fr.elty.fixpacketwrapper.WrapperPlayServerScoreboardTeam;
import fr.leomelki.loupgarou.utils.VariousUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Score;

public class CustomScoreboardEntry {
	private static WrappedChatComponent nullComponent = WrappedChatComponent.fromText("");
	
	//setter car flemme de modifier le systeme pour le rendre plus logique
	@Getter @Setter private int score;
	private final String name;
	private final CustomScoreboard scoreboard;
	private WrappedChatComponent prefix, suffix;
	@Getter private String displayName = "";

	public CustomScoreboardEntry(int score, CustomScoreboard scoreboard) {
		this.score = score;
		this.scoreboard = scoreboard;
		this.name = "§"+VariousUtils.toHex(score);
	}
	
	public void show() {
		if(prefix != null) {
			WrapperPlayServerScoreboardTeam team = new WrapperPlayServerScoreboardTeam();
			team.setPlayers(Arrays.asList(name));
			team.setName(name);
			team.setMethod(0);
			team.setPrefix(prefix.toString());
			if(suffix != null)
				team.setSuffix(suffix.toString());
			team.sendPacket(scoreboard.getPlayer().getPlayer());
			Score boardScore =  scoreboard.getObjective().getScore(displayName);
			boardScore.setScore(score);
		}
	}
	
	public void setDisplayName(String displayName) {
		scoreboard.getBoard().resetScores(this.displayName);
		this.displayName = displayName;
		boolean spawn = prefix == null;
		if(displayName.length() > 16) {
			char colorCode = 'f';
			int limit = displayName.charAt(14) == '§' && displayName.charAt(13) != '§' ? 14 : displayName.charAt(15) == '§' ? 15 : 16;
			String prefixStr = displayName.substring(0, limit);
			
			prefix = WrappedChatComponent.fromText(prefixStr);
			
			if(limit == 16) {
				boolean storeColorCode = false;
				for(char c : prefixStr.toCharArray())
					if(storeColorCode) {
						storeColorCode = false;
						colorCode = c;
					}else
						if(c == '§')
							storeColorCode = true;
				suffix = WrappedChatComponent.fromText("§"+colorCode+displayName.substring(limit));
			}else
				suffix = WrappedChatComponent.fromText(displayName.substring(limit));
		} else {
			prefix = WrappedChatComponent.fromText(displayName);
			suffix = nullComponent;
		}
		
		if(scoreboard.isShown()) {
			show();
			if (!spawn) {
				WrapperPlayServerScoreboardTeam team = new WrapperPlayServerScoreboardTeam();
				team.setPlayers(Arrays.asList(name));
				team.setName(name);
				team.setMethod(2);
				team.setPrefix(prefix.toString());
				if(suffix != null)
					team.setSuffix(suffix.toString());
				team.sendPacket(scoreboard.getPlayer().getPlayer());
			}
		}
	}
	public void delete() {
		hide();
		prefix = null;
		displayName = "";
	}
	public void hide() {
		if(prefix != null && scoreboard.isShown()) {

			scoreboard.getBoard().resetScores(displayName);
			
			WrapperPlayServerScoreboardTeam team = new WrapperPlayServerScoreboardTeam();
			team.setName(name);
			team.setMethod(1);
			team.sendPacket(scoreboard.getPlayer().getPlayer());
		}
	}

}
