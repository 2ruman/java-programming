package truman.java.util.command_chain;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SimpleCommandTest {

    ExecutorService executor = Executors.newSingleThreadExecutor();

    MySimpleCommand success_1 = new MySimpleCommand(1, "I'm Kim");
    MySimpleCommand success_2 = new MySimpleCommand(2, "I'm Lee");
    MySimpleCommand failure_1 = new MySimpleCommand(3, "I'm Park") {
        @Override
        public boolean execute() {
            super.execute();
            return false;
        }
        @Override
        public void rollback() {
            super.rollback();
        }
    };
    MySimpleCommand failure_2 = new MySimpleCommand(4, "I'm Woo") {
        @Override
        public boolean execute() {
            super.execute();
            return false;
        }
    };

    @Test
    void testCallWithExecutor() throws ExecutionException, InterruptedException {
        Future<Boolean> future_1 = executor.submit(success_1);
        Future<Boolean> future_2 = executor.submit(success_2);
        Future<Boolean> future_3 = executor.submit(failure_1);
        Future<Boolean> future_4 = executor.submit(failure_2);

        assertTrue(future_1.get());
        assertTrue(future_2.get());
        assertFalse(future_3.get());
        assertFalse(future_4.get());
    }

    @Test
    void verifySimpleCommand() throws ExecutionException, InterruptedException {
        SimpleCommand spyCommand = spy(new SimpleCommand() {
            @Override
            public boolean execute() {
                return true;
            }
        });
        when(spyCommand.execute()).thenReturn(true);
        assertTrue(executor.submit(spyCommand).get());
        verify(spyCommand, never()).rollback();

        when(spyCommand.execute()).thenReturn(false);
        assertFalse(executor.submit(spyCommand).get());
        verify(spyCommand, times(1)).rollback();
    }
}
