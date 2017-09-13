package com.github.dtcoutu;

import org.immutables.value.Value;

@Value.Immutable
public interface SniperSnapshot {
    String itemId();
    int lastPrice();
    int lastBid();
    SniperState state();
}

