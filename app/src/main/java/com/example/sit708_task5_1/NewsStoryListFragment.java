package com.example.sit708_task5_1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


public class NewsStoryListFragment extends Fragment {
    RecyclerView horizontalRecyclerView, verticalRecyclerView;
    LinearLayoutManager horizontalLayoutManager;
    HorizontalRecyclerViewAdapter horizontalAdapter;
    VerticalRecyclerViewAdapter verticalAdapter;
    Context context;




    public static NewsStoryListFragment newInstance(Context context) {
        NewsStoryListFragment newslistfragment = new NewsStoryListFragment();
        Bundle argument = new Bundle();
        newslistfragment.setArguments(argument);
        newslistfragment.context = context;
        return newslistfragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        horizontalRecyclerView = view.findViewById(R.id.horizontalRecyclerView);
        verticalRecyclerView = view.findViewById(R.id.verticalRecyclerView);

        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        horizontalAdapter = new HorizontalRecyclerViewAdapter(NewsItem.storyList(), (newsItem, relatedNews) -> {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container, NewsStoryFrag.newInstance(newsItem, relatedNews, context), null).commit();
        });
        horizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        horizontalRecyclerView.setAdapter(horizontalAdapter);

        verticalAdapter = new VerticalRecyclerViewAdapter(NewsItem.newsList(), (newsItem, relatedNews) -> {

            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container, NewsStoryFrag.newInstance(newsItem, relatedNews, context), null).commit();
        });
        verticalRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        verticalRecyclerView.setAdapter(verticalAdapter);
        return view;
    }


    public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.viewHolder> {
        List<NewsItem> newsItemList;
        OnItemClickListener listener;

        public class viewHolder extends RecyclerView.ViewHolder {

            TextView storyTitle;
            ImageView storyImageView;

            public viewHolder(View view) {
                super(view);
                storyImageView = (ImageView) view.findViewById(R.id.storyImageView);
                storyTitle = (TextView) view.findViewById(R.id.storyTitle);
            }

            public void bind(final NewsItem newsItem, final OnItemClickListener listener) {
                itemView.setOnClickListener(v -> listener.onItemClick(newsItem, newsItemList));
            }
        }

        public HorizontalRecyclerViewAdapter(List<NewsItem> newsItemList, OnItemClickListener listener) {
            this.newsItemList = newsItemList;
            this.listener = listener;
        }


        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.story_item, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(viewHolder holder, int position) {
            holder.storyTitle.setText(newsItemList.get(position).getTitle());
            holder.bind(newsItemList.get(position), listener);
        }

        @Override
        public int getItemCount() {
            return newsItemList.size();
        }
    }

    public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.viewHolder> {
        List<NewsItem> newsItemList;
        OnItemClickListener listener;

        public class viewHolder extends RecyclerView.ViewHolder {

            TextView newsTitle;
            ImageView imageView;

            public viewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.imageView);
                newsTitle = (TextView) view.findViewById(R.id.newsTitle);
            }

            public void bind(final NewsItem newsItem, final OnItemClickListener listener) {
                itemView.setOnClickListener(v -> listener.onItemClick(newsItem, newsItemList));
            }
        }

        public VerticalRecyclerViewAdapter(List<NewsItem> newsItemList, OnItemClickListener listener) {
            this.newsItemList = newsItemList;
            this.listener = listener;
        }

        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(viewHolder holder, int position) {
            holder.newsTitle.setText(newsItemList.get(position).getTitle());
            holder.bind(newsItemList.get(position), listener);
        }

        @Override
        public int getItemCount() {
            return newsItemList.size();
        }
    }
    public NewsStoryListFragment() {

    }
}
