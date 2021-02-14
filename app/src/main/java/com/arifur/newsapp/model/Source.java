
package com.arifur.newsapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "sources")
public class Source {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String id;
    @ColumnInfo(name = "name")
    private final String name;


    /**
     * @param id   of the news source, example <b>cnn</b>
     * @param name display name of news source, example <b>CNN</b>
     */

    public Source(@NonNull String id, String name) {
        this.id = id;
        this.name = name;

    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
