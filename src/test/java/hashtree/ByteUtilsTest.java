package hashtree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteUtilsTest {
    @Test
    public void testBytesToHex() {
        byte[] bytes = new byte[] {1, 2, 3, 4};
        String hex = ByteUtils.bytesToHex(bytes);
        assertEquals("01020304", hex);
    }

    @Test
    public void testMerge() {
        byte[] left = new byte[] {1, 2, 3, 4};
        byte[] right = new byte[] {5, 6, 7, 8};

        byte[] merged = ByteUtils.merge(left, right);
        String hex = ByteUtils.bytesToHex(merged);

        assertEquals("0102030405060708", hex);
    }
}
