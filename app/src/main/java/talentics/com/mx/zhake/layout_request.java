package talentics.com.mx.zhake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class layout_request extends AppCompatActivity {

    boolean sizeMedium = true;
    float precio =0;
    View mBaseView;
    View mFruitView;
    View mTopicView;
    View mCoverView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_request);

        mBaseView = findViewById(R.id.base_box);
        mFruitView = findViewById(R.id.fruta_box);
        mTopicView = findViewById(R.id.topic_box);
        mCoverView = findViewById(R.id.cover_box);

        Button mAguaButton = (Button) findViewById(R.id.aguaButton);
        mAguaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFruit(0.5f);
            }
        });

        Button mFresasButton = (Button) findViewById(R.id.fresasButton);
        mFresasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTopic(4f);
            }
        });

        Button mSplendaButton = (Button) findViewById(R.id.splendaButton);
        mSplendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCover(2.5f);
            }
        });
        Button mGranolaButton = (Button) findViewById(R.id.granolaButton);
        mGranolaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                precio = precio + 2f;
                if(sizeMedium) {
                    precio = precio + 35f;
                    callOrderM("Mediano,Agua,Fresas,Splenda,Granola,"+ String.valueOf(precio));
                }
                else{
                    precio= precio + 55f;
                    callOrderB("Grande,Agua,Fresas,Splenda,Granola,"+ String.valueOf(precio));
                }

            }
        });


    }
    private void callOrderM(String mess){
        precio = precio + 35f;
        Intent intent = new Intent(this, layout_order.class);
        intent.putExtra(Intent.EXTRA_TEXT, mess);
        startActivity(intent);
    }
    private void callOrderB(String mess){
        precio = precio + 55f;
        Intent intent = new Intent(this, layout_order.class);
        intent.putExtra(Intent.EXTRA_TEXT, mess);
        startActivity(intent);
    }
    private void showFruit(float prize){
        mBaseView.setVisibility(View.INVISIBLE);
        mFruitView.setVisibility(View.VISIBLE);
        precio = precio + prize;
    }
    private void showTopic(float prize){
        mFruitView.setVisibility(View.INVISIBLE);
        mTopicView.setVisibility(View.VISIBLE);
        precio = precio + prize;
    }
    private void showCover(float prize){
        mTopicView.setVisibility(View.INVISIBLE);
        mCoverView.setVisibility(View.VISIBLE);
        precio = precio + prize;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.smothie_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        changeSize();
        return true;
    }



    private void changeSize(){
        TextView mSizeView = (TextView) findViewById(R.id.sizeView);
        ImageView img = (ImageView) findViewById(R.id.smothie_image);
        if(sizeMedium) {
            img.setImageResource(R.drawable.vasogrande);
            sizeMedium = false;
            mSizeView.setText(getString(R.string.big));
        }
        else{
            img.setImageResource(R.drawable.vasomediano);
            sizeMedium= true;
            mSizeView.setText(getString(R.string.medium));
        }
    }
}
