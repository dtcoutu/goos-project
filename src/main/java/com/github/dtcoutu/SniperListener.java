package com.github.dtcoutu;

public interface SniperListener {
    void sniperLost();

    void sniperWon();

    void sniperStateChanged(SniperSnapshot sniperSnapshot);
}
