package com.truman.example.utilities;

import com.truman.test.Test;
import com.truman.test.TestFailedException;
import com.truman.util.BytesUtil;
import com.truman.util.Log;

public class BytesUtilTest extends Test {

    private static final byte[] BYTES_32 = new byte[] {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
            0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F,
    };

    private static String[] checkList = new String[6];

    private void bytesToHexTest() {
        String res, res1, res2;
        Log.d("### Test Started - BytesUtil.bytesToHex() ###");
        res1 = BytesUtil.bytesToHex(BYTES_32);
        Log.d("Result #1 : " + res1);
        res2 = BytesUtil.bytesToHex(BYTES_32, "", " ", BytesUtil.HexCase.UPPER_CASE);
        Log.d("Result #2 : " + res2);
        assertEquals(res1, res2);
        res = BytesUtil.bytesToHex(null);
        Log.d("Result    : " + res);
        assertEquals("null", res);
        res = BytesUtil.bytesToHex(new byte[0]);
        assertEquals("", res);
        Log.d("Result    : " + res);
        Log.d("### Test Finished  - BytesUtil.bytesToHex() ###");
        Log.d("### Test Result --> Passed !!!\n");

        checkList[0] = BytesUtil.bytesToHex(BYTES_32, "", " ", BytesUtil.HexCase.LOWER_CASE);
        checkList[1] = BytesUtil.bytesToHex(BYTES_32, "", "", BytesUtil.HexCase.UPPER_CASE);
        checkList[2] = BytesUtil.bytesToHex(BYTES_32, "", "", BytesUtil.HexCase.LOWER_CASE);
        checkList[3] = BytesUtil.bytesToHex(BYTES_32, "0x", " ", BytesUtil.HexCase.UPPER_CASE);
        checkList[4] = BytesUtil.bytesToHex(BYTES_32, "0x", " ", BytesUtil.HexCase.UPPER_CASE);
        checkList[5] = BytesUtil.bytesToHex(BYTES_32, "0x", "/", BytesUtil.HexCase.LOWER_CASE);
        for (int i = 0 ; i < checkList.length ; i++) {
            Log.d("Check List Item["+i+"] : " + checkList[i]);
        }
        Log.d("");
    }

    private void hexToHexBytesTest() {
        byte[] res;
        Log.d("### Test Started - BytesUtil.hexToHexBytes() ###");
        res = BytesUtil.hexToBytes(checkList[0]);
        assertTrue(BytesUtil.compareBytes(BYTES_32, res));
        Log.d("Check List Item[0] : " +BytesUtil.bytesToHex(res));

        res = BytesUtil.hexToBytes(checkList[1], "", "");
        assertTrue(BytesUtil.compareBytes(BYTES_32, res));
        Log.d("Check List Item[1] : " +BytesUtil.bytesToHex(res));

        res = BytesUtil.hexToBytes(checkList[2], "", "");
        assertTrue(BytesUtil.compareBytes(BYTES_32, res));
        Log.d("Check List Item[2] : " +BytesUtil.bytesToHex(res));

        res = BytesUtil.hexToBytes(checkList[3], "0x", " ");
        assertTrue(BytesUtil.compareBytes(BYTES_32, res));
        Log.d("Check List Item[3] : " +BytesUtil.bytesToHex(res));

        res = BytesUtil.hexToBytes(checkList[4], "0x", " ");
        assertTrue(BytesUtil.compareBytes(BYTES_32, res));
        Log.d("Check List Item[4] : " +BytesUtil.bytesToHex(res));

        res = BytesUtil.hexToBytes(checkList[5], "0x", "/");
        assertTrue(BytesUtil.compareBytes(BYTES_32, res));
        Log.d("Check List Item[5] : " +BytesUtil.bytesToHex(res));

        try {
            BytesUtil.hexToBytes(
                    "00 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 0E 0F 10 11 12 13 14 15 16 17 18 19 1A 1B 1C 1D 1E 1!",
                    "",
                    " ");
            throw new TestFailedException();
        } catch(IllegalArgumentException e) {
            Log.d("Invalid Input Test : " + e.getMessage());
        }
        Log.d("### Test Finished  - BytesUtil.hexToHexBytes() ###");
        Log.d("### Test Result --> Passed !!!\n");
    }

    private void bytesToStringTest() {
        String res;
        String s = "ABCDEfghijk  12345  /_!@#$%^&*()-=/";
        byte[] b = s.getBytes();
        Log.d("### Test Started  - BytesUtil.bytesToStringTest() ###");
        res = BytesUtil.bytesToString(b);
        Log.d("Result    : " + res);
        assertEquals(s, res);
        res = BytesUtil.bytesToString(null);
        Log.d("Result    : " + res);
        assertEquals("null", res);
        res = BytesUtil.bytesToString(new byte[0]);
        Log.d("Result    : " + res);
        assertEquals("", res);
        Log.d("### Test Finished - BytesUtil.bytesToStringTest() ###");
        Log.d("### Test Result --> Passed !!!\n");
    }

    private void compareBytesTest() {
        byte[] b1 = null;
        byte[] b2 = null;
        byte[] b3 = new byte[0];
        byte[] b4 = new byte[0];
        byte[] b5 = {1, 2, 3};
        byte[] b6 = {4, 5, 6, 7};
        byte[] b7 = {1, 2, 3};
        boolean res;
        Log.d("### Test Started  - BytesUtil.compareBytes() ###");
        res = BytesUtil.compareBytes(b1, b2);
        assertTrue(res);
        res = BytesUtil.compareBytes(b1, b3);
        assertFalse(res);
        res = BytesUtil.compareBytes(b3, b4);
        assertTrue(res);
        res = BytesUtil.compareBytes(b5, b6);
        assertFalse(res);
        res = BytesUtil.compareBytes(b5, b7);
        assertTrue(res);
        Log.d("### Test Finished - BytesUtil.compareBytes() ###");
        Log.d("### Test Result --> Passed !!!\n");
    }

    @Override
    public void doSelfTest() {
        compareBytesTest();
        bytesToHexTest();
        hexToHexBytesTest();
        bytesToStringTest();
    }
}
