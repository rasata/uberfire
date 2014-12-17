package org.uberfire.client.views.pfly.tab;

import org.gwtbootstrap3.client.ui.TabListItem;
import org.gwtbootstrap3.client.ui.TabPane;
import org.gwtbootstrap3.client.ui.base.HasActive;

import com.google.gwt.user.client.ui.Widget;

/**
 * Represents an entry in a {@link TabPanelWithDropdowns}. Keeps track of the current title, the tab widget (which could
 * be one of two different types depending on whether the entry is at top-level tab or nested in a dropdown tab), and
 * the associated content widget.
 */
public class TabPanelEntry implements HasActive {

    private String title;
    private final DropDownTabListItem tab;

    /**
     * Container for {@link #contents}.
     */
    private final TabPane contentPane;

    /**
     * The application-provided content widget that should show up when the tab is clicked.
     */
    private final Widget contents;

    public TabPanelEntry( String title, Widget contents ) {
        this.title = title;
        this.tab = new DropDownTabListItem( title );
        this.contents = contents;

        contentPane = new TabPane();
        contentPane.add( contents );

        tab.setDataTargetWidget( contentPane );
    }

    public TabListItem getTabWidget() {
        return tab;
    }

    public Widget getContents() {
        return contents;
    }

    /**
     * Returns the intermediate container that holds the real contents.
     */
    public TabPane getContentPane() {
        return contentPane;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
        tab.setText( title );
    }

    public void setInDropdown( boolean inDropdown ) {
        tab.setInDropdown( inDropdown );
    }

    /**
     * Represents the tab widget that lives in the tab bar or under a dropdown tab.
     */
    static class DropDownTabListItem extends TabListItem {

        public DropDownTabListItem( String label ) {
            super( label );
            addStyleName( "uf-dropdown-tab-list-item" );
        }

        /**
         * Sets this tab for use in the top-level tab bar (isDropdown false) or inside a dropdown tab (isDropdown true).
         */
        void setInDropdown( boolean inDropdown ) {
            anchor.setTabIndex( inDropdown ? -1 : 0 );
        }
    }

    /**
     * Returns true if this tab panel entry believes it's currently the active (displayed) tab in its tab panel.
     */
    @Override
    public boolean isActive() {
        return tab.isActive();
    }

    /**
     * Sets or clears the active state on this tab. Does not actually cause the tab to hide or show.
     */
    @Override
    public void setActive( boolean b ) {
        tab.setActive( b );
    }

    /**
     * Makes this tab show itself and become the active tab, replacing whatever tab was previously active.
     */
    public void showTab() {
        tab.showTab(false);
    }

}