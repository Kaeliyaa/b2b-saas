package com.bs.b2bsaas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "invoice_items")
public class InvoiceItem extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "invoice_id", nullable = false)
  private Invoice invoice;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  // Snapshot fields — copied at invoice generation time
  @Column(nullable = false)
  private String productName;

  @Column(nullable = false)
  private String productSku;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal unitPrice;

  @Column(nullable = false, precision = 5, scale = 2)
  private BigDecimal gstRate;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal gstAmount;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal lineTotal;
}