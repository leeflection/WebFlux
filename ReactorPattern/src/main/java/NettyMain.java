import java.util.List;

public class NettyMain {
    public static void main(String[] args) {
        List<EventLoop> eventLoops = List.of(new EventLoop(8080),new EventLoop(8080));
        eventLoops.forEach(EventLoop::run);

    }
}
