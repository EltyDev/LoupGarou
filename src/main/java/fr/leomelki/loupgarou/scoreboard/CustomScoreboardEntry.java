package fr.leomelki.loupgarou.scoreboard;

import java.util.Arrays;

import com.comphenix.protocol.wrappers.EnumWrappers.ScoreboardAction;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import fr.leomelki.com.comphenix.packetwrapper.wrappers.play.clientbound.WrapperPlayServerScoreboardScore;
import fr.leomelki.com.comphenix.packetwrapper.wrappers.play.clientbound.WrapperPlayServerScoreboardTeam;
import fr.leomelki.loupgarou.utils.VariousUtils;
import lombok.Getter;
import lombok.Setter;

public class CustomScoreboardEntry {
	private static WrappedChatComponent nullComponent = WrappedChatComponent.fromText("");
	
	//setter car flemme de modifier le systeme pour le rendre plus logique
	@Getter @Setter private int score;
	private final String name;
	private final CustomScoreboard scoreboard;
	private WrappedChatComponent prefix, suffix;

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
			WrapperPlayServerScoreboardTeam.WrappedParameters params = new WrapperPlayServerScoreboardTeam.WrappedParameters();
			params.setPlayerPrefix(prefix);
			if(suffix != null)
				params.setPlayerSuffix(suffix);
			team.setParameters(params);
			team.sendPacket(scoreboard.getPlayer().getPlayer());
			
			WrapperPlayServerScoreboardScore score = new WrapperPlayServerScoreboardScore();
			score.setObjectiveName(scoreboard.getName());
			score.setMethod(ScoreboardAction.CHANGE);
			score.setObjectiveName(name);
			score.setScore(this.score);
			score.sendPacket(scoreboard.getPlayer().getPlayer());
		}
	}
	
	public void setDisplayName(String displayName) {
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
			if(spawn)
				show();
			else {
				WrapperPlayServerScoreboardTeam team = new WrapperPlayServerScoreboardTeam();
				team.setPlayers(Arrays.asList(name));
				team.setName(name);
				team.setMethod(2);
				WrapperPlayServerScoreboardTeam.WrappedParameters params = new WrapperPlayServerScoreboardTeam.WrappedParameters();
				params.setPlayerPrefix(prefix);
				if(suffix != null)
					params.setPlayerSuffix(suffix);
				team.setParameters(params);
				team.sendPacket(scoreboard.getPlayer().getPlayer());
			}
		}
	}
	public void delete() {
		hide();
		prefix = null;
	}
	public void hide() {
		if(prefix != null && scoreboard.isShown()) {
			WrapperPlayServerScoreboardScore score = new WrapperPlayServerScoreboardScore();
			score.setObjectiveName(scoreboard.getName());
			score.setMethod(ScoreboardAction.REMOVE);
			score.setObjectiveName(name);
			score.sendPacket(scoreboard.getPlayer().getPlayer());
			
			WrapperPlayServerScoreboardTeam team = new WrapperPlayServerScoreboardTeam();
			team.setName(name);
			team.setMethod(1);
			team.sendPacket(scoreboard.getPlayer().getPlayer());
		}
	}

}
