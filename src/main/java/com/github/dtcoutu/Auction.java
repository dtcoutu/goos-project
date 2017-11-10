package com.github.dtcoutu;

public interface Auction {
    void bid(int amount);

    void join();

    void addAuctionEventListener(AuctionSniper auctionSniper);
}
