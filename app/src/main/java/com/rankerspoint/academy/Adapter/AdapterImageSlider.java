package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.rankerspoint.academy.BaseUrl.BaseUrl;
import com.rankerspoint.academy.Activity.SingleCourseDetails;
import com.rankerspoint.academy.Model.GetSliderImagebyCateModel;
import com.rankerspoint.academy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterImageSlider extends PagerAdapter {
    private Context act;
    private List<GetSliderImagebyCateModel> items;
    private OnItemClickListener onItemClickListener;

    private interface OnItemClickListener {
        void onItemClick(View view, GetSliderImagebyCateModel obj);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // constructor
    public AdapterImageSlider(Context activity, List<GetSliderImagebyCateModel> items) {
        // super();
        this.act = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    public GetSliderImagebyCateModel getItem(int pos) {
        return items.get(pos);
    }

    public void setItems(List<GetSliderImagebyCateModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final GetSliderImagebyCateModel o = items.get(position);
        LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.image_slider, container, false);
        ImageView banner_image = (ImageView) v.findViewById(R.id.banner_image);
        Picasso.with(act).load(BaseUrl.BANN_IMG_URL + o.getImagePath()).into(banner_image);
        banner_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (o.getLinkType().equals("Course")) {
                    Intent intent = new Intent(act, SingleCourseDetails.class);
                    intent.putExtra("COURSE_ID", o.getLinkId());
                    act.startActivity(intent);
                } else {
                    Toast.makeText(act, "Type not course !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.lyt_parent);
        // Tools.displayImageOriginal(act, image, o.image);
//            lyt_parent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    if (onItemClickListener != null) {
//                        onItemClickListener.onItemClick(v, o);
//                    }
//                }
//            });
        ((ViewPager) container).addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}

