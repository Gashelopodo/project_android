package es.mxcircuit.mxcircuit.listeners;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import es.mxcircuit.mxcircuit.R;
import es.mxcircuit.mxcircuit.activities.ProfileCircuitActivity;
import es.mxcircuit.mxcircuit.api.Response;
import es.mxcircuit.mxcircuit.https.SetReview;
import es.mxcircuit.mxcircuit.models.Circuit;
import es.mxcircuit.mxcircuit.models.Review;
import es.mxcircuit.mxcircuit.utils.Utils;

/**
 * Created by gashelopodo on 24/7/17.
 */

public class ListenerVoteCircuit implements View.OnClickListener {

    private Context context;
    private String[] categories = Review.CATEGORIES;
    private int total_stars = Review.TOTAL_STARS;
    private boolean[] votesclick;
    private int[] votes;
    private boolean control = false;
    ProfileCircuitActivity activity;
    private Circuit circuit;

    public ListenerVoteCircuit(Context context, Circuit circuit) {
        votesclick = new boolean[categories.length];
        votes = new int[categories.length];
        this.context = context;
        this.circuit = circuit;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.valorarButton) {
            // verificamos si es primer click en botón valorar o segundo
            if(!control) {
                Button button = (Button) view;
                Utils.alert(context, context.getResources().getString(R.string.info_vote), context.getResources().getString(R.string.info_title));
                resetStars(view);
            }else{
                Review review = new Review("", "", String.valueOf(circuit.getId()), String.valueOf(votes[0]), String.valueOf(votes[1]), String.valueOf(votes[2]), String.valueOf(votes[3]), String.valueOf(votes[4]), "");
                Log.d("MENSAJE", "REVIEW: " + review.getCircuit_id());
                SetReview setReview = new SetReview(context, this);
                setReview.execute(review);
            }
        }else{
            vote(view);
        }

    }

    public void resetStars(View view){
        Button button = (Button) view;
        activity = (ProfileCircuitActivity) context;
        button.setEnabled(false);
        button.setAlpha(.5f);
        for (String category : categories) {
            for (int i=1; i<=total_stars; i++){
                ImageView star = (ImageView) activity.findViewById(context.getResources().getIdentifier(category+"_"+i,"id",context.getPackageName()));
                star.setImageResource(R.drawable.staroff);
                star.setOnClickListener(this);
            }
        }
    }

    public void vote(View view){

        ImageView star = (ImageView) view;
        LinearLayout parent = (LinearLayout) view.getParent();
        String nameId = context.getResources().getResourceEntryName(star.getId());
        int position = 0;
        switch (parent.getId()){
            case R.id.installation: position = 0; break;
            case R.id.terrain: position = 1; break;
            case R.id.irrigation: position = 2; break;
            case R.id.jumps: position = 3; break;
            case R.id.security: position = 4; break;
        }

        Log.d("MENSAJE", "PARWENT: " + context.getResources().getResourceEntryName(star.getId()));
        String[] numberStar = nameId.split("_");
        String nameCategory = numberStar[0];
        int number = Integer.parseInt(numberStar[1]);

        votesclick[position] = true;
        votes[position] = number;

        for (int i=1; i<=total_stars; i++){
            ImageView star_ = (ImageView) activity.findViewById(context.getResources().getIdentifier(nameCategory+"_"+i,"id",context.getPackageName()));
            if(number >= i) star_.setImageResource(R.drawable.staron);
            else star_.setImageResource(R.drawable.staroff);
        }

        checkVotes();

    }

    public void checkVotes(){
        boolean control = true;
        for(boolean b : votesclick) if(!b) control = b;
        if(control){
            Button button = (Button) activity.findViewById(R.id.valorarButton);
            button.setEnabled(true);
            button.setAlpha(1f);
            this.control = true;
        }
    }

    public void getResponse(Response response){
        if(response.getCode_error() == Response.OK){
            Utils.alert(context, response.getMessage_error(), context.getResources().getString(R.string.info_title));
        }else{
            Utils.alert(context, response.getMessage_error(), context.getResources().getString(R.string.error_title));
        }
        resetData();
    }

    public void resetData(){
        this.votesclick = new boolean[categories.length];
        this.votes = new int[categories.length];
        this.control = false;
        for (String category : categories) {
            for (int i=1; i<=total_stars; i++){
                ImageView star = (ImageView) activity.findViewById(context.getResources().getIdentifier(category+"_"+i,"id",context.getPackageName()));
                star.setOnClickListener(null);
            }
        }
    }

}
