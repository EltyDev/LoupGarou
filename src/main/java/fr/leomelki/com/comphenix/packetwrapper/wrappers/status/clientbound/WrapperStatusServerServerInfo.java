package fr.leomelki.com.comphenix.packetwrapper.wrappers.status.clientbound;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import fr.leomelki.com.comphenix.packetwrapper.util.TestExclusion;
import fr.leomelki.com.comphenix.packetwrapper.wrappers.AbstractPacket;

public class WrapperStatusServerServerInfo extends AbstractPacket {

    public static final PacketType TYPE = PacketType.Status.Server.SERVER_INFO;

    public WrapperStatusServerServerInfo() {
        super(TYPE);
    }

    public WrapperStatusServerServerInfo(PacketContainer packet) {
        super(packet, TYPE);
    }

    /**
     * Retrieves the value of field 'status'
     *
     * @return 'status'
     */
    public WrappedServerPing getStatus() {
        return this.handle.getServerPings().read(0);
    }

    /**
     * Sets the value of field 'status'
     *
     * @param value New value for field 'status'
     */
    @TestExclusion
    public void setStatus(WrappedServerPing value) {
        this.handle.getServerPings().write(0, value);
    }

}
