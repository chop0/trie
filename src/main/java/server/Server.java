package server;

import packet.AckPacket;
import packet.BooleanPacket;
import packet.TriePacket;
import packet.WordPacket;
import trie.Trie;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server implements AutoCloseable {
    private static final Trie trie = Trie.trie();
    public static final int PORT = 3000;
    private static final int BUFSIZE = 0x500;

    private final Thread server;

    public Server() throws IOException {
        this.server = new Thread(() -> {
            try {
                var socket = new DatagramSocket(PORT);
                var buffer = ByteBuffer.allocate(BUFSIZE);
                var bufferPacket = new DatagramPacket(buffer.array(), BUFSIZE, buffer.arrayOffset());
//                socket.bind(new InetSocketAddress("localhost", PORT));
                while(!Thread.interrupted()) {

                    socket.receive(bufferPacket);
                    Sys
                    var response = handlePacket((WordPacket) TriePacket.deserialise(bufferPacket)).serialise();

                    socket.send(new DatagramPacket(response, response.length, bufferPacket.getSocketAddress()));
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        server.start();
    }

    private TriePacket handlePacket(WordPacket wordPacket) {
        return switch (wordPacket.operation) {
            case INSERT -> {
                trie.insertWord(wordPacket.word);
                yield new AckPacket();
            }
            case CONTAINS -> new BooleanPacket(trie.containsWord(wordPacket.word));
            case REMOVE -> new BooleanPacket(trie.removeWord(wordPacket.word));
        };
    }

    @Override
    public void close() throws Exception {
        server.interrupt();
        server.join();
    }
}
