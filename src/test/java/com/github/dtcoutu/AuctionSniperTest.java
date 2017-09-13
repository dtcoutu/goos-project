package com.github.dtcoutu;

import org.junit.Test;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuctionSniperTest {
    private static final String ITEM_ID = "auction-item-id";
    private final Auction auction = mock(Auction.class);
    private final SniperListener sniperListener = mock(SniperListener.class);
    private final AuctionSniper sniper = new AuctionSniper(ITEM_ID, auction, sniperListener);

    @Test
    public void reportsLostWhenAuctionClosesImmediately() {
        sniper.auctionClosed();

        verify(sniperListener, atLeastOnce()).sniperLost();
    }

    @Test
    public void reportsLostWhenAuctionClosesWhenBidding() {
        sniper.currentPrice(123, 45, AuctionEventListener.PriceSource.FromOtherBidder);
        sniper.auctionClosed();

        verify(sniperListener).sniperLost();
    }

    @Test
    public void reportsWonWhenAuctionClosesWhenWinning() {
        sniper.currentPrice(123, 45, AuctionEventListener.PriceSource.FromSniper);
        sniper.auctionClosed();

        verify(sniperListener).sniperWon();
    }

    @Test
    public void bidsHighersAndReportsBiddingWhenNewPriceArrives() {
        final int price = 1001;
        final int increment = 25;
        final int bid = price + increment;

        sniper.currentPrice(price, increment, AuctionEventListener.PriceSource.FromOtherBidder);

        verify(auction).bid(bid);
        verify(sniperListener, atLeastOnce())
                .sniperStateChanged(ImmutableSniperSnapshot.builder().itemId(ITEM_ID).lastPrice(price).lastBid(bid).state(SniperState.BIDDING).build());
    }

    @Test
    public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
        sniper.currentPrice(123, 12, AuctionEventListener.PriceSource.FromOtherBidder);
        sniper.currentPrice(135, 45, AuctionEventListener.PriceSource.FromSniper);

        verify(sniperListener).sniperStateChanged(ImmutableSniperSnapshot.builder().itemId(ITEM_ID).lastPrice(135).lastBid(135).state(SniperState.WINNING).build());
    }
}
