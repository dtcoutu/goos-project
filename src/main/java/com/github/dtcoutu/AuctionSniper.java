package com.github.dtcoutu;

public class AuctionSniper implements AuctionEventListener {
    private final String itemId;
    private final Auction auction;
    private final SniperListener sniperListener;
    private SniperSnapshot snapshot;
    private boolean isWinning;

    public AuctionSniper(String itemId, Auction auction, SniperListener sniperListener) {
        this.itemId = itemId;
        this.auction = auction;
        this.sniperListener = sniperListener;
        this.snapshot = SniperSnapshot.joining(itemId);
    }

    @Override
    public void auctionClosed() {
        if (isWinning) {
            sniperListener.sniperStateChanged(snapshot.won());
        } else {
            sniperListener.sniperStateChanged(snapshot.lost());
        }
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        isWinning = priceSource == PriceSource.FromSniper;
        if (isWinning) {
            snapshot = snapshot.winning(price);
        } else {
            int bid = price + increment;
            auction.bid(bid);
            snapshot = snapshot.bidding(price, bid);
        }
        sniperListener.sniperStateChanged(snapshot);
    }
}
