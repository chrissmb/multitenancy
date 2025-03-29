package com.example.multitenancy.config;

import com.example.multitenancy.service.TenantDataSourceService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {

    @Autowired
    private TenantDataSourceService tenantDataSourceService;

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
    }

    @Override
    @Nonnull
    protected DataSource determineTargetDataSource() {
        String tenantId = (String) determineCurrentLookupKey();
        return tenantDataSourceService.getDataSource(tenantId);
    }
}
