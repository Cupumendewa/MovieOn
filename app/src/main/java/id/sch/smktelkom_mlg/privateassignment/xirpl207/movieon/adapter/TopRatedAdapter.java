package id.sch.smktelkom_mlg.privateassignment.xirpl207.movieon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.sch.smktelkom_mlg.privateassignment.xirpl207.movieon.R;
import id.sch.smktelkom_mlg.privateassignment.xirpl207.movieon.modul.Result;

/**
 * Created by Cupumendewa on 14/05/2017.
 */

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.ViewHolder> {
    ArrayList<Result> list;
    TopRatedAdapter.ITopRatedAdapter mITopRatedAdapter;
    Context context;


    public TopRatedAdapter(Context context, ArrayList<Result> list) {
        this.list = list;
        this.context = context;
        mITopRatedAdapter = (TopRatedAdapter.ITopRatedAdapter) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TopRatedAdapter.ViewHolder holder, int position) {
        Result result = list.get(position);
        holder.tvName.setText(result.title);
        holder.tvDesc.setText(result.overview);
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500" + result.poster_path)
                .into(holder.iv_poster);
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public interface ITopRatedAdapter {
        void showArticles(String title, String overview, String poster_path);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_poster;
        TextView tvName;
        TextView tvDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_poster = (ImageView) itemView.findViewById(R.id.imageViewPoster);
            tvName = (TextView) itemView.findViewById(R.id.textViewName);
            tvDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
        }
    }
}