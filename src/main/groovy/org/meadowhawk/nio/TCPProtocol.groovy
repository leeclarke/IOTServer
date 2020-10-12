package org.meadowhawk.nio

import java.nio.channels.SelectionKey

interface TCPProtocol {
    void handleAccept(SelectionKey key) throws IOException
    void handleRead(SelectionKey key) throws IOException
    void handleWrite(SelectionKey key) throws IOException
}