/*
 * Copyright 2014 Guillaume EHRET
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ehret.mixit.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ehret.mixit.R;
import com.ehret.mixit.domain.Salle;
import com.ehret.mixit.domain.talk.Conference;
import com.ehret.mixit.domain.talk.Talk;
import com.ehret.mixit.utils.UIUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Adapter permettant de gérer l'affichage dans la liste des talks
 *
 * @param <T>
 */
public class ListTalkForFilAdapter<T extends Conference> extends BaseAdapter {
    private List<T> datas;
    private Context context;
    private int mSize;

    public ListTalkForFilAdapter(Context context, List<T> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Conference getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public SimpleDateFormat sdf = new SimpleDateFormat("EEE");


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_talk, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.talk_image);
            holder.imageFavorite = (ImageView) convertView.findViewById(R.id.talk_image_favorite);
            holder.name = (TextView) convertView.findViewById(R.id.talk_name);
            holder.descriptif = (TextView) convertView.findViewById(R.id.talk_shortdesciptif);
            holder.level = (TextView) convertView.findViewById(R.id.talk_level);
            holder.horaire = (TextView) convertView.findViewById(R.id.talk_horaire);
            holder.talkImageText = (TextView) convertView.findViewById(R.id.talkImageText);
            holder.talkSalle = (TextView) convertView.findViewById(R.id.talk_salle);
            holder.container2 = (FrameLayout) convertView.findViewById(R.id.container2);
            holder.container3 = (RelativeLayout) convertView.findViewById(R.id.container3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Talk conf = (Talk) datas.get(position);

        if (holder.container2.getLayoutParams().height > 0) {
            mSize = holder.container2.getLayoutParams().height;
        }

        if (conf.getTitle() == null) {
            //In this case this just a time marker
            holder.name.setText(null);
            holder.level.setText(null);
            holder.descriptif.setText(null);
            holder.talkImageText.setText(null);
            holder.talkSalle.setText(null);
            holder.image.setImageDrawable(null);
            holder.imageFavorite.setImageDrawable(null);
            holder.horaire.setText(null);

            if (conf.getFormat() != null && "day1".equals(conf.getFormat())) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.color_home));
                holder.name.setText(context.getResources().getString(R.string.calendrier_jour1));
                holder.name.setTextColor(context.getResources().getColor(R.color.white));
            } else if (conf.getFormat() != null && "day2".equals(conf.getFormat())) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.color_home));
                holder.name.setText(context.getResources().getString(R.string.calendrier_jour1));
                holder.name.setTextColor(context.getResources().getColor(R.color.white));
            } else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.grey_dark_fifty));
                holder.name.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(conf.getEnd()));
                holder.name.setTextColor(context.getResources().getColor(R.color.black));

            }
            holder.horaire.getLayoutParams().height = 0;
            holder.talkSalle.getLayoutParams().height = 0;
            holder.container2.getLayoutParams().height = 0;
            holder.descriptif.getLayoutParams().height = 0;
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            convertView.setBackground(context.getResources().getDrawable(R.drawable.button_white_background));
            holder.horaire.getLayoutParams().height = context.getResources().getDimensionPixelSize(R.dimen.text_size_small_container);
            holder.talkSalle.getLayoutParams().height = context.getResources().getDimensionPixelSize(R.dimen.text_size_small_container);
            holder.container2.getLayoutParams().height = mSize;
            holder.descriptif.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.name.setTextColor(context.getResources().getColor(R.color.black));

            if (conf.getStart() != null && conf.getEnd() != null) {
                holder.horaire.setText(String.format(context.getResources().getString(R.string.periode),
                        sdf.format(conf.getStart()),
                        DateFormat.getTimeInstance(DateFormat.SHORT).format(conf.getStart()),
                        DateFormat.getTimeInstance(DateFormat.SHORT).format(conf.getEnd())
                ));
            } else {
                holder.horaire.setText(context.getResources().getString(R.string.pasdate));

            }

            holder.name.setText(conf.getTitle());
            if (conf.getSummary() != null) {
                holder.descriptif.setText(Html.fromHtml(conf.getSummary().trim()));
            }

            Salle salle = Salle.INCONNU;
            if (Salle.INCONNU != Salle.getSalle(conf.getRoom())) {
                salle = Salle.getSalle(conf.getRoom());
            }
            holder.talkSalle.setText(String.format(context.getResources().getString(R.string.Salle), salle.getNom()));
            holder.talkSalle.setBackgroundColor(context.getResources().getColor(salle.getColor()));


            if (conf.getLevel() != null) {
                holder.level.setText("[" + conf.getLevel() + "]");
            } else {
                holder.level.setText(null);
            }

            if ("Workshop".equals(conf.getFormat())) {
                holder.talkImageText.setText("Workshop");
                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.workshop));
            } else {
                holder.talkImageText.setText("Talk");
                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.talk));
            }

            //On regarde si la conf fait partie des favoris
            SharedPreferences settings = context.getSharedPreferences(UIUtils.PREFS_FAVORITES_NAME, 0);
            boolean trouve = false;
            for (String key : settings.getAll().keySet()) {
                if (key.equals(String.valueOf(conf.getId()))) {
                    trouve = true;
                    holder.imageFavorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_important));
                    break;
                }
            }
            if (!trouve) {
                holder.imageFavorite.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_action_not_important));
            }

        }

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView descriptif;
        ImageView image;
        TextView level;
        TextView horaire;
        TextView talkImageText;
        TextView talkSalle;
        ImageView imageFavorite;
        FrameLayout container2;
        RelativeLayout container3;

    }

}