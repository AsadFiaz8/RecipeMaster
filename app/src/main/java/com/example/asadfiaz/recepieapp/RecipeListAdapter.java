package com.example.asadfiaz.recepieapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asad Fiaz on 1/31/2018.
 */

public class RecipeListAdapter extends ArrayAdapter<RecipeModel> {

    private List<RecipeModel> recipeModelList;
    int resource;
    TextView Title,Ingredients,Url;
    ImageView Poster;
    LayoutInflater inflater;
    Context context;


    public RecipeListAdapter(Context context, int resource ,ArrayList<RecipeModel> objects) {
        super(context, resource, objects);
        recipeModelList = objects;
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {


        inflater  = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_row_list,parent,false);

        RecipeModel recipeModel=getItem(position);

        Title =       (TextView)   view.findViewById(R.id.title);
        Ingredients = (TextView)   view.findViewById(R.id.ingredients);
        Url =         (TextView)   view.findViewById(R.id.url);
        Poster =      (ImageView)  view.findViewById(R.id.poster);

        if (!recipeModel.getPoster().equals("")){
            Picasso.with(context).load(recipeModel.getPoster()).into(Poster);
        }
        else{
            Picasso.with(context).load(R.drawable.not_found).into(Poster);
        }

        Title.append(recipeModel.getTitle());
        Ingredients.append(recipeModel.getIngredients());
        Url.append(recipeModel.getUrl());

        return view;
    }
}
