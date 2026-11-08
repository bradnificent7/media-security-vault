package com.security.vault;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class SecureStreamEngine {

    private static final int BUFFER_SIZE = 4096;

    public void processIncomingStream(InputStream inputStream) {
        // STEP 1: Defensive security check - Abort immediately if input is missing/null
        if (inputStream == null) {
            System.err.println("[SECURITY ENFORCEMENT] Aborting operation: Malicious or uninitialized null payload rejected.");
            return;
        }

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        int chunkCount = 0;
        long totalBytesProcessed = 0;

        System.out.println("[Stream Engine] Initializing memory buffer: " + BUFFER_SIZE + " bytes.");

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                chunkCount++;
                totalBytesProcessed += bytesRead;

                if (chunkCount <= 3) {
                    System.out.println("-> Chunk #" + chunkCount + ": Read " + bytesRead + " bytes into RAM safely.");
                }
            }

            System.out.println("\n[Stream Engine] SUCCESS: Stream processing complete.");
            System.out.println("Total Chunks Managed: " + chunkCount);
            System.out.println("Total Payload Size: " + totalBytesProcessed + " bytes (" + (totalBytesProcessed / 1024) + " KB)");

        } catch (Exception e) {
            System.err.println("[DIAGNOSIS ALERT] Stream processing failed mid-transit!");
            System.err.println("Root Cause Error Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // THIS IS THE MAIN ENTRY METHOD WE NEED TO ADD BACK
    public static void main(String[] args) {
        System.out.println("=== LAUNCHING ENGINE DIAGNOSTIC ===\n");

        SecureStreamEngine engine = new SecureStreamEngine();

        // Testing our defensive gate by passing a malicious 'null' stream
        engine.processIncomingStream(null);

        System.out.println("\n=== INGESTION ENGINE IDLE ===");
    }
}
