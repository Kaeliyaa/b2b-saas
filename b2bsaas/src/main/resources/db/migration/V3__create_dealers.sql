CREATE TABLE dealers (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
                         user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         company_name VARCHAR(255) NOT NULL,
                         gst_number VARCHAR(50),
                         credit_limit NUMERIC(12,2) DEFAULT 0,
                         current_credit_used NUMERIC(12,2) DEFAULT 0,
                         billing_address TEXT,
                         shipping_address TEXT,
                         is_active BOOLEAN DEFAULT TRUE,
                         created_at TIMESTAMPTZ DEFAULT NOW(),
                         updated_at TIMESTAMPTZ DEFAULT NOW(),
                         UNIQUE(tenant_id, user_id)
);

CREATE INDEX idx_dealers_tenant_id ON dealers(tenant_id);
CREATE INDEX idx_dealers_user_id ON dealers(user_id);