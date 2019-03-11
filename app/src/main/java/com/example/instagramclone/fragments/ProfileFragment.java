package com.example.instagramclone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.instagramclone.MainActivity;
import com.example.instagramclone.Post;
import com.example.instagramclone.R;
import com.example.instagramclone.SignupActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import static android.view.View.VISIBLE;

public class ProfileFragment extends PostsFragment {

    Button btnLogout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setVisibility(VISIBLE);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }

    @Override
    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error fetching data");
                    return;
                }
                mPosts.addAll(posts);
                adapter.notifyDataSetChanged();
                for (int i = 0; i < posts.size(); i++) {
                    try {
                        Log.d(TAG, "Post: " + posts.get(i).getDescription() + ", user: " + posts.get(i).getUser().fetchIfNeeded().getUsername());
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        swipeLayout.setRefreshing(false);
    }

    public void Logout() {
        ParseUser.logOut();
        Toast.makeText(getContext(), "Logged out", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getContext(), SignupActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}
