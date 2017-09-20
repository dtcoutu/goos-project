package com.github.dtcoutu;

public interface SniperListener {
    void sniperWon();

    void sniperStateChanged(SniperSnapshot sniperSnapshot);
}
