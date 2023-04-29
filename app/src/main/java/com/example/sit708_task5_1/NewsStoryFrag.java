package com.example.sit708_task5_1;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.Serializable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class NewsStoryFrag extends Fragment {
    TextView newsTitleTextView, newsDetailsTextView;
    ImageView newsImageView;
    Context context;
    List<NewsItem> relatedNews;
    RecyclerView relatedNewsView;
    LinearLayoutManager relatedNewsLayout;
    NewsStoryFrag.RelatedNewsAdapter relatedNewsAdapter;
    Button homeButton;
    NewsItem newsItem;
    
    public static NewsStoryFrag newInstance(NewsItem newsItem, List<NewsItem> relatedNews, Context context) {
        NewsStoryFrag newsfragment = new NewsStoryFrag();

        Bundle argument = new Bundle();
        argument.putSerializable("relatedNews", (Serializable) relatedNews);
        argument.putSerializable("news", newsItem);

        newsfragment.setArguments(argument);
        newsfragment.context = context;
        return newsfragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            relatedNews = (List<NewsItem>) getArguments().getSerializable("relatedNews");
            newsItem = (NewsItem) getArguments().getSerializable("news");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsImageView = view.findViewById(R.id.newsImageView);
        newsTitleTextView = view.findViewById(R.id.newsTitleTextView);
        newsDetailsTextView = view.findViewById(R.id.newsDetailsTextView);
        relatedNewsView = view.findViewById(R.id.relatedStoriesRecyclerView);
        homeButton = view.findViewById(R.id.button);
        newsTitleTextView.setText(newsItem.getTitle());
        newsDetailsTextView.setText(newsItem.getDescription());
        homeButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container, NewsStoryListFragment.newInstance(context), null).addToBackStack(null).commit();
        });
        
        relatedNewsLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        relatedNewsAdapter = new RelatedNewsAdapter(relatedNews, (newsItem, relatedNews) -> {
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.fragment_container, NewsStoryFrag.newInstance(newsItem, relatedNews, context), null).commit();
        });
        relatedNewsView.setLayoutManager(relatedNewsLayout);
        relatedNewsView.setAdapter(relatedNewsAdapter);

        return view;
    }

    public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.viewHolder> {
        private List<NewsItem> newsItemList;
        private final OnItemClickListener listener;

        public class viewHolder extends RecyclerView.ViewHolder {
            ImageView relatedNewsItemImageview;
            TextView relatedNewsTitle, relatedNewsDescription;
            
            public viewHolder(View view) {
                super(view);
                relatedNewsItemImageview = (ImageView) view.findViewById(R.id.relatedNewsItemImageview);
                relatedNewsTitle = (TextView) view.findViewById(R.id.relatedNewsTitle);
                relatedNewsDescription = (TextView) view.findViewById(R.id.relatedNewsDescription);
            }

            public void bind(final NewsItem newsItem, final OnItemClickListener listener) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(newsItem, newsItemList);
                    }
                });
            }
        }

        public RelatedNewsAdapter(List<NewsItem> newsItemList, OnItemClickListener listener) {
            this.newsItemList = newsItemList;
            this.listener = listener;
        }


        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.related_stories_item, parent, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(viewHolder holder, int position) {
            holder.relatedNewsTitle.setText(newsItemList.get(position).getTitle());
            holder.relatedNewsDescription.setText(newsItemList.get(position).getDescription());
            holder.bind(newsItemList.get(position), listener);
        }

        @Override
        public int getItemCount() {
            return newsItemList.size();
        }
    }
    public NewsStoryFrag() {
    }
}