package nio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JavaNIONonBlockingServer {
    @SneakyThrows
    public static void main(String[] args) {
        log.info("start main");
        try(ServerSocketChannel serverSocket = ServerSocketChannel.open()){
            serverSocket.bind(new InetSocketAddress("localhost",8080));
            //non-blocking config
            serverSocket.configureBlocking(false);
            while(true){
                SocketChannel clientSocket = serverSocket.accept();
                // NPE validation
                if(clientSocket == null){
                    Thread.sleep(100);
                    continue;
                }

                ByteBuffer requestbyteBuffer = ByteBuffer.allocateDirect(1024);
                while(clientSocket.read(requestbyteBuffer)==0){
                    log.info("Reading......");
                }
                requestbyteBuffer.flip();
                String requestBody = StandardCharsets.UTF_8.decode(requestbyteBuffer).toString();

                log.info("request: {}", requestBody);

               ByteBuffer responseByteBuffer = ByteBuffer.wrap("This is server".getBytes());
               clientSocket.write(responseByteBuffer);
               clientSocket.close();
            }
        }
    }
}
