package credit.sina.com.delate;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import credit.sina.com.delate.deleteview.SwipeItemLayout;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        recyclerView.setAdapter(new MyAdapter(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
    }




public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
    private Context mContext;
    private List<String> datas;

    public MyAdapter(Context context) {
        mContext = context;
        datas= new ArrayList<>();
        for (int i = 0; i < 25; i++)
            datas.add("第"+i+"条目");
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        return new Holder(root);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.tv.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView tv;

        Holder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.tv);
            View main = itemView.findViewById(R.id.main);
            main.setOnClickListener(this);
            main.setOnLongClickListener(this);

            View delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main:
                    Toast.makeText(v.getContext(), "点击了main，位置为：" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    break;

                case R.id.delete:
                    int pos = getAdapterPosition();
                    datas.remove(pos);
                    notifyItemRemoved(pos);
                    break;
            }
        }

        @Override
        public boolean onLongClick(View v) {
            switch (v.getId()) {
                case R.id.main:
                    Toast.makeText(v.getContext(), "长按了main，位置为：" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    }
}


}
