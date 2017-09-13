package com.github.dtcoutu;

public interface SniperListener {
    void sniperLost();

    void sniperBidding(ImmutableSniperState sniperState);

    void sniperWinning();

    void sniperWon();
}
