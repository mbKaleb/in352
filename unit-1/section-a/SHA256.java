import java.nio.charset.StandardCharsets;

public class SHA256 {

    // constants
    private static final int[] K = { /* 64 round constants */ };
    private static final int[] H0 = { /* 8 initial hash values */ };

    private static int sig0(int x) {return rotr(x,7) ^ rotr(x,18) ^ (x>>>3);}
    private static int sig1(int x) {return rotr(x, 17) ^ rotr(x,19) ^ (x>>>10);}



    // entry point for standalone testing
    public static void main(String[] args) {
        byte[] digest = hash("abc".getBytes(StandardCharsets.UTF_8));
        System.out.println(toHex(digest));
        // cross-check: echo -n "abc" | sha256sum
    }

    // public API
    public static byte[] hash(byte[] msg) {
        byte[] padded = pad(msg);
        int[] h = H0.clone();
        for (int i = 0; i < padded.length / 64; i++) {
            int[] w = scheduleWords(padded, i);
            compress(h, w);
        }
        return toBytes(h);
    }

    private static byte[] pad(byte[] msg) {
        // first we need to find the length of the block and return our padded message,
        // we find the length by adding 1 to our current length, mod by 64, take the
        // remmainder and find how many bytes we need to add to get to 64
        int msgLen = msg.length;

        int rem = (msgLen + 1) % 64; // compute once
        int intzerofill = (rem <= 56) ? (56 - rem) : (120 - rem); // use it //a - (msgLen+1) % a

        int blockSize = msgLen + 1 + intzerofill + 8; // message +

        byte[] block = new byte[blockSize];
        System.arraycopy(msg, 0, block, 0, msgLen);
        block[msgLen] = (byte) 0x80;

        long bitLen = (long) msgLen * 8; // bits of our length to append to the end of a block
        int start = blockSize - 8;
        for (int i = 0; i < 8; i++) {
            block[start + i] = (byte) (bitLen >>> (56 - 8 * i));
        }

        return block;

        // append 0x80, zeros to 56 mod 64, then 64-bit big-endian bit-length // should
        // be 64 bytes
    }

    private static int[] scheduleWords(byte[] block, int blockIndex) {
        // first 16 words from block, extend to 64 via sigma functions
        int[] w = new int[64];
        int off = blockIndex * 64;

        // each word is 16 bytes
        for (int i = 0; i < 16; i++){ 
            // combind bytes into larger number, | since we dont have overlapping bits
            w[i] = 
                (block[off + 4*i] &0xff) << 24 | 
                (block[off + 4*i+1] &0xff) << 16 | 
                (block[off + 4*i+2] &0xff) << 8 | 
                block[off +4*i+3] &0xff;
        }

        for (int i = 16; i<64; i++){
            w[i] = (sig1(w[i-2]) + w[i-7] + sig0(w[i-15]) + w[i-16]);
        }

        return w;
    }

    private static void compress(int[] h, int[] w) {
        // 64 rounds with a-h working vars, add result back into h
        
    }

    // ---- helpers ----
    private static int rotr(int x, int n) {
        return (x >>> n) | (x << (32 - n));
    }

    private static byte[] toBytes(int[] h) {
        // 8 ints -> 32 bytes, big-endian
        return null;
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }
}