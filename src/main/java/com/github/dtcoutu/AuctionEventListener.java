package com.github.dtcoutu;

public interface AuctionEventListener {
    void auctionClosed();

    void currentPrice(int price, int increment, PriceSource priceSource);

    enum PriceSource {
        FromSniper, FromOtherBidder;
    }
}
