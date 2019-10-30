package com.example.stoic.googleatelier_app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MoviesSeenFragment extends Fragment {


    private TextView tvMovieLink;
    private TextView tvMovieName;
    private TextView tvGenre;
    private TextView tvActors;
    private TextView tvYear;
    private TextView tvDescription;
    private ImageView ivMoviePoster;
    private RatingBar rbMovieRating;

    private String movieName = "";
    private String movieGenre = "";
    private String moviePhotoBase64 = "";
    private String movieActors = "";
    private float movieRating = 0;
    private String movieDescription = "";
    private int movieYear = 0;
    private String movieIMDBLink = "";
    private ImageView expandedImageView;
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private RelativeLayout container;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main, null);
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle != null) {
            try {
                movieName = bundle.getString(Constants.MOVIE_NAME);
                movieGenre = bundle.getString(Constants.MOVIE_GENRE);
                moviePhotoBase64 = bundle.getString(Constants.MOVIE_PHOTO);
                movieRating = bundle.getFloat(Constants.MOVIE_RATING);
                movieDescription = bundle.getString(Constants.MOVIE_DESCRIPTION);
                movieActors = bundle.getString(Constants.MOVIE_ACTORS);
                movieYear = bundle.getInt(Constants.MOVIE_YEAR);
                movieIMDBLink = bundle.getString(Constants.MOVIE_IMDB_LINK);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Crashed", Toast.LENGTH_LONG).show();
            }



        }

        tvMovieName = view.findViewById(R.id.tvMovieName);
        tvGenre = view.findViewById(R.id.tvGenre);
        tvActors = view.findViewById(R.id.tvActors);
        tvDescription = view.findViewById(R.id.tvShortDes);
        tvMovieLink = view.findViewById(R.id.tvLink);
        ivMoviePoster = view.findViewById(R.id.ivMoviePoster);
        rbMovieRating = view.findViewById(R.id.rbRating);
        tvMovieLink = view.findViewById(R.id.tvLink);


        tvMovieName.setText(movieName);
        tvGenre.setText(movieGenre);
        tvActors.setText(movieActors);
        rbMovieRating.setRating(movieRating);
        ivMoviePoster.setImageBitmap(ImageUtils.decodeImageFromString(moviePhotoBase64));
        ivMoviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                zoomImageFromThumb(ivMoviePoster, moviePhotoBase64,view1);
            }
        });
        tvDescription.setText(movieDescription);

        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        //Rating.


        //tvMovieLink.setOnClickListener(new Activity);

    }
    private void zoomImageFromThumb(final View thumbView, String imageRes, View view) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.

        expandedImageView.setImageBitmap(ImageUtils.decodeImageFromString(imageRes));

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        container.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(shortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                });
                set.start();
                currentAnimator = set;
            }
        });
    }
}
