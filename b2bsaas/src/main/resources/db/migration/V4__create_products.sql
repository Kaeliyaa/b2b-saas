CREATE TABLE product_categories (
                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
                                    name VARCHAR(255) NOT NULL,
                                    description TEXT,
                                    parent_id UUID REFERENCES product_categories(id),
                                    created_at TIMESTAMPTZ DEFAULT NOW(),
                                    updated_at TIMESTAMPTZ DEFAULT NOW(),
                                    UNIQUE(tenant_id, name)
);

CREATE TABLE products (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
                          category_id UUID REFERENCES product_categories(id),
                          sku VARCHAR(100) NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          base_price NUMERIC(12,2) NOT NULL,
                          gst_rate NUMERIC(5,2) NOT NULL DEFAULT 18.00,
                          unit VARCHAR(50) DEFAULT 'piece',
                          is_active BOOLEAN DEFAULT TRUE,
                          created_at TIMESTAMPTZ DEFAULT NOW(),
                          updated_at TIMESTAMPTZ DEFAULT NOW(),
                          UNIQUE(tenant_id, sku)
);

CREATE TABLE dealer_pricing (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
                                dealer_id UUID NOT NULL REFERENCES dealers(id) ON DELETE CASCADE,
                                product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
                                custom_price NUMERIC(12,2) NOT NULL,
                                valid_from TIMESTAMPTZ DEFAULT NOW(),
                                valid_until TIMESTAMPTZ,
                                created_at TIMESTAMPTZ DEFAULT NOW(),
                                updated_at TIMESTAMPTZ DEFAULT NOW(),
                                UNIQUE(tenant_id, dealer_id, product_id)
);

CREATE TABLE inventory (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
                           product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
                           quantity_available INTEGER NOT NULL DEFAULT 0,
                           quantity_reserved INTEGER NOT NULL DEFAULT 0,
                           reorder_level INTEGER DEFAULT 10,
                           created_at TIMESTAMPTZ DEFAULT NOW(),
                           updated_at TIMESTAMPTZ DEFAULT NOW(),
                           UNIQUE(tenant_id, product_id),
                           CONSTRAINT positive_quantity CHECK (quantity_available >= 0),
                           CONSTRAINT positive_reserved CHECK (quantity_reserved >= 0)
);

CREATE INDEX idx_products_tenant_id ON products(tenant_id);
CREATE INDEX idx_products_sku ON products(tenant_id, sku);
CREATE INDEX idx_dealer_pricing_dealer ON dealer_pricing(dealer_id);
CREATE INDEX idx_inventory_product ON inventory(product_id);