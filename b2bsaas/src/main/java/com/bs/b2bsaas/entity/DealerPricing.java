package com.bs.b2bsaas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dealer_pricing",
    uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "dealer_id", "product_id"}))
public class DealerPricing extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id", nullable = false)
  private Tenant tenant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dealer_id", nullable = false)
  private Dealer dealer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  private Instant validFrom = Instant.now();

  private Instant validUntil;

}
