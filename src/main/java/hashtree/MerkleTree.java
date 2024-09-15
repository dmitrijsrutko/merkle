package hashtree;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MerkleTree {
    // to avoid Second Preimage Attack
    private static final byte[] LEAF_NODE_BYTES = new byte[] {0};
    private static final byte[] BRANCH_NODE_BYTES = new byte[] {1};

    private final MessageDigest digest;
    private final MerkleNode root;
    private final HashMap<Integer, MerkleNode> nodes = new HashMap<>();

    public MerkleTree(List<String> blocks) throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("SHA-256");  // or "SHA3-256"

        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < blocks.size(); i++) {
            indexes.add(i);
        }

        root = buildTree(blocks, indexes);
    }

    public MerkleNode getRoot() {
        return root;
    }

    private byte[] getHash(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        byte[] merged = ByteUtils.merge(LEAF_NODE_BYTES, bytes);
        return getDigestHash(merged);
    }

    private byte[] getHash(byte[] left, byte[] right) {
        byte[] bytes = ByteUtils.merge(left, right);
        byte[] merged = ByteUtils.merge(BRANCH_NODE_BYTES, bytes);
        return getDigestHash(merged);
    }

    private byte[] getDigestHash(byte[] bytes) {
        return digest.digest(bytes);
    }

    private MerkleNode buildTree(List<String> blocks, List<Integer> indexes) {
        if (blocks.isEmpty()) {
            return null;
        }

        if (blocks.size() == 1) {
            MerkleNode node = new MerkleNode(null, null, blocks.get(0), getHash(blocks.get(0)), indexes.get(0));
            nodes.put(indexes.get(0), node);
            return node;
        }

        int index = blocks.size() / 2;
        MerkleNode node = new MerkleNode();
        node.left = buildTree(blocks.subList(0, index), indexes.subList(0, index));
        node.right = buildTree(blocks.subList(index, blocks.size()), indexes.subList(index, blocks.size()));

        if (node.left != null) {
            node.left.isLeft = true;
            node.left.parent = node;
        }
        if (node.right != null) {
            node.right.isLeft = false;
            node.right.parent = node;
        }

        if ((node.left != null) && (node.right != null)) {
            node.hash = getHash(node.left.hash, node.right.hash);
        }

        return node;
    }

    public List<ProofNode> getProof(int index) {
        if (!nodes.containsKey(index)) {
            return null;
        }

        List<ProofNode> proofNodes = new ArrayList<>();

        MerkleNode node = nodes.get(index);
        while (node.parent != null) {
            ProofNode proofNode;
            if (node.isLeft) {
                proofNode = new ProofNode(node.parent.right.hash, false);
            } else {
                proofNode = new ProofNode(node.parent.left.hash, true);
            }
            proofNodes.add(proofNode);
            node = node.parent;
        }

        return proofNodes;
    }

    public boolean verifyProof(String item, List<ProofNode> proofNodes) {
        if ((proofNodes == null) || (this.root == null)) {
            return false;
        }

        byte[] hash = getHash(item);
        for (ProofNode proofNode : proofNodes) {
            if (proofNode.isLeft) {
                hash = getHash(proofNode.hash, hash);
            } else {
                hash = getHash(hash, proofNode.hash);
            }
        }

        return Arrays.equals(hash, this.root.hash);
    }

    public void printNodes(MerkleNode node) {
        if (node == null) {
            return;
        }

        System.out.println(node);

        printNodes(node.left);
        printNodes(node.right);
    }
}
