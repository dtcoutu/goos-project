package com.github.dtcoutu;

public interface AuctionEventListener {
    void auctionClosed();

    void currentPrice(int price, int increment);
}
