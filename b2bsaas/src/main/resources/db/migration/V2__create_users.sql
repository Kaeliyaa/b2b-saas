CREATE TYPE user_role AS ENUM (
    'SUPER_ADMIN',
    'ADMIN',
    'DEALER',
    'WAREHOUSE_STAFF',
    'ACCOUNTANT'
);

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
                       email VARCHAR(255) NOT NULL,
                       password_hash TEXT,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       role user_role NOT NULL DEFAULT 'DEALER',
                       is_active BOOLEAN DEFAULT TRUE,
                       google_id VARCHAR(255),
                       last_login_at TIMESTAMPTZ,
                       created_at TIMESTAMPTZ DEFAULT NOW(),
                       updated_at TIMESTAMPTZ DEFAULT NOW(),
                       UNIQUE(tenant_id, email)
);

CREATE INDEX idx_users_tenant_id ON users(tenant_id);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_tenant_email ON users(tenant_id, email);