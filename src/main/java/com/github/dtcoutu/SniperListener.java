package com.github.dtcoutu;

public interface SniperListener {
    void sniperLost();

    void sniperBidding(ImmutableSniperSnapshot sniperState);

    void sniperWinning();

    void sniperWon();
}
