import java.nio.charset.StandardCharsets;

public class SHA256 {

    // ---- constants ----
    private static final int[] K = { /* 64 round constants */ };
    private static final int[] H0 = { /* 8 initial hash values */ };

    // ---- entry point for standalone testing ----
    public static void main(String[] args) {
        byte[] digest = hash("abc".getBytes(StandardCharsets.UTF_8));
        System.out.println(toHex(digest));
        // cross-check: echo -n "abc" | sha256sum
    }

    // ---- public API ----
    public static byte[] hash(byte[] msg) {
        byte[] padded = pad(msg);
        int[] h = H0.clone();
        for (int i = 0; i < padded.length / 64; i++) {
            int[] w = scheduleWords(padded, i);
            compress(h, w);
        }
        return toBytes(h);
    }

    // ---- core steps (your TODOs) ----
    private static byte[] pad(byte[] msg) {
        // first we need to find the length of the block and return our padded message, we find the length by adding 1 to our current length, mod by 64, take the remmainder and find how many bytes we need to add to get to 64
        int msgLen = msg.length;
        int intzerofill = 56 - ((msgLen + 1) % 56); //a - (msgLen+1) % a
        int blockSize = msgLen + 1 + intzerofill + 8; // message + 
        
        byte[] block = new byte[blockSize];

        
        byte[] result = new byte[a.length + b.length];

        
        // append 0x80, zeros to 56 mod 64, then 64-bit big-endian bit-length
        return [msg + 0x00 + 00fill to mod 64]; // should be 64 bytes
    }

    private static int[] scheduleWords(byte[] block, int blockIndex) {
        // first 16 words from block, extend to 64 via sigma functions
        return null;
    }

    private static void compress(int[] h, int[] w) {
        // 64 rounds with a-h working vars, add result back into h
    }

    // ---- helpers ----
    private static int rotr(int x, int n) { return (x >>> n) | (x << (32 - n)); }

    private static byte[] toBytes(int[] h) {
        // 8 ints -> 32 bytes, big-endian
        return null;
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }
}