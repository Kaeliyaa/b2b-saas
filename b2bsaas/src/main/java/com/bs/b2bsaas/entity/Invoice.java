package com.bs.b2bsaas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "invoices",
  uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "invoice_number"}))
public class Invoice extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tenant_id", nullable = false)
  private Tenant tenant;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dealer_id", nullable = false)
  private Dealer dealer;

  @Column(nullable = false)
  private String invoiceNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private InvoiceStatus status = InvoiceStatus.DRAFT;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal subtotal;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal gstAmount;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal totalAmount;

  private LocalDate dueDate;
  private Instant paidAt;
  private String pdfUrl;

  @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<InvoiceItem> items = new ArrayList<>();
}
