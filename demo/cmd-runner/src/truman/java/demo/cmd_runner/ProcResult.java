package truman.java.demo.cmd_runner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class is to contain the result from a process, separating the normal and
 * the error output.
 * 
 * @version 0.1.0
 * @author Truman Kim (truman.t.kim@gmail.com)
 */
public final class ProcResult {

    public static final ProcResult EMPTY = new ProcResult();

    private List<String> mOut = new LinkedList<>();
    private List<String> mErr = new LinkedList<>();

    private int mOutCnt;
    private int mErrCnt;

    public ProcResult() {
        mOutCnt = 0;
        mErrCnt = 0;
    }

    public void addOut(String line) {
        if (line != null) {
            synchronized (mOut) {
                mOut.add(line);
                mOutCnt++;
            }
        }
    }

    public void addErr(String line) {
        if (line != null) {
            synchronized (mErr) {
                mErr.add(line);
                mErrCnt++;
            }
        }
    }

    public boolean isEmpty() {
        return (mOutCnt + mErrCnt) == 0;
    }

    public int getOutCnt() {
        return mOutCnt;
    }

    public int getErrCnt() {
        return mErrCnt;
    }

    public String[] outToArray() {
        synchronized (mOut) {
            return (String[]) mOut.toArray();
        }
    }

    public Stream<String> outToStream() {
        synchronized (mOut) {
            return mOut.stream();
        }
    }

    public List<String> outToList() {
        return outToList(false);
    }

    public List<String> outToList(boolean copied) {
        if (copied) {
            synchronized (mOut) {
                List<String> ret = new ArrayList<>(mOut.size());
                ret.addAll(mOut);
                return ret;
            }
        }
        return mOut;
    }

    public String[] errToArray() {
        synchronized (mErr) {
            return (String[]) mErr.toArray();
        }
    }

    public Stream<String> errToStream() {
        synchronized (mErr) {
            return mErr.stream();
        }
    }

    public List<String> errToList() {
        return errToList(false);
    }

    public List<String> errToList(boolean copied) {
        if (copied) {
            synchronized (mErr) {
                List<String> ret = new ArrayList<>(mErr.size());
                ret.addAll(mErr);
                return ret;
            }
        }
        return mErr;
    }
}
