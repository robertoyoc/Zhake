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
    String[] data = new String[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_request);

        mBaseView = findViewById(R.id.base_box);
        mFruitView = findViewById(R.id.fruta_box);
        mTopicView = findViewById(R.id.topic_box);
        mCoverView = findViewById(R.id.cover_box);

        data[0] = "Mediano";

        Button mAguaButton = (Button) findViewById(R.id.aguaButton);
        mAguaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBase("Agua");
                showFruit(0.5f);
            }
        });

        Button mLecheButton = (Button) findViewById(R.id.lecheButton);
        mLecheButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBase("Leche");
                showFruit(3f);
            }
        });
        Button mLightButton = (Button) findViewById(R.id.lightButton);
        mLightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBase("Leche Light");
                showFruit(3f);
            }
        });
        Button mYogurtButton = (Button) findViewById(R.id.yogurtButton);
        mYogurtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBase("Yogurt");
                showFruit(3f);
            }
        });

        Button mFresasButton = (Button) findViewById(R.id.fresasButton);
        mFresasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFruit("Fresas");
                showTopic(4f);
            }
        });
        Button mPlatanoButton = (Button) findViewById(R.id.platanoButton);
        mPlatanoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFruit("Platano");
                showTopic(1.5f);
            }
        });
        Button mBlueberryButton = (Button) findViewById(R.id.blueberryButton);
        mBlueberryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFruit("Blueberry");
                showTopic(12f);
            }
        });

        Button mFrambuesaButton = (Button) findViewById(R.id.frambuesaButton);
        mFrambuesaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFruit("Frambuesa");
                showTopic(9f);
            }
        });
        Button mMangoButton = (Button) findViewById(R.id.mangoButton);
        mMangoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFruit("Mango");
                showTopic(3.5f);
            }
        });
        Button mManzanaButton = (Button) findViewById(R.id.manzanaButton);
        mManzanaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFruit("Manzana");
                showTopic(5f);
            }
        });



        Button mSplendaButton = (Button) findViewById(R.id.splendaButton);
        mSplendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTopic("Splenda");
                showCover(2.5f);
            }
        });

        Button mChocolateButton = (Button) findViewById(R.id.chocolateButton);
        mChocolateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTopic("Chocolate");
                showCover(2.5f);
            }
        });

        Button mMielButton = (Button) findViewById(R.id.mielButton);
        mMielButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTopic("Miel");
                showCover(2.5f);
            }
        });

        Button mAzucarButton = (Button) findViewById(R.id.azucarButton);
        mAzucarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTopic("Azucar");
                showCover(2.5f);
            }
        });

        Button mGranolaButton = (Button) findViewById(R.id.granolaButton);
        mGranolaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCover("Granola");
                setPrice(2f);
                finalPrice();
            }
        });

        Button mCremaButton = (Button) findViewById(R.id.cremaButton);
        mCremaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCover("Crema");
                setPrice(2f);
                finalPrice();
            }
        });

        Button mCremaBatidaButton = (Button) findViewById(R.id.cremabatidaButton);
        mCremaBatidaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCover("Crema Batida");
                setPrice(2f);
                finalPrice();
            }
        });
        Button mChispasButton = (Button) findViewById(R.id.chispasButton);
        mChispasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCover("Chispas");
                setPrice(2f);
                finalPrice();
            }
        });


    }
    private String dataToString(String arr[]){
        return arr[0] +"," + arr[1] +"," + arr[2] +"," + arr[3] +"," + arr[4] +"," + String.valueOf(precio);
    }
    private void finalPrice(){
        if(sizeMedium) {
            setPrice(35f);
        }
        else{
            setPrice(55f);
        }
        callOrder(dataToString(data));
    }
    private void setPrice(float price){
        precio = precio + price;
    }
    private void callOrder(String mess){
        precio = precio + 35f;
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
    private void setBase(String Base){
        data[1] =  Base;
    }
    private void setFruit(String Fruit){
        data[2] = Fruit;
    }
    private void setTopic(String Topic){
        data[3] = Topic;
    }
    private void setCover(String Cover){
        data[4] = Cover;
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
            data[0] = "Grande";
            mSizeView.setText(getString(R.string.big));
        }
        else{
            img.setImageResource(R.drawable.vasomediano);
            sizeMedium= true;
            data[0] = "Mediano";
            mSizeView.setText(getString(R.string.medium));
        }
    }
}
