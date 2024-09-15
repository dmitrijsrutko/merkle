package hashtree;

public class MerkleNode {
    public MerkleNode left, right, parent;
    public String value;
    public byte[] hash;
    public boolean isLeft;
    public int index;

    public MerkleNode() {
    }

    public MerkleNode(MerkleNode left, MerkleNode right, String value, byte[] hash, int index) {
        this.left = left;
        this.right = right;
        this.value = value;
        this.hash = hash;
        this.index = index;
    }

    @Override
    public String toString() {
        String result = "isLeaf=" + String.format("%-5s", this.value != null);
        result += " isLeft=" + String.format("%-5s", isLeft) + " hash=0x" + ByteUtils.bytesToHex(this.hash);
        if (this.value != null) {
            result += " value=" + value;
            result += " index=" + index;
        }
        return result;
    }
}
