package com.security.vault;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SecureStreamEngine {

    private static final int BUFFER_SIZE = 4096;

    /**
     * Phase 2 Upgrade: Streams incoming data in 4KB chunks while simultaneously
     * computing a tamper-evident SHA-256 cryptographic fingerprint in real time.
     */
    public String processAndFingerprintStream(InputStream inputStream) {
        // STEP 1: Defensive security gate against null payloads
        if (inputStream == null) {
            System.err.println("[SECURITY ENFORCEMENT] Aborting operation: Malicious or uninitialized null payload rejected.");
            return null;
        }

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        int chunkCount = 0;
        long totalBytesProcessed = 0;

        System.out.println("[Stream Engine] Initializing 4KB memory buffer & SHA-256 cryptographic engine...");

        try {
            // STEP 2: Initialize the SHA-256 hashing engine
            MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");

            // STEP 3: Read chunks and feed them straight into the cryptographic engine
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                chunkCount++;
                totalBytesProcessed += bytesRead;

                // Feed ONLY the exact bytes read in this chunk into the hash calculation
                sha256Digest.update(buffer, 0, bytesRead);
            }

            // STEP 4: Finalize the math and convert the raw hash bytes into readable Hexadecimal
            byte[] finalHashBytes = sha256Digest.digest();
            String hexFingerprint = bytesToHex(finalHashBytes);

            System.out.println("\n[Stream Engine] SUCCESS: Ingestion & Fingerprinting complete.");
            System.out.println("Total Chunks Managed: " + chunkCount);
            System.out.println("Total Payload Size: " + totalBytesProcessed + " bytes");
            System.out.println("Tamper-Evident SHA-256: " + hexFingerprint);

            return hexFingerprint;

        } catch (Exception e) {
            System.err.println("[DIAGNOSIS ALERT] Stream or cryptographic processing failed!");
            System.err.println("Root Cause Error Message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Helper method: Converts raw binary bytes into a clean 64-character Hex string.
     */
    private String bytesToHex(byte[] hashBytes) {
        StringBuilder hexString = new StringBuilder(2 * hashBytes.length);
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== LAUNCHING EMPIRE VAULT INGESTION & INTEGRITY TEST ===\n");

        SecureStreamEngine engine = new SecureStreamEngine();

        // Test 1: Verify our defensive gate still blocks null attacks
        System.out.println("--- Test 1: Malicious Null Payload ---");
        engine.processAndFingerprintStream(null);

        // Test 2: Simulate a valid 15KB tactical gameplay recording
        System.out.println("\n--- Test 2: Valid Media Asset Stream ---");
        String mockVideoData = "AV1_HEADER_DATA_" + "FRAME_PAYLOAD_".repeat(1200);
        InputStream validStream = new ByteArrayInputStream(mockVideoData.getBytes(StandardCharsets.UTF_8));

        engine.processAndFingerprintStream(validStream);

        System.out.println("\n=== INGESTION ENGINE IDLE ===");
    }
}
