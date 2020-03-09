package explorer.contentPane.favoritePane;

import explorer.contentPane.Node;

public interface FavoriteListObserver {
    void updateState(boolean isAdd, Node node);
}
