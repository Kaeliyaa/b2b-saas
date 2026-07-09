package com.bs.b2bsaas.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inventory",
    uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "product_id"}))
public class Inventory extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id", nullable = false)
  private Tenant tenant;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(nullable = false)
  private Integer quantityAvailable = 0;

  @Column(nullable = false)
  private Integer quantityReserved = 0;

  private Integer reorderLevel = 10;
}
