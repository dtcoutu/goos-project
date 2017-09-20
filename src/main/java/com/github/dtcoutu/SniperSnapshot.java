package com.github.dtcoutu;

import org.immutables.value.Value;

@Value.Immutable
public abstract class SniperSnapshot {
    abstract String itemId();
    abstract int lastPrice();
    abstract int lastBid();
    abstract SniperState state();

    public static SniperSnapshot joining(String itemId) {
        return ImmutableSniperSnapshot.builder().itemId(itemId).lastPrice(0).lastBid(0).state(SniperState.JOINING).build();
    }

    public SniperSnapshot winning(int newLastPrice) {
        return ImmutableSniperSnapshot.copyOf(this).withLastPrice(newLastPrice).withState(SniperState.WINNING);
    }

    public SniperSnapshot bidding(int newLastPrice, int newLastBid) {
        return ImmutableSniperSnapshot.copyOf(this).withLastPrice(newLastPrice).withLastBid(newLastBid).withState(SniperState.BIDDING);
    }

    public SniperSnapshot lost() {
        return ImmutableSniperSnapshot.copyOf(this).withState(SniperState.LOST);
    }

    public SniperSnapshot won() {
        return ImmutableSniperSnapshot.copyOf(this).withState(SniperState.WON);
    }
}

