package hashtree;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MerkleTreeTest {
    @Test
    public void testEmpty() throws NoSuchAlgorithmException {
        ArrayList<String> blocks = new ArrayList();
        MerkleTree tree = new MerkleTree(blocks);

        MerkleNode root = tree.getRoot();
        assertNull(root);
    }

    @Test
    public void testOne() throws NoSuchAlgorithmException {
        String[] dataBlocks = new String[] {
                "banana"
        };

        ArrayList<String> blocks = new ArrayList(List.of(dataBlocks));
        MerkleTree tree = new MerkleTree(blocks);

        String hex = ByteUtils.bytesToHex(tree.getRoot().hash);
        assertEquals("113b1cd81fbaf46c16cfa07e7ac8eb414cf2a5ac25c133dbe64be9499020de4f", hex);
    }

    @Test
    public void testFive() throws NoSuchAlgorithmException {
        String[] dataBlocks = new String[] {
                "banana", "cocoa", "apple", "grapes", "berry"
        };

        ArrayList<String> blocks = new ArrayList(List.of(dataBlocks));
        MerkleTree tree = new MerkleTree(blocks);

        String hex = ByteUtils.bytesToHex(tree.getRoot().hash);
        assertEquals("0ce35c1ec87ae021997de4c39a74a34d665309087580608afb75f1cec6d0c077", hex);
    }

    @Test
    public void testGetProof() throws NoSuchAlgorithmException {
        String[] dataBlocks = new String[] {
                "banana", "cocoa", "apple", "grapes", "berry"
        };

        ArrayList<String> blocks = new ArrayList(List.of(dataBlocks));
        MerkleTree tree = new MerkleTree(blocks);

        int index = 3;
        List<ProofNode> proof = tree.getProof(index);

        assertEquals(3, proof.size());

        byte[] hash0 = proof.get(0).hash;
        byte[] hash1 = proof.get(1).hash;
        byte[] hash2 = proof.get(2).hash;

        assertEquals("62c7e07362060d8a838c05e73d5dac8a8ab84e915f2c6b1fc3128d264e807e7b", HexFormat.of().formatHex(hash0));
        assertEquals("03cfd2a81065d4f0b9ca6da0d8d09b25db0e2c5e0cc3914b2ea8c6e0fd303e2a", HexFormat.of().formatHex(hash1));
        assertEquals("ec73ab2ac71d96e428e14d6a23922b4edacdb1f69efc7d2774a007e060f60f63", HexFormat.of().formatHex(hash2));
    }

    @Test
    public void testVerifyProof() throws NoSuchAlgorithmException {
        String[] dataBlocks = new String[] {
                "banana", "cocoa", "apple", "grapes", "berry"
        };

        ArrayList<String> blocks = new ArrayList(List.of(dataBlocks));
        MerkleTree tree = new MerkleTree(blocks);

        int index = 3;
        List<ProofNode> proof = tree.getProof(index);

        boolean isValid = tree.verifyProof(dataBlocks[index], proof);
        assertTrue(isValid);
    }

    @Test
    public void testVerifyProofNoValid() throws NoSuchAlgorithmException {
        String[] dataBlocks = new String[] {
                "banana", "cocoa", "apple", "grapes", "berry"
        };

        ArrayList<String> blocks = new ArrayList(List.of(dataBlocks));
        MerkleTree tree = new MerkleTree(blocks);

        int index = 3;
        List<ProofNode> proof = tree.getProof(index);

        boolean isValid = tree.verifyProof("Invalid item", proof);
        assertFalse(isValid);
    }
}
