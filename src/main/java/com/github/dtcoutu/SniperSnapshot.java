package com.github.dtcoutu;

import org.immutables.value.Value;

@Value.Immutable
public abstract class SniperSnapshot {
    public abstract String itemId();
    public abstract int lastPrice();
    public abstract int lastBid();
    public abstract SniperState state();

    public static SniperSnapshot joining(String itemId) {
        return ImmutableSniperSnapshot.builder().itemId(itemId).lastPrice(0).lastBid(0).state(SniperState.JOINING).build();
    }

    public SniperSnapshot winning(int newLastPrice) {
        return ImmutableSniperSnapshot.copyOf(this).withLastPrice(newLastPrice).withState(SniperState.WINNING);
    }

    public SniperSnapshot bidding(int newLastPrice, int newLastBid) {
        return ImmutableSniperSnapshot.copyOf(this).withLastPrice(newLastPrice).withLastBid(newLastBid).withState(SniperState.BIDDING);
    }

    public SniperSnapshot closed() {
        return ImmutableSniperSnapshot.copyOf(this).withState(state().whenAuctionClosed());
    }

    public boolean isForSameItemAs(SniperSnapshot snapshot) {
        return this.itemId().equals(snapshot.itemId());
    }
}

