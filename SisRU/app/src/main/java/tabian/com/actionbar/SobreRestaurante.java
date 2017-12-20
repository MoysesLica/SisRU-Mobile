package tabian.com.actionbar;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SobreRestaurante extends Fragment {
    private static final String TAG = "SobreRestaurante";
    TextView texto_boas_vindas;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sobre_restaurante,container,false);
        texto_boas_vindas = (TextView) view.findViewById(R.id.texto_boas_vindas);

        SharedPreferences dados = this.getActivity().getSharedPreferences("Dados", Context.MODE_PRIVATE);
        String nome_usuario = dados.getString("Nome_Usuario", "Bem-vindo ao SisRU");

        if (!nome_usuario.equals("Bem-vindo ao SisRU")) {

            String[] NomeDoUsuario = nome_usuario.split(" ");

            String NomeDoUsuarioParaExibir = NomeDoUsuario[0] + " " + NomeDoUsuario[NomeDoUsuario.length - 1];

            try {

                texto_boas_vindas.setText("Bem-Vindo " + NomeDoUsuario[0] + " " + NomeDoUsuario[NomeDoUsuario.length - 1]);

            } catch (Exception e) {

                e.printStackTrace();
                alertaBasico(e.getMessage());
                alertaBasico(e.toString());

            }

        }

        return view;
    }

    public void alertaBasico(String mensagem){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity());
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