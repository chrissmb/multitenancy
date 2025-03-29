package com.example.multitenancy.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultitenantDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("MultitenantDataSource determineCurrentLookupKey " + TenantContext.getCurrentTenant());
        return TenantContext.getCurrentTenant();
    }
}
