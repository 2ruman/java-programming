package truman.java.util.command_chain;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class CommandChainTest {

    Util mUtil = new Util();

    MyCommand success_1 = new MyCommand(1, "I'm Kim");
    MyCommand success_2 = new MyCommand(2, "I'm Lee");
    MyCommand success_3 = new MyCommand(3, "I'm Choi");
    MyCommand success_4 = new MyCommand(4, "I'm Jung");
    MyCommand failure = new MyCommand(4, "I'm Park") {
        @Override
        public boolean execute() {
            super.execute();
            return false;
        }
    };

    @Test
    void testOf() {
        assertThrows(IllegalArgumentException.class, () -> CommandChain.of(null));
        AtomicReference<CommandChain> commandChain = new AtomicReference<>();
        assertDoesNotThrow(() -> commandChain.set(CommandChain.of(success_1)));
    }

    @Test
    void testLength() {
        CommandChain commandChain;

        commandChain = CommandChain.of(success_1);
        assertEquals(1, commandChain.length());

        commandChain = new CommandChain(success_1, new CommandChain(success_2, new CommandChain(success_3, new CommandChain(success_4))));
        assertEquals(4, commandChain.length());
    }

    @Test
    void testFollowedBy() {
        CommandChain commandChain = CommandChain.of(success_1)
                                                .followedBy(success_2)
                                                .followedBy(success_3)
                                                .followedBy(failure)
                                                .followedBy(success_4);
        assertEquals(5, commandChain.length());
    }

    @Test
    void testSetNext() {
        CommandChain chain1 = new CommandChain(success_1);
        CommandChain chain2 = new CommandChain(success_2);
        CommandChain chain3 = new CommandChain(success_3);
        assertEquals(1, chain1.length());
        chain1.setNext(chain2);
        assertEquals(2, chain1.length());
        chain2.setNext(chain3);
        assertEquals(3, chain1.length());
    }

    @Test
    void testHasNext() {
        CommandChain chain1 = new CommandChain(success_1);
        assertFalse(chain1.hasNext());
        chain1.setNext(new CommandChain(success_2));
        assertTrue(chain1.hasNext());
    }

    @Test
    void testTail() {
        CommandChain chain1 = new CommandChain(success_1);
        CommandChain chain2 = new CommandChain(success_2);
        CommandChain chain3 = new CommandChain(success_3);
        chain1.setNext(chain2);
        chain2.setNext(chain3);
        assertEquals(chain3, chain1.tail());
    }

    @Test
    void testSuccessCommandChain() {
        CommandChain successCommandChain = CommandChain.of(success_1)
                .followedBy(success_2).followedBy(success_3).followedBy(success_4);
        assertTrue(successCommandChain.execute());
    }

    @Test
    void approveSuccessCommandChain() {
        String actualFileName =  "CommandChainTest.approveSuccessCommandChain.received.txt";
        mUtil.runWith(actualFileName, () ->CommandChain.of(success_1)
                .followedBy(success_2).followedBy(success_3).followedBy(success_4)
                .execute());
        Approvals.verify(mUtil.getTargetFile(actualFileName));
    }

    @Test
    void testFailureCommandChain() {
        CommandChain failureCommandChain = CommandChain.of(success_1)
                .followedBy(success_2).followedBy(success_3).followedBy(failure).followedBy(success_4);
        assertFalse(failureCommandChain.execute());
    }

    @Test
    void approveFailureCommandChain() {
        String actualFileName =  "CommandChainTest.approveFailureCommandChain.received.txt";
        mUtil.runWith(actualFileName, () -> CommandChain.of(success_1)
                .followedBy(success_2).followedBy(success_3).followedBy(failure).followedBy(success_4)
                .execute());
        Approvals.verify(mUtil.getTargetFile(actualFileName));
    }

    @Test
    void testFailureCommandChainWithScheduler() {
        CommandChain failureCommandChain = CommandChain.of(success_1)
                .followedBy(success_2).followedBy(success_3).followedBy(failure).followedBy(success_4);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executorService.submit(failureCommandChain);
        Boolean result = null;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            assertDoesNotThrow(() -> { throw e; });
        }
        executorService.shutdownNow();
        assertEquals(Boolean.FALSE, result);
    }
}