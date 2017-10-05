package com.github.dtcoutu;

import org.junit.Before;
import org.junit.Test;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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
        model.addSniper(SniperSnapshot.joining("item id"));
        model.sniperStateChanged(ImmutableSniperSnapshot.builder().itemId("item id").lastPrice(555).lastBid(666).state(SniperState.BIDDING).build());

        assertColumnEquals(SnipersTableModel.Column.ITEM_IDENTIFIER, "item id");
        assertColumnEquals(SnipersTableModel.Column.LAST_PRICE, 555);
        assertColumnEquals(SnipersTableModel.Column.LAST_BID, 666);
        assertColumnEquals(SnipersTableModel.Column.SNIPER_STATE, SnipersTableModel.textFor(SniperState.BIDDING));

        verify(listener, times(2)).tableChanged(refEq(new TableModelEvent(model, 0)));
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

    @Test
    public void notifiesListenersWhenAddingASniper() {
        SniperSnapshot joining = SniperSnapshot.joining("item123");

        assertEquals(0, model.getRowCount());

        model.addSniper(joining);

        assertEquals(1, model.getRowCount());
        assertRowMatchesSnapshot(0, joining);
    }

    @Test
    public void holdsSnipersInAdditionOrder() {
        model.addSniper(SniperSnapshot.joining("item 0"));
        model.addSniper(SniperSnapshot.joining("item 1"));

        assertThat(cellValue(0, SnipersTableModel.Column.ITEM_IDENTIFIER), equalTo("item 0"));
        assertThat(cellValue(1, SnipersTableModel.Column.ITEM_IDENTIFIER), equalTo("item 1"));
    }

    private Object cellValue(int rowIndex, SnipersTableModel.Column column) {
        return model.getValueAt(rowIndex, column.ordinal());
    }

    private void assertRowMatchesSnapshot(int rowIndex, SniperSnapshot snapshot) {
        assertThat(snapshot.itemId(), equalTo(model.getValueAt(rowIndex, 0)));
        assertThat(snapshot.lastPrice(), equalTo(model.getValueAt(rowIndex, 1)));
        assertThat(snapshot.lastBid(), equalTo(model.getValueAt(rowIndex, 2)));
        assertThat(SnipersTableModel.textFor(snapshot.state()), equalTo(model.getValueAt(rowIndex, 3)));
    }

    private void assertColumnEquals(SnipersTableModel.Column column, Object expected) {
        final int rowIndex = 0;
        final int columnIndex = column.ordinal();
        assertEquals(expected, model.getValueAt(rowIndex, columnIndex));
    }
}