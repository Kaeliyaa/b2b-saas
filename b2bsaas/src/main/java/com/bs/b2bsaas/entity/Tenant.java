package com.bs.b2bsaas.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "tenants")
public class Tenant extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String slug;

  @Column(unique = true, nullable = false)
  private String email;

  private String phone;

  @Column(columnDefinition = "TEXT")
  private String address;

  private String gstNumber;

  @Column(nullable = false)
  private Boolean isActive = true;
}
