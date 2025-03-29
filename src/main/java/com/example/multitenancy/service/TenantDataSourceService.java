package com.example.multitenancy.service;

import com.example.multitenancy.central.entity.TenantConfigEntity;
import com.example.multitenancy.central.repository.TenantConfigRepository;
import com.example.multitenancy.config.util.DummyDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TenantDataSourceService {

    private static final String DUMMY_ID = "dummy";

    @Autowired
    private TenantConfigRepository tenantConfigRepository;

    private final Map<String, DataSource> dataSourceCache = new ConcurrentHashMap<>();

    public DataSource getDataSource(String tenantId) {
        String id = tenantId != null && !tenantId.isBlank() ? tenantId : DUMMY_ID;
        return dataSourceCache.computeIfAbsent(id, this::createDataSourceForTenant);
    }

    private DataSource createDataSourceForTenant(String tenantId) {
        if (tenantId.equals(DUMMY_ID)) {
            return new DummyDataSource();
        }
        TenantConfigEntity tenantConfigEntity = tenantConfigRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found: " + tenantId));

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(tenantConfigEntity.getDbDriver());
        dataSourceBuilder.username(tenantConfigEntity.getDbUsername());
        dataSourceBuilder.password(tenantConfigEntity.getDbPassword());
        dataSourceBuilder.url(tenantConfigEntity.getDbUrl());
        return dataSourceBuilder.build();
    }
}
