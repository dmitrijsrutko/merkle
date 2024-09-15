package hashtree;

import java.util.HexFormat;

public class ByteUtils {
    public static String bytesToHex(byte[] hash) {
        return HexFormat.of().formatHex(hash);
    }

    public static byte[] merge(byte[] left, byte[] right) {
        byte[] merged = new byte[left.length + right.length];

        System.arraycopy(left, 0, merged, 0, left.length);
        System.arraycopy(right, 0, merged, left.length, right.length);

        return merged;
    }
}
