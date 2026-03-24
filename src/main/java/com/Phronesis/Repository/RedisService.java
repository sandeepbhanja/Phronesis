package com.Phronesis.Repository;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import jakarta.annotation.PostConstruct;

@Service
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Value("${spring.redis.secret}")
    private String redisPassword;

    private RedisClient client;
    private StatefulRedisConnection<String, String> connection;
    private RedisAsyncCommands<String, String> commands;
    private final int MAX_RETRY = 5;

    @PostConstruct
    public void init() {
        logger.info("Inside init()");
        RedisURI uri = RedisURI.Builder
                .redis("redis-11171.crce280.asia-south1-1.gcp.cloud.redislabs.com", 11171)
                .withAuthentication("default", redisPassword)
                .build();
        client = RedisClient.create(uri);
        connection = client.connect();
        commands = connection.async();
        ensureConnected();
    }

    public boolean isConnected() {
        if (connection == null || !connection.isOpen()) {
            return false;
        }
        try {
            RedisFuture<String> redisResponse = commands.ping();
            String value = redisResponse.get(1, TimeUnit.MINUTES);
            return value.equalsIgnoreCase("PONG");
        } catch (Exception e) {
            return false;
        }
    }

    public void ensureConnected() {
        int currentRetry = 0;
        double baseTime = 2.0; // seconds

        while (currentRetry < MAX_RETRY) {
            if (isConnected()) {
                logger.info("Connected Successfully!");
                return; // Success
            }

            reconnect();

            if (!isConnected()) {
                long delayMs = (long) (baseTime * Math.pow(2, currentRetry) * 1000);
                currentRetry++;

                try {
                    Thread.sleep(delayMs); // Use sleep instead of wait()
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Connection retry interrupted", e);
                }
            } else {
                return; // Successfully reconnected
            }
        }

        throw new RuntimeException("Failed to connect to Redis after " + MAX_RETRY + " attempts");
    }

    private void reconnect() {
        close();
        init();
    }

    private void close() {
        if (connection != null && connection.isOpen())
            connection.close();
        if (client != null)
            client.shutdown();
    }

    public boolean setRedisData(String value) {
        return setData(value);
    }

    private boolean setData(String value) {
        try {
            int hashId = value.hashCode();
            commands.set(String.valueOf(hashId), value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
