package jtframework.server;

public class Main {

    public static void main(String[] argv) {
        new JT809Server().startServer();

        // todo 加上 http 接口创建 monitor 模式
        /*new Thread(() -> {
            CurrentConditions currentConditions = new CurrentConditions();
            JT808ClientHandler.monitorData.registerObserver(currentConditions);
        }).start();*/
    }
}
