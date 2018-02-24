package com.example.chongieball.slimadapterdemo.model;

import com.example.chongieball.slimadapterdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chongieball on 23/02/18.
 */

public class DataDummy {

    private DataDummy() {}

    public static List<Object> getDataDummy() {
        List<Object> dummies = new ArrayList<>();
        dummies.add(new SectionHeader("My Friends"));
        dummies.add(new User("Jack", 21, R.drawable.icon1, "123456789XX"));
        dummies.add(new User("Marry", 17, R.drawable.icon2, "123456789XX"));
        dummies.add(new SectionHeader("My Images"));
        dummies.add(new Image(R.drawable.cover1));
        dummies.add(new Image(R.drawable.cover2));
        dummies.add(new Image(R.drawable.cover3));
        dummies.add(new Image(R.drawable.cover4));
        dummies.add(new Image(R.drawable.cover5));
        dummies.add(new Image(R.drawable.cover6));
        dummies.add(new Image(R.drawable.cover7));
        dummies.add(new Image(R.drawable.cover8));
        dummies.add(new Image(R.drawable.cover9));
        dummies.add(new Image(R.drawable.cover10));
        dummies.add(new Image(R.drawable.cover11));
        dummies.add(new SectionHeader("My Musics"));
        dummies.add(new Music("Love story", R.drawable.icon3));
        dummies.add(new Music("Nothing's gonna change my love for u", R
                .drawable.icon4));
        dummies.add(new Music("Just one last dance", R.drawable.icon5));

        return dummies;
    }

    public static List<Object> getOtherDummies() {
        List<Object> otherDummies = new ArrayList<>();
        otherDummies.addAll(getDataDummy());
        otherDummies.remove(1);
        otherDummies.remove(5);
        otherDummies.remove(6);

        return otherDummies;
    }
}
