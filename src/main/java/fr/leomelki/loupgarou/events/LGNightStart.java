package fr.leomelki.loupgarou.events;

import fr.leomelki.loupgarou.classes.LGGame;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

public class LGNightStart extends LGEvent implements Cancellable{

	public LGNightStart(LGGame game) {
		super(game);
	}

	@Getter @Setter boolean cancelled;

}
