package com.rankerspoint.academy.Adapter;

import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rankerspoint.academy.Model.GetAllDoubtsAnswerModel;
import com.rankerspoint.academy.Model.MySpannable;
import com.rankerspoint.academy.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GetDoubtsAllAnswerAdapter extends RecyclerView.Adapter<GetDoubtsAllAnswerAdapter.BannerImage>  {


    List<GetAllDoubtsAnswerModel> getPreEaxmModels;
   // List<GetPreEaxmModel> searchList;
    Context context;

    public GetDoubtsAllAnswerAdapter(Context context, List<GetAllDoubtsAnswerModel> getPreEaxmModels) {
        this.context = context;
        this.getPreEaxmModels = getPreEaxmModels;
//        this.searchList=new ArrayList<>();
//        this.searchList.addAll(getPreEaxmModels);

    }

    @NonNull
    @Override
    public GetDoubtsAllAnswerAdapter.BannerImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.doubt_answer_layout, parent, false);
        return new BannerImage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GetDoubtsAllAnswerAdapter.BannerImage holder, int position) {
//        Log.e("tag", "onBindViewHolder: " + images.get(position));


      //  picasso.load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);

        final GetAllDoubtsAnswerModel getPreEaxmModel = getPreEaxmModels.get(position);

        holder.txt_ans.setText(getPreEaxmModel.getAnswer());
        int line=holder.txt_ans.getLineCount();
        if (holder.txt_ans.length()<100) {

            holder.txt_ans.setText(getPreEaxmModel.getAnswer());
        }else {


            makeTextViewResizable(holder.txt_ans, 2, "Read More..", true);
        }

        holder.tv_toolbar_title.setText(getPreEaxmModel.getUserName());
try {
    //dateformat
    String datef = getPreEaxmModel.getAddDate();

    String datae=datef.substring(0,10);
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
    Date date = dt.parse(datae);
    // *** same for the format String below
    SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);



    holder.tv_toolbar_date.setText(dt1.format(date));



}catch (Exception ex)
{
    Toast.makeText(context, "date:--"+ex, Toast.LENGTH_SHORT).show();
}



        //Picasso.with(context).load(images.get(position)).error(R.mipmap.ic_launcher).into(holder.img);
        //Picasso.with(context).load(BANN_IMG_URL + getPreEaxmModel.getPic()).into(holder.img);

       // holder.img.setImageResource(images.get(position));
//        holder.card_preexam_subcat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SubCategoryClass.class);
//                intent.putExtra("CAT_ID", getPreEaxmModel.getCategoryId().toString());
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
      return getPreEaxmModels.size();

    }

    public static class BannerImage extends RecyclerView.ViewHolder {



        private TextView txt_title_doubt,txt_ans,tv_toolbar_title,tv_toolbar_date;
        private CardView card_preexam_subcat;
        LinearLayout linear_dislike,linear_like;
        public BannerImage(@NonNull View itemView) {
            super(itemView);

            txt_ans=itemView.findViewById(R.id.txt_ans);
            tv_toolbar_title=itemView.findViewById(R.id.tv_toolbar_title);
            tv_toolbar_date=itemView.findViewById(R.id.tv_toolbar_date);
            linear_like=itemView.findViewById(R.id.linear_like);
            linear_dislike=itemView.findViewById(R.id.linear_dislike);

        }
        // each data item is just a string in this case

    }
    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false){
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less..", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 2, "Read More..", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }


}
