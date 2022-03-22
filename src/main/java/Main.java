import packet.WordPacket;
import packet.TriePacket;
import server.Server;
import trie.Trie;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class Main {
    private static final int BUFSIZE = 0x500;

    public static void main(String[] args) throws IOException {
        new Server();
        var buffer = ByteBuffer.allocate(BUFSIZE);
        var bufferPacket = new DatagramPacket(buffer.array(), BUFSIZE, buffer.arrayOffset());//        System.out.println(tree.getPrefix("fas"));

            var socket = new DatagramSocket();

            var ser = new WordPacket(WordPacket.Operation.CONTAINS, "hello").serialise();
            socket.send(new DatagramPacket(ser, ser.length, new InetSocketAddress("localhost", 3000)));
        socket.receive(bufferPacket);
        var response = (new WordPacket(buffer.slice(buffer.position(), bufferPacket.getLength()))).serialise();
            System.out.println(response);

//            System.out.println(TriePacket.deserialise(new DatagramPacket(ser, ser.length)));
        /*
        tree.insertWord("hello");
        tree.insertWord("hellobruh");
        tree.insertWord("hellomruh");
        tree.insertWord("hellobruh2");
//        System.out.println(tree.containsWord("hello"));
////        tree.removeWord("hello");
//        System.out.println(tree.containsWord("hellobruh"));
//        System.out.println(tree.containsWord("hellobruh2"));

        System.out.println(tree);
//        System.out.println(tree.getAllWords());
//        System.out.println(tree.getPrefix("hellob").get().getAllWords());*/
    }

}
