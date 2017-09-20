package com.github.dtcoutu;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {

    private static final SniperSnapshot STARTING_UP = ImmutableSniperSnapshot.builder().itemId("").lastPrice(0).lastBid(0).state(SniperState.JOINING).build();
    private SniperSnapshot sniperSnapshot = STARTING_UP;
    private String statusText = MainWindow.STATUS_JOINING;

    private static final String[] STATUS_TEXT = {
            MainWindow.STATUS_JOINING,
            MainWindow.STATUS_BIDDING,
            MainWindow.STATUS_WINNING,
            MainWindow.STATUS_LOST,
            MainWindow.STATUS_WON
    };

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (Column.at(columnIndex)) {
            case ITEM_IDENTIFIER:
                return sniperSnapshot.itemId();
            case LAST_PRICE:
                return sniperSnapshot.lastPrice();
            case LAST_BID:
                return sniperSnapshot.lastBid();
            case SNIPER_STATE:
                return statusText;
        }
        return statusText;
    }

    public void setStatusText(String newStatusText) {
        statusText = newStatusText;
        fireTableRowsUpdated(0,0);
    }

    public void sniperStatusChanged(SniperSnapshot sniperSnapshot) {
        this.sniperSnapshot = sniperSnapshot;
        this.statusText = STATUS_TEXT[sniperSnapshot.state().ordinal()];
        fireTableRowsUpdated(0, 0);
    }

    public enum Column {
        ITEM_IDENTIFIER,
        LAST_PRICE,
        LAST_BID, SNIPER_STATE;

        public static Column at(int offset) {
            return values()[offset];
        }
    }
}
