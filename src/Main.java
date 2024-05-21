public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow=new ServerWindow();
        new ChatClientWindow(serverWindow);
        new ChatClientWindow(serverWindow);

    }
}

