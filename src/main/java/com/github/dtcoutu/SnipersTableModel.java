package com.github.dtcoutu;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel implements SniperListener {

    private static final SniperSnapshot STARTING_UP = ImmutableSniperSnapshot.builder().itemId("").lastPrice(0).lastBid(0).state(SniperState.JOINING).build();
    private SniperSnapshot sniperSnapshot = STARTING_UP;

    private static final String[] STATUS_TEXT = {
            "Joining", "Bidding", "Winning", "Lost", "Won"
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
        return Column.at(columnIndex).valueIn(sniperSnapshot);
    }

    @Override
    public void sniperStateChanged(SniperSnapshot sniperSnapshot) {
        this.sniperSnapshot = sniperSnapshot;
        fireTableRowsUpdated(0, 0);
    }

    public enum Column {
        ITEM_IDENTIFIER {
            @Override public Object valueIn(SniperSnapshot snapshot) { return snapshot.itemId(); }
        },
        LAST_PRICE {
            @Override public Object valueIn(SniperSnapshot snapshot) { return snapshot.lastPrice(); }
        },
        LAST_BID {
            @Override public Object valueIn(SniperSnapshot snapshot) { return snapshot.lastBid(); }
        },
        SNIPER_STATE {
            @Override public Object valueIn(SniperSnapshot snapshot) { return SnipersTableModel.textFor(snapshot.state()); }
        };

        public static Column at(int offset) {
            return values()[offset];
        }

        public abstract Object valueIn(SniperSnapshot snapshot);
    }

    public static String textFor(SniperState state) {
        return STATUS_TEXT[state.ordinal()];
    }
}
