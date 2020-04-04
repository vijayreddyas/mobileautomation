package helpers;

import java.io.IOException;
import java.net.ServerSocket;

import static org.springframework.util.SocketUtils.findAvailableTcpPort;

public class CommonUtils {

    /**
     *
     * @param hostMachine
     * @return
     * @throws IOException
     */
    public static int getAvailablePort(String hostMachine) throws IOException {
        ServerSocket socket = new ServerSocket(0);
        socket.setReuseAddress(true);
        int port = socket.getLocalPort();
        socket.close();
        return port;
    }

    /**
     *
     * @param from
     * @param to
     * @return
     */
    public static int nextFreePort(int from, int to) {
        return findAvailableTcpPort(from, to);
    }


}
