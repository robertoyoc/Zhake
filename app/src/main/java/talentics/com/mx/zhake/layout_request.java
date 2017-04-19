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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_request);

        final View mBaseView = findViewById(R.id.base_box);
        final View mFruitView = findViewById(R.id.fruta_box);
        final View mTopicView = findViewById(R.id.topic_box);
        final View mCoverView = findViewById(R.id.cover_box);

        Button mAguaButton = (Button) findViewById(R.id.aguaButton);
        mAguaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                precio = precio + 0.5f;
                mBaseView.setVisibility(View.INVISIBLE);
                mFruitView.setVisibility(View.VISIBLE);
            }
        });

        Button mFresasButton = (Button) findViewById(R.id.fresasButton);
        mFresasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                precio = precio + 4f;
                mFruitView.setVisibility(View.INVISIBLE);
                mTopicView.setVisibility(View.VISIBLE);
            }
        });

        Button mSplendaButton = (Button) findViewById(R.id.splendaButton);
        mSplendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                precio = precio + 2.5f;
                mTopicView.setVisibility(View.INVISIBLE);
                mCoverView.setVisibility(View.VISIBLE);
            }
        });
        Button mGranolaButton = (Button) findViewById(R.id.granolaButton);
        mGranolaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                precio = precio + 2f;
                if(sizeMedium) {
                    precio = precio + 35f;
                    callOrder("Mediano,Agua,Fresas,Splenda,Granola"+ String.valueOf(precio));
                }
                else{
                    precio= precio + 55f;
                    callOrder("Grande,Agua,Fresas,Splenda,Granola"+ String.valueOf(precio));
                }
            }
        });


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

    private void callOrder(String mess){
        Intent intent = new Intent(this, layout_login.class);
        intent.putExtra(Intent.EXTRA_TEXT, mess);
        startActivity(intent);
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
