A Merkle proof is a cryptographic method used to verify whether a specific piece of data is included in a Merkle tree without requiring access to the entire data set. Merkle trees are a fundamental data structure in many blockchain systems, such as Bitcoin and Ethereum, where they are used to organize and verify large sets of data in an efficient and secure manner.

Here’s how it works:

Merkle Tree: It is a binary tree structure where every leaf node represents a hash of data, and every non-leaf node is a hash of its child nodes. The topmost node is called the Merkle Root, which represents the combined integrity of all the data in the tree.

Merkle Proof: When you want to prove that a specific data point (e.g., a transaction in a blockchain) is part of a Merkle tree, a Merkle proof provides the necessary hashes that allow you to reconstruct the path from that data point to the Merkle root. You don’t need the entire tree, just the hashes along the path.

Verification: By combining the provided proof (i.e., the sequence of hashes) with the specific data point, one can calculate the Merkle root. If the calculated Merkle root matches the expected Merkle root (which is publicly known), the data is verified as part of the tree.

Use Cases
Blockchain Transactions: Merkle proofs allow lightweight clients (like SPV clients in Bitcoin) to verify the inclusion of a transaction in a block without downloading the entire block.
Data Integrity: Merkle proofs are used to ensure the integrity of data in distributed systems like file-sharing networks, where verifying data authenticity without downloading the entire data set is important.
main.test.hashtree.Example
Suppose you have a Merkle tree with four transactions (T1, T2, T3, T4). If you want to verify T2, you would receive a Merkle proof that consists of the hash of T1, the hash of the combined T3 and T4, and use these along with T2 to reconstruct the Merkle root. If the reconstructed root matches the known Merkle root, T2 is confirmed as part of the tree.

Merkle proofs enable fast and secure verification in environments where data must be verified without accessing all of it.




Merkle trees primarily support operations related to cryptographic verification rather than dynamic data manipulation like insertions or deletions. However, depending on the implementation, they can be adapted to handle certain operations like adding, updating, or deleting data. Let's explore the typical operations and their characteristics:

1. Add (Insertion)
   Operation: Adding new data (e.g., a new transaction) to the tree involves creating a new leaf node that represents the hash of the new data.
   Characteristics:
   In some cases (like blockchain), a Merkle tree is built in batches where all transactions (data points) for a given block are added at once.
   For a static Merkle tree, adding new data is generally not supported after the tree is built.
   In a dynamic Merkle tree (e.g., Merkle Patricia Trees used in Ethereum), insertions can be handled by recalculating affected hashes along the path from the new node to the root.
2. Update (Modification)
   Operation: Updating data in a Merkle tree means changing the value of a specific leaf node and recalculating the hashes along the path from the updated node to the Merkle root.
   Characteristics:
   This requires recalculating all the hashes from the updated leaf to the root, ensuring the integrity of the Merkle root.
   In dynamic Merkle trees (like Merkle Patricia Trees), updating a value is more straightforward since they are designed to allow changes. However, the same recalculation process is required.
3. Delete (Removal)
   Operation: Deleting a node from a Merkle tree removes the leaf node and affects the structure of the tree.
   Characteristics:
   In a static Merkle tree, deletion is typically not supported, as the structure is fixed after being built.
   In dynamic Merkle trees, deleting a node involves recalculating the hashes from the sibling nodes up to the root, much like updating or adding.
   Deleting a node could also create an imbalance in the tree, especially if the number of nodes is no longer a power of 2. Some implementations might duplicate neighboring hashes to maintain binary structure.
4. Verify (Merkle Proof)
   Operation: The most common operation in Merkle trees is verifying whether a given piece of data belongs to the tree using a Merkle proof.
   Characteristics:
   This is done by providing the data point, along with its sibling hashes, to calculate the Merkle root. If the calculated root matches the known Merkle root, the proof is valid.
   This operation is lightweight and does not require knowledge of the entire tree, making it useful for verifying large data sets efficiently.
5. Rebuilding the Tree
   If multiple additions, deletions, or updates are needed, the entire tree might be rebuilt from scratch to reflect the changes. This is typical in static Merkle trees (like those used in Bitcoin block headers).
   Static vs. Dynamic Merkle Trees:
   Static Merkle Trees: Once built, they are immutable, meaning no more additions, deletions, or updates can be made. Any change requires rebuilding the tree from scratch. Blockchains like Bitcoin use static Merkle trees, where each block contains a Merkle tree of all transactions in that block.

Dynamic Merkle Trees: Some systems, like Ethereum, use variations such as the Merkle Patricia Tree (a combination of a Merkle tree and a Patricia trie), which allows for dynamic insertion, updating, and deletion of data.

Summary of Operations:
Operation	Supported in Static Merkle Tree?	Supported in Dynamic Merkle Tree?
Add	No (rebuild required)	Yes (recalculation needed)
Update	No (rebuild required)	Yes (recalculation needed)
Delete	No (rebuild required)	Yes (recalculation needed)
Verify	Yes	Yes
In general, static Merkle trees prioritize verification over data manipulation, while dynamic versions allow more flexibility but with a computational cost.

This implementation favours static Merkle tree. Implementation follows classic digest of 256-bit hash, if 128-bit has is required it could be reasonably safely truncated.

Second preimage attack
The Merkle hash root does not indicate the tree depth, enabling a second-preimage attack in which an attacker creates a document other than the original that has the same Merkle hash root. For the example above, an attacker can create a new document containing two data blocks, where the first is hash 0-0 + hash 0-1, and the second is hash 1-0 + hash 1-1.[14][15]

One simple fix is defined in Certificate Transparency: when computing leaf node hashes, a 0x00 byte is prepended to the hash data, while 0x01 is prepended when computing internal node hashes.[13] Limiting the hash tree size is a prerequisite of some formal security proofs, and helps in making some proofs tighter. Some implementations limit the tree depth using hash tree depth prefixes before hashes, so any extracted hash chain is defined to be valid only if the prefix decreases at each step and is still positive when the leaf is reached.

