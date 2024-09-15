package hashtree;

import java.util.ArrayList;
import java.util.List;

public class Example {
    public static void main(String[] args) {
        try {
            String[] dataBlocks = new String[]{
                    "banana", "cocoa", "apple", "grapes", "berry"
            };

            ArrayList<String> blocks = new ArrayList(List.of(dataBlocks));

            MerkleTree tree = new MerkleTree(blocks);

            System.out.println("Entire tree");
            tree.printNodes(tree.getRoot());
            System.out.println();

            System.out.println("Root: " + tree.getRoot());
            System.out.println();

            int index = 3;
            List<ProofNode> proof = tree.getProof(index);
            System.out.println("Proof: " + proof);
            System.out.println();

            String item = dataBlocks[index];
            boolean isValid = tree.verifyProof(item, proof);
            System.out.println("isValid: " + isValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}