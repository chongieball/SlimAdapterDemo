package com.example.chongieball.slimadapterdemo;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.chongieball.slimadapterdemo.model.DataDummy;
import com.example.chongieball.slimadapterdemo.model.Image;
import com.example.chongieball.slimadapterdemo.model.Music;
import com.example.chongieball.slimadapterdemo.model.SectionHeader;
import com.example.chongieball.slimadapterdemo.model.User;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.ex.loadmore.SimpleLoadMoreViewCreator;
import net.idik.lib.slimadapter.ex.loadmore.SlimMoreLoader;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.recyler_view)
    RecyclerView recyclerView;

    private List<Object> dummies = DataDummy.getDataDummy();
    private List<Object> otherDummies = DataDummy.getOtherDummies();
    private List<Object> currentData;

    private SlimAdapter slimAdapter;

    private Random random = new Random(System.currentTimeMillis());
    private int loadTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        currentData = new ArrayList<>(dummies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return slimAdapter.getItem(position) instanceof Image ? 1 : 3;
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);

        slimAdapter = SlimAdapter.create()
                .register(R.layout.item_user, new SlimInjector<User>() {
                    @Override
                    public void onInject(User data, IViewInjector injector) {
                        injector.text(R.id.name, data.getName())
                                .image(R.id.avatar, data.getAvatarRes())
                                .text(R.id.phone, data.getPhone())
                                .textColor(R.id.phone, Color.RED)
                                .textSize(R.id.phone, 12);
                    }
                })
                .register(R.layout.item_setion_header, new SlimInjector<SectionHeader>() {
                    @Override
                    public void onInject(SectionHeader data, IViewInjector injector) {
                        injector.text(R.id.section_title, data.getTitle());
                    }
                })
                .register(R.layout.item_image, new SlimInjector<Image>() {
                    @Override
                    public void onInject(Image data, IViewInjector injector) {
                        injector.with(R.id.imageView, new IViewInjector
                                .Action<ImageView>() {
                            @Override
                            public void action(ImageView view) {
                                Glide.with(MainActivity.this).load(data.getRes())
                                        .into(view);
                            }
                        });
                    }
                })
                .register(R.layout.item_music, new SlimInjector<Music>() {
                    @Override
                    public void onInject(Music data, IViewInjector injector) {
                        injector.text(R.id.name, data.getName())
                                .image(R.id.cover, data.getCoverRes());
                    }
                })
                .enableDiff()
                .enableLoadMore(new SlimMoreLoader(this, new
                        SimpleLoadMoreViewCreator(this).setNoMoreHint("No " +
                        "more data").setErrorHint("Error cuy")) {
                    @Override
                    protected void onLoadMore(Handler handler) {
                        SystemClock.sleep(3_000L);
                        if (random.nextInt(10) > 7) handler.error();
                        else {
                            handler.loadCompleted(otherDummies);
                            loadTime++;
                        }
                    }

                    @Override
                    protected boolean hasMore() {
                        return loadTime < 3;
                    }
                })
                .attachTo(recyclerView);

        slimAdapter.updateData(currentData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_data:
                loadTime = 0;
                currentData = currentData == dummies ? new ArrayList<>(otherDummies)
                        : new ArrayList<>(dummies);
                slimAdapter.updateData(currentData);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
