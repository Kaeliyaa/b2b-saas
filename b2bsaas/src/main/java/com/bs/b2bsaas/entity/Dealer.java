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
@Table(name = "dealers",
    uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "user_id"}))
public class Dealer extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id", nullable = false)
  private Tenant tenant;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String companyName;

  private String gstNumber;

  @Column(precision = 12, scale = 2)
  private BigDecimal creditLimit = BigDecimal.ZERO;

  @Column(precision = 12, scale = 2)
  private BigDecimal currentCreditUsed = BigDecimal.ZERO;

  @Column(columnDefinition = "TEXT")
  private String billingAddress;

  @Column(columnDefinition = "TEXT")
  private String shippingAddress;

  @Column(nullable = false)
  private Boolean isActive = true;
}
