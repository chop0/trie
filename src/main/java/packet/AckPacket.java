package packet;

import java.io.IOException;
import java.nio.ByteBuffer;

public final class AckPacket extends TriePacket {
    public static final byte ID = 1;

    public AckPacket() {
        super(ID);
    }

    public AckPacket(ByteBuffer buf) throws IOException {
        super(ID);
        if (buf.remaining() != 0)
            throw new IOException("Invalid data length.  Expected 0, got %d".formatted(buf.remaining()));
    }

    @Override
    protected byte[] serialiseData() {
        return new byte[0];
    }
}
