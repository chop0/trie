package packet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class WordPacket extends TriePacket {
    public static final byte ID = 0;

    public enum Operation {
        INSERT,
        CONTAINS,
        REMOVE
    }

    public final Operation operation;
    public final String word;

    public WordPacket(Operation operation, String word) {
        super(ID);
        this.operation = operation;
        this.word = word;
    }

    public WordPacket(ByteBuffer buf) throws IOException {
        super(ID);
        System.out.println(buf.capacity());
        int length = buf.getInt();
        if (length != buf.remaining())
            throw new IOException("Invalid length.  Expected %d, got %d".formatted(length, buf.remaining()));

        int operation = buf.get() & 0xff;
        if (operation >= Operation.values().length)
            throw new IOException("Invalid operation %d".formatted(operation));

        this.word = StandardCharsets.UTF_8.decode(buf).toString();
        this.operation = Operation.values()[operation];
        System.out.println(word);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        WordPacket that = (WordPacket) o;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, operation);
    }

    @Override
    protected byte[] serialiseData() {
        var wordEncoded = StandardCharsets.UTF_8.encode(word);
        var buf = ByteBuffer.allocate(5 + wordEncoded.remaining());

        buf.putInt(1 + wordEncoded.remaining());
        buf.put((byte) operation.ordinal());
        buf.put(buf.position(), wordEncoded, wordEncoded.position(), wordEncoded.remaining());

        return buf.array();
    }

    @Override
    public String toString() {
        return word;
    }
}
