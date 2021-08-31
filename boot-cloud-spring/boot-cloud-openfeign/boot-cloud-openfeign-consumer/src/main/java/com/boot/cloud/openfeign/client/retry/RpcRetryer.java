package com.boot.cloud.openfeign.client.retry;

import feign.RetryableException;
import feign.Retryer;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * com.boot.cloud.openfeign.client.retry.RpcRetry
 *
 * @author lipeng
 * @date 2021/8/31 1:58 PM
 */
public class RpcRetryer implements Retryer {

    private final int maxAttempts;
    private final long period;
    private final long maxPeriod;
    int attempt;
    long sleptForMillis;

    public RpcRetryer() {
        this(100, SECONDS.toMillis(1), 4);
    }

    public RpcRetryer(long period, long maxPeriod, int maxAttempts) {
        this.period = period;
        this.maxPeriod = maxPeriod;
        this.maxAttempts = maxAttempts;
        this.attempt = 1;
    }

    protected long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        if (attempt++ >= maxAttempts) {
            throw e;
        }

        long interval;
        if (e.retryAfter() != null) {
            interval = e.retryAfter().getTime() - currentTimeMillis();
            if (interval > maxPeriod) {
                interval = maxPeriod;
            }
            if (interval < 0) {
                return;
            }
        } else {
            interval = nextMaxInterval();
        }
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
            throw e;
        }
        sleptForMillis += interval;
    }

    /**
     * Calculates the time interval to a retry attempt. <br>
     * The interval increases exponentially with each attempt, at a rate of nextInterval *= 1.5
     * (where 1.5 is the backoff factor), to the maximum interval.
     *
     * @return time in nanoseconds from now until the next attempt.
     */
    long nextMaxInterval() {
        long interval = (long) (period * Math.pow(1.5, attempt - 1));
        return Math.min(interval, maxPeriod);
    }

    @Override
    public Retryer clone() {
        return new RpcRetryer(period, maxPeriod, maxAttempts);
    }
}
