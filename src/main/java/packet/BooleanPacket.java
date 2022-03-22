package packet;

import java.io.IOException;
import java.nio.ByteBuffer;

public final class BooleanPacket extends TriePacket {
    public static final byte ID = 2;
    private final boolean value;

    public BooleanPacket(boolean value) {
        super(ID);
        this.value = value;
    }

    public BooleanPacket(ByteBuffer buf) throws IOException {
        super(ID);
        if (buf.remaining() != 1)
            throw new IOException("Invalid data length.  Expected 1, got %d".formatted(buf.remaining()));

        this.value = buf.get() == 1;
    }


    @Override
    protected byte[] serialiseData() {
        return new byte[]{(byte) (value ? 1 : 0)};
    }
}
