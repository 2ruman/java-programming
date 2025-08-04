package truman.java.demo.adbconsole.control;

import truman.java.demo.adbconsole.common.Executor;

import java.util.concurrent.*;

import static truman.java.demo.adbconsole.Application.withUi;

public class AdbExecutor implements Executor {

    private final ExecutorService executorService;
    private AdbExecutor() {
        executorService = new ThreadPoolExecutor(
                1,
                5,
                60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3)
        );
    }

    public static AdbExecutor get() {
        return Holder.instance;
    }

    @Override
    public void execute(String command) {
        try {
            executorService.execute(() -> {
                AdbShell.execute(command, line -> {
                    withUi(ui -> ui.println(line));
                });
            });
        } catch (RejectedExecutionException e) {
            withUi(ui -> ui.println("Rejected command: " + command));
        }
    }

    private static class Holder {
        private static final AdbExecutor instance = new AdbExecutor();
    }
}
