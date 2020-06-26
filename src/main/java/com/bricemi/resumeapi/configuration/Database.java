package com.bricemi.resumeapi.configuration;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Scope;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Database {

    @NonNull
    @Value("${storage.host}")
    private String host;

    @Value("${storage.bucket}")
    private String bucket;

    @Value("${storage.username}")
    private String username;

    @Value("${storage.password}")
    private String password;

    public @Bean Cluster loginCluster() {
        return Cluster.connect(host, username, password);
    }

    public @Bean Bucket loginBucket() {
        return loginCluster().bucket(bucket);
    }
}
