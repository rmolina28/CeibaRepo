package co.com.ceiba.mobile.pruebadeingreso.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;

import static co.com.ceiba.mobile.pruebadeingreso.view.PostActivity.USER_EMAIL;
import static co.com.ceiba.mobile.pruebadeingreso.view.PostActivity.USER_ID;
import static co.com.ceiba.mobile.pruebadeingreso.view.PostActivity.USER_NAME;
import static co.com.ceiba.mobile.pruebadeingreso.view.PostActivity.USER_PHONE;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolderData> {

    private ArrayList<UserElement> listUser;
    private Context context;

    public ListAdapter(Context context, ArrayList<UserElement> listUser) {
        this.listUser = listUser;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item,null,false);
        return new ViewHolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, final int position) {
        holder.setUserData(listUser.get(position));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostActivity.class);
                intent.putExtra(USER_ID, listUser.get(position).getId());
                intent.putExtra(USER_NAME, listUser.get(position).getUserName());
                intent.putExtra(USER_EMAIL, listUser.get(position).getUserEmail());
                intent.putExtra(USER_PHONE, listUser.get(position).getUserPhone());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        TextView userName;
        TextView userPhone;
        TextView userEmail;
        LinearLayout linearLayout;


        public ViewHolderData(View itemView) {
            super(itemView);
            //Reference each card element
            userName =  itemView.findViewById(R.id.name);
            userPhone = itemView.findViewById(R.id.phone);
            userEmail = itemView.findViewById(R.id.email);
            linearLayout = itemView.findViewById(R.id.contentCard);
        }

        public void setUserData(UserElement userData) {
            userName.setText(userData.userName);
            userEmail.setText(userData.userEmail);
            userPhone.setText(userData.userPhone);
        }
    }

    public void refreshFilterList(List<UserElement> refreshList){
        listUser = new ArrayList<>();
        listUser.addAll(refreshList);
        notifyDataSetChanged();
    }


    public static class UserElement{
        private String userName;
        private String userEmail;
        private String userPhone;
        private Integer id;

        public UserElement(Integer id, String userName, String userEmail, String userPhone) {
            this.id= id;
            this.userName = userName;
            this.userEmail = userEmail;
            this.userPhone = userPhone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

}
