package com.truman.test;

public abstract class Test {

    public static void assertTrue(boolean testResult) {
        if (!testResult) {
            throw new TestFailedException();
        }
    }

    public static void assertFalse(boolean testResult) {
        if (testResult) {
            throw new TestFailedException();
        }
    }

    public static void assertNull(Object testResult) {
        if (testResult != null) {
            throw new TestFailedException();
        }
    }

    public static void assertNotNull(Object testResult) {
        if (testResult == null) {
            throw new TestFailedException();
        }
    }

    public static void assertEquals(Object expected, Object testResult) {
        if (expected == null && testResult == null) return;
        if (expected != null && expected.equals(testResult)) return;
        throw new TestFailedException();
    }

    public static void assertNotEquals(Object expected, Object testResult) {
        if (expected == null && testResult == null) throw new TestFailedException();
        if (expected != null && expected.equals(testResult)) throw new TestFailedException();

    }

    public abstract void doSelfTest();
}
