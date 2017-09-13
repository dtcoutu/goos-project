package com.github.dtcoutu;

import org.immutables.value.Value;

@Value.Immutable
public interface SniperState {
    String itemId();
    int lastPrice();
    int lastBid();
}

