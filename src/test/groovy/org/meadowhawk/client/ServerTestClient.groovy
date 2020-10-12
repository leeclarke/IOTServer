package org.meadowhawk.client


class ServerTestClient {

    static void main(String[] args) throws IOException {
//TODO: support args or no args
        String server = ""//args[0]       // Server name or IP address
        // Convert argument String to bytes using the default character encoding
        byte[] data = "00000002304022020060615330100323064211226145800003245100Hello World                                                                                                                                                                                         "//args[1] getBytes()

        int servPort = 4242// (args.length == 3) ? Integer.parseInt(args[2]) : 7

        // Create socket that is connected to server on specified port
        Socket socket = new Socket(server, servPort)
        System.out.println("Connected to server...sending echo string")

        InputStream inStr = socket.getInputStream()
        OutputStream out = socket.getOutputStream()

        out.write(data) // Send the encoded string to the server

        // Receive the same string back from the server
        int totalBytesRcvd = 0 // Total bytes received so far
        int bytesRcvd          // Bytes received in last read
        while (totalBytesRcvd < data.length) {
            if ((bytesRcvd = inStr.read(data, totalBytesRcvd,
                    data.length - totalBytesRcvd)) == -1)
                throw new SocketException("Connection closed prematurely")
            totalBytesRcvd += bytesRcvd
        } // data array is full

        System.out.println("Received: " + new String(data))

        socket.close() // Close the socket and its streams
    }
}
