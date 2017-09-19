package com.example.apple.heypal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<Msg> Message;
    private Context context;

    public MyAdapter(List<Msg> Message, Context context) {
        this.Message = Message;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        if(Message.get(position).messageId=="sent")
            return R.layout.item_message_sent;
        else
            return R.layout.item_message_received;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg mssg = Message.get(position);
        holder.textViewMsgBody.setText(mssg.getMessageBody());
        holder.textViewMsgTime.setText(mssg.getMessageTime());

    }

    @Override
    public int getItemCount() {
        return Message.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewMsgBody;
        public TextView textViewMsgTime;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewMsgBody =(TextView)itemView.findViewById(R.id.text_message_body);
            textViewMsgTime =(TextView)itemView.findViewById(R.id.text_message_time);

        }
    }

}





