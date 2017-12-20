package tabian.com.actionbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 4/15/2017.
 */

public class Saldo_Extrato extends AppCompatActivity {

    Button botao_saldo;
    Button botao_extrato;
    ProgressBar loading;

    /*MOSTRA OS ITENS DA TELA*/

    public void mostrarItens(){

        botao_saldo.setVisibility(View.VISIBLE);
        botao_extrato.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);

    }

    /*ESCONDE OS ITENS DA TELA*/

    public void esconderItens(){

        botao_saldo.setVisibility(View.GONE);
        botao_extrato.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saldo_extrato);

        botao_saldo= (Button) findViewById(R.id.botao_saldo);
        botao_extrato= (Button) findViewById(R.id.botao_extrato);
        loading= (ProgressBar) findViewById(R.id.loading);

        botao_saldo.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {

                esconderItens();

                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected) {

                    /*AQUI SE VERIFICA A ULTIMA VERSÃO DO BANCO DE IMAGENS*/

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://monitoriacastanhal.ufpa.br/SistemaRU/acao.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    alertaBasico(response);

                                    mostrarItens();

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    alertaBasico(error.toString());
                                    mostrarItens();
                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() {

                            SharedPreferences dados = getSharedPreferences("Dados", 0);
                            String Numero_Cartao = dados.getString("Numero_Cartao", "0");
                            String Matricula_Siape = dados.getString("Matricula_Siape", "0");

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Acao", "VerificarSaldo");
                            params.put("Numero_Cartao", Numero_Cartao);
                            params.put("Matricula_Siape", Matricula_Siape);
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/x-www-form-urlencoded");
                            return params;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Saldo_Extrato.this);
                    requestQueue.add(stringRequest);

                }

            }

        });

        botao_extrato.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {

                esconderItens();

                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected) {

                    /*AQUI SE VERIFICA A ULTIMA VERSÃO DO BANCO DE IMAGENS*/

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://monitoriacastanhal.ufpa.br/SistemaRU/acao.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    alertaBasico(response);

                                    mostrarItens();

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    alertaBasico(error.toString());
                                    mostrarItens();
                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() {

                            SharedPreferences dados = getSharedPreferences("Dados", 0);
                            String Numero_Cartao = dados.getString("Numero_Cartao", "0");
                            String Matricula_Siape = dados.getString("Matricula_Siape", "0");

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Acao", "VerificarExtrato");
                            params.put("Numero_Cartao", Numero_Cartao);
                            params.put("Matricula_Siape", Matricula_Siape);
                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/x-www-form-urlencoded");
                            return params;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Saldo_Extrato.this);
                    requestQueue.add(stringRequest);

                }

            }

        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:
                        Intent intent0 = new Intent(Saldo_Extrato.this, MainActivity.class);
                        startActivity(intent0);
                        break;

                    case R.id.ic_android:

                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(Saldo_Extrato.this, Cardapio.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(Saldo_Extrato.this, Configuracoes.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:
                        Intent intent4 = new Intent(Saldo_Extrato.this, Avaliacao.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });
    }

    /*ALERTA BÁSICO*/

    public void alertaBasico(String mensagem){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Saldo_Extrato.this);
        builder1.setMessage(mensagem);
        builder1.setCancelable(true);

        builder1.setNegativeButton(
                "Fechar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}
