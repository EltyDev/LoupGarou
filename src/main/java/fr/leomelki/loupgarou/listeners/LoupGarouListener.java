package fr.leomelki.loupgarou.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.comphenix.protocol.wrappers.EnumWrappers.ScoreboardAction;

import fr.leomelki.com.comphenix.packetwrapper.wrappers.play.clientbound.WrapperPlayServerScoreboardScore;
import fr.leomelki.com.comphenix.packetwrapper.wrappers.play.clientbound.WrapperPlayServerScoreboardTeam;
import fr.leomelki.loupgarou.events.LGGameJoinEvent;

public class LoupGarouListener implements Listener {
	@EventHandler
	public void onGameJoin(LGGameJoinEvent e) {
		//Tous les loups-garous
		WrapperPlayServerScoreboardTeam teamDelete = new WrapperPlayServerScoreboardTeam();
		teamDelete.setMethod(1);
		teamDelete.setName("loup_garou_list");
		
		teamDelete.sendPacket(e.getPlayer().getPlayer());
		
		//Loup-Garou noir
		WrapperPlayServerScoreboardScore score = new WrapperPlayServerScoreboardScore();
		score.setObjectiveName("lg_scoreboard");
		score.setScore(0);
		score.setObjectiveName("été");
		score.setMethod(ScoreboardAction.REMOVE);
		score.sendPacket(e.getPlayer().getPlayer());
	}
}
