package truman.java.example.ser_obj_bytes_conv;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Main {

    private static final Object EMPTY_OBJECT = new Object();

    public static byte[] convSerToBytes(Serializable serObj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(serObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static Object convBytesToSer(byte[] bytes) {
        Object ret = EMPTY_OBJECT;
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try (ObjectInputStream oos = new ObjectInputStream(bais)) {
            ret = oos.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < bytes.length ; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MyData myData = new MyData("Kim", 24, "Whoogle", "1234".getBytes());

        System.out.println("[ Before Conv. ]");
        System.out.println(myData);
        System.out.println();

        byte[] myDataBytes = convSerToBytes(myData);
        System.out.println("[ After Conv. ]");
        System.out.println(bytesToHex(myDataBytes));
        System.out.println("(len : " + myDataBytes.length + ")");
        System.out.println();

        Object object = convBytesToSer(myDataBytes);
//      Object object = convBytesToSer(Arrays.copyOf(myDataBytes, myDataBytes.length - 1));
        System.out.println("[ After Restore ]");
        if (object instanceof MyData) {
            System.out.println((MyData) object);
        } else {
            System.out.println("Data loss!");
        }
    }
}