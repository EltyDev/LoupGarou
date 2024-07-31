package fr.leomelki.com.comphenix.packetwrapper.wrappers.status.serverbound;

import fr.leomelki.com.comphenix.packetwrapper.wrappers.AbstractPacket;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperStatusClientStart extends AbstractPacket {

    public static final PacketType TYPE = PacketType.Status.Client.START;

    public WrapperStatusClientStart() {
        super(TYPE);
    }

    public WrapperStatusClientStart(PacketContainer packet) {
        super(packet, TYPE);
    }


}
