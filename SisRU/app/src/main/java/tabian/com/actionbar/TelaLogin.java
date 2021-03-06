package tabian.com.actionbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class TelaLogin extends AppCompatActivity {

    EditText numero_cartao;
    EditText matricula_siape;
    Button botao_logar;
    ProgressBar loading;
    ImageView logo_restaurante;

    /*MOSTRA OS ITENS DA TELA*/

    public void mostrarItens(){

        numero_cartao.setVisibility(View.VISIBLE);
        matricula_siape.setVisibility(View.VISIBLE);
        botao_logar.setVisibility(View.VISIBLE);
        logo_restaurante.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        (findViewById(R.id.label_matricula_siape)).setVisibility(View.VISIBLE);
        (findViewById(R.id.label_numero_cartao)).setVisibility(View.VISIBLE);

    }

    /*ESCONDE OS ITENS DA TELA*/

    public void esconderItens(){

        numero_cartao.setVisibility(View.GONE);
        matricula_siape.setVisibility(View.GONE);
        botao_logar.setVisibility(View.GONE);
        logo_restaurante.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        (findViewById(R.id.label_matricula_siape)).setVisibility(View.GONE);
        (findViewById(R.id.label_numero_cartao)).setVisibility(View.GONE);

    }

    /*CRIA A TELA (PADRÃO)*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*CARREGA O XML DA TELA*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        SharedPreferences dados = getSharedPreferences("Dados", 0);
        int Logado = dados.getInt("Logado", 0);

        if(Logado == 0){

            /*ATRIBUINDO ELEMENTOS XML PARA VARIAVEIS JAVA*/

            botao_logar = (Button) findViewById(R.id.botao_logar);
            numero_cartao = (EditText) findViewById(R.id.numero_cartao);
            matricula_siape = (EditText) findViewById(R.id.matricula_siape);
            loading = (ProgressBar) findViewById(R.id.loading);
            logo_restaurante = (ImageView) findViewById(R.id.logo_ru);

            loading.setVisibility(View.GONE);

            /*AQUI SUBMETEMOS O LOGIN DO USUÁRIO*/

            botao_logar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    esconderItens();

                    final String s_numero_cartao = numero_cartao.getText().toString();
                    final String s_matricula_siape = matricula_siape.getText().toString();

                    /*AQUI FAZ-SE A VERIFICAÇÃO DA CONEXÃO DE INTERNET DO USUÁRIO*/

                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                    if(isConnected){

                    /*AQUI SE VERIFICA A ULTIMA VERSÃO DO BANCO DE IMAGENS*/

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://monitoriacastanhal.ufpa.br/SistemaRU/acao.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        String[] linhas = response.split("\\r?\\n");

                                        if(linhas[0].contains("True")){

                                            SharedPreferences dados2 = getSharedPreferences("Dados", 0);

                                            SharedPreferences.Editor editor = dados2.edit();

                                            editor.putInt("Logado", 1);

                                            String[] linha_nome = linhas[1].split(":");

                                            if(linha_nome[0].contains("Nome do Usuário")){

                                                editor.putString("Nome_Usuario", linha_nome[1]);

                                            }
                                            editor.putString("Numero_Cartao",s_numero_cartao);
                                            editor.putString("Matricula_Siape",s_matricula_siape);

                                            editor.commit();

                                            Intent intent = new Intent(TelaLogin.this, MainActivity.class);
                                            startActivity(intent);

                                        }else{

                                            alertaBasico("Usuário não existe!");

                                        }

                                        mostrarItens();

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        alertaBasico(error.toString());
                                        mostrarItens();
                                    }
                                }){

                            @Override
                            protected Map<String,String> getParams(){
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("Acao","Logar");
                                params.put("Numero_Cartao", s_numero_cartao);
                                params.put("Matricula_Siape", s_matricula_siape);
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("Content-Type","application/x-www-form-urlencoded");
                                return params;
                            }

                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(TelaLogin.this);
                        requestQueue.add(stringRequest);

                    }else{

                        alertaBasico("Sem Internet");

                    }

                }
            });

        }else{

            Intent intent = new Intent(TelaLogin.this, MainActivity.class);
            startActivity(intent);

        }

    }

    /*ALERTA BÁSICO*/

    public void alertaBasico(String mensagem){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(TelaLogin.this);
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