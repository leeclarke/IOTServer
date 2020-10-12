package org.meadowhawk.nio

import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel

class IOTServer {

    private static final int BUFSIZE = 256   // Buffer size (bytes)
    private static final int TIMEOUT = 3000  // Wait timeout (milliseconds)

    static void main(String[] args) throws IOException {

        if (args.length < 1) { // Test for correct # of args
            throw new IllegalArgumentException("Parameter(s): <Port> ...")
        }

        // Create a selector to multiplex listening sockets and connections
        Selector selector = Selector.open()

        // Create listening socket channel for each port and register selector
        for (String arg : args) {
            ServerSocketChannel listnChannel = ServerSocketChannel.open()
            listnChannel.socket().bind(new InetSocketAddress(Integer.parseInt(arg)))
            listnChannel.configureBlocking(false) // must be nonblocking to register
            listnChannel.register(selector, SelectionKey.OP_ACCEPT)
        }

        TCPProtocol protocol = new BasicSelectorProtocol(BUFSIZE)

        while (true) {
            // Wait for some channel to be ready (or timeout)
            if (selector.select(TIMEOUT) == 0) { // returns # of ready chans
                System.out.print(".")
                continue
            }

            Iterator<SelectionKey> keys = selector.selectedKeys().iterator()
            while (keys.hasNext()) {
                SelectionKey key = keys.next() // Key is bit mask
                // Server socket channel has pending connection requests?
                if (key.isAcceptable()) {
                    protocol.handleAccept(key)
                }
                // Client socket channel has pending data?
                if (key.isReadable()) {
                    protocol.handleRead(key)
                }
                if (key.isValid() && key.isWritable()) {
                    protocol.handleWrite(key)
                }
                keys.remove()
            }
        }
    }
}