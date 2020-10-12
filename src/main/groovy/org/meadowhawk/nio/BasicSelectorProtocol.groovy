package org.meadowhawk.nio

import org.meadowhawk.util.IOTCompressedFormatParser

import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.nio.charset.StandardCharsets
import java.util.logging.Level
import java.util.logging.Logger

class BasicSelectorProtocol implements TCPProtocol{
    int bufferSize
    long CHN_CLOSED = -1l
    final Logger logger = Logger.getLogger("practical")

    BasicSelectorProtocol(int bufsize) {
        this.bufferSize = bufsize
    }

    @Override
    void handleAccept(SelectionKey key) throws IOException {
        SocketChannel clientChanel = ((ServerSocketChannel) key.channel()).accept()
        clientChanel.configureBlocking(false)
        // Register the selector with new channel for read and attach byte buffer

        clientChanel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize))
    }

    @Override
    void handleRead(SelectionKey key) throws IOException {
        // Client socket channel has pending data
        SocketChannel clientChanel = (SocketChannel) key.channel()
        ByteBuffer buffer = (ByteBuffer) key.attachment()
        long bytesRead = clientChanel.read(buffer)
        //reset buffer to start so input can be read as the buffer position is set to  bytesRead after reading from the channel, a little counter-intuitive but since read calls put.. its really the expected state of the buffer
        buffer.rewind()
        logger.info("bytesRead: ${bytesRead}")

        byte[] bytesIn = new byte[bytesRead]
        buffer.get(bytesIn)
        String s = new String(bytesIn)
        if(s.startsWith('00')) {
            def iotData = IOTCompressedFormatParser.parse(s)
            logger.info("IOTData Received:  "+iotData.toString())
        }
        logger.info("Read message = " +  s)

        if (bytesRead == CHN_CLOSED) { 
            clientChanel.close()
        } else if (bytesRead > 0) {
          // Indicate via key that reading/writing are both of interest now.
          key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE)
        }
    }

    @Override
    void handleWrite(SelectionKey key) throws IOException {
        // Retrieve data read earlier
        ByteBuffer buffer = (ByteBuffer) key.attachment()
        buffer.flip() // Prepare buffer for writing
        SocketChannel clntChan = (SocketChannel) key.channel()
        clntChan.write(buffer)
        if (!buffer.hasRemaining()) key.interestOps(SelectionKey.OP_READ)  //nothing left to write, only read now.
        buffer.compact() // Make room for more data to be read in
    }
}
