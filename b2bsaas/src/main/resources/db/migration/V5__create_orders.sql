CREATE TYPE order_status AS ENUM (
    'DRAFT',
    'CONFIRMED',
    'PROCESSING',
    'PICKING',
    'PACKED',
    'DISPATCHED',
    'DELIVERED',
    'CANCELLED'
);

CREATE TABLE orders (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
                        dealer_id UUID NOT NULL REFERENCES dealers(id),
                        order_number VARCHAR(50) NOT NULL,
                        status order_status NOT NULL DEFAULT 'DRAFT',
                        subtotal NUMERIC(12,2) NOT NULL DEFAULT 0,
                        gst_amount NUMERIC(12,2) NOT NULL DEFAULT 0,
                        total_amount NUMERIC(12,2) NOT NULL DEFAULT 0,
                        shipping_address TEXT,
                        notes TEXT,
                        idempotency_key VARCHAR(255),
                        confirmed_at TIMESTAMPTZ,
                        dispatched_at TIMESTAMPTZ,
                        delivered_at TIMESTAMPTZ,
                        cancelled_at TIMESTAMPTZ,
                        created_at TIMESTAMPTZ DEFAULT NOW(),
                        updated_at TIMESTAMPTZ DEFAULT NOW(),
                        UNIQUE(tenant_id, order_number),
                        UNIQUE(idempotency_key)
);

CREATE TABLE order_items (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
                             order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                             product_id UUID NOT NULL REFERENCES products(id),
                             quantity INTEGER NOT NULL,
                             unit_price NUMERIC(12,2) NOT NULL,
                             gst_rate NUMERIC(5,2) NOT NULL,
                             gst_amount NUMERIC(12,2) NOT NULL,
                             line_total NUMERIC(12,2) NOT NULL,
                             created_at TIMESTAMPTZ DEFAULT NOW(),
                             updated_at TIMESTAMPTZ DEFAULT NOW(),
                             CONSTRAINT positive_quantity CHECK (quantity > 0),
                             CONSTRAINT positive_price CHECK (unit_price >= 0)
);

CREATE INDEX idx_orders_tenant_id ON orders(tenant_id);
CREATE INDEX idx_orders_dealer_id ON orders(dealer_id);
CREATE INDEX idx_orders_status ON orders(tenant_id, status);
CREATE INDEX idx_orders_idempotency ON orders(idempotency_key);
CREATE INDEX idx_order_items_order ON order_items(order_id);