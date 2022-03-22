package packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public abstract sealed class TriePacket permits AckPacket, BooleanPacket, WordPacket {
    private final byte id;

    protected TriePacket(byte id) {
        this.id = id;
    }

    public static TriePacket deserialise(DatagramPacket packet) throws IOException {
        var data = ByteBuffer.wrap(packet.getData(), packet.getOffset(), packet.getLength() - packet.getOffset() - 1);

        int expectedHashCode = data.getInt();
        byte id = data.get();
        return switch (id) {
            case WordPacket.ID -> {
                var result = new WordPacket(data);

                if (result.hashCode() != expectedHashCode)
                    throw new IOException("Incorrect hashcode on packet.  Expected %d, got %d".formatted(expectedHashCode
                            , result.hashCode()));

                yield result;
            }
            case AckPacket.ID -> new AckPacket();

            case default -> null;
        };
    }

    protected abstract byte[] serialiseData();
    public byte[] serialise() {
        var data = serialiseData();
        var buf = ByteBuffer.allocate(5 + data.length);

        buf.putInt(hashCode());
        buf.put(id);
        buf.put(data);

        return buf.array();
    }
}
