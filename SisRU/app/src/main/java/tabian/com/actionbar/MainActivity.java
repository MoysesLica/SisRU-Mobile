package tabian.com.actionbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    TextView texto_boas_vindas;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.user_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.res_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.nos_24dp);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        texto_boas_vindas = (TextView) findViewById(R.id.texto_boas_vindas);

        SharedPreferences dados = getSharedPreferences("Dados", Context.MODE_PRIVATE);
        String nome_usuario = dados.getString("Nome_Usuario", "Bem-vindo ao SisRU");

        if(!nome_usuario.equals("Bem-vindo ao SisRU")){

            String[] NomeDoUsuario = nome_usuario.split(" ");

            String NomeDoUsuarioParaExibir = NomeDoUsuario[0] + " " + NomeDoUsuario[NomeDoUsuario.length - 1];

            /*LEO, BEM AQUI COLOCA PRA EXIBIR NO TEXTVIEW O NOME DO CARA*/

            try{

                texto_boas_vindas.setText(NomeDoUsuario[0] + " " + NomeDoUsuario[NomeDoUsuario.length - 1]);

            }catch(RuntimeException e){

                alertaBasico(e.getMessage());

            }

        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_arrow:

                        break;

                    case R.id.ic_android:
                        Intent intent1 = new Intent(MainActivity.this, Saldo_Extrato.class);
                        startActivity(intent1);
                        break;

                    case R.id.ic_books:
                        Intent intent2 = new Intent(MainActivity.this, Cardapio.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_center_focus:
                        Intent intent3 = new Intent(MainActivity.this, Configuracoes.class);
                        startActivity(intent3);
                        break;

                    case R.id.ic_backup:
                        Intent intent4 = new Intent(MainActivity.this, Avaliacao.class);
                        startActivity(intent4);
                        break;
                }


                return false;
            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SobreRestaurante());
        adapter.addFragment(new Informacoes());
        adapter.addFragment(new SobreAplicativo());
        viewPager.setAdapter(adapter);
    }

    public void alertaBasico(String mensagem){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
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
