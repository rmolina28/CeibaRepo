package co.com.ceiba.mobile.pruebadeingreso.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.R;

public class ListAdapterPublications extends RecyclerView.Adapter<ListAdapterPublications.ViewHolderData> {

    private ArrayList<PublicationElement> listPublications;

    public ListAdapterPublications(ArrayList<PublicationElement> listPublications) {
        this.listPublications = listPublications;
    }

    @NonNull
    @Override
    public ListAdapterPublications.ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item,null,false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        holder.setPublicationData(listPublications.get(position));
    }


    @Override
    public int getItemCount() {
        return listPublications.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        TextView title;
        TextView body;

        public ViewHolderData(View itemView) {
            super(itemView);
            //Reference each card element
            title =  itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
        }

        public void setPublicationData(PublicationElement publicationData) {
            title.setText(publicationData.title);
            body.setText(publicationData.body);
        }
    }

    public static class PublicationElement{
        private String title;
        private String body;
        private Integer id;

        public PublicationElement(Integer id, String title, String body) {
            this.id= id;
            this.title = title;
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
