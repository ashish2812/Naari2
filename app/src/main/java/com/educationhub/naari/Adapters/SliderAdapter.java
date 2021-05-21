package com.educationhub.naari.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.educationhub.naari.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int image[] = {
            R.drawable.placeicon,
            R.drawable.sanitary_pads,
            R.drawable.girlpng,
            R.drawable.review
    };

    int title[] = {

            R.string.OnBoardingSliderOneTitle,
            R.string.OnBoardingSliderTwoTitle,
            R.string.OnBoardingSliderThreeTitle,
            R.string.OnBoardingSliderFourTitle

    };
    int descriptions[] = {

            R.string.OnBoardingSliderOneDescription,
            R.string.OnBoardingSliderTwoDescription,
            R.string.OnBoardingSliderThreeDescription,
            R.string.OnBoardingSliderFourDescription

    };

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider, container, false);

        //hooks


        ImageView imageView = view.findViewById(R.id.imageView);
        TextView heading = view.findViewById(R.id.sliderHeading);
        TextView description = view.findViewById(R.id.sliderDescription);

        imageView.setImageResource(image[position]);
        heading.setText(title[position]);
        description.setText(descriptions[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
