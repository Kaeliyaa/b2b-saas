package com.bs.b2bsaas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders",
    uniqueConstraints = @UniqueConstraint(columnNames ={"tenant_id, order_number"}))
public class Order extends BaseEntity{

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id", nullable = false)
  private Tenant tenant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dealer_id", nullable = false)
  private Dealer dealer;

  @Column(nullable = false)
  private String orderNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatus status = OrderStatus.DRAFT;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal subtotal = BigDecimal.ZERO;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal gstAmount = BigDecimal.ZERO;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal totalAmount = BigDecimal.ZERO;

  @Column(columnDefinition = "TEXT")
  private String shippingAddress;

  @Column(columnDefinition = "TEXT")
  private String notes;

  @Column(unique = true)
  private String idempotencyKey;

  private Instant confirmedAt;
  private Instant dispatchedAt;
  private Instant deliveredAt;
  private Instant cancelledAt;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
    orphanRemoval = true)
  @Builder.Default
  private List<OrderItem> items = new ArrayList<>();



}
