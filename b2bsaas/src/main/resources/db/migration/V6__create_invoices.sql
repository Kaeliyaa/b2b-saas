CREATE TYPE invoice_status AS ENUM (
    'DRAFT',
    'ISSUED',
    'PAID',
    'CANCELLED',
    'OVERDUE'
);

CREATE TABLE invoices (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
                          order_id UUID NOT NULL REFERENCES orders(id),
                          dealer_id UUID NOT NULL REFERENCES dealers(id),
                          invoice_number VARCHAR(50) NOT NULL,
                          status invoice_status NOT NULL DEFAULT 'DRAFT',
                          subtotal NUMERIC(12,2) NOT NULL,
                          gst_amount NUMERIC(12,2) NOT NULL,
                          total_amount NUMERIC(12,2) NOT NULL,
                          due_date DATE,
                          paid_at TIMESTAMPTZ,
                          pdf_url TEXT,
                          created_at TIMESTAMPTZ DEFAULT NOW(),
                          updated_at TIMESTAMPTZ DEFAULT NOW(),
                          UNIQUE(tenant_id, invoice_number)
);

CREATE TABLE invoice_items (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                               invoice_id UUID NOT NULL REFERENCES invoices(id) ON DELETE CASCADE,
                               product_id UUID NOT NULL REFERENCES products(id),
                               product_name VARCHAR(255) NOT NULL,
                               product_sku VARCHAR(100) NOT NULL,
                               quantity INTEGER NOT NULL,
                               unit_price NUMERIC(12,2) NOT NULL,
                               gst_rate NUMERIC(5,2) NOT NULL,
                               gst_amount NUMERIC(12,2) NOT NULL,
                               line_total NUMERIC(12,2) NOT NULL,
                               created_at TIMESTAMPTZ DEFAULT NOW(),
                               updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_invoices_tenant_id ON invoices(tenant_id);
CREATE INDEX idx_invoices_order_id ON invoices(order_id);
CREATE INDEX idx_invoices_dealer_id ON invoices(dealer_id);
CREATE INDEX idx_invoice_items_invoice ON invoice_items(invoice_id);