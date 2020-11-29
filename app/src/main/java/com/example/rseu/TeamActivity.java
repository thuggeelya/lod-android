package com.example.rseu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import com.example.rseu.ui.ProfileFragment;
import com.example.rseu.ui.slideshow.SlideshowFragment;

public class TeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        String name = SlideshowFragment.myName;

        Team team = new Team();
        for (int i = 0; i < SlideshowFragment.teams.size(); i++)
            if (SlideshowFragment.teams.get(i).getTeamByName(SlideshowFragment.teams.get(i) ,name)) {
                team = SlideshowFragment.teams.get(i);
                break;
            }

        TextView teamName = findViewById(R.id.team_name);
        TextView teamField = findViewById(R.id.team_field);
        final ListView teamMembers = findViewById(R.id.team_members);

        teamName.setText(team.getName());
        teamField.setText(team.getField());
        teamMembers.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, team.getUsers()));

        teamMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Interests interests = new Interests();
                for (int i = 0; i < ProfileFragment.allInterests.size(); i++)
                    if (ProfileFragment.allInterests.get(i).getInterestByName(ProfileFragment.allInterests.get(i), Login.userName)) {
                        interests = ProfileFragment.allInterests.get(i);
                    }

                TextView textView = findViewById(R.id.interests);
                textView.setText("Интересы: " + interests.getInterests());

                TooltipCompat.setTooltipText(teamMembers, "Интересы: " + interests.getInterests());
            }
        });

        Button back = findViewById(R.id.back_to_teamset_button);
    }

    public void OnBackToTeamsetClick (View view) {
        Intent intent = new Intent(this, StartPage.class);
        SlideshowFragment.b.setEnabled(true);
        startActivity(intent);
    }
}