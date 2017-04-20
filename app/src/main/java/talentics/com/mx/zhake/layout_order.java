package talentics.com.mx.zhake;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class layout_order extends AppCompatActivity {


    Session session = null;
    Context context = null;
    ProgressDialog pDialog = null;
    String message = "";
    String recipient, subject, textMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_order);

        context = this;


        Intent intent = getIntent();

        String data = intent.getStringExtra(intent.EXTRA_TEXT);
        TextView text = (TextView) findViewById(R.id.mensaje);
        String values[] = data.split(",");
        message = "" +
                "Tu pedido fue:\n Un smoothie " + values[0] +
                " con una base de " + values[1] +
                ", seleccionaste " + values[2] +
                " como fruta.\n Añadiste un topic de " + values[3] +
                " y una cobertura de " + values[4] +
                ".\n El total es de $" + values[5];

        text.setText(message);

        FeedReaderDbHelper dbHelper = new FeedReaderDbHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String dir = "https://www.google.com.mx/maps/place/"+ addPlus(user.u_street) + "+" + user.u_number +
                ",+" + addPlus(user.u_col) + "+" + addPlus(user.u_city) + ",+" + user.u_state;

        final String html_message =
                "<h1> Tienes un nuevo pedido </h1> <br><br>" +
                "<p> Smothie  " + values[0] + ". </p>"+
                "<p> Base: " + values[1] + ". </p>"+
                "<p> Fruta(s): " + values[2] + ". </p>"+
                "<p> Topic: " + values[3] + ". </p>"+
                "<p> Cobertura: " + values[4] + ". </p><br>"+
                "<p> Precio a cobrar: " + values[5] + ". </p><br><br>"+
                "<h2> Datos del Cliente: " + " </h2>"+
                "<p> Nombre: <br>" + user.f_name + " " + user.l_name + ". </p>"+
                "<p> Dirección:<br>" + user.u_street + " " + user.u_number +". </p>"+
                "<p>" + user.u_col + ", " + user.u_city + ", " + user.u_state +". </p>"+
                "<p> Código Postal: " + user.u_cp + ". </p><br> <br>"+
                "<p> Buscar en Google Maps: </p>"+
                "<p> <a href=\" " + dir +"\"> " + "Buscar dirección</a> </p>";




        Button mSendOrderButton = (Button) findViewById(R.id.sendOrderButton);
        mSendOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail(html_message);


            }
        });
    }

    public String addPlus(String s){
        String parts[] = s.split(" ");
        String ret = parts[0];
        for(int x=1; x<parts.length; x++){
            ret = ret + "+" + parts[x];
        }
        return ret;
    }
    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRICES = "prices";

    // Common column names
    private static final String KEY_ID = "id";

    // users Table - column names
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_SEX = "sex";
    private static final String KEY_AGE = "age";
    private static final String KEY_STREET = "street";
    private static final String KEY_NUMBER = "number";
    private static final String KEY_COL = "col";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_CP = "cp";

    // prices Table - column names
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private void consulta(SQLiteDatabase db){
        String[] projection = {
                KEY_ID,
                KEY_EMAIL,
                KEY_PASS
        };


        String selection = KEY_EMAIL + " = ?";
        String[] selectionArgs = {  };

        String sortOrder =
                KEY_EMAIL + " ASC";

        Cursor c= null;
        c = db.query(
                TABLE_USERS,                              // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

    }
    private void callOrder(){
        Intent intent = new Intent(this, layout_request.class);
        startActivity(intent);
    }
    private void sendMail(String mess){

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.googlemail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("zhakecompany@gmail.com", "Zhake1234");
            }
        });
        recipient = "zhakeorders@gmail.com";
        subject= "Nuevo pedido";
        textMessage = mess;


        pDialog = ProgressDialog.show(context, "", "Enviando orden...", true);

        if(session != null) {
            RetreiveFeedTask task = new RetreiveFeedTask();
            task.execute();
        } else
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

    }
    private class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("zhakecompany@gmail.com", "Tu robot de pedidos"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");

                Transport.send(message);

            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();

            Toast.makeText(getApplicationContext(), "Orden enviada", Toast.LENGTH_LONG).show();
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            callOrder();
        }


    }
}
