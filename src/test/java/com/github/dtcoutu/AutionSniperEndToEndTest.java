package com.github.dtcoutu;

import org.junit.After;
import org.junit.Test;

public class AutionSniperEndToEndTest {
    private FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private ApplicationRunner application = new ApplicationRunner();

    @Test public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper();
        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

    // Additional cleanup
    @After public void stopAuction() {
        auction.stop();
    }
    @After public void stopApplication() {
        application.stop();
    }
}
