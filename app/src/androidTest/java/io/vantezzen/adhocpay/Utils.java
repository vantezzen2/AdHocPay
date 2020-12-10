package io.vantezzen.adhocpay;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Utility Methoden für Android Tests
 */
public class Utils {
    /**
     * Führe einen Tests auf einen bestimmten Unter-view aus.
     * z.B. das dritte Element in einem Recyclerview
     *
     * @param position Position des Unter-Views
     * @param itemMatcher Matcher, welcher genutzt werden soll
     * @param targetViewId View ID interhalb dieses Unter-Views, welcher getestet werden soll
     * @return Matcher
     * Quelle: https://stackoverflow.com/a/37119501/10590162
     */
    public static Matcher<View> atPositionOnView(final int position, final Matcher<View> itemMatcher,
                                                 @NonNull final int targetViewId) {

        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has view id " + itemMatcher + " at position " + position);
            }

            @Override
            public boolean matchesSafely(final RecyclerView recyclerView) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                View targetView = viewHolder.itemView.findViewById(targetViewId);
                return itemMatcher.matches(targetView);
            }
        };
    }
}
