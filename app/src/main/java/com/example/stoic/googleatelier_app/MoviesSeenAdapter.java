package com.example.stoic.googleatelier_app;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class MoviesSeenAdapter extends RecyclerView.Adapter<MoviesSeenAdapter.MyViewHolder2>{
    private ArrayList<Movie> mDataset;
    private AdapterItemClickListener onItemClickListener;

    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        public TextView tvMovieName;
        public TextView tvMovieGenre;
        public RatingBar rbMovieRating;
        public ImageView ivMovieIcon;
        public TextView tvShortDes;
        public CheckBox checkBox;
        private String moviePhotoBase64 = "";
        private String movieDescription = "";
        private String movieGenre = "";
        private String movieActors = "";




        private SharedPreferences checkPreferences;
        private SharedPreferences.Editor checkPrefsEditor;
        private boolean saveChecked;

        public MyViewHolder2(View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, getAdapterPosition());
                }
            });

            final myDBHandler dbHandler = new myDBHandler(view.getContext(), null, null, 1);


            tvMovieName = view.findViewById(R.id.tvMovieName);
            tvMovieGenre = view.findViewById(R.id.tv_movieGenre);
            rbMovieRating = view.findViewById(R.id.rbmovieRating);
            ivMovieIcon = view.findViewById(R.id.ivMovieIcon);
            tvShortDes = view.findViewById(R.id.tvShortDes);
            checkBox = view.findViewById(R.id.checkRemember);

            checkPreferences = view.getContext().getSharedPreferences("checkPrefs", MODE_PRIVATE);
            checkPrefsEditor = checkPreferences.edit();

            saveChecked = checkPreferences.getBoolean("saveChecked", false);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        Movie movie = mDataset.get(getAdapterPosition());

                        moviePhotoBase64 = movie.getPhotoBase64();
                        movieDescription = movie.getShortDesc();
                        movieActors = movie.getActors();
                        movieGenre = movie.getGenre();
                        saveChecked = Boolean.parseBoolean(movie.getChecked());
                        //dbHandler.changeCheckColumn("Movie",tvMovieName.getText().toString().trim(),"COLUMN_CHECKED",true)
                        dbHandler.addHandler(new Movie(tvMovieName.getText().toString().trim(), rbMovieRating.getRating(), movieGenre,
                                movieDescription, movieActors, moviePhotoBase64, null,Boolean.toString(checkBox.isChecked())));

                        dbHandler.deleteHandler("Movie_Seen",tvMovieName.getText().toString().trim());
                        removeSingleItem(getAdapterPosition());
                        Toast.makeText(v.getContext(),"Moved to All Movies",Toast.LENGTH_LONG).show();

                    }
                }
            });

        }
    }

    private void removeSingleItem(int index) {
        int removeIndex = index;
        mDataset.remove(removeIndex);
        this.notifyItemRemoved(removeIndex);
    }

    public MoviesSeenAdapter(ArrayList<Movie> myDataset,AdapterItemClickListener item) {
        this.mDataset = myDataset;
        this.onItemClickListener = item;
    }

    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View rlMovieItemContainer = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new MyViewHolder2(rlMovieItemContainer);
    }


    public Bitmap decodeImageFromString(String base64){
        byte[] decodedString = Base64.decode(base64,Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
        return decodedByte;
    }

    public void onBindViewHolder(MyViewHolder2 holder, int position) {
        Movie movie = mDataset.get(position);
        holder.tvMovieName.setText(movie.getName());
        holder.tvMovieGenre.setText(movie.getGenre());
        holder.rbMovieRating.setRating(movie.getRating());
        holder.ivMovieIcon.setImageBitmap(decodeImageFromString(movie.getPhotoBase64()));
//        if (Boolean.parseBoolean(movie.getChecked())) {
//            holder.checkBox.setChecked(true);
//        }else{
//            holder.checkBox.setChecked(false);
//        }



    }

    public int getItemCount() {
        return mDataset.size();
    }
}