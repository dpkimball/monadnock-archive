package com.oxbow.monadnock.service.domain;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.oxbow.monadnock.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {
    private LayoutInflater inflater;

    private Context context;
    private List<Receipt> mReceipts;
    private Context mContext;
    private static final String TAG = "ReceiptAdapter";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CircleImageView image;
        public TextView address;
        public TextView purchase_price;
        public TextView purchase_date;
        RelativeLayout parent_layout;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            image = v.findViewById(R.id.image);
            address = v.findViewById(R.id.address);
            purchase_price = v.findViewById(R.id.purchase_price);
            purchase_date = v.findViewById(R.id.purchase_date);
            parent_layout = v.findViewById(R.id.parent_layout);
        }
    }

    public void updateList(List<Receipt> data) {
        mReceipts = data;
        //notifyDataSetChanged();
    }

    public void add(int position, Receipt item) {
        mReceipts.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mReceipts.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)

    public ReceiptAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.mReceipts = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ReceiptAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: called");
        // create a new view
        //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.receipt_row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //Glide.with(mContext)
        //        .asBitmap()
        //        .load(mReceipts.get(position))
        //        .into(holder.image);

        holder.address.setText(mReceipts.get(position).store.getFullAddress());
        holder.purchase_price.setText(mReceipts.get(position).getFormattedPurchasePrice());
        holder.purchase_date.setText(mReceipts.get(position).purchase_date.toString());

        holder.parent_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "oncClick: clicked on: " + mReceipts.get(position));
                remove(position);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return mReceipts.size();
    }

}
