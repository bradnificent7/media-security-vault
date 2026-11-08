package com.security.vault;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class SecureStreamEngine {

    // A standard enterprise memory buffer size (4 Kilobytes)
    private static final int BUFFER_SIZE = 4096;

    /**
     * Processes a file stream using small chunks to protect system memory (RAM).
     * This prevents the server from crashing on large video uploads.
     */
    public void processIncomingStream(InputStream inputStream) {
        // Allocate exactly 4KB of memory buffer space
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        int chunkCount = 0;
        long totalBytesProcessed = 0;

        System.out.println("[Stream Engine] Initializing memory buffer: " + BUFFER_SIZE + " bytes.");

        try {
            // Read the file stream block by block until inputStream.read() returns -1 (End of File)
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                chunkCount++;
                totalBytesProcessed += bytesRead;

                // Simulate processing the data chunk (e.g., passing it to an encryption algorithm)
                // We only print the first few chunks so we don't flood your console log
                if (chunkCount <= 3) {
                    System.out.println("-> Chunk #" + chunkCount + ": Read " + bytesRead + " bytes into RAM safely.");
                }
            }

            System.out.println("\n[Stream Engine] SUCCESS: Stream processing complete.");
            System.out.println("Total Chunks Managed: " + chunkCount);
            System.out.println("Total Payload Size: " + totalBytesProcessed + " bytes (" + (totalBytesProcessed / 1024) + " KB)");

        } catch (Exception e) {
            // CRITICAL CYBERSECURITY RULE: Capture the exact exception trace to diagnose bugs
            System.err.println("[DIAGNOSIS ALERT] Stream processing failed mid-transit!");
            System.err.println("Root Cause Error Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== LAUNCHING EMPIRE VAULT INGESTION SERVICE ===");

        SecureStreamEngine engine = new SecureStreamEngine();

        // 1. Simulate a mock 15 Kilobyte video clip file using random text repeated
        String mockLargeFileData = "RIFF....WAVEfmt ...." + "A".repeat(15000);
        byte[] mockFileBytes = mockLargeFileData.getBytes();

        // 2. Wrap the bytes into an InputStream (simulating an active network or disk upload stream)
        InputStream simulatedUploadStream = new ByteArrayInputStream(mockFileBytes);

        // 3. Execute the streaming pipeline
        engine.processIncomingStream(null);

        System.out.println("=== INGESTION ENGINE IDLE ===");
    }
}
