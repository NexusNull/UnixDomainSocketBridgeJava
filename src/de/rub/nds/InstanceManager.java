package de.rub.nds;

import java.io.IOException;
import java.util.ArrayList;

public class InstanceManager extends Thread {
    int threads;
    String path;
    ArrayList<Process> processes = new ArrayList<>();

    InstanceManager(String path, int threads) {
        this.threads = threads;
        this.path = path;
        this.start();
    }

    @Override
    public void run() {
        while (processes.size() < this.threads) {
            try {
                this.startInstance();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void startInstance() throws IOException {
        ArrayList<String> params = new ArrayList<String>();
        params.add(this.path);
        params.add("/tmp/asd");

        ProcessBuilder pb = new ProcessBuilder(params);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process p = pb.start();
        processes.add(p);
    }
}
