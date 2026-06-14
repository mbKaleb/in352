import java.nio.charset.StandardCharsets;

public class SHA256 {

    // constants
    private static final int[] K = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5,
            0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
            0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
            0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7,
            0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
            0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3,
            0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
            0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
            0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };

    private static final int[] H0 = {
            0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a,
            0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19

    };

    private static int sig0(int x) {
        return rotr(x, 7) ^ rotr(x, 18) ^ (x >>> 3);
    }

    private static int sig1(int x) {
        return rotr(x, 17) ^ rotr(x, 19) ^ (x >>> 10);
    }

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
        for (int i = 0; i < 16; i++) {
            // combind bytes into larger number, | since we dont have overlapping bits
            w[i] = (block[off + 4 * i] & 0xff) << 24 |
                    (block[off + 4 * i + 1] & 0xff) << 16 |
                    (block[off + 4 * i + 2] & 0xff) << 8 |
                    block[off + 4 * i + 3] & 0xff;
        }

        for (int i = 16; i < 64; i++) {
            w[i] = (sig1(w[i - 2]) + w[i - 7] + sig0(w[i - 15]) + w[i - 16]);
        }

        return w;
    }

    private static void compress(int[] state, int[] w) {
        int a = state[0], b = state[1], c = state[2], d = state[3];
        int e = state[4], f = state[5], g = state[6], h = state[7];

        for (int i = 0; i< 64; i++) {
            int S1 = rotr(e, 6) ^ rotr(e,11) ^ rotr(e,25);
            in ch = 
        }
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