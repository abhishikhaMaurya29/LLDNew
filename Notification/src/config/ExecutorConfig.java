package config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorConfig {
    public static ExecutorService createExecutor() {
        return Executors.newFixedThreadPool(5);
    }
}