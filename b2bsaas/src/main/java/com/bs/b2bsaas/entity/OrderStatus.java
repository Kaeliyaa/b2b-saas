package com.bs.b2bsaas.entity;

public enum OrderStatus {
  DRAFT,
  CONFIRMED,
  PROCESSING,
  PICKING,
  PACKED,
  DISPATCHED,
  DELIVERED,
  CANCELLED;

  public boolean canTransitionTo(OrderStatus next){
    return switch (this) {
      case DRAFT -> next == CONFIRMED || next == CANCELLED;
      case CONFIRMED -> next == PROCESSING || next == CANCELLED;
      case PROCESSING -> next == PICKING || next == CANCELLED;
      case PICKING -> next == PACKED;
      case PACKED -> next == DISPATCHED;
      case DISPATCHED -> next == DELIVERED;
      case DELIVERED, CANCELLED -> false;
    };

  }

}
