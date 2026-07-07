CREATE TABLE tenants (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         name VARCHAR(255) NOT NULL,
                         slug VARCHAR(100) UNIQUE NOT NULL,
                         emoail VARCHAR(255) UNIQUE NOT NULL,
                         phone VARCHAR(20),
                         address TEXT,
                         gst_number VARCHAR(50),
                         is_active BOOLEAN DEFAULT TRUE,
                         created_at TIMESTAMPTZ DEFAULT NOW(),
                         updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_tenants_slug ON tenants(slug);