package hashtree;

public class ProofNode {
    public byte[] hash;
    public boolean isLeft;

    public ProofNode(byte[] hash, boolean isLeft) {
        this.hash = hash;
        this.isLeft = isLeft;
    }

    @Override
    public String toString() {
        return "isLeft=" + String.format("%-5s", isLeft) + "hash=0x" + ByteUtils.bytesToHex(hash);
    }
}
