package com.github.dtcoutu;

import org.junit.Before;
import org.junit.Test;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SnipersTableModelTest {
    private TableModelListener listener = mock(TableModelListener.class);
    private final SnipersTableModel model = new SnipersTableModel();

    @Before
    public void attachModelListener() {
        model.addTableModelListener(listener);
    }

    @Test
    public void hasEnoughColumns() {
        assertThat(model.getColumnCount(), equalTo(SnipersTableModel.Column.values().length));
    }

    @Test
    public void setsSniperValuesInColumns() {
        model.sniperStatusChanged(ImmutableSniperSnapshot.builder().itemId("item id").lastPrice(555).lastBid(666).state(SniperState.BIDDING).build());

        assertColumnEquals(SnipersTableModel.Column.ITEM_IDENTIFIER, "item id");
        assertColumnEquals(SnipersTableModel.Column.LAST_PRICE, 555);
        assertColumnEquals(SnipersTableModel.Column.LAST_BID, 666);
        assertColumnEquals(SnipersTableModel.Column.SNIPER_STATE, SnipersTableModel.textFor(SniperState.BIDDING));

        verify(listener).tableChanged(refEq(new TableModelEvent(model, 0)));
    }

    @Test
    public void shouldReturnCorrectValuePerColumnFromSnapshot() {
        SniperSnapshot sniperSnapshot = ImmutableSniperSnapshot.builder().itemId("itemId").lastPrice(100).lastBid(120).state(SniperState.BIDDING).build();
        assertThat(SnipersTableModel.Column.at(0).valueIn(sniperSnapshot), equalTo("itemId"));
        assertThat(SnipersTableModel.Column.at(1).valueIn(sniperSnapshot), equalTo(100));
        assertThat(SnipersTableModel.Column.at(2).valueIn(sniperSnapshot), equalTo(120));
        assertThat(SnipersTableModel.Column.at(3).valueIn(sniperSnapshot), equalTo(SnipersTableModel.textFor(
                SniperState.BIDDING)));
    }

    private void assertColumnEquals(SnipersTableModel.Column column, Object expected) {
        final int rowIndex = 0;
        final int columnIndex = column.ordinal();
        assertEquals(expected, model.getValueAt(rowIndex, columnIndex));
    }
}