package com.github.dtcoutu;

import com.github.dtcoutu.util.Announcer;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPAuction implements Auction {
    private static final String AUCTION_RESOURCE = "Auction";
    private static final String ITEM_ID_AS_LOGIN = "auction%s";
    private static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

    private final Announcer<AuctionEventListener> auctionEventListeners = Announcer.to(AuctionEventListener.class);
    private final Chat chat;

    public XMPPAuction(XMPPConnection connection, String itemId) {
        chat = connection.getChatManager().createChat(auctionId(itemId, connection), new AuctionMessageTranslator(connection.getUser(), auctionEventListeners.announce()));
    }

    @Override
    public void bid(int amount) {
        sendMessage(String.format(Main.BID_COMMAND_FORMAT, amount));
    }

    @Override
    public void join() {
        sendMessage(Main.JOIN_COMMAND_FORMAT);
    }

    @Override
    public void addAuctionEventListener(AuctionSniper auctionSniper) {
        auctionEventListeners.addListener(auctionSniper);
    }

    private void sendMessage(final String message) {
        try {
            chat.sendMessage(message);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

    private static String auctionId(String itemId, XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }
}
