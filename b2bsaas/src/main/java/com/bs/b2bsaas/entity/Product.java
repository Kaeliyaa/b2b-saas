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
@Table (name = "products",
uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "sku"}))

public class Product extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id", nullable = false)
  private Tenant tenant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private ProductCategory category;

  @Column(nullable = false)
  private String sku;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal basePrice;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal gstRate = new BigDecimal("18.00");

  private String unit = "piece";

  @Column(nullable = false)
  private Boolean isActive = true;
}