package com.mattchiaravalloti.commonactors;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Matt Chiaravalloti on 7/20/2015.
 */
public class ActorListStore {
    public static ActorListStore instance;

    private Set<String> common;

    private ActorListStore() {
        this.common = new HashSet<String>();
    }

    public static ActorListStore getInstance() {
        if (instance == null) {
            instance = new ActorListStore();
        }
        return instance;
    }

    public void setCommon(Set<String> common) {
        this.common = common;
    }

    public Set<String> getCommon() {
        return this.common;
    }
}
