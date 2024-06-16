package truman.java.example.junit.csvsourceconverter;

public enum MyEnum {
    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6);

    private final int code;

    MyEnum(int id) {
        this.code = id;
    }

    public int getCode() {
        return code;
    }
}
