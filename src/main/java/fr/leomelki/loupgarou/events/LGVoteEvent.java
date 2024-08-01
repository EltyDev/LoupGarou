package fr.leomelki.loupgarou.events;

import fr.leomelki.loupgarou.classes.LGGame;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

public class LGVoteEvent extends LGEvent implements Cancellable{
	public LGVoteEvent(LGGame game) {
		super(game);
	}

	@Getter @Setter private boolean cancelled;
}
