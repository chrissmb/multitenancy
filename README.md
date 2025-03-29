# Multitenancy Service

Central database script:
```roomsql
CREATE TABLE tb_tenant_config (
    tenant_id     VARCHAR(100) PRIMARY KEY,
    db_url        VARCHAR(500),
    db_username   VARCHAR(100),
    db_password   VARCHAR(500),
    db_driver     VARCHAR(100),
    active        BOOLEAN,
    last_updated  TIMESTAMP
);

insert into public.tb_tenant_config (tenant_id, db_url, db_username, db_password, db_driver, active, last_updated) values
	('tenant1', 'jdbc:postgresql://localhost:5432/tenant1', 'my_user', 'my_pwd', 'org.postgresql.Driver', true, now()),
	('tenant2', 'jdbc:postgresql://localhost:5432/tenant2', 'my_user', 'my_pwd', 'org.postgresql.Driver', true, now());
```

Tenant database script:

```roomsql
create table tb_person (
	id bigint primary key,
	name varchar(255)
);
```