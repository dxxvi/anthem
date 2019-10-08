package com.googlecode.pngtastic.core.processing;

import com.googlecode.pngtastic.core.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/**
 * Implements PNG compression and decompression
 *
 * @author rayvanderborght
 * @author me (I want to use an Executor for the null compressionLevel case)
 */
public class PngtasticCompressionHandler implements PngCompressionHandler {
    public static final ExecutorService EXECUTOR_SERVICE =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 2);

    private static final int[] compressionStrategies =
            { Deflater.DEFAULT_STRATEGY, Deflater.FILTERED, Deflater.HUFFMAN_ONLY };

    public PngtasticCompressionHandler(Logger log) {}
    public PngtasticCompressionHandler() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] deflate(PngByteArrayOutputStream inflatedImageData, Integer compressionLevel, boolean concurrent) {
        final List<byte[]> results = deflateImageDataConcurrently(inflatedImageData);

        byte[] result = null;
        for (byte[] data : results)
            if (result == null || (data.length < result.length))
                result = data;

        return result;
    }

    @Override
    public String encodeBytes(byte[] bytes) {
        return Base64.encodeBytes(bytes);
    }

    private static class DeflatingTask implements Runnable {
        private final List<byte[]> results;
        private final CountDownLatch latch;
        private final PngByteArrayOutputStream inflatedImageData;
        private final int strategy;
        private final int compression;

        public DeflatingTask(List<byte[]> results, CountDownLatch latch, PngByteArrayOutputStream inflatedImageData, int strategy, int compression) {
            this.results = results;
            this.latch = latch;
            this.inflatedImageData = inflatedImageData;
            this.strategy = strategy;
            this.compression = compression;
        }

        @Override
        public void run() {
            ByteArrayOutputStream baos = PngtasticCompressionHandler.deflate(inflatedImageData, strategy, compression);
            if (baos != null)
                results.add(baos.toByteArray());
            latch.countDown();
        }
    }
    /*
     * Do the work of deflating (compressing) the image data with the
     * different compression strategies in separate threads to take
     * advantage of multiple core architectures.
     */
    private List<byte[]> deflateImageDataConcurrently(final PngByteArrayOutputStream inflatedImageData) {
        final List<byte[]> results = new CopyOnWriteArrayList<>();

        DeflatingTask dTask = null;
        CountDownLatch latch =
                new CountDownLatch(compressionStrategies.length * (Deflater.BEST_COMPRESSION - Deflater.NO_COMPRESSION));
        for (final int strategy : compressionStrategies) {
            for (int compression = Deflater.BEST_COMPRESSION; compression > Deflater.NO_COMPRESSION; compression--) {
                if (dTask == null)
                    dTask = new DeflatingTask(results, latch, inflatedImageData, strategy, compression);
                else
                    EXECUTOR_SERVICE.execute(new DeflatingTask(results, latch, inflatedImageData, strategy, compression));
            }
        }
        dTask.run();
        try {
            latch.await();
        }
        catch (InterruptedException __) { /* who cares */ }
        return results;
    }

    /* */
    private static ByteArrayOutputStream deflate(PngByteArrayOutputStream inflatedImageData, int strategy, int compression) {
        final ByteArrayOutputStream deflatedOut = new ByteArrayOutputStream();
        final Deflater deflater = new Deflater(compression);
        deflater.setStrategy(strategy);

        try {
            final DeflaterOutputStream stream = new DeflaterOutputStream(deflatedOut, deflater);
            stream.write(inflatedImageData.get(), 0, inflatedImageData.len());
            stream.close();
        }
        catch (IOException __) {
            return null;
        }
        finally {
            deflater.end();
        }

        return deflatedOut;
    }
}