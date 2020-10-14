package org.meadowhawk.nio

import org.tomlj.Toml
import org.tomlj.TomlParseResult

import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.file.Path
import java.nio.file.Paths

class IOTServer {

    private static final int BUFSIZE = 256   // Buffer size (bytes)
    private static final int TIMEOUT = 3000  // Wait timeout (milliseconds)

    String configFile = "things.toml"
    TomlParseResult config
    Selector selector

    IOTServer(String configFilePath){
        if(configFilePath) this.configFile = configFilePath
        config = loadConfig()
        setUpDataStores()
        setUpServer()
    }

    TomlParseResult loadConfig(){
        Path source = Paths.get(configFile)
        TomlParseResult config
        if(source.toFile().exists()){
            config = Toml.parse(source)
            config.errors().each{error -> System.err.println(error.toString())}
        } else {
            config = Toml.parse("")
            System.out.println("Can not find config file things.toml")
        }
        config
    }

    def setUpServer(){
        List<Integer> ports = config.getTable("server")?.getArray("ports")?.toList()
        if(!ports || ports.size() ==0) {
            System.out.println("No ports provided in config, defaulting to port 4242")
            ports = [4242]
        }
        // Create a selector to multiplex listening sockets and connections
        selector = Selector.open()

        // Create listening socket channel for each port and register selector
        ports.each {port ->
            ServerSocketChannel listnChannel = ServerSocketChannel.open()
            listnChannel.socket().bind(new InetSocketAddress((Integer)port))
            listnChannel.configureBlocking(false) // must be nonblocking to register
            listnChannel.register(selector, SelectionKey.OP_ACCEPT)
            System.out.println("Listening on port:"+ port)
        }
    }

    def setUpDataStores(){
        //TODO: add Datastores enabled in config
    }
    def run(){
        TCPProtocol protocol = new BasicSelectorProtocol(BUFSIZE)

        while (true) {
            // Wait for some channel to be ready (or timeout)
            if (selector.select(TIMEOUT) == 0) { // returns # of ready chans
                System.out.print(".")//TODO: This needs something or removed more likely
                continue
            }

            Iterator<SelectionKey> keys = selector.selectedKeys().iterator()
            while (keys.hasNext()) {
                SelectionKey key = keys.next()
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

    static void main(String[] args) throws IOException {
        //TODO: Add support for alt path from args
        def server = new IOTServer(null)
        server.run()
    }
}