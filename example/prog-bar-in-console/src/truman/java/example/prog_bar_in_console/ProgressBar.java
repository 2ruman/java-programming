package truman.java.example.prog_bar_in_console;

public class ProgressBar {

    private final int mWidth;
    private final int mTotal;

    public ProgressBar(int width, int total) {
        mWidth = width;
        mTotal = total;
    }

    private void drawBar(int pct) {
        System.out.print(String.format(" %d%% ", pct));

        System.out.print("\033[47m");

        int i = 0, filled = mWidth * pct / 100;
        for (; i < filled; i++) {
            System.out.print(" ");
        }

        System.out.print("\033[0m");

        for (; i < mWidth; i++) {
            System.out.print(".");
        }
    }

    public void init() {
        onProgress(0);
    }

    public void onProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        } else if (progress > mTotal) {
            progress = mTotal;
        }
        drawBar((int) (progress * 100 / mTotal));
    }
}
